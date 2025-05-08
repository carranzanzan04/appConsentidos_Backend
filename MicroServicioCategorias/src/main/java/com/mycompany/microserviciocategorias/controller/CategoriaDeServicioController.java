/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciocategorias.controller;


import com.mycompany.microserviciocategorias.model.CategoriaDeServicio;
import com.mycompany.microserviciocategorias.service.CategoriaDeServicioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("/categorias")
public class CategoriaDeServicioController {

    private final CategoriaDeServicioService categoriaService;

    public CategoriaDeServicioController() {
        this.categoriaService = new CategoriaDeServicioService(new com.mycompany.microserviciocategorias.dao.CategoriaDeServicioDAO());
    }

    // Crear una categoría (POST /api/categorias/crear)
    
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCategoria(Map<String, Object> request) {
        try {
            String nombre = (String) request.get("nombre");
            String descripcion = (String) request.get("descripcion");
            Integer idAdministrador = (Integer) request.get("id_administrador");

            if (nombre == null || descripcion == null || idAdministrador == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            CategoriaDeServicio categoria = new CategoriaDeServicio();
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setIdAdministrador(idAdministrador);

            int id = categoriaService.crearCategoria(categoria);
            return Response.ok(Map.of("success", true, "message", "Categoría creada exitosamente", "id", id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al crear categoría: " + e.getMessage()))
                    .build();
        }
    }

    // Obtener una categoría por ID (GET /api/categorias/obtener/{id})
    @GET
    @Path("/obtener/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCategoriaPorId(@PathParam("id") int id) {
        try {
            CategoriaDeServicio categoria = categoriaService.obtenerCategoriaPorId(id);
            return Response.ok(categoria).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("success", false, "message", e.getMessage()))
                    .build();
        }
    }

    // Obtener todas las categorías (GET /api/categorias/listar)
    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodasLasCategorias() {
        try {
            List<CategoriaDeServicio> categorias = categoriaService.obtenerTodasLasCategorias();
            return Response.ok(categorias).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al obtener categorías: " + e.getMessage()))
                    .build();
        }
    }

    // Actualizar una categoría (PUT /api/categorias/actualizar/{id})
    @PUT
    @Path("/actualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCategoria(@PathParam("id") int id, Map<String, Object> request) {
        try {
            String nombre = (String) request.get("nombre");
            String descripcion = (String) request.get("descripcion");
            Integer idAdministrador = (Integer) request.get("id_administrador");

            if (nombre == null || descripcion == null || idAdministrador == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            CategoriaDeServicio categoria = new CategoriaDeServicio();
            categoria.setId(id);
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setIdAdministrador(idAdministrador);

            categoriaService.actualizarCategoria(categoria);
            return Response.ok(Map.of("success", true, "message", "Categoría actualizada exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al actualizar categoría: " + e.getMessage()))
                    .build();
        }
    }

    // Eliminar una categoría (DELETE /api/categorias/eliminar/{id})
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarCategoria(@PathParam("id") int id) {
        try {
            categoriaService.eliminarCategoria(id);
            return Response.ok(Map.of("success", true, "message", "Categoría eliminada exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al eliminar categoría: " + e.getMessage()))
                    .build();
        }
    }
}
