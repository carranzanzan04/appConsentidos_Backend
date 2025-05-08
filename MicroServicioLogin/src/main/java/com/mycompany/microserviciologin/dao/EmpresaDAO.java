/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.dao;

import com.mycompany.microserviciologin.model.Empresa;
import com.mycompany.microserviciologin.util.DatabaseUtil;
import jakarta.ejb.Local;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    // Crear una nueva empresa
    public void registrarEmpresa(int id,Empresa empresa) {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            // Verificar que el cliente exista
            if (!clienteExiste(id, con)) {
                throw new SQLException("El cliente con ID " + empresa.getId() + " no existe o está eliminado");
            }

           

            // Insertar en la tabla empresas
            String sqlEmpresa = "INSERT INTO empresas (id, nombre, created_at) VALUES (?, ?, ?)";
            try (PreparedStatement stmtEmpresa = con.prepareStatement(sqlEmpresa)) {
                stmtEmpresa.setInt(1, id);
                stmtEmpresa.setString(2, empresa.getNombre());
                stmtEmpresa.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                stmtEmpresa.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al crear empresa  1", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
    }

    // Obtener una empresa por ID
    public Empresa obtenerEmpresaPorId(int id) {
        String sql = "SELECT e.*, p.created_at AS p_created_at, p.updated_at AS p_updated_at, p.deleted_at AS p_deleted_at " +
                    "FROM empresas e " +
                    "JOIN prestadores p ON e.id = p.id " +
                    "WHERE e.id = ? AND e.deleted_at IS NULL AND p.deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearEmpresa(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener empresa por ID", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
        return null;
    }

    // Obtener todas las empresas (no eliminadas)
    public List<Empresa> obtenerTodasLasEmpresas() {
        String sql = "SELECT e.*, p.created_at AS p_created_at, p.updated_at AS p_updated_at, p.deleted_at AS p_deleted_at " +
                    "FROM empresas e " +
                    "JOIN prestadores p ON e.id = p.id " +
                    "WHERE e.deleted_at IS NULL AND p.deleted_at IS NULL";
        List<Empresa> empresas = new ArrayList<>();
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empresas.add(mapearEmpresa(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las empresas", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
        return empresas;
    }

    // Actualizar una empresa
    public void actualizarEmpresa(Empresa empresa) {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            // Actualizar en la tabla prestadores
            String sqlPrestador = "UPDATE prestadores SET updated_at = ?, deleted_at = ? WHERE id = ?";
            try (PreparedStatement stmtPrestador = con.prepareStatement(sqlPrestador)) {
                stmtPrestador.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                stmtPrestador.setTimestamp(2, empresa.getDeletedAt() != null ? Timestamp.valueOf(empresa.getDeletedAt()) : null);
                stmtPrestador.setInt(3, empresa.getId());
                stmtPrestador.executeUpdate();
            }

            // Actualizar en la tabla empresas
            String sqlEmpresa = "UPDATE empresas SET nombre = ?, updated_at = ?, deleted_at = ? WHERE id = ? AND deleted_at IS NULL";
            try (PreparedStatement stmtEmpresa = con.prepareStatement(sqlEmpresa)) {
                stmtEmpresa.setString(1, empresa.getNombre());
                stmtEmpresa.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                stmtEmpresa.setTimestamp(3, empresa.getDeletedAt() != null ? Timestamp.valueOf(empresa.getDeletedAt()) : null);
                stmtEmpresa.setInt(4, empresa.getId());

                int affectedRows = stmtEmpresa.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("No se encontró la empresa con ID " + empresa.getId() + " o ya está eliminada");
                }
            }

            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al actualizar empresa", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
    }

    // Eliminar (soft delete) una empresa
    public void eliminarEmpresa(int id) {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            LocalDateTime now = LocalDateTime.now();

            // Actualizar en la tabla prestadores
            String sqlPrestador = "UPDATE prestadores SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
            try (PreparedStatement stmtPrestador = con.prepareStatement(sqlPrestador)) {
                stmtPrestador.setTimestamp(1, Timestamp.valueOf(now));
                stmtPrestador.setTimestamp(2, Timestamp.valueOf(now));
                stmtPrestador.setInt(3, id);
                stmtPrestador.executeUpdate();
            }

            // Actualizar en la tabla empresas
            String sqlEmpresa = "UPDATE empresas SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
            try (PreparedStatement stmtEmpresa = con.prepareStatement(sqlEmpresa)) {
                stmtEmpresa.setTimestamp(1, Timestamp.valueOf(now));
                stmtEmpresa.setTimestamp(2, Timestamp.valueOf(now));
                stmtEmpresa.setInt(3, id);

                int affectedRows = stmtEmpresa.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("No se encontró la empresa con ID " + id + " o ya está eliminada");
                }
            }

            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al eliminar empresa", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
    }

    // Método auxiliar para verificar si un cliente existe y no está eliminado
    private boolean clienteExiste(int id, Connection con) throws SQLException {
        String sql = "SELECT 1 FROM clientes WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Método auxiliar para mapear un ResultSet a un objeto Empresa
    private Empresa mapearEmpresa(ResultSet rs) throws SQLException {
        Empresa empresa = new Empresa();
        empresa.setId(rs.getInt("id"));
        empresa.setNombre(rs.getString("nombre"));
        empresa.setCreatedAt(rs.getTimestamp("p_created_at").toLocalDateTime());
        empresa.setUpdatedAt(rs.getTimestamp("p_updated_at") != null ? rs.getTimestamp("p_updated_at").toLocalDateTime() : null);
        empresa.setDeletedAt(rs.getTimestamp("p_deleted_at") != null ? rs.getTimestamp("p_deleted_at").toLocalDateTime() : null);
        return empresa;
    }
}