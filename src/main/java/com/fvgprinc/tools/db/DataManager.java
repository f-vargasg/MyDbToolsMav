package com.fvgprinc.tools.db;

import com.fvgprinc.tools.db.config.ConfigLoader;
import com.fvgprinc.tools.db.config.DbConnectionBe;
import com.fvgprinc.tools.string.MyCommonString;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hsqldb.jdbc.JDBCDataSource;
import org.sqlite.SQLiteDataSource;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

/**
 *
 * @author garfi
 */
public class DataManager {

    private String dataBaseName;

    public String getDatabaseName() {
        return this.dataBaseName;
    }

    private DataSource dataSource;

    // --- NUEVO: Manejo de Contexto de Sesión ---
    private static String sessionUsuarioId;
    private static String sessionIpMaquina;
    private static String sessionIdCiaActual;
    private static String sessionOtherInfo;

    public DataManager(String pDataBaseName) {

        // 1. Intentamos cargar desde el nuevo formato JSON
        boolean cargadoConJson = readConfigurationJson(pDataBaseName);

        // 2. Fallback: Si no se encontró en JSON, intentamos el XML legacy
        if (!cargadoConJson) {
            try {
                readConfigurationDb(pDataBaseName);
            } catch (ConfigurationException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, "Error en configuración legacy", ex);
            }
        }
    }

    /**
     * Nueva rutina para leer la configuración desde el archivo JSON.
     *
     * @return true si logró configurar el DataSource, false de lo contrario.
     */
    private boolean readConfigurationJson(String pDataBaseName) {
        DbConnectionBe config = ConfigLoader.getConnectionById(pDataBaseName);

        if (config == null) {
            return false; // No existe en el JSON
        }
        String dbDriver = config.getDbDriver();
        String dbUrl = config.getDbUrl();
        String dbUserName = config.getDbUsuario();

        if (dbDriver.toUpperCase().contains("SQLITE")) {
            SQLiteDataSource sqliteDataSource = new SQLiteConnectionPoolDataSource();
            sqliteDataSource.setUrl(dbUrl);
            this.dataSource = sqliteDataSource;
        } else if (dbDriver.toUpperCase().contains("HSQLDB")) {
            JDBCDataSource HsqlDbDataSource = new JDBCDataSource();
            HsqlDbDataSource.setURL(dbUrl);
            HsqlDbDataSource.setUser(dbUserName);
            this.dataSource = HsqlDbDataSource;
        } else {
            this.dataBaseName = config.getDbIdConn();
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(dbDriver);
            basicDataSource.setUrl(dbUrl);
            basicDataSource.setUsername(dbUserName);
            basicDataSource.setPassword(config.getDbPassw());

            // Usamos los parámetros de pooling que incluimos en la entidad
            if (config.getDbPoolInicial() != null) {
                basicDataSource.setInitialSize(config.getDbPoolInicial());
            }
            if (config.getDbPoolMax() != null) {
                basicDataSource.setMaxTotal(config.getDbPoolMax());
            }
            this.dataSource = basicDataSource;
        }
        return true;
    }

    private void readConfigurationDb(String pDataBaseName) throws ConfigurationException {

        // read configuracion from configuration file
        //     XMLConfiguration config = new XMLConfiguration(getClass().getResource("/configuracion.xml"));
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder
                = new FileBasedConfigurationBuilder<>(XMLConfiguration.class).configure(params.xml().setFileName("configuracion.xml"));
        XMLConfiguration config = builder.getConfiguration();

        // Acceder a la llave 'connection'
        HierarchicalConfiguration sub = config.configurationAt("connections.connection(0)");
        BasicDataSource basicDataSource;
        List<HierarchicalConfiguration<ImmutableNode>> connections = config.configurationsAt("connections.connection");
        this.dataBaseName = MyCommonString.EMPTYSTR;
        boolean found = false;
        for (Iterator<HierarchicalConfiguration<ImmutableNode>> iterator = connections.iterator(); iterator.hasNext() && !found;) {
            HierarchicalConfiguration next = iterator.next();
            if (next.getString("dbIdConn").compareTo(pDataBaseName) == 0) {
                found = true;
                String dbDriver = next.getString("dbDriver");
                String dbUrl = next.getString("dbUrl");
                String dbUserName = next.getString("dbUsuario");
                if (dbDriver.toUpperCase().contains("SQLITE")) {
                    SQLiteDataSource sqliteDataSource = new SQLiteConnectionPoolDataSource();
                    sqliteDataSource.setUrl(dbUrl);
                    this.dataSource = sqliteDataSource;
                } else if (dbDriver.toUpperCase().contains("HSQLDB")) {
                    JDBCDataSource HsqlDbDataSource = new JDBCDataSource();
                    HsqlDbDataSource.setURL(dbUrl);
                    HsqlDbDataSource.setUser(dbUserName);
                    this.dataSource = HsqlDbDataSource;
                } else {
                    this.dataBaseName = next.getString("dbIdConn");
                    basicDataSource = new BasicDataSource();
                    basicDataSource.setDriverClassName(next.getString("dbDriver"));
                    basicDataSource.setUrl(dbUrl);
                    basicDataSource.setUsername(next.getString("dbUsuario"));
                    basicDataSource.setPassword(next.getString("dbPassw"));
                    basicDataSource.setInitialSize(next.getInt("dbPoolInicial"));
                    basicDataSource.setMaxTotal(next.getInt("dbPoolMax"));
                    this.dataSource = basicDataSource;
                }
            }
        }
    }

    public static void setSession(String usuarioId, String ip, String idCompaniaActual, String otherInfo) {
        sessionUsuarioId = usuarioId;
        sessionIpMaquina = ip;
        sessionIdCiaActual = idCompaniaActual;
        sessionOtherInfo = otherInfo;
    }

    /**
     * @deprecated Este método ha sido reemplazado por {@link #getConnection()}
     */
    @Deprecated
    public Connection getConnectioin() throws SQLException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            System.out.println("Conexión exitosa a la base de datos -> " + java.time.LocalDateTime.now());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage() + java.time.LocalDateTime.now());
            throw e;
        }
        return connection;
    }

    public Connection getConnection() throws SQLException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            if (sessionUsuarioId != null) {
                initMariaDbSession(connection);
            }
            System.out.println("Conexión exitosa a la base de datos -> " + java.time.LocalDateTime.now());

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage() + java.time.LocalDateTime.now());
            throw e;
        }
        return connection;
    }

    private void initMariaDbSession(Connection conn) throws SQLException {
        String sql = "SET @usuario_id = ?, @ip_maquina = ?,  @id_compania_actual=?,  @other_info = ?";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sessionUsuarioId);
            ps.setString(2, sessionIpMaquina);
            ps.setString(3, sessionIdCiaActual);            
            ps.setString(4, sessionOtherInfo);
            ps.execute();
        }
    }

}
