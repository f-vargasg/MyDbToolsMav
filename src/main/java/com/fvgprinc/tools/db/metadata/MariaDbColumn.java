
package com.fvgprinc.tools.db.metadata;

/**
 *
 * @author Ryzen9-Gaming
 */
class MariaDbColumn extends  DbColumn {

    public MariaDbColumn(String ideColS, String ideTablaS, String ideEsquemaS, String tipoDatoS, int longitud, int precisionN, int escala, boolean llavePrimaria) {
        super(ideColS, ideTablaS, ideEsquemaS, tipoDatoS, longitud, precisionN, escala, llavePrimaria);
    }

   
    @Override
    public DbColumn getInfoColumn() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
