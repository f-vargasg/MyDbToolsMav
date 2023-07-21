/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module com.fvgprinc.tools.db {
    exports com.fvgprinc.tools.db;
    
    requires java.logging;
    requires java.sql;
    requires com.fvgprinc.tools.string;
    requires com.fvgprinc.tools.utilities;
    requires org.apache.commons.pool2;
    requires org.mariadb.jdbc;
    requires org.xerial.sqlitejdbc;
    requires org.hsqldb;
}
