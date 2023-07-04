/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

import java.sql.SQLException;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;

/**
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author fvargas
 */
public class MariaDbConnManager extends DbConnManager {

    private MariaDbConn mariaDbConn;

    @Override
    public DbConn createDbConn() throws SQLException, CommonDALExceptions {
        this.mariaDbConn = new MariaDbConn();
        this.mariaDbConn.setDbConnInfo(this.dbConnectionInfo);
        this.mariaDbConn.initConnection();
        return this.mariaDbConn;
              
    }

    @Override
    public DbConn getDbConn() {
        return this.mariaDbConn;
    }

}
