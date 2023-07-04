/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fvgprinc.tools.common.app.dbconnection3;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @deprecated 
 * Mejor utilizar DIContainer y DataManager
 * @author garfi
 */
public class ConnectionDB {

    private DataSource dataSource;

    public ConnectionDB() {
        loadConfiguration();
    }

    public ConnectionDB(String dbIdConn) throws ParserConfigurationException, SAXException, IOException {
        loadConfigurationXml(dbIdConn);
    }

    public Connection getConnection() throws SQLException {
        Connection conexion = null;
        try {
            conexion = dataSource.getConnection();
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
        return conexion;
    }

    private void loadConfiguration() {
        Properties properties = new Properties();
        try ( FileInputStream fis = new FileInputStream("src/main/resources/configuracion.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(properties.getProperty("db.driver"));
        basicDataSource.setUrl(properties.getProperty("db.url"));
        basicDataSource.setUsername(properties.getProperty("db.usuario"));
        basicDataSource.setPassword(properties.getProperty("db.password"));
        basicDataSource.setInitialSize(Integer.parseInt(properties.getProperty("db.pool.inicial")));
        basicDataSource.setMaxTotal(Integer.parseInt(properties.getProperty("db.pool.maximo")));
        dataSource = basicDataSource;
    }

    private void loadConfigurationXml(String dbIdConn) throws ParserConfigurationException, SAXException, IOException {
        try {
            // Cargar el archivo XML de configuración
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = (Document) builder.parse("src/main/resources/configuracion.xml"); // Reemplaza "config.xml" por la ruta de tu archivo XML
            String dbIdConnToFind = dbIdConn;
            Element root = (Element) document.getDocumentElement(); // get root xml
            if (dbIdConn.equalsIgnoreCase("")) {
                NodeList defConnNodeList = root.getElementsByTagName("defaultConn");
                if (defConnNodeList.getLength() > 0) {
                    Node childNode = defConnNodeList.item(0);
                    dbIdConnToFind = childNode.getFirstChild().getNodeValue();
                }
            }

            // Iterar sobre los elementos de configuración
            Map<String, String> connection = new HashMap<>();
            boolean foundNode = false;
            NodeList connectionsNodeList = root.getElementsByTagName("connection");
            for (int i = 0; i < connectionsNodeList.getLength() && !foundNode; i++) {
                Element connectionItem = (Element) connectionsNodeList.item(i);
                NodeList connectionItemChildNodes = connectionItem.getChildNodes();
                for (int j = 0; j < connectionItemChildNodes.getLength(); j++) {
                    Node childNode = connectionItemChildNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        String valueNode = (childNode.getFirstChild() != null ? childNode.getFirstChild().getNodeValue() : "");
                        connection.put(childNode.getNodeName(), valueNode);
                        foundNode = (childNode.getNodeName().compareTo("dbIdConn") == 0
                                && valueNode.compareTo(dbIdConnToFind) == 0);

                    }
                }
            }

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(connection.get("dbDriver"));
            basicDataSource.setUrl(connection.get("dbUrl"));
            basicDataSource.setUsername(connection.get("dbUsuario"));
            basicDataSource.setPassword(connection.get("dbPassw"));
            basicDataSource.setInitialSize(Integer.parseInt(connection.get("dbPoolInicial")));
            basicDataSource.setMaxTotal(Integer.parseInt(connection.get("dbPoolMax")));
            dataSource = basicDataSource;

        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }
}
