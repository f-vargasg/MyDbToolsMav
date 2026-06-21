
package com.fvgprinc.tools.db.metadata;

import java.util.ArrayList;

/**
 *
 * @author Ryzen9-Gaming
 */
public class DbConstraint {
     public enum ConstraintType {

        CHECK_CONST, FK_CONST, PK_CONST, UK_CONST
    }
    protected ArrayList<DbColumn> dbColumns;

    public ArrayList<DbColumn> getDbColumns() {
        return dbColumns;
    }

    public void setDbColumns(ArrayList<DbColumn> dbColumns) {
        this.dbColumns = dbColumns;
    }

    protected ConstraintType constraintType;

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }
    
    protected String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    
    
    protected String rConstraintOwner;

    public String getrConstraintOwner() {
        return rConstraintOwner;
    }

    public void setrConstraintOwner(String rConstraintOwner) {
        this.rConstraintOwner = rConstraintOwner;
    }
    
    // the reference constraint (when 
    // the constraint is a FK 
    // When the constraint not is FK, 
    // the value es empty
    protected String rConstraint;

    public String getrConstraint() {
        return rConstraint;
    }

    public void setrConstraint(String rConstraint) {
        this.rConstraint = rConstraint;
    }

    protected String constraintName;

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    protected String searchCondition;

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public DbConstraint() {
        this.dbColumns = new ArrayList<DbColumn>();
    }

    public DbConstraint(ArrayList<DbColumn> dbColumns, String constraintName,
            ConstraintType constraintType, String rconstraint,
            String seaStringCondition, String owner, String rConstraintOwner) {
        this.dbColumns = dbColumns;
        this.constraintName = constraintName;
        this.constraintType = constraintType;
        this.rConstraint = rconstraint;
        this.searchCondition = seaStringCondition;
        this.owner = owner;
        this.rConstraintOwner = rConstraintOwner;
    }
}
