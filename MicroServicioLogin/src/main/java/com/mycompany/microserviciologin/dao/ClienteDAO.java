/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.dao;

import com.mycompany.microserviciologin.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 *
 * @author SARA CARRANZA
 */
public class ClienteDAO {
        public int registrarCliente(int idusiario) {
       int id = 0;
     
                  String sql="INSERT INTO clientes (id, created_at) VALUES ( ?, ?)";
            try (Connection con = DatabaseUtil.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
                con.setAutoCommit(false);
                
                stmt.setInt(1, idusiario); 
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("Falló la creación de administrador");
                }
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                    
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Imprimir el error para depuración
                throw new RuntimeException("Error al registrar cliente en la base de datos", e);
            }
   
   return id;
}
}
