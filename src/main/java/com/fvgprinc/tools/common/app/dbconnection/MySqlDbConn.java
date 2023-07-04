/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

/**
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author francisco
 */
class MySqlDbConn extends DbConn {
    /*
     private static MySqlDbConn instance;

     static {
     instance = new MySqlDbConn();
     }
     * 
     * */

    public MySqlDbConn()  {
        this.connection = null;
    }
    /*
     public static MySqlDbConn getInstance() {
     return instance;
     }
     * */
}
