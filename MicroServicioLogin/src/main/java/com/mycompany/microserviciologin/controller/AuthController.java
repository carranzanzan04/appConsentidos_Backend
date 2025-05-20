/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.controller;

import com.mycompany.microserviciologin.dao.UsuarioDAO;
import com.mycompany.microserviciologin.model.Administrador;
import com.mycompany.microserviciologin.model.AtencionAlCliente;
import com.mycompany.microserviciologin.model.Autonomo;
import com.mycompany.microserviciologin.model.Cliente;
import com.mycompany.microserviciologin.model.Dueno;
import com.mycompany.microserviciologin.model.Empresa;
import com.mycompany.microserviciologin.model.Usuario;
import com.mycompany.microserviciologin.service.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
public class AuthController {
   
    private final UsuarioService usuarioService;
    private final UsuarioDAO usuariodao;

    public AuthController() {
         this.usuariodao=new UsuarioDAO();
        this.usuarioService = new UsuarioService(usuariodao);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(Map<String, String> credentials) {
        String correo = credentials.get("correo");
        String contrasena = credentials.get("contrasena");

        System.out.println("Autenticación REST - Correo: " + correo);

        try {
            Usuario usuario = usuarioService.authenticate(correo, contrasena);
             
            if (usuario != null) {
                 Map<String, Object> response = new HashMap<>();
                   if(getUserRole(usuario).equals("autonomo")||getUserRole(usuario).equals("dueno")||getUserRole(usuario).equals("administrador")||getUserRole(usuario).equals("atencion_al_cliente")||getUserRole(usuario).equals("empresa")){
                       switch (getUserRole(usuario)) {
                           case "administrador" -> {
                                 Administrador adm= (Administrador )usuarioService.obtenerUsuarioEspecifico(usuario.getId());
                                 response.put("success", true);
                                 response.put("message", "Autenticación exitosa");
                                 response.put("id", usuario.getId());
                                 response.put("role", getUserRole(usuario));
                                 response.put("correo", usuario.getCorreo());
                                 response.put("nombre", adm.getNombre());
                                 response.put("apellido", adm.getApellido());
                                 System.out.println("Autenticación REST exitosa para: " + correo+":emprea"+adm.getNombre());
                           }
                           case "atencion_al_cliente" -> {
                                    AtencionAlCliente atc= (AtencionAlCliente )usuarioService.obtenerUsuarioEspecifico(usuario.getId());
                                 response.put("success", true);
                                 response.put("message", "Autenticación exitosa");
                                 response.put("id", usuario.getId());
                                 response.put("role", getUserRole(usuario));
                                 response.put("correo", usuario.getCorreo());
                                 response.put("nombre", atc.getNombre());
                                 response.put("apellido",atc.getApellido());
                                 System.out.println("Autenticación REST exitosa para: " + correo+":emprea"+atc.getNombre());
                           }
                           case "autonomo" -> {
                                 Autonomo aut =(Autonomo) usuarioService.obtenerUsuarioEspecifico(usuario.getId());
                            
                                 response.put("success", true);
                                 response.put("message", "Autenticación exitosa");
                                 response.put("id", usuario.getId());
                                 response.put("role", getUserRole(usuario));
                                 response.put("correo", usuario.getCorreo());
                                 response.put("nombre", aut.getNombre());
                                 response.put("apellido", aut.getApellido());
                                 System.out.println("Autenticación REST exitosa para: " + correo+":autonomo"+aut.getNombre() );
                           }
                           case "dueno" -> {
                                   Dueno duen =(Dueno)usuarioService.obtenerUsuarioEspecifico(usuario.getId());
                                   response.put("success", true);
                                   response.put("message", "Autenticación exitosa");
                                   response.put("id", usuario.getId());
                                   response.put("role", getUserRole(usuario));
                                   response.put("correo", usuario.getCorreo());
                                   response.put("nombre", duen.getNombre());
                                   response.put("apellido", duen.getApellido());
                                   System.out.println("Autenticación REST exitosa para: " + correo+":dueno"+duen.getNombre());
                           }
                                
                           case "empresa" -> {
                                 Empresa emp= (Empresa )usuarioService.obtenerUsuarioEspecifico(usuario.getId());
                                 response.put("success", true);
                                 response.put("message", "Autenticación exitosa");
                                 response.put("id", usuario.getId());
                                 response.put("role", getUserRole(usuario));
                                 response.put("correo", usuario.getCorreo());
                                 response.put("nombre", emp.getNombre());
                                 System.out.println("Autenticación REST exitosa para: " + correo+":emprea"+emp.getNombre());
                           }

                           default -> throw new AssertionError();
                       }
                       
                   }
                
                return Response.ok(response).build();
            } else {
                System.out.println("Autenticación REST fallida para: " + correo);
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("success", false, "message", "Credenciales inválidas"))
                        .build();
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error interno del servidor"))
                    .build();
        }
    }

    private String getUserRole(Usuario usuario) {
        if (usuario instanceof Administrador) return "administrador";
        if (usuario instanceof AtencionAlCliente) return "atencion_al_cliente";
        if (usuario instanceof Dueno) return "dueno";
        if (usuario instanceof Autonomo) return "autonomo";
        if (usuario instanceof Empresa) return "empresa";
        if (usuario instanceof Cliente) return "cliente";
        return "unknown";
    }
}