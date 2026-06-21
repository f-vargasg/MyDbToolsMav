
package com.fvgprinc.tools.db.metadata;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ryzen9-Gaming
 */
public abstract class DbObjectFactory {

    public abstract DbTable createDbTable();

    public abstract DbTable createDbTable(String pSchemaName, String pTableName) throws SQLException;

    public abstract ArrayList<String> listTablesByEsquema(String pSchemaName);
}
