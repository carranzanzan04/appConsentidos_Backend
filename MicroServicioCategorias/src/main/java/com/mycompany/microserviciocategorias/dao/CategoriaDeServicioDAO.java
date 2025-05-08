/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciocategorias.dao;


import com.mycompany.microserviciocategorias.model.CategoriaDeServicio;
import com.mycompany.microserviciocategorias.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDeServicioDAO {

    // Crear una nueva categoría de servicio
    public int crearCategoria(CategoriaDeServicio categoria) {
        String sql = "INSERT INTO categorias_de_servicios (nombre, descripcion, id_administrador, created_at) VALUES (?, ?, ?, ?)";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            // Verificar que el administrador exista y no esté eliminado
            if (!administradorExiste(categoria.getIdAdministrador(), con)) {
                throw new SQLException("El administrador con ID " + categoria.getIdAdministrador() + " no existe o está eliminado");
            }

            try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, categoria.getNombre());
                stmt.setString(2, categoria.getDescripcion());
                stmt.setInt(3, categoria.getIdAdministrador());
                stmt.setTimestamp(4, Timestamp.valueOf(categoria.getCreatedAt()));

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("Error al crear la categoría de servicio");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener el ID generado para la categoría");
                    }
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
            throw new RuntimeException("Error al crear categoría de servicio", e);
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

    // Obtener una categoría por ID
    public CategoriaDeServicio obtenerCategoriaPorId(int id) {
        String sql = "SELECT * FROM categorias_de_servicios WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearCategoria(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener categoría por ID", e);
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

    // Obtener todas las categorías (no eliminadas)
    public List<CategoriaDeServicio> obtenerTodasLasCategorias() {
        String sql = "SELECT * FROM categorias_de_servicios WHERE deleted_at IS NULL";
        List<CategoriaDeServicio> categorias = new ArrayList<>();
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categorias.add(mapearCategoria(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las categorías", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
        return categorias;
    }

    // Actualizar una categoría
    public void actualizarCategoria(CategoriaDeServicio categoria) {
        String sql = "UPDATE categorias_de_servicios SET nombre = ?, descripcion = ?, id_administrador = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            // Verificar que el administrador exista y no esté eliminado
            if (!administradorExiste(categoria.getIdAdministrador(), con)) {
                throw new SQLException("El administrador con ID " + categoria.getIdAdministrador() + " no existe o está eliminado");
            }

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNombre());
                stmt.setString(2, categoria.getDescripcion());
                stmt.setInt(3, categoria.getIdAdministrador());
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(5, categoria.getId());

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("No se encontró la categoría con ID " + categoria.getId() + " o ya está eliminada");
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
            throw new RuntimeException("Error al actualizar categoría", e);
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

    // Eliminar (soft delete) una categoría
    public void eliminarCategoria(int id) {
        String sql = "UPDATE categorias_de_servicios SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
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
                    throw new SQLException("No se encontró la categoría con ID " + id + " o ya está eliminada");
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
            throw new RuntimeException("Error al eliminar categoría", e);
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

    // Método auxiliar para verificar si un administrador existe y no está eliminado
    private boolean administradorExiste(int idAdministrador, Connection con) throws SQLException {
        String sql = "SELECT 1 FROM administradores WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAdministrador);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Método auxiliar para mapear un ResultSet a un objeto CategoriaDeServicio
    private CategoriaDeServicio mapearCategoria(ResultSet rs) throws SQLException {
        CategoriaDeServicio categoria = new CategoriaDeServicio();
        categoria.setId(rs.getInt("id"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setDescripcion(rs.getString("descripcion"));
        categoria.setIdAdministrador(rs.getInt("id_administrador"));
        categoria.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        categoria.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        categoria.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        return categoria;
    }
}