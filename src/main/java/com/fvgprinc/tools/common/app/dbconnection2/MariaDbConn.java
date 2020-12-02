/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection2;

import com.fvgprinc.tools.common.datalayer.CommonDAL;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;
import com.fvgprinc.tools.common.string.MyCommonString;
import java.sql.SQLException;

/**
 *
 * @author garfi
 */

/*
TODO: Leer los parametros del archivo de configuración
*/
public class MariaDbConn extends DbConn {
    
     public MariaDbConn() throws SQLException, CommonDALExceptions {
         
         super("TESTDB", "10.25.1.86", "3306", "root","valerie5250",true, CommonDAL.DbTypes.MariaDb);
         /*
        this.dbType = CommonDAL.DbTypes.MariaDb;
        this.ipAddress = "10.25.1.86";
        this.ipPort = "3306";
        this.databaseName = "TESTDB";
        this.user = "root";
        this.pass = "valerie5250";
        this.autoCommit = true; */
         
        String hostName = this.ipAddress + ":" + this.ipPort;
         this.conn = CommonDAL.getConnection(this.dbType, hostName, this.databaseName, this.user,
                       this.pass, this.autoCommit, MyCommonString.EMPTYSTR);

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
/*
    @Override
    public Connection openConexion() {
        
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
*/

    
}
