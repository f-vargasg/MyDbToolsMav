/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fvgprinc.tools.db.metadata;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ryzen9-Gaming
 */
public class MariaDbSqlDbObjectFactory extends  DbObjectFactory{

    @Override
    public DbTable createDbTable() {
        return new MariaDbTable();
    }

    @Override
    public DbTable createDbTable(String pSchemaName, String pTableName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<String> listTablesByEsquema(String pSchemaName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
