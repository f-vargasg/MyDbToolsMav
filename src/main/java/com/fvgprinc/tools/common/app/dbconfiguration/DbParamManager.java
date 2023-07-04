/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconfiguration;

import com.fvgprinc.tools.common.app.configuration.AppParameter;
import com.fvgprinc.tools.common.app.configuration.ParameterManager;

/**
 * sub factory
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author francisco
 */
public class DbParamManager extends  ParameterManager {

    @Override
    public AppParameter getParams() {
        return new AppDbParam();
    }
    
}
