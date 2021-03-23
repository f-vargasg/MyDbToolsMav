/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.app.layers;

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

    public ParamAction(String columName, JavaTypes dataType, Object value) {
        this.columName = columName;
        this.dataType = dataType;
        this.value = value;
    }

    public ParamAction(JavaTypes dataType, Object value) {
        this.dataType = dataType;
        this.value = value;
    }
}
