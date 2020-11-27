/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.datalayer;

/**
 *
 * @author fvargas
 */
public class CommonDALExceptions extends Exception {

    String mistake;

    // Default constructor
    public CommonDALExceptions() {
        super();
        mistake = "unknown";
    }

    // Constructor receives some kind of error
    public CommonDALExceptions(String mistake) {
        this.mistake = mistake;
    }

    // public method, callable by exception catcher
    public String getError() {
        return mistake;
    }
}
