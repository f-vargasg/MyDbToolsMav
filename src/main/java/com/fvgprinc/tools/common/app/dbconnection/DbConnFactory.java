package com.fvgprinc.tools.common.app.dbconnection;

import java.sql.SQLException;
import com.fvgprinc.tools.common.datalayer.CommonDAL;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This combine Abstract Factory with singleton. Note: The first time all
 * connections specified in the configuration file are open.
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author francisco
 */
public class DbConnFactory {

    private static DbConnFactory instance;
    //private static final String MSGERR = "Ocurrio una excepcion instanciado el objeto: GlobalConnection";

    static {
        try {
            instance = new DbConnFactory();
        } catch (SQLException ex) {
            throw new RuntimeException("SQL Exception " + ex.getMessage(), ex);
        } catch (CommonDALExceptions ex) {
            throw new RuntimeException("CommonDALExceptions " + ex.getMessage(), ex);
        }
        /*catch (Exception ex) {
            Logger.getLogger(DbConnFactory.class.getName()).log(Level.SEVERE, null, ex);
        } */
    }
    private DbConnManager[] dbConnManager;

    private DbConnFactory() throws SQLException, CommonDALExceptions {

        /**
         * NOTA ESTO SE HACE EN EL ENTRY POINT, LA APLICACION ES LA RESPONSABLE
         * DE CARGASR PARAMETROS ParameterManager parameterManager =
         * DbParamFactory.getInstance().getParameterMananger(); AppParameter
         * appParameter = parameterManager.getParams();
         * appParameter.readParameters(); // Esta responsabilidad es de la
         * aplicacion en el entryPoint Esta a clase asume que la información de
         * las conexiones ya fue invocada por otra clase que la leyo del archivo
         * de configuracion
        *
         */
        /**
         * ParameterManager parameterManager =
         * DbParamFactory.getInstance().getParameterMananger(); AppParameter
         * appParameter = parameterManager.getParams();
        *
         */
        int cantConnections = DbInfoDbConnectionsApp.getInstance().getNumConnections();
        int index = 0;
        dbConnManager = new DbConnManager[cantConnections];

        for (DbConnectionInfo dbConnectionInfo : DbInfoDbConnectionsApp.getInstance().getLstConnectionInfo()) {
            dbConnManager[index++] = initSpecificConn(dbConnectionInfo);
        }

    }

    private DbConnManager initSpecificConn(DbConnectionInfo pDbConnectionInfo) throws SQLException, CommonDALExceptions {
        DbConnManager res = null;
        CommonDAL.DbTypes dbType = CommonDAL.getProvider(pDbConnectionInfo.getDbProvider());
        switch (dbType) {
            case MySQL:
                res = new MySqlDbConnManager();
                //res.setDbConnectionInfo(pDbConnectionInfo);
                //res.createDbConn();
                break;
            case HSQLDBServer:
            case HSQLDB:
                res = new HSqlDbConnManager();
                //res.setDbConnectionInfo(pDbConnectionInfo);
                //res.createDbConn();
                break;
            case OracleThin:
                res = new OracleDbConnManager();
                //res.setDbConnectionInfo(pDbConnectionInfo);
                // res.createDbConn();
                break;
            case MariaDb:
                res = new MariaDbConnManager();
            break;
        }
        if (res != null) {
                res.setDbConnectionInfo(pDbConnectionInfo);
                res.createDbConn();
        }
        return res;
    }

    public DbConnManager[] createDbConnManager() {
        return dbConnManager;
    }

    public static DbConnFactory getInstance() {
        return instance;
    }
}
