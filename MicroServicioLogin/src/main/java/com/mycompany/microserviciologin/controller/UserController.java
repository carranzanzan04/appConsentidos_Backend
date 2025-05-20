/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.controller;

import com.mycompany.microserviciologin.model.*;
import com.mycompany.microserviciologin.service.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.Map;

@Path("/users")
public class UserController {

    private final UsuarioService usuarioService;
    private final AdministradorService administradorService;
    private final AtencionAlClienteService atencionAlClienteService;
    private final AutonomoService autonomoService;
    private final DuenoService duenoService;
    private final EmpresaService empresaService;

    public UserController() {
        com.mycompany.microserviciologin.dao.UsuarioDAO usuarioDAO = new com.mycompany.microserviciologin.dao.UsuarioDAO();
        com.mycompany.microserviciologin.dao.AdministradorDAO administradorDAO = new com.mycompany.microserviciologin.dao.AdministradorDAO();
        com.mycompany.microserviciologin.dao.AtencionAlClienteDAO atencionAlClienteDAO = new com.mycompany.microserviciologin.dao.AtencionAlClienteDAO();
        com.mycompany.microserviciologin.dao.ClienteDAO clienteDAO = new com.mycompany.microserviciologin.dao.ClienteDAO();
       com.mycompany.microserviciologin.dao.PrestadorDAO prestadorDAO= new com.mycompany.microserviciologin.dao.PrestadorDAO();
        com.mycompany.microserviciologin.dao.AutonomoDAO autonomoDAO = new com.mycompany.microserviciologin.dao.AutonomoDAO();
        com.mycompany.microserviciologin.dao.DuenoDAO duenoDAO = new com.mycompany.microserviciologin.dao.DuenoDAO();
        com.mycompany.microserviciologin.dao.EmpresaDAO empresaDAO = new com.mycompany.microserviciologin.dao.EmpresaDAO();

        this.usuarioService = new UsuarioService(usuarioDAO);
        this.administradorService = new AdministradorService(usuarioDAO, administradorDAO);
        this.atencionAlClienteService = new AtencionAlClienteService(usuarioDAO, atencionAlClienteDAO);
        this.autonomoService = new AutonomoService(usuarioDAO, clienteDAO,prestadorDAO, autonomoDAO);
        this.duenoService = new DuenoService(usuarioDAO, clienteDAO, duenoDAO);
        this.empresaService = new EmpresaService(usuarioDAO, clienteDAO,prestadorDAO, empresaDAO);
    }

