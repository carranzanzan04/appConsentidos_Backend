/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.dao;

import com.mycompany.microservicioservicios.model.ServicioPorMascota;
import com.mycompany.microservicioservicios.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class ServicioPorMascotaDAO {

    public int crearServicioPorMascota(ServicioPorMascota servicioPorMascota) {
        String sql = "INSERT INTO servicios_por_mascota (id_oferta, id_pago, id_mascota, fecha_inicio, fecha_final, atencion, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, servicioPorMascota.getIdOferta());
                stmt.setInt(2, servicioPorMascota.getIdPago());
                stmt.setInt(3, servicioPorMascota.getIdMascota());
                stmt.setTimestamp(4, servicioPorMascota.getFechaInicio() != null ? Timestamp.valueOf(servicioPorMascota.getFechaInicio()) : null);
                stmt.setTimestamp(5, servicioPorMascota.getFechaFinal() != null ? Timestamp.valueOf(servicioPorMascota.getFechaFinal()) : null);
                stmt.setBoolean(6, servicioPorMascota.isAtencion());
                stmt.setTimestamp(7, Timestamp.valueOf(servicioPorMascota.getCreatedAt()));

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("Error al crear el servicio por mascota");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener el ID generado para el servicio por mascota");
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
            throw new RuntimeException("Error al crear servicio por mascota", e);
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

    public void actualizarServicioPorMascota(ServicioPorMascota servicioPorMascota) {
        String sql = "UPDATE servicios_por_mascota SET id_oferta = ?, id_pago = ?, id_mascota = ?, fecha_inicio = ?, fecha_final = ?, atencion = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, servicioPorMascota.getIdOferta());
                stmt.setInt(2, servicioPorMascota.getIdPago());
                stmt.setInt(3, servicioPorMascota.getIdMascota());
                stmt.setTimestamp(4, servicioPorMascota.getFechaInicio() != null ? Timestamp.valueOf(servicioPorMascota.getFechaInicio()) : null);
                stmt.setTimestamp(5, servicioPorMascota.getFechaFinal() != null ? Timestamp.valueOf(servicioPorMascota.getFechaFinal()) : null);
                stmt.setBoolean(6, servicioPorMascota.isAtencion());
                stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(8, servicioPorMascota.getId());

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("No se encontró el servicio por mascota con ID " + servicioPorMascota.getId() + " o ya está eliminado");
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
            throw new RuntimeException("Error al actualizar servicio por mascota", e);
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

    public void eliminarServicioPorMascota(int id) {
        String sql = "UPDATE servicios_por_mascota SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
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
                    throw new SQLException("No se encontró el servicio por mascota con ID " + id + " o ya está eliminado");
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
            throw new RuntimeException("Error al eliminar servicio por mascota", e);
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
