package com.fvgprinc.tools.db;

import java.util.ArrayList;

/**
 *
 * @author garfi
 */
public class ResultInsert {
     private int affectedRows;
     private ArrayList<Long> generatedKeys;

    public ResultInsert() {
    }

    public ResultInsert(int affectedRows, ArrayList<Long> generatedKeys) {
        this.affectedRows = affectedRows;
        this.generatedKeys = generatedKeys;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }

    public ArrayList<Long> getGeneratedKeys() {
        return generatedKeys;
    }

    public void setGeneratedKeys(ArrayList<Long> generatedKeys) {
        this.generatedKeys = generatedKeys;
    }
     
}
