/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

/**
 *
 * @author francisco
 */
public class DbConnectionInfo {

    private String dbProvider;
    private String dbServerName;
    private String dbName;
    private String dbUser;
    private boolean dbPassEncrypted;
    private String dbPassword;
    private String dbTnsString;  // for special oracle connection to blades

    public String getDbProvider() {
        return dbProvider;
    }

    public void setDbProvider(String dbProvider) {
        this.dbProvider = dbProvider;
    }

    public String getDbServerName() {
        return dbServerName;
    }

    public void setDbServerName(String dbServerName) {
        this.dbServerName = dbServerName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public boolean isDbPassEncrypted() {
        return dbPassEncrypted;
    }

    public void setDbPassEncrypted(boolean dbPassEncrypted) {
        this.dbPassEncrypted = dbPassEncrypted;
    }

    
    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbTnsString() {
        return dbTnsString;
    }

    public void setDbTnsString(String dbTnsString) {
        this.dbTnsString = dbTnsString;
    }

    public DbConnectionInfo(String dbProvider, String dbServerName, String dbName, String dbUser, boolean passwEncrypted, String dbPassword, String dbTnsString) {
        this.dbProvider = dbProvider;
        this.dbServerName = dbServerName;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassEncrypted = passwEncrypted;
        this.dbPassword = dbPassword;
        this.dbTnsString = dbTnsString;
    }
    
    
    
}
