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
public class MySqlDbConnManager extends DbConnManager {

    private MySqlDbConn mySqlDbConn;
/*
    public MySqlDbConn getMySqlDbCom() {
        return mySqlDbConn;
    }

    public void setMySqlDbCom(MySqlDbConn mySqlDbCom) {
        this.mySqlDbConn = mySqlDbCom;
    }
  */  
    
    
    @Override
    public  DbConn createDbConn() throws SQLException, CommonDALExceptions  {
            // aqui se pasan los parametros al objeto y se crea.
            // creo el objeto pero tengo que setearlo
            // e inicializado
            
            /*
            MySqlDbConn.getInstance().setDbConnInfo(this.getDbConnectionInfo());
            MySqlDbConn.getInstance().initConnection();
            return  MySqlDbConn.getInstance();
            * */
            this.mySqlDbConn = new MySqlDbConn();
            this.mySqlDbConn.setDbConnInfo(this.dbConnectionInfo);
            this.mySqlDbConn.initConnection();
            return this.mySqlDbConn;

    }

    @Override
    public DbConn getDbConn() {
        return this.mySqlDbConn;
    }
    
    
    
    

    
    
}
