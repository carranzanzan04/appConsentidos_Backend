/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.service;

import com.mycompany.microservicioservicios.dao.ServicioPorMascotaDAO;
import com.mycompany.microservicioservicios.model.ServicioPorMascota;
import com.mycompany.microservicioservicios.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class ServicioPorMascotaService {
    private final ServicioPorMascotaDAO servicioPorMascotaDAO;

    public ServicioPorMascotaService(ServicioPorMascotaDAO servicioPorMascotaDAO) {
        this.servicioPorMascotaDAO = servicioPorMascotaDAO;
    }

    public int crearServicioPorMascota(ServicioPorMascota servicioPorMascota) {
        if (servicioPorMascota == null || servicioPorMascota.getIdOferta() <= 0 ||
            servicioPorMascota.getIdPago() <= 0 || servicioPorMascota.getIdMascota() <= 0) {
            throw new IllegalArgumentException("Los campos idOferta, idPago e idMascota son obligatorios");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            int id = servicioPorMascotaDAO.crearServicioPorMascota(servicioPorMascota);
            servicioPorMascota.setId(id);

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
        if (servicioPorMascota == null || servicioPorMascota.getId() <= 0 ||
            servicioPorMascota.getIdOferta() <= 0 || servicioPorMascota.getIdPago() <= 0 ||
            servicioPorMascota.getIdMascota() <= 0) {
            throw new IllegalArgumentException("Los campos id, idOferta, idPago e idMascota son obligatorios");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            servicioPorMascotaDAO.actualizarServicioPorMascota(servicioPorMascota);

            con.commit();
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
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            servicioPorMascotaDAO.eliminarServicioPorMascota(id);

            con.commit();
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
