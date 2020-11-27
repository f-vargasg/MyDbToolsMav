/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

import java.sql.SQLException;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;

/**
 *
 * @author fvargas
 */
public class OracleDbConnManager extends DbConnManager {

    private OracleConn oracleConn;

    @Override
    public DbConn createDbConn() throws SQLException, CommonDALExceptions  {
        this.oracleConn =new OracleConn();
        this.oracleConn.setDbConnInfo(this.dbConnectionInfo);
        this.oracleConn.initConnection();
        return this.oracleConn;
    }

    @Override
    public DbConn getDbConn() {
        return this.oracleConn;
    }
    
}
