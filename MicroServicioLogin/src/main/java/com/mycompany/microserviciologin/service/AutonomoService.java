/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.service;

import com.mycompany.microserviciologin.model.Autonomo;
import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.dao.ClienteDAO;
import com.mycompany.microserviciologin.dao.AutonomoDAO;
import com.mycompany.microserviciologin.dao.PrestadorDAO;
import java.sql.SQLException;

public class AutonomoService {
    private final UsuarioDAO usuarioDAO;
    private final ClienteDAO clienteDAO;
    private final PrestadorDAO prestadorDAO;
    private final AutonomoDAO autonomoDAO;

    public AutonomoService(UsuarioDAO usuarioDAO, ClienteDAO clienteDAO,PrestadorDAO prestadorDAO, AutonomoDAO autonomoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.clienteDAO = clienteDAO;
        this.prestadorDAO=prestadorDAO;
        this.autonomoDAO = autonomoDAO;
    }

    public void registrarAutonomo(Autonomo autonomo) {
        if (autonomo != null) {
            try {
                int idu = usuarioDAO.crearUsuario(autonomo);
                if (idu <= 0) {
                    throw new RuntimeException("No se pudo crear el usuario en la tabla usuarios");
                }
                int idc = clienteDAO.registrarCliente(idu);
                if (idc <= 0) {
                    throw new RuntimeException("No se pudo registrar el cliente en la tabla clientes");
                }
                prestadorDAO.crearPrestador(idc);
                autonomoDAO.registrarAutonomo(idc, autonomo);
            } catch (RuntimeException e) {
                throw new RuntimeException("Error al registrar autónomo", e);
            }
        } else {
            throw new IllegalArgumentException("El autónomo no puede ser null");
        }
    }

    public void actualizarAutonomo(Autonomo autonomo) {
        if (autonomo != null) {
            try {
                usuarioDAO.actualizarUsuario(autonomo);
                autonomoDAO.actualizarAutonomo(autonomo);
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar autónomo", e);
            }
        } else {
            throw new IllegalArgumentException("El autónomo no puede ser null");
        }
    }

    public void eliminarAutonomo(int id) {
        try {
            autonomoDAO.eliminarAutonomo(id);
            usuarioDAO.eliminarUsuario(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar autónomo", e);
        }
    }
}
