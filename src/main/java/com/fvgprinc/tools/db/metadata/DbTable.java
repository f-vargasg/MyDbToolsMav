package com.fvgprinc.tools.db.metadata;

import com.fvgprinc.tools.string.MyCommonString;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Ryzen9-Gaming
 */
public abstract class DbTable {

    protected Connection conn;
    protected String ideTablaS;
    protected String ideEsquemaS;
    protected String ideProveedorS; // es si es Oracle, MySql, etc.
    protected ArrayList<DbColumn> dbColumns;
    protected ArrayList<DbConstraint> dbContraints;

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public String getIdeTablaS() {
        return ideTablaS;
    }

    public void setIdeTablaS(String ideTablaS) {
        this.ideTablaS = ideTablaS;
    }

    public String getIdeEsquemaS() {
        return ideEsquemaS;
    }

    public void setIdeEsquemaS(String ideEsquemaS) {
        this.ideEsquemaS = ideEsquemaS;
    }

    public String getIdeProveedorS() {
        return ideProveedorS;
    }

    public void setIdeProveedorS(String ideProveedorS) {
        this.ideProveedorS = ideProveedorS;
    }

    public int getColumnCount() {
        return this.dbColumns.size();
    }

    public ArrayList<DbColumn> getDbColumns() {
        return dbColumns;
    }

    public void setDbColumns(ArrayList dbColumns) {
        this.dbColumns = dbColumns;
    }

    public ArrayList<DbConstraint> getDbContraints() {
        return dbContraints;
    }

    public void setDbContraints(ArrayList<DbConstraint> dbContraints) {
        this.dbContraints = dbContraints;
    }

    public DbTable() {
        // this.conn = DbConnFactory.getInstance().createDbConnManager()[0].getDbConn().getConnection();
        this.dbColumns = new ArrayList();
        this.dbContraints = new ArrayList<DbConstraint>();
    }

    public abstract void loadColumns() throws SQLException;

    /**
     * *
     * Determ if the columne pIdeColS is Primary Key o part the Primary Key if
     * the Primary key is composite
     *
     * @param pIdeColS
     * @return
     * @throws SQLException
     */
    protected abstract boolean isPrimaryKeyDb(String pIdeColS) throws SQLException;

    public boolean isPrimaryKey(String pIdeColS) {
        boolean foundColumn = false;

        //  System.out.println(this.dbColumns.size());
        // Iterator<DbColumn> iterColumn = this.dbColumns.iterator();
        for (Iterator<DbColumn> iterator = this.dbColumns.iterator(); iterator.hasNext() && !foundColumn;) {
            DbColumn next = iterator.next();
            if (next.getIdeColS().compareToIgnoreCase(pIdeColS) == 0) {
                foundColumn = next.isLlavePrimaria();
            }
        }
        return foundColumn;
    }

    public ArrayList<DbColumn> getPrimaryKey() {
        ArrayList<DbColumn> res = new ArrayList<DbColumn>();

        //  System.out.println(this.dbColumns.size());
        // Iterator<DbColumn> iterColumn = this.dbColumns.iterator();
        for (DbColumn next : this.dbColumns) {
            if (next.isLlavePrimaria()) {
                res.add(next);
            }
        }
        return res;
    }

    protected String getFileNameData() {
        String res;

        res = this.ideTablaS + "-dat.sql";

        return res;
    }

    public String genConstraintsScript() {
        String res;

        res = genCreatePkScript();
        res += (MyCommonString.LINEFEED + genCreateFkScript());
        res += (MyCommonString.LINEFEED) + genCreateChkScript();

        return res;
    }

    public abstract String[] getTableGenStrDef(String pEsquema, String pTabla) throws SQLException;

    public abstract ArrayList<String> getChildsTables() throws SQLException;

    public abstract ArrayList<String> getParentsTables() throws SQLException;

    public abstract void loadConstraints() throws SQLException;

    public abstract String genCreateScript();

    public abstract String genCreatePkScript();

    public abstract String genCreateFkScript();

    public abstract String genCreateChkScript();

    public abstract String genExportInsertStm(String pcond) throws SQLException;

    public abstract void genExportInsertStmFile(String pPathName, String pcond) throws SQLException;

}
