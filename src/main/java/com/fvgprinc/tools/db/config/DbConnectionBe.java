package com.fvgprinc.tools.db.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Entidad que mapea la configuración de conexión incluyendo el pooling. Basado
 * en el estándar de configuración de la clínica.
 */
public class DbConnectionBe implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("dbIdConn")
    private String dbIdConn;

    @JsonProperty("dbDriver")
    private String dbDriver;

    @JsonProperty("dbUrl")
    private String dbUrl;

    @JsonProperty("dbUsuario")
    private String dbUsuario;

    @JsonProperty("dbPassw")
    private String dbPassw;

    // Campos de Pooling que faltaban
    @JsonProperty("dbPoolInicial")
    private Integer dbPoolInicial;

    @JsonProperty("dbPoolMax")
    private Integer dbPoolMax;

    // --- Getters y Setters ---
    public String getDbIdConn() {
        return dbIdConn;
    }

    public void setDbIdConn(String dbIdConn) {
        this.dbIdConn = dbIdConn;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsuario() {
        return dbUsuario;
    }

    public void setDbUsuario(String dbUsuario) {
        this.dbUsuario = dbUsuario;
    }

    public String getDbPassw() {
        return dbPassw;
    }

    public void setDbPassw(String dbPassw) {
        this.dbPassw = dbPassw;
    }

    public Integer getDbPoolInicial() {
        return dbPoolInicial;
    }

    public void setDbPoolInicial(Integer dbPoolInicial) {
        this.dbPoolInicial = dbPoolInicial;
    }

    public Integer getDbPoolMax() {
        return dbPoolMax;
    }

    public void setDbPoolMax(Integer dbPoolMax) {
        this.dbPoolMax = dbPoolMax;
    }
}
