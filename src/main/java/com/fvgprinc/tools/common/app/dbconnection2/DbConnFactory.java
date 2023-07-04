/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection2;

import com.fvgprinc.tools.common.datalayer.CommonDAL.DbTypes;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;
import java.sql.SQLException;

/**
 *
 * @deprecated Mejor utilizar DIContainer y DataManager
 * @author garfi
 */
public class DbConnFactory {

    private static DbConn dbConn;

    public static DbConn getDbConn(DbTypes dbTypes) throws SQLException, CommonDALExceptions {
        if (dbConn != null) {
            return dbConn;
        }
        switch (dbTypes) {
            case MariaDb:
                dbConn = new MariaDbConn();
                break;
            default:
                dbConn = null;
        }
        return dbConn;
    }

    /*
    public static DbConn getDbConn() throws SQLException, CommonDALExceptions {
        DbTypes dbTypes;

        /*
        Ir a la configuración y leer el tipo de Base de datos
        y la configuración de cada DB
        
     */
 /*
        
        if (dbConn != null) {
            return dbConn;
        }
        switch (dbTypes) {
            case MariaDb:
                dbConn = new MariaDbConn();
                break;
            default:
                dbConn = null;
        }
        return dbConn;
    }
     */
}
