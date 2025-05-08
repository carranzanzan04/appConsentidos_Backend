/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.dao;

import com.mycompany.microserviciologin.model.AtencionAlCliente;
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
public class AtencionAlClienteDAO {
    public void registrarAtencionAlCliente(int idusuario, AtencionAlCliente Atcliente) {
         
             String sql=    "INSERT INTO atencion_al_cliente(id, nombre, apellido, created_at) VALUES (?, ?, ?, ?)";
               try(Connection con =DatabaseUtil.getConnection();
                   PreparedStatement stmt=con.prepareStatement(sql)) {
                     con.setAutoCommit(false);
                     
                     stmt.setInt(1, idusuario );
                     stmt.setString(2, Atcliente.getNombre());
                     stmt.setString(3, Atcliente.getApellido());
                     stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                     int affecRow = stmt.executeUpdate();
                     if(affecRow==0){
                         throw new SQLException("nose ha podido registrar el at cliente");
                     }
                     
                     con.setAutoCommit(true);
                     con.close();
             } catch (SQLException e) {
                 e.printStackTrace();
                 throw new RuntimeException("no se ha podido crear el usuario",e);
             }
            
            
        

    }
    public void actualizarAtencionAlCliente(AtencionAlCliente atCliente) {
        String sql = "UPDATE atencion_al_cliente SET nombre = ?, apellido = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con=null;
        try{
        con = DatabaseUtil.getConnection();
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            stmt.setString(1, atCliente.getNombre());
            stmt.setString(2, atCliente.getApellido());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, atCliente.getId());

            int affectedRows = stmt.executeUpdate();
            con.commit();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el usuario de atención al cliente con ID " + atCliente.getId() + " o ya está eliminado");
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
            throw new RuntimeException("Error al actualizar usuario de atención al cliente", e);
        }finally{
            if(con!=null){
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException EXclose) {
                    throw new RuntimeException("Error al cerrar la coneccion");
                }
            }
        }
        
            
        
    }

    public void eliminarAtencionAlCliente(int id) {
        String sql = "UPDATE atencion_al_cliente SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con=null;
        try{
        con = DatabaseUtil.getConnection();
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, id);

            int affectedRows = stmt.executeUpdate();
            con.commit();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el usuario de atención al cliente con ID " + id + " o ya está eliminado");
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
            throw new RuntimeException("Error al eliminar usuario de atención al cliente", e);
        }finally{
            if(con!=null){
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException EXclose) {
                    throw new RuntimeException("Error al cerra la coneccion");
                }
            }
        }
    }
    
}
