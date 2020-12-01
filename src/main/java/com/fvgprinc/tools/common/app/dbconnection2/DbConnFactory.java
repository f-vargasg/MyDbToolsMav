/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection2;

import com.fvgprinc.tools.common.datalayer.CommonDAL.DbTypes;

/**
 *
 * @author garfi
 */
public class DbConnFactory {

    public static DbConn getDbConn (DbTypes dbTypes)
    {
        DbConn res;
        switch (dbTypes) {
            case MariaDb:
                res = new MariaDbConn();
                break;
            default:
                res = null;
        }
        return res;
    }
            
}
