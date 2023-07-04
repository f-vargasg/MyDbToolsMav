/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import com.fvgprinc.tools.common.datalayer.CommonDAL;
import com.fvgprinc.tools.common.datalayer.CommonDALExceptions;
import com.fvgprinc.tools.common.utilities.CryptStringFvg;

/**
 * Es el producto abstracto
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author francisco
 */
public abstract class DbConn {

    public static final boolean AUTOCOMMITMODE = true;  //TODO: pasar esto al comonDal    
    private static final String semillaEdv = "SemElecDelValle";
    DbConnectionInfo dbConnInfo;

    public DbConnectionInfo getDbConnInfo() {
        return dbConnInfo;
    }
    protected Connection connection;

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
    }

    public void setDbConnInfo(DbConnectionInfo dbConnectionInfo) {
        this.dbConnInfo = dbConnectionInfo;
    }

    public void initConnection() throws SQLException, CommonDALExceptions {
//        try {
        // get dbParameters
        CommonDAL.DbTypes dbType = CommonDAL.getProvider(this.getDbConnInfo().getDbProvider());
        String decryptDbPassword = this.getDbConnInfo().getDbPassword();
        if (this.getDbConnInfo().isDbPassEncrypted()) {
            CryptStringFvg cryptStringFvgI;
            try {
                cryptStringFvgI = new CryptStringFvg(semillaEdv);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommonDALExceptions("Error Encriptando Password: Exception -> InvalidKeyException");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommonDALExceptions("Error Encriptando Password: Exception -> NoSuchAlgorithmException");                
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommonDALExceptions("Error Encriptando Password: Exception -> NoSuchPaddingException");                                
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommonDALExceptions("Error Encriptando Password: Exception -> InvalidKeySpecException");                                                
            }
            String encryptPassword = this.getDbConnInfo().getDbPassword();
            try {
                decryptDbPassword = cryptStringFvgI.decryptBase64(encryptPassword);
            } catch (Exception ex) {
                Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommonDALExceptions("Error Encriptando Password");
            }
        }
        this.connection = CommonDAL.getConnection(dbType,
                this.getDbConnInfo().getDbServerName(),
                this.getDbConnInfo().getDbName(),
                this.getDbConnInfo().getDbUser(),
                decryptDbPassword, AUTOCOMMITMODE,
                this.getDbConnInfo().getDbTnsString());
        /*            
         } catch (SQLException ex) {
         Logger.getLogger(HSqlDbConn.class.getName()).log(Level.SEVERE, null, ex);
         } catch (CommonDALExceptions ex) {
         Logger.getLogger(HSqlDbConn.class.getName()).log(Level.SEVERE, null, ex);
         }
         * */
    }
}
