
package com.fvgprinc.tools.db;

/**
 *
 * @author Ryzen9-Gaming
 */

public class ParamStoredProc {
    private String name;
    private ParamAction.JavaTypes type;
    private boolean outParam;
    private int size;
    private Object value; 

    public ParamStoredProc() {
    }

    public ParamStoredProc(String name, ParamAction.JavaTypes paramType, boolean outParam, int size, Object value) {
        this.name = name;
        this.type = paramType;
        this.outParam = outParam;
        this.size = size;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParamAction.JavaTypes getType() {
        return type;
    }

    public void setType(ParamAction.JavaTypes paramType) {
        this.type = paramType;
    }

    public boolean isOutParam() {
        return outParam;
    }

    public void setOutParam(boolean outParam) {
        this.outParam = outParam;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
        
}
