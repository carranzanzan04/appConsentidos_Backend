/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.dao;

import com.mycompany.microservicioservicios.model.Servicio;
import com.mycompany.microservicioservicios.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public int crearServicio(Servicio servicio) {
        String sql = "INSERT INTO servicios (nombre, descripcion, id_prestador, id_categoria, created_at) VALUES (?, ?, ?, ?, ?)";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, servicio.getNombre());
                stmt.setString(2, servicio.getDescripcion());
                stmt.setInt(3, servicio.getIdPrestador());
                stmt.setInt(4, servicio.getIdCategoria());
                stmt.setTimestamp(5, Timestamp.valueOf(servicio.getCreatedAt()));

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("Error al crear el servicio");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener el ID generado para el servicio");
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
            throw new RuntimeException("Error al crear servicio", e);
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
public List<Servicio> listarServicios() {
        String sql = "SELECT id, nombre, descripcion, id_prestador, id_categoria, created_at, updated_at, deleted_at " +
                     "FROM servicios WHERE deleted_at IS NULL";
        List<Servicio> servicios = new ArrayList<>();
        Connection con = null;

        try {
            con = DatabaseUtil.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    int idPrestador = rs.getInt("id_prestador");
                    int idCategoria = rs.getInt("id_categoria");
                    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null ? 
                                             rs.getTimestamp("updated_at").toLocalDateTime() : null;
                    LocalDateTime deletedAt = rs.getTimestamp("deleted_at") != null ? 
                                             rs.getTimestamp("deleted_at").toLocalDateTime() : null;

                    Servicio servicio = new Servicio(id, nombre, descripcion, idPrestador, idCategoria, 
                                                    createdAt, updatedAt, deletedAt);
                    servicios.add(servicio);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar servicios", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
        return servicios;
    }

 public Servicio obtenerServicio(int id) {
        String sql = "SELECT id, nombre, descripcion, id_prestador, id_categoria, created_at, updated_at, deleted_at " +
                     "FROM servicios WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;

        try {
            con = DatabaseUtil.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int servicioId = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        String descripcion = rs.getString("descripcion");
                        int idPrestador = rs.getInt("id_prestador");
                        int idCategoria = rs.getInt("id_categoria");
                        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                        LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null ? 
                                                 rs.getTimestamp("updated_at").toLocalDateTime() : null;
                        LocalDateTime deletedAt = rs.getTimestamp("deleted_at") != null ? 
                                                 rs.getTimestamp("deleted_at").toLocalDateTime() : null;

                        return new Servicio(servicioId, nombre, descripcion, idPrestador, idCategoria, 
                                            createdAt, updatedAt, deletedAt);
                    } else {
                        return null; // Servicio no encontrado o eliminado
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener servicio por ID", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
    }

    
    
    public void actualizarServicio(Servicio servicio) {
        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, id_prestador = ?, id_categoria = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, servicio.getNombre());
                stmt.setString(2, servicio.getDescripcion());
                stmt.setInt(3, servicio.getIdPrestador());
                stmt.setInt(4, servicio.getIdCategoria());
                stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(6, servicio.getId());

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("No se encontró el servicio con ID " + servicio.getId() + " o ya está eliminado");
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
            throw new RuntimeException("Error al actualizar servicio", e);
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

    public void eliminarServicio(int id) {
        String sql = "UPDATE servicios SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                LocalDateTime now = LocalDateTime.now();
                stmt.setTimestamp(1, Timestamp.valueOf(now));
                stmt.setTimestamp(2, Timestamp.valueOf(now));
                stmt.setInt(3, id);

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("No se encontró el servicio con ID " + id + " o ya está eliminado");
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
            throw new RuntimeException("Error al eliminar servicio", e);
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
}
