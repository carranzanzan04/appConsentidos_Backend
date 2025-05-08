package com.mycompany.microserviciologin.service;

import com.mycompany.microserviciologin.dao.AtencionAlClienteDAO;
import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.model.AtencionAlCliente;
import java.sql.SQLException;

public class AtencionAlClienteService {
    private final UsuarioDAO usuarioDAO;
    private final AtencionAlClienteDAO atencionAlClienteDAO;

    public AtencionAlClienteService(UsuarioDAO usuarioDAO, AtencionAlClienteDAO atencionAlClienteDAO) {
        this.usuarioDAO = usuarioDAO;
        this.atencionAlClienteDAO = atencionAlClienteDAO;
    }

    public void registrarAtencionAlCliente(AtencionAlCliente atencionAlCliente) {
        if (atencionAlCliente != null) {
            try {
                int id = usuarioDAO.crearUsuario(atencionAlCliente);
                if (id <= 0) {
                    throw new RuntimeException("No se pudo crear el usuario en la tabla usuarios");
                }
                atencionAlClienteDAO.registrarAtencionAlCliente(id, atencionAlCliente);
            } catch (RuntimeException e) {
                throw new RuntimeException("Error al registrar usuario de atención al cliente", e);
            }
        } else {
            throw new IllegalArgumentException("El usuario de atención al cliente no puede ser null");
        }
    }

    public void actualizarAtencionAlCliente(AtencionAlCliente atencionAlCliente) {
        if (atencionAlCliente != null) {
            try {
                usuarioDAO.actualizarUsuario(atencionAlCliente);
                atencionAlClienteDAO.actualizarAtencionAlCliente(atencionAlCliente);
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar usuario de atención al cliente", e);
            }
        } else {
            throw new IllegalArgumentException("El usuario de atención al cliente no puede ser null");
        }
    }

    public void eliminarAtencionAlCliente(int id) {
        try {
            atencionAlClienteDAO.eliminarAtencionAlCliente(id);
            usuarioDAO.eliminarUsuario(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario de atención al cliente", e);
        }
    }
}