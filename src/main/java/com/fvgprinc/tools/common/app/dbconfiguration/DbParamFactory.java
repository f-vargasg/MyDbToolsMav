/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.dbconfiguration;

import com.fvgprinc.tools.common.app.configuration.AppConfigUtils;
import com.fvgprinc.tools.common.app.configuration.ParameterManager;

/**
 *
 * @author francisco
 */
public class DbParamFactory {

    private static final DbParamFactory instance;

    static {
        instance = new DbParamFactory();
    }
    ParameterManager parameterMananger;

    private DbParamFactory() {
        parameterMananger = initSpecificParameter(AppConfigUtils.DEFAULTCONFIGFILETYPE);
    }

    private ParameterManager initSpecificParameter(AppConfigUtils.AppConfigFileTypes pTypeFile) {
        ParameterManager res;

        switch (pTypeFile) {
            case XMLFile:
                res = new DbParamManager();
                break;
            default:
                res = null;
                break;

        }
        return res;

    }

    public ParameterManager getParameterMananger() {
        return parameterMananger;
    }
    
    public static DbParamFactory getInstance () {
        return instance;
    }
}
