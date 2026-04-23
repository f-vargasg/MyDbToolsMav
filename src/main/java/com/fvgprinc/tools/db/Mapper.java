package com.fvgprinc.tools.db;

import static com.fvgprinc.tools.db.ParamAction.JavaTypes.TIMESTAMPJAVASQL;
import com.fvgprinc.tools.string.MyCommonString;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author fvargas
 */
public abstract class Mapper<T> {

    protected DataManager dm;
    protected StoredProcedureCall spCall;

    /**
     * Given rs, convert to entity.As is until run time, which is known the
     * particular type of the entity, the return type is an object. The doLoad
     * is implemented in concrete class.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public T load(ResultSet rs) throws SQLException {
        return (T) this.doLoad(rs);
    }

    /**
     * Find object in Db given field(s) key. Depends on doFind, that is
     * implemented in concrete class.
     *
     * @param keyValueFields
     * @return
     * @throws SQLException
     */
    public T find(ArrayList<ParamAction> keyValueFields) throws SQLException {
        return this.doFind(keyValueFields);
    }

    /**
     * Executes SQL statement NOT return recordsets
     *
     * @param sqlStm
     * @param pValues
     * @throws SQLException
     */
    protected void doStatement(String sqlStm, ArrayList<ParamAction> pValues) throws SQLException {
        // PreparedStatement stm;
    //    try (Connection conn = dm.getConnectioin(); PreparedStatement stm = conn.prepareStatement(sqlStm);) {
         try (Connection conn = dm.getConnection(); PreparedStatement stm = conn.prepareStatement(sqlStm);) {
            this.setParamPreparedStm(stm, pValues);
            stm.execute();
        } catch (Exception ex) {
            throw ex;
        }

        // this.conn.close();
    }

    /**
     *
     * @param sqlStm
     * @param pValues
     * @return
     * @throws SQLException
     */
    protected ResultInsert doStatementInsert(String sqlStm, ArrayList<ParamAction> pValues) throws SQLException {
        // PreparedStatement stm;
        ResultInsert res = null;
        ArrayList<Long> keys = new ArrayList<>();
        int affectedRows = 0;

        try (Connection conn = dm.getConnection(); PreparedStatement stm = conn.prepareStatement(sqlStm, PreparedStatement.RETURN_GENERATED_KEYS);) {
            this.setParamPreparedStm(stm, pValues);
            affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long newId = generatedKeys.getLong(1);
                        keys.add(newId);
                    }
                }
                res = new ResultInsert(affectedRows, keys);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return res;

    }

    public abstract void insert(ArrayList<ParamAction> paramDLs) throws SQLException;

    public abstract void update(ArrayList<ParamAction> paramDLs) throws SQLException;

    public abstract void delete(ArrayList<ParamAction> keyFields) throws SQLException;

    protected abstract T doLoad(ResultSet rs) throws SQLException;

    public abstract T doFind(ArrayList<ParamAction> keyFields) throws SQLException;

    /**
     * Implementación genérica de 'find'. La clase hija puede llamar a este
     * método en lugar de reescribir la lógica.
     */
    // <-- CAMBIO: Ya no es <T> Object, sino T (usa el genérico de la clase)
    public T doFind2(ArrayList<ParamAction> keyFiedls, String pSql) throws SQLException {
        T t = null;
        String wSql = pSql;
        try (Connection conn = dm.getConnection(); PreparedStatement stm = conn.prepareStatement(wSql)) {
            this.setParamPreparedStm(stm, keyFiedls);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    // <-- CAMBIO: Llama a load(rs) que ahora devuelve T. ¡Sin cast!
                    t = load(rs);
                }
            }
        }
        return t;
    }

    public ArrayList<T> listar(ArrayList<ParamAction> params, String pSql) throws SQLException {
        ArrayList<T> lstRes = new ArrayList<>();
        String condSql = ParamAction.queryCond(params);
        String sqlStm = pSql + (condSql.length() > 0 ? " WHERE " : MyCommonString.EMPTYSTR) + condSql;
        try (Connection conn = dm.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlStm)) {
            this.setParamPreparedStm(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // <-- CAMBIO: Llama a load(rs) que ahora devuelve T. ¡Sin cast!
                    T ub = load(rs);
                    lstRes.add(ub);
                }
            }
        }
        return lstRes;
    }

    /**
     * Este retorna un PreparedStatemets de acuerdo a la lista de objetos de
     * tipo ParamAction Este no le interesa los nombres de las columnas ya que
     * asume que la sentencia SQL tiene los tags "?" que corresponde a los
     * parametros
     *
     * @param stm
     * @param pValues
     * @throws SQLException
     */
    protected void setParamPreparedStm(PreparedStatement stm, ArrayList<ParamAction> pValues) throws SQLException {
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
                case LOCALDATE:
                    stm.setObject(i + 1, pValues.get(i).getValue());
                    break;
                case LONG:
                    stm.setLong(i + 1, (Long) (pValues.get(i).getValue()));
                    break;
                case SHORT:
                    stm.setShort(i + 1, (Short) (pValues.get(i).getValue()));
                default:
                    break;
            }
        }
    }
}