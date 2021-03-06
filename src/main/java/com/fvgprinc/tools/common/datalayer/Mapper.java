/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fvgprinc.tools.common.datalayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.fvgprinc.tools.common.app.dbconnection.DbConnFactory;
import com.fvgprinc.tools.common.app.layers.ParamAction;
import com.fvgprinc.tools.common.string.MyCommonString;

/**
 *
 * @author fvargas
 */
public abstract class Mapper {

    protected Connection conn;

    public Mapper() throws SQLException, CommonDALExceptions  {
        this.conn = DbConnFactory.getInstance().createDbConnManager()[0].createDbConn().getConnection();
    }

    public Mapper(int indConn) throws SQLException, CommonDALExceptions  {
        this.conn = DbConnFactory.getInstance().createDbConnManager()[indConn].createDbConn().getConnection();
    }
    
    public Mapper (Connection pconn)
    {
        this.conn = pconn;
    }

    /**
     * Given rs, convert to entity.As is until run time, which is known the
     * particular type of the entity, the return type is an object. The doLoad
     * is implemented in concrete class.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public Object load(ResultSet rs) throws SQLException {
        return this.doLoad(rs);
    }

    /**
     * Find object in Db given field(s) key. Depends on doFind, that is
     * implemented in concrete class.
     *
     * @param keyValueFields
     * @return
     * @throws SQLException
     */
    public Object find(ArrayList<ParamAction> keyValueFields) throws SQLException {
        return this.doFind(keyValueFields);
    }

    /**
     * Executes SQL statement not return recordsets
     *
     * @param sqlStm
     * @param pValues
     * @throws SQLException
     */
    protected void doStatement(String sqlStm, ArrayList<ParamAction> pValues) throws SQLException {
        PreparedStatement stm;
        stm = this.conn.prepareStatement(sqlStm);
        stm = this.setParamPreparedStm(stm, pValues);
        stm.execute();
        stm.close();
    }

    /**
     * Returns resultSet product of execution of sqlStm.
     *
     * @param sqlStm
     * @param pValues
     * @return resultset of the execution of sqlStm
     * @throws SQLException
     */
    protected ResultSet doStmReturnData(String sqlStm, ArrayList<ParamAction> pValues) throws SQLException {
        // sqlStm += ((pValues.size() > 0 ? " where " : MyCommonString.EMPTYSTR) + queryCond(pValues));
        PreparedStatement stm = conn.prepareStatement(sqlStm);
        stm = this.setParamPreparedStm(stm, pValues);
        ResultSet rs = stm.executeQuery();
        stm.close();
        return rs;

    }

    public abstract void insert(ArrayList<ParamAction> paramDLs) throws SQLException;

    public abstract void update(ArrayList<ParamAction> paramDLs) throws SQLException;

    public abstract void delete(ArrayList<ParamAction> keyFields) throws SQLException;

    protected abstract Object doLoad(ResultSet rs) throws SQLException;

    protected abstract Object doFind(ArrayList<ParamAction> keyFiedls) throws SQLException;

    protected PreparedStatement setParamPreparedStm(PreparedStatement stm, ArrayList<ParamAction> pValues) throws SQLException {
        for (int i = 0; i < pValues.size(); i++) {
            switch (pValues.get(i).getDataType()) {
                case INTEGER:
                    stm.setInt(i + 1, (Integer) (pValues.get(i).getValue()));
                    break;
                case DOUBLE:
                    stm.setDouble(i + 1, (Double) (pValues.get(i).getValue()));
                    break;
                case STRING:
                    stm.setString(i + 1, (String) (pValues.get(i).getValue()));
                    break;
                case DATEJAVASQL:
                    stm.setDate(i + 1, (java.sql.Date) pValues.get(i).getValue());
                    break;
                case TIMESTAMPJAVASQL:
                    stm.setTimestamp(i + 1, (java.sql.Timestamp) pValues.get(i).getValue());
                    break;
                case LONG:
                    stm.setLong(i + 1, (Long) (pValues.get(i).getValue()));
                    break;
                default:
                    break;
            }
        }
        return stm;
    }

    /**
     * Get query jdbc standard (ie fiedl1 = ? and field2 = ? .... and fieldn= ?
     *
     * @param pValues
     * @return sql cond in jdbc standard
     */
    protected String queryCond(ArrayList<ParamAction> pValues) {
        String res = "";
        boolean ft = true;
        for (int i = 0; i < pValues.size(); i++) {
            res += ((!ft ? " and " : MyCommonString.EMPTYSTR) + pValues.get(i).getColumName() + " = ?");
        }
        return res;
    }
}
