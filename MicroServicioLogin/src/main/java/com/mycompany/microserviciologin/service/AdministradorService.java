package com.mycompany.microserviciologin.service;

import com.mycompany.microserviciologin.dao.AdministradorDAO;
import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.model.Administrador;
import java.sql.SQLException;

public class AdministradorService {
    private final UsuarioDAO usuarioDAO;
    private final AdministradorDAO administradorDAO;

    public AdministradorService(UsuarioDAO usuarioDAO, AdministradorDAO administradorDAO) {
        this.usuarioDAO = usuarioDAO;
        this.administradorDAO = administradorDAO;
    }

    public void registrarAdministrador(Administrador administrador) {
        if (administrador != null) {
            try {
                int id = usuarioDAO.crearUsuario(administrador);
                if (id <= 0) {
                    throw new RuntimeException("No se pudo crear el usuario en la tabla usuarios");
                }
                administradorDAO.registrarAdministrador(id, administrador);
            } catch (RuntimeException e) {
                throw new RuntimeException("Error al registrar administrador", e);
            }
        } else {
            throw new IllegalArgumentException("El administrador no puede ser null");
        }
    }

    public void actualizarAdministrador(Administrador administrador) {
        if (administrador != null) {
            try {
                usuarioDAO.actualizarUsuario(administrador);
                administradorDAO.actualizarAdministrador(administrador);
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar administrador", e);
            }
        } else {
            throw new IllegalArgumentException("El administrador no puede ser null");
        }
    }

    public void eliminarAdministrador(int id) {
        try {
            administradorDAO.eliminarAdministrador(id);
            usuarioDAO.eliminarUsuario(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar administrador", e);
        }
    }
}
