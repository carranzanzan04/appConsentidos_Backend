/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.dao;

import com.mycompany.microserviciologin.model.Autonomo;
import com.mycompany.microserviciologin.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 *
 * @author SARA CARRANZA
 */
public class AutonomoDAO {
       public void registrarAutonomo(int idcliente, Autonomo autonomo) {
       
           String sql="INSERT INTO autonomos (id, nombre, apellido, created_at) VALUES (?, ?, ?, ?)";
            try (Connection con = DatabaseUtil.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                
                stmt.setInt(1, idcliente); // Usamos idusuario en lugar de admin.getId()
                stmt.setString(2,autonomo.getNombre());
                stmt.setString(3, autonomo.getApellido());
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("Falló la creación de administrador");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Imprimir el error para depuración
                throw new RuntimeException("Error al registrar autonomo en la base de datos", e);
            }
        
        
    }
       public void actualizarAutonomo(Autonomo autonomo) {
        String sql = "UPDATE autonomos SET nombre = ?, apellido = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con=null;
        try{
        con = DatabaseUtil.getConnection();
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            stmt.setString(1, autonomo.getNombre());
            stmt.setString(2, autonomo.getApellido());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, autonomo.getId());

            int affectedRows = stmt.executeUpdate();
            con.commit();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el autónomo con ID " + autonomo.getId() + " o ya está eliminado");
            }
         }
        } catch (SQLException e) {
            try {
                if(con!=null){
                   con.rollback();
                }
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al actualizar autónomo", e);
        }finally{
            if(con!=null){
                try {
                     con.setAutoCommit(true);
                     con.close();
                } catch (SQLException EXclose) {
                    throw new RuntimeException("Error al cerrar la coneccion ",EXclose);
                }
            }
        }
    }

    public void eliminarAutonomo(int id) {
        String sql = "UPDATE autonomos SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con=null;
        try{
        con = DatabaseUtil.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, id);

            int affectedRows = stmt.executeUpdate();
            con.commit();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el autónomo con ID " + id + " o ya está eliminado");
            }
         }
        } catch (SQLException e) {
            try {
                if(con!=null)
                con.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al eliminar autónomo", e);
        }finally{
            if(con!=null){
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException EXclose) {
                  throw new  RuntimeException("Error al cerrar la conexion",EXclose);
                }
            }
        }
    }
    
}
