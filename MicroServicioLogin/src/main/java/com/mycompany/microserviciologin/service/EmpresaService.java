/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.service;

import com.mycompany.microserviciologin.model.Empresa;
import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.dao.ClienteDAO;
import com.mycompany.microserviciologin.dao.EmpresaDAO;
import com.mycompany.microserviciologin.dao.PrestadorDAO;
import java.sql.SQLException;

public class EmpresaService {
    private final UsuarioDAO usuarioDAO;
    private final ClienteDAO clienteDAO;
    private final PrestadorDAO prestadorDAO;
    private final EmpresaDAO empresaDAO;

    public EmpresaService(UsuarioDAO usuarioDAO, ClienteDAO clienteDAO,PrestadorDAO prestadorDAO, EmpresaDAO empresaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.clienteDAO = clienteDAO;
        this.prestadorDAO=prestadorDAO;
        this.empresaDAO = empresaDAO;
    }

    public void registrarEmpresa(Empresa empresa) {
        if (empresa != null) {
            try {
                int idu = usuarioDAO.crearUsuario(empresa);
                if (idu <= 0) {
                    throw new RuntimeException("No se pudo crear el usuario en la tabla usuarios");
                }
                int idc = clienteDAO.registrarCliente(idu);
                if (idc <= 0) {
                    throw new RuntimeException("No se pudo registrar el cliente en la tabla clientes");
                }
                prestadorDAO.crearPrestador(idc);
                empresaDAO.registrarEmpresa(idc, empresa);
            } catch (RuntimeException e) {
                throw new RuntimeException("Error al registrar empresa", e);
            }
        } else {
            throw new IllegalArgumentException("La empresa no puede ser null");
        }
    }

    public void actualizarEmpresa(Empresa empresa) {
        if (empresa != null) {
            try {
                usuarioDAO.actualizarUsuario(empresa);
                empresaDAO.actualizarEmpresa(empresa);
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar empresa", e);
            }
        } else {
            throw new IllegalArgumentException("La empresa no puede ser null");
        }
    }

    public void eliminarEmpresa(int id) {
        try {
            empresaDAO.eliminarEmpresa(id);
            usuarioDAO.eliminarUsuario(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar empresa", e);
        }
    }
}