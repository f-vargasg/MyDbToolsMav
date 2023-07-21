/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.db;

/**
 *
 * @author fvargas
 */
public class NoDataSQLFoundCustEx extends Exception
    {
    public NoDataSQLFoundCustEx ()
        {
        }

    public NoDataSQLFoundCustEx (String message)
        {
        super (message);
        }

    public NoDataSQLFoundCustEx (Throwable cause)
        {
        super (cause);
        }

    public NoDataSQLFoundCustEx (String message, Throwable cause)
        {
        super (message, cause);
        }
    }