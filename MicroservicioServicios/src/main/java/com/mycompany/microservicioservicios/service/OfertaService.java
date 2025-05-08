/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.service;

import com.mycompany.microservicioservicios.dao.OfertaDAO;
import com.mycompany.microservicioservicios.model.Oferta;
import com.mycompany.microservicioservicios.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class OfertaService {
    private final OfertaDAO ofertaDAO;

    public OfertaService(OfertaDAO ofertaDAO) {
        this.ofertaDAO = ofertaDAO;
    }

    public int crearOferta(Oferta oferta) {
        if (oferta == null || oferta.getCosto() <= 0 || oferta.getNumeroTurnos() <= 0 ||
            oferta.getFechaInicio() == null || oferta.getFechaFin() == null || oferta.getIdServicio() <= 0) {
            throw new IllegalArgumentException("Los campos costo, numeroTurnos, fechaInicio, fechaFin e idServicio son obligatorios");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            int id = ofertaDAO.crearOferta(oferta);
            oferta.setId(id);

            con.commit();
            return id;
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
        if (oferta == null || oferta.getId() <= 0 || oferta.getCosto() <= 0 || oferta.getNumeroTurnos() <= 0 ||
            oferta.getFechaInicio() == null || oferta.getFechaFin() == null || oferta.getIdServicio() <= 0) {
            throw new IllegalArgumentException("Los campos id, costo, numeroTurnos, fechaInicio, fechaFin e idServicio son obligatorios");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            ofertaDAO.actualizarOferta(oferta);

            con.commit();
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
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            ofertaDAO.eliminarOferta(id);

            con.commit();
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