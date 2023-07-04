/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fvgprinc.tools.common.app.dbconnection4;

import com.fvgprinc.tools.common.string.MyCommonString;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
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

    public DataManager(String pDataBaseName) {
        try {
            readConfigurationDb(pDataBaseName);

        } catch (ConfigurationException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readConfigurationDb(String pDataBaseName) throws ConfigurationException {
        // read configuracion from configuration file
        XMLConfiguration config = new XMLConfiguration(getClass().getResource("/configuracion.xml"));

        // Acceder a la llave 'connection'
        HierarchicalConfiguration sub = config.configurationAt("connections.connection(0)");
        BasicDataSource basicDataSource;
        List<HierarchicalConfiguration> connections = config.configurationsAt("connections.connection");
        this.dataBaseName = MyCommonString.EMPTYSTR;
        boolean found = false;
        for (Iterator<HierarchicalConfiguration> iterator = connections.iterator(); iterator.hasNext() && !found;) {
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
                }
                else {
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

    public Connection getConnectioin() throws SQLException {
        Connection connection;
        try {
            connection = dataSource.getConnection();

            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
        return connection;
    }

}
