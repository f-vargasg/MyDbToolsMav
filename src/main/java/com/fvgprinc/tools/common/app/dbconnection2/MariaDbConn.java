/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection2;

import com.fvgprinc.tools.common.datalayer.CommonDAL;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;
import com.fvgprinc.tools.common.string.MyCommonString;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author garfi
 */

/*
TODO: Leer los parametros del archivo de configuración
*/
public class MariaDbConn extends DbConn {

    public MariaDbConn() {
        this.dbType = CommonDAL.DbTypes.MariaDb;
        this.ipAddress = "10.25.1.86";
        this.ipPort = "3306";
        this.databaseName = "TESTDB";
        this.user = "admin";
        this.pass = "valerie5250";
        this.autoCommit = true;

    }
    
    public MariaDbConn(String databaseName, String ipAddress, String ipPort, String user, String pass, boolean autoCommit, CommonDAL.DbTypes dbType) {
        super(databaseName, ipAddress, ipPort, user, pass, autoCommit, dbType);
    }
     
    

    @Override
    public String getConnStr() {
        String hostName = this.ipAddress + ":" + this.ipPort;
        String res = CommonDAL.getDbConnStr(this.dbType, hostName, this.databaseName);
        return res;
    }

    @Override
    public Connection openConexion() {
        Connection conn = null;
        try {
            String hostName = this.ipAddress + ":" + this.ipPort;
            conn = CommonDAL.getConnection(this.dbType, hostName, this.databaseName, this.user,
                    this.pass, this.autoCommit, MyCommonString.EMPTYSTR);
        } catch (SQLException ex) {
            Logger.getLogger(MariaDbConn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommonDALExceptions ex) {
            Logger.getLogger(MariaDbConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

}
