/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconnection;

import java.util.ArrayList;
import com.fvgprinc.tools.common.app.configuration.AppDbParam;
import com.fvgprinc.tools.common.app.configuration.AppParameter;
import com.fvgprinc.tools.common.app.configuration.DbParamFactory;
import com.fvgprinc.tools.common.app.configuration.ParameterManager;

/**
 * Se encarga de ser inicializada y obtener la informacion de todas las
 * conexiones de la aplicaicon
 *
 * @author fvargas
 */
public class DbInfoDbConnectionsApp {

    private static DbInfoDbConnectionsApp instance;
    // definir extra atributos	

    static {
        try {
            instance = new DbInfoDbConnectionsApp();
        }  catch (Exception ex) {
            throw new RuntimeException("Ocurrio una exception: " + 
                    (ex.getMessage() == null ? "Error no definido" : ex.getMessage()), ex);
            /*
             JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Ocurrio una exception", JOptionPane.ERROR_MESSAGE);
                    * */
        }
    }
    // class atributtes
   private ArrayList<DbConnectionInfo> lstConnectionInfo;

    // Private constructor
    private DbInfoDbConnectionsApp() throws Exception {
        ParameterManager parameterManager = DbParamFactory.getInstance().getParameterMananger();
        AppParameter appParameter = parameterManager.getParams();
        appParameter.readParameters();
        int cantConnections = ((AppDbParam) appParameter).getDbConnInfo().length;
        lstConnectionInfo = new ArrayList<DbConnectionInfo>();
        for (int i = 0; i < cantConnections; i++) {
            DbConnectionInfo dbConnectionInfo =
                    new DbConnectionInfo(((AppDbParam) appParameter).getDbConnInfo()[i].getDbProvider(),
                    ((AppDbParam) appParameter).getDbConnInfo()[i].getDbServerName(),
                    ((AppDbParam) appParameter).getDbConnInfo()[i].getDbName(),
                    ((AppDbParam) appParameter).getDbConnInfo()[i].getDbUser(),
                    ((AppDbParam) appParameter).getDbConnInfo()[i].isDbPassEncrypted(),
                    ((AppDbParam) appParameter).getDbConnInfo()[i].getDbPassword(),
                    ((AppDbParam) appParameter).getDbConnInfo()[i].getDbTnsString());
            this.lstConnectionInfo.add(dbConnectionInfo);

        }
    }

    public ArrayList<DbConnectionInfo> getLstConnectionInfo() {
        return this.lstConnectionInfo;
    }
    
    public int getNumConnections() {
        return this.lstConnectionInfo.size();
    }

    public static DbInfoDbConnectionsApp getInstance() {
        return instance;
    }
}
