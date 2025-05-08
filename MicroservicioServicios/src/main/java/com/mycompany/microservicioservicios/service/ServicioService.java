/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.service;

import com.mycompany.microservicioservicios.dao.ServicioDAO;
import com.mycompany.microservicioservicios.model.Servicio;
import com.mycompany.microservicioservicios.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServicioService {
    private final ServicioDAO servicioDAO;

    public ServicioService(ServicioDAO servicioDAO) {
        this.servicioDAO = servicioDAO;
    }

    public int crearServicio(Servicio servicio) {
        if (servicio == null || servicio.getNombre() == null || servicio.getNombre().trim().isEmpty() ||
            servicio.getDescripcion() == null || servicio.getDescripcion().trim().isEmpty() ||
            servicio.getIdPrestador() <= 0 || servicio.getIdCategoria() <= 0) {
            throw new IllegalArgumentException("Los campos nombre, descripcion, idPrestador e idCategoria son obligatorios");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            int id = servicioDAO.crearServicio(servicio);
            servicio.setId(id);

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
        return servicioDAO.listarServicios();
    }
 public Servicio obtenerServicio(int id) {
        return servicioDAO.obtenerServicio(id);
    }
    
    public void actualizarServicio(Servicio servicio) {
        if (servicio == null || servicio.getId() <= 0 ||
            servicio.getNombre() == null || servicio.getNombre().trim().isEmpty() ||
            servicio.getDescripcion() == null || servicio.getDescripcion().trim().isEmpty() ||
            servicio.getIdPrestador() <= 0 || servicio.getIdCategoria() <= 0) {
            throw new IllegalArgumentException("Los campos id, nombre, descripcion, idPrestador e idCategoria son obligatorios");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            servicioDAO.actualizarServicio(servicio);

            con.commit();
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
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0");
        }

        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            servicioDAO.eliminarServicio(id);

            con.commit();
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
