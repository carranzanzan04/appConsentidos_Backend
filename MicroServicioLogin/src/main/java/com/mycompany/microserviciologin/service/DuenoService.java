/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.service;

import com.mycompany.microserviciologin.model.Dueno;
import com.mycompany.microserviciologin.dao.DuenoDAO;
import com.mycompany.microserviciologin.dao.ClienteDAO;
import com.mycompany.microserviciologin.dao.UsuarioDAO;
import java.sql.SQLException;

public class DuenoService {
    private final UsuarioDAO usuarioDAO;
    private final ClienteDAO clienteDAO;
    private final DuenoDAO duenoDAO;

    public DuenoService(UsuarioDAO usuarioDAO, ClienteDAO clienteDAO, DuenoDAO duenoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.clienteDAO = clienteDAO;
        this.duenoDAO = duenoDAO;
    }

    public void registrarDueno(Dueno dueno) {
        if (dueno != null) {
            try {
                int idu = usuarioDAO.crearUsuario(dueno);
                if (idu <= 0) {
                    throw new RuntimeException("No se pudo crear el usuario en la tabla usuarios");
                }
                int idc = clienteDAO.registrarCliente(idu);
                if (idc <= 0) {
                    throw new RuntimeException("No se pudo registrar el cliente en la tabla clientes");
                }
                duenoDAO.registrarDuenos(idc, dueno);
            } catch (RuntimeException e) {
                throw new RuntimeException("Error al registrar dueño", e);
            }
        } else {
            throw new IllegalArgumentException("El dueño no puede ser null");
        }
    }

    public void actualizarDueno(Dueno dueno) {
        if (dueno != null) {
            try {
                usuarioDAO.actualizarUsuario(dueno);
                duenoDAO.actualizarDueno(dueno);
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar dueño", e);
            }
        } else {
            throw new IllegalArgumentException("El dueño no puede ser null");
        }
    }

    public void eliminarDueno(int id) {
        try {
            duenoDAO.eliminarDueno(id);
            usuarioDAO.eliminarUsuario(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar dueño", e);
        }
    }
}
