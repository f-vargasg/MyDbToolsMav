package com.fvgprinc.tools.db.metadata;

import com.fvgprinc.tools.string.MyCommonString;

/**
 *
 * @author Ryzen9-Gaming
 */
public abstract class DbColumn {

    protected String ideColS;
    protected String ideTablaS;
    protected String ideEsquemaS;
    protected String tipoDatoS;
    protected int longitud;
    protected int precisionN;
    protected int escala;
    protected boolean llavePrimaria;

    public String getIdeColS() {
        return ideColS;
    }

    public void setIdeColS(String ideColS) {
        this.ideColS = ideColS;
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

    public String getTipoDatoS() {
        return tipoDatoS;
    }

    public void setTipoDatoS(String tipoDato) {
        this.tipoDatoS = tipoDato;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getPrecisionN() {
        return precisionN;
    }

    public void setPrecisionN(int precisionN) {
        this.precisionN = precisionN;
    }

    public int getEscala() {
        return escala;
    }

    public void setEscala(int escala) {
        this.escala = escala;
    }

    public boolean isLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(boolean llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }

    public DbColumn() {
        this.ideColS = MyCommonString.EMPTYSTR;
        this.ideTablaS = MyCommonString.EMPTYSTR;
        this.ideEsquemaS = MyCommonString.EMPTYSTR;
        this.tipoDatoS = MyCommonString.EMPTYSTR;
        this.longitud = 0;
        this.precisionN = 0;
        this.escala = 0;
        this.llavePrimaria = false;
    }

    public DbColumn(String ideColS, String ideTablaS, String ideEsquemaS, String tipoDatoS, int longitud, int precisionN, int escala, boolean llavePrimaria) {
        this.ideColS = ideColS;
        this.ideTablaS = ideTablaS;
        this.ideEsquemaS = ideEsquemaS;
        this.tipoDatoS = tipoDatoS;
        this.longitud = longitud;
        this.precisionN = precisionN;
        this.escala = escala;
        this.llavePrimaria = llavePrimaria;
    }

    public abstract DbColumn getInfoColumn();

}
