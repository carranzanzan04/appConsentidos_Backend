/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.microserviciologin.model.*;
import com.mycompany.microserviciologin.util.DatabaseUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UsuarioDAO {
    
    public int crearUsuario(Usuario usuario) {
        int id = 0;

        try {
           String sql=" INSERT INTO usuarios (correo, contrasena, confirmacion,nid, created_at, activo) VALUES (?,?,?,?,?,?)";
            try (Connection con = DatabaseUtil.getConnection(); PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                con.setAutoCommit(false);
                stmt.setString(1, usuario.getCorreo());
                // encirptacion de contrasena
                String hashedPassword = BCrypt.withDefaults().
                        hashToString(12, usuario.getContrasena().toCharArray());
                stmt.setString(2, hashedPassword);
                stmt.setBoolean(3, true);
                stmt.setString(4, usuario.getNid());
                stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setBoolean(6, usuario.isActivo());
                int affectedRows = stmt.executeUpdate();
                con.commit();
                if (affectedRows == 0) {
                    throw new SQLException("error al crear el usuario");
                }
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                    
                }

            }
        } catch (  SQLException e) {
            
        }
        return id;
    }

    // Nuevo método para actualizar un usuario
    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET correo = ?,  nid = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con=null;
        try{
         con = DatabaseUtil.getConnection();
             try (PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getNid());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, usuario.getId());

            int affectedRows = stmt.executeUpdate();
            con.commit();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el usuario con ID " + usuario.getId() + " o ya está eliminado");
            }
             }
        } catch (SQLException e) {
            
            try {
               if (con != null) {
                con.rollback();
            }
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al actualizar usuario", e);
        }finally {
        if (con != null) {
            try {
                con.setAutoCommit(true); // Restaurar autoCommit
                con.close(); // Cerrar la conexión explícitamente
            } catch (SQLException closeEx) {
                throw new RuntimeException("Error al cerrar la conexión", closeEx);
            }
        }
    }
        
    }

    // Nuevo método para eliminar (soft delete) un usuario
 public void eliminarUsuario(int id) {
    String sql = "UPDATE usuarios SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
    Connection con = null;
    try {
        con = DatabaseUtil.getConnection();
        con.setAutoCommit(false);
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, id);

            int affectedRows = stmt.executeUpdate();
            con.commit();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el usuario con ID " + id + " o ya está eliminado");
            }
        }
    } catch (SQLException e) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException rollbackEx) {
            throw new RuntimeException("Error al hacer rollback", rollbackEx);
        }
        throw new RuntimeException("Error al eliminar usuario", e);
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true); // Restaurar autoCommit
                con.close(); // Cerrar la conexión explícitamente
            } catch (SQLException closeEx) {
                throw new RuntimeException("Error al cerrar la conexión", closeEx);
            }
        }
    }
}

  public Usuario authenticate(String correo, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND confirmacion = TRUE AND deleted_at IS NULL";
        System.out.println("Buscando usuario con correo: " + correo);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String hashedContrasena = rs.getString("contrasena");
                System.out.println("Usuario encontrado. ID: " + id + ", Hash almacenado: " + hashedContrasena);
                System.out.println("Contraseña ingresada: " + contrasena);
                // Verificar la contraseña usando at.favre.lib:bcrypt
                BCrypt.Result result = BCrypt.verifyer().verify(contrasena.toCharArray(), hashedContrasena);
                System.out.println("Resultado de la verificación: " + result.verified);
                if (result.verified) {
                    System.out.println("Autenticación exitosa para el usuario: " + correo);
                    return findUserById(id);
                } else {
                    System.out.println("La contraseña no coincide.");
                }
            } else {
                System.out.println("No se encontró un usuario con el correo: " + correo);
            }
        }
        return null;
    }

    public Usuario findUserById(int id) throws SQLException {
        String sqlUsuario = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contrasena");
                boolean confirmacion = rs.getBoolean("confirmacion");
                String nid = rs.getString("nid");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null;
                LocalDateTime deletedAt = rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null;
                boolean activo = rs.getBoolean("activo");

                if (isAdministrador(userId, conn)) {
                    return getAdministrador(userId, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, conn);
                } else if (isAtencionAlCliente(userId, conn)) {
                    return getAtencionAlCliente(userId, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, conn);
                } else if (isCliente(userId, conn)) {
                    return getCliente(userId, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, conn);
                }
            }
        }
        return null;
    }

    private boolean isAdministrador(int id, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM administradores WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeQuery().next();
        }
    }

    private boolean isAtencionAlCliente(int id, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM atencion_al_cliente WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeQuery().next();
        }
    }

    private boolean isCliente(int id, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM clientes WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeQuery().next();
        }
    }
    public void actualizarContrasena(String password){
        
    }
    public void inhabilitarUsuario(){
        
    }
    private Administrador getAdministrador(int id, String correo, String contrasena, boolean confirmacion, String nid,
                                           LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                                           Connection conn) throws SQLException {
        String sql = "SELECT * FROM administradores WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                return new Administrador(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, nombre, apellido);
            }
        }
        return null;
    }

    private AtencionAlCliente getAtencionAlCliente(int id, String correo, String contrasena, boolean confirmacion, String nid,
                                                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                                                   Connection conn) throws SQLException {
        String sql = "SELECT * FROM atencion_al_cliente WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                return new AtencionAlCliente(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, nombre, apellido);
            }
        }
        return null;
    }

    private Cliente getCliente(int id, String correo, String contrasena, boolean confirmacion, String nid,
                               LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                               Connection conn) throws SQLException {
        String sqlDueno = "SELECT * FROM duenos WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDueno)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                return new Dueno(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, nombre, apellido);
            }
        }

        String sqlAutonomo = "SELECT * FROM autonomos WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sqlAutonomo)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                return new Autonomo(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, nombre, apellido);
            }
        }

        String sqlEmpresa = "SELECT * FROM empresas WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sqlEmpresa)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                return new Empresa(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, nombre);
            }
        }

        return null;
    }
}