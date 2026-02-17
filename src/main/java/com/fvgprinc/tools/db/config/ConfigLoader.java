
package com.fvgprinc.tools.db.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

/**
 *
 * @author Ryzen9-Gaming
 */
public class ConfigLoader {
    private static JsonNode rootNode;
    private static final String FILE_NAME = "config.json";

    static {
        try {
            String path = System.getProperty("user.dir") + File.separator + FILE_NAME;
            rootNode = new ObjectMapper().readTree(new File(path));
        } catch (Exception e) {
            System.err.println("Error cargando JSON: " + e.getMessage());
        }
    }

    /**
     * Busca y devuelve la configuración de una conexión específica por su ID.
     * @param id El dbIdConn que buscas (ej: "conn13")
     * @return El objeto DbConnectionBe o null si no existe.
     */
    public static DbConnectionBe getConnectionById(String id) {
        if (rootNode == null || !rootNode.has("connections")) return null;

        JsonNode listNode = rootNode.path("connections").path("list");
        ObjectMapper mapper = new ObjectMapper();

        if (listNode.isArray()) {
            for (JsonNode node : listNode) {
                // Comparamos el ID del nodo con el que buscamos
                if (node.path("dbIdConn").asText().equals(id)) {
                    try {
                        return mapper.treeToValue(node, DbConnectionBe.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
