/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconfiguration;

import com.fvgprinc.tools.common.app.configuration.AppParameter;
import java.net.URL;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import com.fvgprinc.tools.common.app.configuration.source.ConfigFile;
import com.fvgprinc.tools.common.app.configuration.source.ConfigFileFactory;
import com.fvgprinc.tools.common.app.dbconnection.DbConnectionInfo;

/**
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author francisco
 */
public class AppDbParam extends AppParameter {

    private DbConnectionInfo[] dbConnInfo;
//    protected Properties properties;
    protected XMLConfiguration myConfig;

    public XMLConfiguration getMyConfig() {
        return myConfig;
    }

    public DbConnectionInfo[] getDbConnInfo() {
        return dbConnInfo;
    }

    @Override
    public void readParameters() throws ConfigurationException {
            //Object coll;
            HierarchicalConfiguration sub;
            //int defaConn;
            //XMLConfigFileManager xmlConfigFileManager = new XMLConfigFileManager();
            ConfigFile configFile = ConfigFileFactory.getInstance().getConfigFileManager().getConfigurationFile();
            //ConfigFile configurationFile = XMLConfigFileManager.getInstance().getConfigurationFile();
            URL urlFile;
            urlFile = configFile.readURLFile();
            this.myConfig = new XMLConfiguration(urlFile);
            String[] activeConns = myConfig.getStringArray("activeConnections");
            dbConnInfo = new DbConnectionInfo[activeConns.length];
            for (int i = 0; i < activeConns.length; i++) {
                sub = myConfig.configurationAt("connections.connection(" + activeConns[i] + ")");
                String scrap = sub.getString("dbPassword[@encrypted]");
                boolean passwordEncrypted = false;
                if (scrap != null) {
                    passwordEncrypted = (scrap.compareTo("S") == 0);
                }

                dbConnInfo[i] = new DbConnectionInfo(sub.getString("dbProvider"),
                        sub.getString("dbServerName"),
                        sub.getString("dbName"),
                        sub.getString("dbUser"),
                        passwordEncrypted,
                        sub.getString("dbPassword"),
                        sub.getString("tnsConnStr"));

            }
            //coll= myConfig.getProperty("connections.connection.dbProvider");

    }
}
