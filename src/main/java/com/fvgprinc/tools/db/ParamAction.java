/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.db;

import com.fvgprinc.tools.string.MyCommonString;
import java.util.ArrayList;


/**
 *
 * @author fvargas
 */
public class ParamAction {

    public enum JavaTypes {

        STRING, INTEGER, DOUBLE, DATEJAVASQL, TIMESTAMPJAVASQL, LONG, SHORT
    }
    private String columName;

    /**
     * Get the value of columName
     *
     * @return the value of columName
     */
    public String getColumName() {
        return columName;
    }

    /**
     * Set the value of columName
     *
     * @param columName new value of columName
     */
    public void setColumName(String columName) {
        this.columName = columName;
    }
    private JavaTypes dataType;

    /**
     * Get the value of dataType
     *
     * @return the value of dataType
     */
    public JavaTypes getDataType() {
        return dataType;
    }

    /**
     * Set the value of dataType
     *
     * @param dataType new value of dataType
     */
    public void setDataType(JavaTypes dataType) {
        this.dataType = dataType;
    }
    private Object value;

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    private boolean cmpWithLike;

    /**
     * Get the value of cmpWithLike
     *
     * @return the value of cmpWithLike
     */
    public boolean isCmpWithLike() {
        return cmpWithLike;
    }

    /**
     * Set the value of cmpWithLike
     *
     * @param cmpWithLike new value of cmpWithLike
     */
    public void setCmpWithLike(boolean cmpWithLike) {
        this.cmpWithLike = cmpWithLike;
    }

    public ParamAction(String columName, JavaTypes dataType, Object value, boolean cmpLike) {
        this.columName = columName;
        this.dataType = dataType;
        this.value = value;
        this.cmpWithLike = cmpLike;
    }

    public ParamAction(String columName, JavaTypes dataType, Object value) {
        this.columName = columName;
        this.dataType = dataType;
        this.value = value;
        this.cmpWithLike = false;
    }

    public ParamAction(JavaTypes dataType, Object value) {
        this.columName = MyCommonString.EMPTYSTR;
        this.dataType = dataType;
        this.value = value;
        this.cmpWithLike = false;
    }

    public String buildCond() {
        String res;

        res = this.columName + " = " + "?";
        if (this.dataType == JavaTypes.STRING) {
            res = this.columName + (this.cmpWithLike ? " LIKE " : " = ") + "?";
        }

        return res;
    }
    /**
     * Dada una lista de parametros de tipo ParamAction, retorna una condición 
     * para una sentencia sql
     * Los objetos ParamAction de la lista DEBEN de tener el nombre la columna
     * OJO: Retorna una hilarera con el tag "?", para luego ser aplicado a las 
     * operaciones de JDBC (ie usando PreparedStatemet)
     * @param pValues
     * @return 
     */
    public static  String queryCond(ArrayList<ParamAction> pValues) {
        String res = "";
        boolean ft = true;
        for (int i = 0; i < pValues.size(); i++) {
            // res += ((!ft ? " and " : MyCommonString.EMPTYSTR) + pValues.get(i).getColumName() + " =  ?");
            String cond = pValues.get(i).buildCond();
            if (cond.compareTo(MyCommonString.EMPTYSTR) != 0) {
                res += ((!ft ? " and " : MyCommonString.EMPTYSTR) + cond);
            }
            ft = false;
        }
        return res;
    }
}
