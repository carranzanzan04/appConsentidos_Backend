<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.microserviciologin.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Bienvenido, <%= ((Usuario) session.getAttribute("usuario")).getCorreo() %></h2>
    <p>Rol: <%= request.getParameter("role") %></p>
    <% 
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario instanceof Persona) {
            Persona persona = (Persona) usuario;
    %>
        <p>Nombre: <%= persona.getNombre() %> <%= persona.getApellido() %></p>
    <% 
        } else if (usuario instanceof Empresa) {
            Empresa empresa = (Empresa) usuario;
    %>
        <p>Nombre: <%= empresa.getNombre() %></p>
    <% } %>
    <a href="logout">Cerrar Sesión</a>
</body>
</html>