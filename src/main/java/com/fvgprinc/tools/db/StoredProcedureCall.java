package com.fvgprinc.tools.db;

/**
 *
 * @author Ryzen9-Gaming
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoredProcedureCall {

    private String procedureName;
    private ArrayList<ParamStoredProc> parameters;
    private Map<String, Object> outValues  = new HashMap<>();

    public StoredProcedureCall(String procedureName) {
        this.procedureName = procedureName;
        this.parameters = new ArrayList<>();
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void addParameter(ParamStoredProc param) {
        parameters.add(param);
    }

    public void clearParameters() {
        parameters.clear();
    }

    public Map<String, Object> getOutValues() {
        return outValues;
    }
    
    

    public ResultSet execute(Connection conn) throws SQLException {
        ResultSet rs = null;
        String sqlCall = buildProcedureCall();

        try (CallableStatement stm = conn.prepareCall(sqlCall)) {
            setParameters(stm);
            boolean hasResultSet = stm.execute();
            
            this.outValues = getOutParameters(stm);

            if (hasResultSet) {
                rs = stm.getResultSet();
            }
        }
        return rs;
    }

    /**
     * Obtiene los valores de los parámetros OUT después de ejecutar el
     * procedimiento.
     *
     * @param stm El objeto CallableStatement después de la ejecución.
     * @return Un mapa con los nombres de los parámetros y sus valores.
     * @throws SQLException si ocurre un error al obtener los valores de los
     * parámetros.
     */
    private Map<String, Object> getOutParameters(CallableStatement stm) throws SQLException {
        // Map<String, Object> outValues = new HashMap<>();
        for (int i = 0; i < parameters.size(); i++) {
            ParamStoredProc param = parameters.get(i);
            if (param.isOutParam()) {
                Object value = stm.getObject(i + 1);  // Obtener valor del parámetro OUT
                outValues.put(param.getName(), value);  // Usar nombre de columna como clave
            }
        }
        return outValues;
    }

    private String buildProcedureCall() {
        StringBuilder sb = new StringBuilder("{call ");
        sb.append(procedureName).append("(");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append("?");
            if (i < parameters.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")}");
        return sb.toString();
    }

    /**
     * Configura los parámetros IN y OUT en el CallableStatement.
     *
     * @param stm El objeto CallableStatement que se está configurando.
     * @throws SQLException si ocurre un error al establecer los parámetros.
     */
    private void setParameters(CallableStatement stm) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            ParamStoredProc param = parameters.get(i);
            switch (param.getType()) {
                case INTEGER:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.INTEGER);
                    } else {
                        stm.setInt(i + 1, (Integer) param.getValue());
                    }
                    break;
                case DOUBLE:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.DOUBLE);
                    } else {
                        stm.setDouble(i + 1, (Double) param.getValue());
                    }
                    break;
                case STRING:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.VARCHAR);
                    } else {
                        stm.setString(i + 1, (String) param.getValue());
                    }
                    break;
                case DATEJAVASQL:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.DATE);
                    } else {
                        stm.setDate(i + 1, (java.sql.Date) param.getValue());
                    }
                    break;
                case TIMESTAMPJAVASQL:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.TIMESTAMP);
                    } else {
                        stm.setTimestamp(i + 1, (java.sql.Timestamp) param.getValue());
                    }
                    break;
                case LONG:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.BIGINT);
                    } else {
                        stm.setLong(i + 1, (Long) param.getValue());
                    }
                    break;
                case SHORT:
                    if (param.isOutParam()) {
                        stm.registerOutParameter(i + 1, Types.SMALLINT);
                    } else {
                        stm.setShort(i + 1, (Short) param.getValue());
                    }
                    break;
                default:
                    throw new SQLException("Tipo de parámetro no soportado: " + param.getType());
            }
        }
    }
}
