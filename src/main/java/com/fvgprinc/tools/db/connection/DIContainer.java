/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fvgprinc.tools.db.connection;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author garfi
 */
public class DIContainer {
    private static DIContainer SINGLE_INSTANCE = null;
    
    private DIContainer() { }

    public static DIContainer getInstance () {
        synchronized (DIContainer.class) {
            if (SINGLE_INSTANCE == null) {
                SINGLE_INSTANCE = new DIContainer();
            }
        }
        return SINGLE_INSTANCE;
    }
    
    private static final  Map<String, DataManager> dataManagers = new HashMap<>();

    public static void registerDataManager(String databaseName) {
        DataManager dataManager = new DataManager(databaseName);
        dataManagers.put(databaseName, dataManager);
    }

    public DataManager getDataManager(String databaseName) {
        return dataManagers.get(databaseName);
    }
}
