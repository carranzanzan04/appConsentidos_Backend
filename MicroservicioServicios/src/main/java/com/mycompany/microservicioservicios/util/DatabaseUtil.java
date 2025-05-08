/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.util;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
       public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "lucho96!";
    public static final String DB_NAME = "ips";
    public static String DB_URL = "jdbc:postgresql://localhost:5433/" + DB_NAME;

   static {
        try {
            // Registro explícito del driver (opcional en JDBC 4.0+)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo cargar el driver de PostgreSQL", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}