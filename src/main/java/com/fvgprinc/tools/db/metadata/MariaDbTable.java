package com.fvgprinc.tools.db.metadata;

import com.fvgprinc.tools.string.MyCommonString;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ryzen9-Gaming
 */
public final class MariaDbTable extends DbTable {

    public MariaDbTable() {
        this.ideEsquemaS = this.ideTablaS = MyCommonString.EMPTYSTR;
    }

    public MariaDbTable(String pschema, String pTableName) throws SQLException {
        this.ideEsquemaS = pschema;
        this.ideTablaS = pTableName;
        this.loadColumns();
        this.loadConstraints();
    }

    @Override
    public void loadColumns() throws SQLException {
        String sqlStm = "SELECT A.ordinal_position, A.table_schema, A.column_name, A.data_type\n"
                + "from INFORMATION_SCHEMA.COLUMNS A "
                + "where a.table_name = "
                + MyCommonString.entreChar(this.ideTablaS, MyCommonString.COMILLA_SENCILLA)
                + " and a.table_schema =  "
                + MyCommonString.entreChar(this.ideEsquemaS, MyCommonString.COMILLA_SENCILLA);

        // boolean ft = true;
        boolean isPk = false;

        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery(sqlStm);
        while (rs.next()) {
            isPk = isPrimaryKeyDb(rs.getString("column_name"));
            MariaDbColumn mySqlDbColumnI = new MariaDbColumn(rs.getString("column_name"),
                    rs.getString("table_name"),
                    rs.getString("table_schema"),
                    rs.getString("data_type"),
                    rs.getInt("character_maximum_length"),
                    rs.getInt("numeric_precision"),
                    rs.getInt("numeric_scale"),
                    isPk);
            this.dbColumns.add(mySqlDbColumnI);
        }
    }

    @Override
    protected boolean isPrimaryKeyDb(String pIdeColS) throws SQLException {
        boolean foundColumn = false;
        String sqlStm = "SELECT IF(COLUMN_KEY = 'PRI', 1, 0) AS es_primary_key\n"
                + "FROM INFORMATION_SCHEMA.COLUMNS\n"
                + "WHERE TABLE_SCHEMA = "
                + MyCommonString.entreChar(this.ideEsquemaS, MyCommonString.COMILLA_SENCILLA)
                + "  AND TABLE_NAME = "
                + MyCommonString.entreChar(this.ideTablaS, MyCommonString.COMILLA_SENCILLA) + "\n"
                + "  AND COLUMN_NAME = "
                + MyCommonString.entreChar(pIdeColS, MyCommonString.COMILLA_SENCILLA);

        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery(sqlStm);
        while (rs.next()) {
            foundColumn = (rs.getInt("es_primary_key") == 1);
        }
        rs.close();
        stm.close();

        return foundColumn;
    }

    @Override
    public String[] getTableGenStrDef(String pEsquema, String pTabla) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<String> getChildsTables() throws SQLException {
        ArrayList<String> res = new ArrayList<String>();
        String sqlStm;

        sqlStm = "SELECT DISTINCT TABLE_NAME AS tabla_hija\n"
                + "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n"
                + "WHERE REFERENCED_TABLE_SCHEMA = "
                + MyCommonString.entreChar(this.ideEsquemaS, MyCommonString.COMILLA_SENCILLA)
                + "  AND REFERENCED_TABLE_NAME = "
                + MyCommonString.entreChar(this.ideTablaS, MyCommonString.COMILLA_SENCILLA);
        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery(sqlStm);
        while (rs.next()) {
            res.add(rs.getString("table_name"));
        }
        return res;
    }

    @Override
    public ArrayList<String> getParentsTables() throws SQLException {
        ArrayList<String> res = new ArrayList<String>();
        String sqlStm;

        sqlStm = "SELECT DISTINCT TABLE_NAME AS tabla_hija\n"
                + "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n"
                + "WHERE REFERENCED_TABLE_SCHEMA = "
                + MyCommonString.entreChar(this.ideEsquemaS, MyCommonString.COMILLA_SENCILLA)
                + "  AND REFERENCED_TABLE_NAME = "
                + MyCommonString.entreChar(this.ideTablaS, MyCommonString.COMILLA_SENCILLA);

        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery(sqlStm);
        while (rs.next()) {
            res.add(rs.getString("table_name"));
        }
        return res;
    }

    private DbConstraint.ConstraintType convertMariaDbConstraintType2Generic(String pConstraintType) {
        DbConstraint.ConstraintType res = null;

        if (pConstraintType.compareToIgnoreCase("R") == 0) { // Reference
            res = DbConstraint.ConstraintType.FK_CONST;

        } else if (pConstraintType.compareToIgnoreCase("P") == 0) {
            res = DbConstraint.ConstraintType.PK_CONST;

        } else if (pConstraintType.compareToIgnoreCase("C") == 0) {
            res = DbConstraint.ConstraintType.CHECK_CONST;
        } else if (pConstraintType.compareToIgnoreCase("U") == 0) {
            res = DbConstraint.ConstraintType.UK_CONST;
        }

        return res;
    }

