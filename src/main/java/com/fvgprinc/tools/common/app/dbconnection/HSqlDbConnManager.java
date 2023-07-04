/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

import java.sql.SQLException;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;

/**
 
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author francisco
 */
public class HSqlDbConnManager extends DbConnManager {

    private HSqlDbConn hSqlDbConn;

    @Override
    public DbConn createDbConn() throws SQLException, CommonDALExceptions {
        // aqui se pasan los parametros al objeto y se crea.
        // creo el objeto pero tengo que setearlo
        // e inicializado

        /*
         MySqlDbConn.getInstance().setDbConnInfo(this.getDbConnectionInfo());
         MySqlDbConn.getInstance().initConnection();
         return  MySqlDbConn.getInstance();
         * */
        this.hSqlDbConn = new HSqlDbConn();
        this.hSqlDbConn.setDbConnInfo(this.dbConnectionInfo);
        this.hSqlDbConn.initConnection();
        return this.hSqlDbConn;
    }

    @Override
    public DbConn getDbConn() {
        return this.hSqlDbConn;
    }
}