    @POST
    @Path("/register/client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerClient(Map<String, Object> request) {
        try {
            String tipo = (String) request.get("tipo");
            String correo = (String) request.get("correo");
            String contrasena = (String) request.get("contrasena");
            String nid = (String) request.get("nid");
            boolean activo = (Boolean) request.get("activo");

            if (correo == null || contrasena == null || nid == null || tipo == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            switch (tipo) {
                case "Dueno":
                    String nombreDueno = (String) request.get("nombre");
                    String apellidoDueno = (String) request.get("apellido");
                    if (nombreDueno == null || apellidoDueno == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Dueño"))
                                .build();
                    }
                    Dueno dueno = new Dueno(0, correo, contrasena, true, nid, LocalDateTime.now(), null, null, activo, nombreDueno, apellidoDueno);
                    duenoService.registrarDueno(dueno);
                    break;

                case "Autonomo":
                    String nombreAutonomo = (String) request.get("nombre");
                    String apellidoAutonomo = (String) request.get("apellido");
                    if (nombreAutonomo == null || apellidoAutonomo == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Autónomo"))
                                .build();
                    }
                    Autonomo autonomo = new Autonomo(0, correo, contrasena, true, nid, LocalDateTime.now(), null, null, activo, nombreAutonomo, apellidoAutonomo);
                    autonomoService.registrarAutonomo(autonomo);
                    break;

                case "Empresa":
                    String nombreEmpresa = (String) request.get("nombre");
                    if (nombreEmpresa == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Empresa"))
                                .build();
                    }
                    Empresa empresa = new Empresa(0, correo, contrasena, true, nid, LocalDateTime.now(), null, null, activo, nombreEmpresa);
                    empresaService.registrarEmpresa(empresa);
                    break;

                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("success", false, "message", "Tipo de cliente inválido"))
                            .build();
            }

            return Response.ok(Map.of("success", true, "message", tipo + " registrado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al registrar cliente: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/register/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerStaff(Map<String, Object> request) {
        try {
            String tipo = (String) request.get("tipo");
            String correo = (String) request.get("correo");
            String contrasena = (String) request.get("contrasena");
            String nid = (String) request.get("nid");
            boolean activo = (Boolean) request.get("activo");

            if (correo == null || contrasena == null || nid == null || tipo == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            switch (tipo) {
                case "Administrador":
                    String nombreAdmin = (String) request.get("nombre");
                    String apellidoAdmin = (String) request.get("apellido");
                    if (nombreAdmin == null || apellidoAdmin == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Administrador"))
                                .build();
                    }
                    Administrador administrador = new Administrador(0, correo, contrasena, true, nid, LocalDateTime.now(), null, null, activo, nombreAdmin, apellidoAdmin);
                    administradorService.registrarAdministrador(administrador);
                    break;

                case "AtencionAlCliente":
                    String nombreAtc = (String) request.get("nombre");
                    String apellidoAtc = (String) request.get("apellido");
                    if (nombreAtc == null || apellidoAtc == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Atención al Cliente"))
                                .build();
                    }
                    AtencionAlCliente atencionAlCliente = new AtencionAlCliente(0, correo, contrasena, true, nid, LocalDateTime.now(), null, null, activo, nombreAtc, apellidoAtc);
                    atencionAlClienteService.registrarAtencionAlCliente(atencionAlCliente);
                    break;

                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("success", false, "message", "Tipo de staff inválido"))
                            .build();
            }

            return Response.ok(Map.of("success", true, "message", tipo + " registrado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al registrar staff: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/update/client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            String tipo = (String) request.get("tipo");
            String correo = (String) request.get("correo");
            String nid = (String) request.get("nid");
            if (id == null || tipo == null || correo == null  || nid == null ) {
                System.out.println(id+" "+tipo+" "+ correo+" "+nid);
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            switch (tipo) {
                case "Dueno":
                    String nombreDueno = (String) request.get("nombre");
                    String apellidoDueno = (String) request.get("apellido");
                    if (nombreDueno == null || apellidoDueno == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Dueño"))
                                .build();
                    }
                    Dueno dueno = new Dueno();
                    dueno.setId(id);
                    dueno.setCorreo(correo);
                    dueno.setNid(nid);
                    dueno.setNombre(nombreDueno);
                    dueno.setApellido(apellidoDueno);
                    duenoService.actualizarDueno(dueno);
                    break;

                case "Autonomo":
                    String nombreAutonomo = (String) request.get("nombre");
                    String apellidoAutonomo = (String) request.get("apellido");
                    if (nombreAutonomo == null || apellidoAutonomo == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Autónomo"))
                                .build();
                    }
                    Autonomo autonomo = new Autonomo();
                    autonomo.setId(id);
                    autonomo.setCorreo(correo);
                    autonomo.setNid(nid);
                    autonomo.setNombre(nombreAutonomo);
                    autonomo.setApellido(apellidoAutonomo);
                    autonomoService.actualizarAutonomo(autonomo);
                    break;

                case "Empresa":
                    String nombreEmpresa = (String) request.get("nombre");
                    if (nombreEmpresa == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Empresa"))
                                .build();
                    }
                    Empresa empresa = new Empresa();
                    empresa.setId(id);
                    empresa.setCorreo(correo);
                    empresa.setNid(nid);
                    empresa.setNombre(nombreEmpresa);
                    empresaService.actualizarEmpresa(empresa);
                    break;

                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("success", false, "message", "Tipo de cliente inválido"))
                            .build();
            }

            return Response.ok(Map.of("success", true, "message", tipo + " actualizado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al actualizar cliente: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/update/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStaff(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            String tipo = (String) request.get("tipo");
            String correo = (String) request.get("correo");
            String nid = (String) request.get("nid");
            

            if (id == null || tipo == null || correo == null ||  nid == null ) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            switch (tipo) {
                case "Administrador":
                    String nombreAdmin = (String) request.get("nombre");
                    String apellidoAdmin = (String) request.get("apellido");
                    if (nombreAdmin == null || apellidoAdmin == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Administrador"))
                                .build();
                    }
                    Administrador administrador = new Administrador();
                    administrador.setId(id);
                    administrador.setCorreo(correo);
                    administrador. setNid(nid);
                    administrador.setNombre(nombreAdmin);
                    administrador.setApellido(apellidoAdmin);
                    administradorService.actualizarAdministrador(administrador);
                    break;

                case "AtencionAlCliente":
                    String nombreAtc = (String) request.get("nombre");
                    String apellidoAtc = (String) request.get("apellido");
                    if (nombreAtc == null || apellidoAtc == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("success", false, "message", "Faltan campos obligatorios para Atención al Cliente"))
                                .build();
                    }
                    AtencionAlCliente atencionAlCliente = new AtencionAlCliente();
                    atencionAlCliente.setId(id);
                    atencionAlCliente.setCorreo(correo);
                    atencionAlCliente.setNid(nid);
                    atencionAlCliente.setNombre(nombreAtc);
                    atencionAlCliente.setApellido(apellidoAtc);
                    atencionAlClienteService.actualizarAtencionAlCliente(atencionAlCliente);
                    break;

                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("success", false, "message", "Tipo de staff inválido"))
                            .build();
            }

            return Response.ok(Map.of("success", true, "message", tipo + " actualizado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al actualizar staff: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/delete/client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClient(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            String tipo = (String) request.get("tipo");

            if (id == null || tipo == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            switch (tipo) {
                case "Dueno":
                    duenoService.eliminarDueno(id);
                    break;
                case "Autonomo":
                    autonomoService.eliminarAutonomo(id);
                    break;
                case "Empresa":
                    empresaService.eliminarEmpresa(id);
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("success", false, "message", "Tipo de cliente inválido"))
                            .build();
            }

            return Response.ok(Map.of("success", true, "message", tipo + " eliminado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al eliminar cliente: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/delete/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStaff(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            String tipo = (String) request.get("tipo");

            if (id == null || tipo == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            switch (tipo) {
                case "Administrador":
                    administradorService.eliminarAdministrador(id);
                    break;
                case "AtencionAlCliente":
                    atencionAlClienteService.eliminarAtencionAlCliente(id);
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("success", false, "message", "Tipo de staff inválido"))
                            .build();
            }

            return Response.ok(Map.of("success", true, "message", tipo + " eliminado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al eliminar staff: " + e.getMessage()))
                    .build();
        }
    }
}