    private void addConstraint(ArrayList<DbColumn> lstDbColumns, String constraintName, String constraintType,
            String rConstraint, String searchCondition, String owner, String rConstraintOwner) throws SQLException {
        ArrayList<DbColumn> newList = (ArrayList<DbColumn>) lstDbColumns.clone();
        DbConstraint dbConstraint = new DbConstraint(newList,
                constraintName,
                convertMariaDbConstraintType2Generic(constraintType),
                rConstraint, searchCondition, owner, rConstraintOwner);
        this.dbContraints.add(dbConstraint);
        lstDbColumns.clear();
    }

    private DbColumn findDbColumn(String ideTablaS) {
        DbColumn res;
        boolean f = false;
        int findex = -1;

        for (int i = 0; i < this.dbColumns.size() && !f; i++) {
            f = (this.dbColumns.get(i).getIdeColS().compareTo(ideTablaS) == 0);
            findex = (f ? i : -1);
        }

        res = (f ? this.dbColumns.get(findex) : null);
        return res;
    }

    @Override
    public void loadConstraints() throws SQLException {
        this.dbContraints = new ArrayList<DbConstraint>();
        String sqlStm = "SELECT \n"
                + "    tc.CONSTRAINT_SCHEMA, \n"
                + "    tc.TABLE_NAME ,\n"
                + "    tc.CONSTRAINT_NAME ,\n"
                + "    tc.CONSTRAINT_TYPE,\n"
                + "    kcu.COLUMN_NAME AS columna,\n"
                + "    kcu.REFERENCED_TABLE_NAME AS tabla_referenciada,\n"
                + "    kcu.REFERENCED_COLUMN_NAME AS columna_referenciada\n"
                + "FROM \n"
                + "    INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc\n"
                + "JOIN \n"
                + "    INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu \n"
                + "    ON tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME \n"
                + "    AND tc.TABLE_SCHEMA = kcu.TABLE_SCHEMA\n"
                + "WHERE \n"
                + "    tc.CONSTRAINT_SCHEMA = 'inventClinica'\n"
                + MyCommonString.entreChar(this.ideEsquemaS, MyCommonString.COMILLA_SENCILLA)
                + "and  tc.TABLE_NAME = "
                + MyCommonString.entreChar(this.ideTablaS, MyCommonString.COMILLA_SENCILLA)
                + "ORDER BY \n"
                + "    tc.TABLE_NAME, tc.CONSTRAINT_NAME, kcu.ORDINAL_POSITION";

        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery(sqlStm);
        String constraintName = MyCommonString.EMPTYSTR;
        String constraintType = MyCommonString.EMPTYSTR;
        String rConstraint = MyCommonString.EMPTYSTR;
        String searchCondition = MyCommonString.EMPTYSTR;
        String owner = MyCommonString.EMPTYSTR;
        String rConstraintOwner = MyCommonString.EMPTYSTR;
        ArrayList<DbColumn> lstDbColumns = new ArrayList<DbColumn>();
        boolean ft = true;

        while (rs.next()) {
            // find in dbColumns the column
            if (rs.getString("CONSTRAINT_NAME").compareToIgnoreCase(constraintName) != 0
                    && !ft) {
                addConstraint(lstDbColumns, constraintName, constraintType, rConstraint,
                        searchCondition, owner, rConstraintOwner);

            }

            DbColumn dbColumn = findDbColumn(rs.getString("COLUMN_NAME"));
            if (dbColumn != null) {
                lstDbColumns.add(dbColumn);
            }
            constraintName = rs.getString("CONSTRAINT_NAME");
            constraintType = rs.getString("CONSTRAINT_TYPE");
            rConstraint = rs.getString("R_CONSTRAINT_NAME");
            searchCondition = rs.getString("REFERENCED_TABLE_NAME") + "-" +
                                            rs.getString("REFERENCED_COLUMN_NAME") ;
            owner = rs.getString("CONSTRAINT_SCHEMA");
            ft = false;
        }
        if (lstDbColumns.size() > 0) {
            addConstraint(lstDbColumns, constraintName, constraintType, rConstraint,
                    searchCondition, owner, rConstraintOwner);
        }
        rs.close();
        stm.close();
    }

    @Override
    public String genCreateScript() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String genCreatePkScript() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String genCreateFkScript() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String genCreateChkScript() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String genExportInsertStm(String pcond) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void genExportInsertStmFile(String pPathName, String pcond) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
