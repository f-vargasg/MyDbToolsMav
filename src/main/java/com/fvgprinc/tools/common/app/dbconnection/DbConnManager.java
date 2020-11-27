/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

import java.sql.SQLException;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;

/**
 *
 * @author francisco
 */
public abstract class DbConnManager {


    protected DbConnectionInfo dbConnectionInfo;
    

    public DbConnectionInfo getDbConnectionInfo() {
        return dbConnectionInfo;
    }

    public void setDbConnectionInfo(DbConnectionInfo dbConnectionInfo) {
        this.dbConnectionInfo = dbConnectionInfo;
    }
    
    

    public abstract DbConn createDbConn() throws SQLException, CommonDALExceptions ;
    
    public abstract DbConn getDbConn();
}
