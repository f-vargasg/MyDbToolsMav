/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.datalayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import com.fvgprinc.tools.common.utilities.MyPropertyLoader;
import com.fvgprinc.tools.common.utilities.PropertyLoader;

public class CommonDAL {

    public static enum DbTypes {

        OracleThin, MySQL, MsAccessDB, HSQLDB,
        HSQLDBServer, OracleTNS, MariaDb, UndefinedDB
    }
    // provider names for configuration file
    public static final String NAMHSQLDB = "hsqldb";
    public static final String NAMHSQLDBSERVER = "hsqldbserver";
    public static final String NAMMYSQL = "mysql";
    public static final String NAMMARIADB = "mariadb";
    public static final String NAMORACLE = "oracle";
    public static final String NAMORACLETNS = "oracleTns";

    public static java.sql.Date ConvertString2SqlDate(String pStrDate) throws ParseException {

        java.util.Date parsedUtilDate;
        // String stringDate = "2009-06-15 19:44:58";
        String stringDate = pStrDate;

        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        parsedUtilDate = formater.parse(stringDate);

        return new java.sql.Date(parsedUtilDate.getTime());
    }

    public static String getDbClassName(DbTypes ptype) {
        String wres = "";
        switch (ptype) {
            case MySQL:
                wres = "com.mysql.jdbc.Driver";
                break;
            case OracleThin:
            case OracleTNS:
                wres = "oracle.jdbc.OracleDriver";
                break;
            case MsAccessDB:
                wres = "sun.jdbc.odbc.JdbcOdbcDriver";
                break;
            case HSQLDB:
                wres = "org.hsqldb.jdbcDriver";
                break;
            case HSQLDBServer:
                wres = "org.hsqldb.jdbc.JDBCDriver";
                break;
            case MariaDb:
                wres = "org.mariadb.jdbc.Driver";
                break;
            default:
                break;
        }
        return wres;

    }

    /**
     *
     * @param ptype
     * @param pHostName (base path for HSQLDB withoud / final)
     * @param pDataBaseName
     * @param tnsDescriptor (only has sense for Oracle when connected to blades)
     * @return
     * @throws CommonDALExceptions
     */
    /*
     public static String getDbConnStr(DbTypes ptype, String pHostName,
     String pDataBaseName, String tnsDescriptor) throws CommonDALExceptions {
     */
    public static String getDbConnStr(DbTypes ptype, String pHostName,
            String pDataBaseName) throws CommonDALExceptions {
        String wres = "";

        switch (ptype) {
            case MySQL:
                wres = "jdbc:mysql://" + pHostName + "/".trim() + pDataBaseName.trim()
                        + "?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
                break;
            case OracleThin:
                wres = "jdbc:oracle:thin:@//" + pHostName + ":1521/".trim() + pDataBaseName.trim();
                break;
            case MsAccessDB:
                wres = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + pDataBaseName.trim() + ";DriverID=22;READONLY=true}";
                break;

            case HSQLDB:  // pHostName es el path, pDataBaseName is the DataFileName
                // wres = "jdbc:hsqldb:" + pDataBaseName + ";shutdown=true";
                wres = "jdbc:hsqldb:" + pHostName + "/" + pDataBaseName;
                break;
            case HSQLDBServer:  // pHostName es el path, pDataBaseName is the DataFileName
                // wres = "jdbc:hsqldb:" + pDataBaseName + ";shutdown=true";
                wres = "jdbc:hsqldb:hsql://" + pHostName + "/" + pDataBaseName;
                break;
            case OracleTNS:/*
                 if (tnsDescriptor.length() == 0) {
                 throw new CommonDALExceptions("OracleThinTNS, bad defined");
                 }  */

                wres = "jdbc:oracle:thin:" + pHostName;
                break;
            case MariaDb:
                wres = "jdbc:mariadb://" + pHostName + "/".trim() + pDataBaseName.trim();
                break;
            default:
                break;
        }
        return wres;
    }

