/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.dao;

import com.mycompany.microservicioservicios.model.Oferta;
import com.mycompany.microservicioservicios.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class OfertaDAO {

    public int crearOferta(Oferta oferta) {
        String sql = "INSERT INTO ofertas (costo, numero_turnos, fecha_inicio, fecha_fin, id_servicio, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setDouble(1, oferta.getCosto());
                stmt.setInt(2, oferta.getNumeroTurnos());
                stmt.setTimestamp(3, Timestamp.valueOf(oferta.getFechaInicio()));
                stmt.setTimestamp(4, Timestamp.valueOf(oferta.getFechaFin()));
                stmt.setInt(5, oferta.getIdServicio());
                stmt.setTimestamp(6, Timestamp.valueOf(oferta.getCreatedAt()));

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("Error al crear la oferta");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener el ID generado para la oferta");
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
            throw new RuntimeException("Error al crear oferta", e);
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

    public void actualizarOferta(Oferta oferta) {
        String sql = "UPDATE ofertas SET costo = ?, numero_turnos = ?, fecha_inicio = ?, fecha_fin = ?, id_servicio = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setDouble(1, oferta.getCosto());
                stmt.setInt(2, oferta.getNumeroTurnos());
                stmt.setTimestamp(3, Timestamp.valueOf(oferta.getFechaInicio()));
                stmt.setTimestamp(4, Timestamp.valueOf(oferta.getFechaFin()));
                stmt.setInt(5, oferta.getIdServicio());
                stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(7, oferta.getId());

                int affectedRows = stmt.executeUpdate();
                con.commit();

                if (affectedRows == 0) {
                    throw new SQLException("No se encontró la oferta con ID " + oferta.getId() + " o ya está eliminada");
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
            throw new RuntimeException("Error al actualizar oferta", e);
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

    public void eliminarOferta(int id) {
        String sql = "UPDATE ofertas SET deleted_at = ?, updated_at = ? WHERE id = ? AND deleted_at IS NULL";
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
                    throw new SQLException("No se encontró la oferta con ID " + id + " o ya está eliminada");
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
            throw new RuntimeException("Error al eliminar oferta", e);
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
