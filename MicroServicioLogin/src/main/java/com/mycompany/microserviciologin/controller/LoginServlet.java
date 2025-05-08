/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.controller;

import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.model.*;
import com.mycompany.microserviciologin.service.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        UsuarioDAO usuariodao = new UsuarioDAO();
      this.usuarioService = new UsuarioService(usuariodao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        System.out.println("Correo recibido en LoginServlet: " + correo); // Depuración
        System.out.println("Contraseña recibida en LoginServlet: " + contrasena); // Depuración

        try {
            Usuario usuario = usuarioService.authenticate(correo, contrasena);
            System.out.println(usuario); 
            if (usuario != null) {
                System.out.println("Usuario autenticado: " + usuario.getCorreo()); // Depuración
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                // Redirigir según el tipo de usuario
                if (usuario instanceof Administrador) {
                    response.sendRedirect("dashboard.jsp?role=administrador");
                } else if (usuario instanceof AtencionAlCliente) {
                    response.sendRedirect("dashboard.jsp?role=atencion_al_cliente");
                } else if (usuario instanceof Dueno) {
                    response.sendRedirect("dashboardDueno.jsp?role=dueno");
                } else if (usuario instanceof Autonomo) {
                    response.sendRedirect("dashboardAutonomo.jsp?role=autonomo");
                } else if (usuario instanceof Empresa) {
                    response.sendRedirect("dashboard.jsp?role=empresa");
                } else if (usuario instanceof Cliente) {
                    response.sendRedirect("dashboard.jsp?role=cliente");
                } else {
                    request.setAttribute("error", "unknown_role");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            } else {
                System.out.println("Autenticación fallida para el correo: " + correo); // Depuración
                request.setAttribute("error", "invalid_credentials");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error al autenticar", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
}