/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection2;

// import com.fvgprinc.tools.common.datalayer;
import com.fvgprinc.tools.common.datalayer.CommonDAL.DbTypes;
import java.sql.Connection;

/**
 *
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author garfi
 */
public abstract class DbConn {

    protected Connection conn;
    protected String databaseName;
    protected String ipAddress;
    protected String ipPort;
    protected String user;
    protected String pass;
    protected boolean autoCommit;
    protected DbTypes dbType;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return ipPort;
    }

    public void setPort(String port) {
        this.ipPort = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public DbConn() {
    }

    public DbConn(String databaseName, String ipAddress, String ipPort, String user, String pass, boolean autoCommit, DbTypes dbType) {
        this.databaseName = databaseName;
        this.ipAddress = ipAddress;
        this.ipPort = ipPort;
        this.user = user;
        this.pass = pass;
        this.autoCommit = autoCommit;
        this.dbType = dbType;
    }

    public abstract String getConnStr();

    public Connection getConnection() {
        return this.conn;
    }

}
