/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.service;

import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.model.Usuario;

import java.sql.SQLException;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    public Usuario authenticate(String correo, String contrasena) throws SQLException {
        return usuarioDAO.authenticate(correo, contrasena);
    }
}