    public static String getDbPoolConnStr(DbTypes ptype, 
                                       String pHostName,
                                       String pDataBaseName,
                                       String pUserName,
                                       String pPassword,
                                       int pMaxPoolSize) throws CommonDALExceptions {
        String wres = "";

        switch (ptype) {
            case MySQL:
                wres = "jdbc:mysql://" + pHostName + "/".trim() + pDataBaseName.trim()
                        + "?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
                break;
            case OracleThin:
                wres = "jdbc:oracle:thin:@//" + pHostName + ":1521/".trim() + pDataBaseName.trim();
                break;
            case MsAccessDB:
                wres = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + pDataBaseName.trim() + ";DriverID=22;READONLY=true}";
                break;

            case HSQLDB:  // pHostName es el path, pDataBaseName is the DataFileName
                // wres = "jdbc:hsqldb:" + pDataBaseName + ";shutdown=true";
                wres = "jdbc:hsqldb:" + pHostName + "/" + pDataBaseName;
                break;
            case HSQLDBServer:  // pHostName es el path, pDataBaseName is the DataFileName
                // wres = "jdbc:hsqldb:" + pDataBaseName + ";shutdown=true";
                wres = "jdbc:hsqldb:hsql://" + pHostName + "/" + pDataBaseName;
                break;
            case OracleTNS:/*
                 if (tnsDescriptor.length() == 0) {
                 throw new CommonDALExceptions("OracleThinTNS, bad defined");
                 }  */

                wres = "jdbc:oracle:thin:" + pHostName;
                break;
            case MariaDb:
                wres = "jdbc:mariadb://" + pHostName + "/".trim() + pDataBaseName.trim() + "?" +
                        "user=" + pUserName +"&"+ "password=" + pPassword +
                        "maxPoolSize="+ Integer.toString(pMaxPoolSize) ;
                break;
            default:
                break;
        }
        return wres;
    }

    /**
     *
     * @param propFileName
     * @return Return a property with the next structure dbServerName=""
     * dbName="" dbUser="" dbPassword=""
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Properties readDbPropertiesFromFile(String propName) throws FileNotFoundException, IOException {
        new MyPropertyLoader();
        Properties properties;

        properties = PropertyLoader.loadProperties(propName);

        return properties;
    }

    public static Properties readDBProperties(String puser, String ppassword) {
        Properties p = new Properties();

        p.put("user", puser);
        p.put("password", ppassword);
        return p;
    }

    public static DbTypes getProvider(String provName) {
        DbTypes res = DbTypes.HSQLDB;

        if (provName.compareTo(NAMHSQLDB) == 0) {
            res = DbTypes.HSQLDB;
        } else {
            if (provName.compareTo(NAMHSQLDBSERVER) == 0) {
                res = DbTypes.HSQLDBServer;
            } else {
                if (provName.compareTo(NAMMYSQL) == 0) {
                    res = DbTypes.MySQL;
                } else {
                    if (provName.compareTo(NAMORACLE) == 0) {
                        res = DbTypes.OracleThin;
                    } else {
                        if (provName.compareTo(NAMMARIADB) == 0) {
                            res = DbTypes.MariaDb;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static Connection getConnection(DbTypes ptype, String pHostName,
            String pDataBaseName, String puser,
            String ppassword, boolean activeAutoCommit,
            String tnsString)
            throws SQLException, CommonDALExceptions {
        Connection res;
        try {
            Class.forName(getDbClassName(ptype));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // String dbConnStr = getDbConnStr(ptype, pHostName, pDataBaseName, tnsString);
        String dbConnStr = getDbConnStr(ptype, pHostName, pDataBaseName);
        Properties p = readDBProperties(puser, ppassword);
        res = DriverManager.getConnection(dbConnStr, p);
        res.setAutoCommit(activeAutoCommit); // works transactional
        return res;
    }

    public static void ShutDown(Connection conn, DbTypes pType)
            throws SQLException {
        Statement stm = conn.createStatement();

        // db writes out to files and performs clean shuts down
        // otherwise there will be an unclean shutdown
        // when program ends
        if (pType == DbTypes.HSQLDB) {
            stm.execute("SHUTDOWN COMPACT");
//            conn.close();
        }
    }

    public static void ExecuteDBModify(String sSql, DbTypes pType,
            Connection conn) throws SQLException {
        Statement stm;

        stm = conn.createStatement();
        stm.executeUpdate(sSql);
        stm.close();

        // Hsqldb require this sentences to operate
        //ShutDown(conn, pType);
    }
}
