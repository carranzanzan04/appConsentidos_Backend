/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.controller;

import com.mycompany.microservicioservicios.dao.OfertaDAO;
import com.mycompany.microservicioservicios.dao.ServicioDAO;
import com.mycompany.microservicioservicios.dao.ServicioPorMascotaDAO;
import com.mycompany.microservicioservicios.model.*;
import com.mycompany.microservicioservicios.service.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("/servicios")
public class ServicioController {

    private final ServicioService servicioService;
    private final OfertaService ofertaService;
    private final ServicioPorMascotaService servicioPorMascotaService;

    public ServicioController() {
        ServicioDAO servicioDAO = new ServicioDAO();
        OfertaDAO ofertaDAO = new OfertaDAO();
        ServicioPorMascotaDAO servicioPorMascotaDAO = new ServicioPorMascotaDAO();

        this.servicioService = new ServicioService(servicioDAO);
        this.ofertaService = new OfertaService(ofertaDAO);
        this.servicioPorMascotaService = new ServicioPorMascotaService(servicioPorMascotaDAO);
    }

    @POST
    @Path("/register/servicio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerServicio(Map<String, Object> request) {
        try {
            String nombre = (String) request.get("nombre");
            String descripcion = (String) request.get("descripcion");
            Integer idPrestador = (Integer) request.get("idPrestador");
            Integer idCategoria = (Integer) request.get("idCategoria");

            if (nombre == null || descripcion == null || idPrestador == null || idCategoria == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            Servicio servicio = new Servicio(0, nombre, descripcion, idPrestador, idCategoria, LocalDateTime.now(), null, null);
            int id = servicioService.crearServicio(servicio);

            return Response.ok(Map.of("success", true, "message", "Servicio registrado exitosamente", "id", id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al registrar servicio: " + e.getMessage()))
                    .build();
        }
    }
    
 @GET
   @Path("/list")
   @Produces(MediaType.APPLICATION_JSON)
   public Response listServicios() {
       try {
           List<Servicio> servicios = servicioService.listarServicios(); // Implementar en ServicioService
           return Response.ok(Map.of("success", true, "servicios", servicios)).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity(Map.of("success", false, "message", "Error al listar servicios: " + e.getMessage()))
                   .build();
       }
   }
   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getServicio(@PathParam("id") int id) {
       try {
           Servicio servicio = servicioService.obtenerServicio(id); // Implementar en ServicioService
           if (servicio == null) {
               return Response.status(Response.Status.NOT_FOUND)
                       .entity(Map.of("success", false, "message", "Servicio no encontrado"))
                       .build();
           }
           return Response.ok(Map.of("success", true, "servicio", servicio)).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity(Map.of("success", false, "message", "Error al obtener servicio: " + e.getMessage()))
                   .build();
       }
   }
    
    @POST
    @Path("/update/servicio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateServicio(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            String nombre = (String) request.get("nombre");
            String descripcion = (String) request.get("descripcion");
            Integer idPrestador = (Integer) request.get("idPrestador");
            Integer idCategoria = (Integer) request.get("idCategoria");

            if (id == null || nombre == null || descripcion == null || idPrestador == null || idCategoria == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            Servicio servicio = new Servicio(id, nombre, descripcion, idPrestador, idCategoria, null, LocalDateTime.now(), null);
            servicioService.actualizarServicio(servicio);

            return Response.ok(Map.of("success", true, "message", "Servicio actualizado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al actualizar servicio: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/delete/servicio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteServicio(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");

            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Falta el campo id"))
                        .build();
            }

            servicioService.eliminarServicio(id);

            return Response.ok(Map.of("success", true, "message", "Servicio eliminado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al eliminar servicio: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/register/oferta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerOferta(Map<String, Object> request) {
        try {
            Double costo = (Double) request.get("costo");
            Integer numeroTurnos = (Integer) request.get("numeroTurnos");
            String fechaInicioStr = (String) request.get("fechaInicio");
            String fechaFinStr = (String) request.get("fechaFin");
            Integer idServicio = (Integer) request.get("idServicio");

            if (costo == null || numeroTurnos == null || fechaInicioStr == null || fechaFinStr == null || idServicio == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr);
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr);

            Oferta oferta = new Oferta(0, costo, numeroTurnos, fechaInicio, fechaFin, idServicio, LocalDateTime.now(), null, null);
            int id = ofertaService.crearOferta(oferta);

            return Response.ok(Map.of("success", true, "message", "Oferta registrada exitosamente", "id", id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al registrar oferta: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/update/oferta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOferta(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            Double costo = (Double) request.get("costo");
            Integer numeroTurnos = (Integer) request.get("numeroTurnos");
            String fechaInicioStr = (String) request.get("fechaInicio");
            String fechaFinStr = (String) request.get("fechaFin");
            Integer idServicio = (Integer) request.get("idServicio");

            if (id == null || costo == null || numeroTurnos == null || fechaInicioStr == null || fechaFinStr == null || idServicio == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr);
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr);

            Oferta oferta = new Oferta(id, costo, numeroTurnos, fechaInicio, fechaFin, idServicio, null, LocalDateTime.now(), null);
            ofertaService.actualizarOferta(oferta);

            return Response.ok(Map.of("success", true, "message", "Oferta actualizada exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al actualizar oferta: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/delete/oferta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOferta(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");

            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Falta el campo id"))
                        .build();
            }

            ofertaService.eliminarOferta(id);

            return Response.ok(Map.of("success", true, "message", "Oferta eliminada exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al eliminar oferta: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/register/servicio-por-mascota")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerServicioPorMascota(Map<String, Object> request) {
        try {
            Integer idOferta = (Integer) request.get("idOferta");
            Integer idPago = (Integer) request.get("idPago");
            Integer idMascota = (Integer) request.get("idMascota");
            String fechaInicioStr = (String) request.get("fechaInicio");
            String fechaFinalStr = (String) request.get("fechaFinal");
            Boolean atencion = (Boolean) request.get("atencion");

            if (idOferta == null || idPago == null || idMascota == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            LocalDateTime fechaInicio = fechaInicioStr != null ? LocalDateTime.parse(fechaInicioStr) : null;
            LocalDateTime fechaFinal = fechaFinalStr != null ? LocalDateTime.parse(fechaFinalStr) : null;
            boolean atencionValue = atencion != null ? atencion : false;

            ServicioPorMascota servicioPorMascota = new ServicioPorMascota(0, idOferta, idPago, idMascota, fechaInicio, fechaFinal, atencionValue, LocalDateTime.now(), null, null);
            int id = servicioPorMascotaService.crearServicioPorMascota(servicioPorMascota);

            return Response.ok(Map.of("success", true, "message", "Servicio por mascota registrado exitosamente", "id", id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al registrar servicio por mascota: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/update/servicio-por-mascota")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateServicioPorMascota(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");
            Integer idOferta = (Integer) request.get("idOferta");
            Integer idPago = (Integer) request.get("idPago");
            Integer idMascota = (Integer) request.get("idMascota");
            String fechaInicioStr = (String) request.get("fechaInicio");
            String fechaFinalStr = (String) request.get("fechaFinal");
            Boolean atencion = (Boolean) request.get("atencion");

            if (id == null || idOferta == null || idPago == null || idMascota == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Faltan campos obligatorios"))
                        .build();
            }

            LocalDateTime fechaInicio = fechaInicioStr != null ? LocalDateTime.parse(fechaInicioStr) : null;
            LocalDateTime fechaFinal = fechaFinalStr != null ? LocalDateTime.parse(fechaFinalStr) : null;
            boolean atencionValue = atencion != null ? atencion : false;

            ServicioPorMascota servicioPorMascota = new ServicioPorMascota(id, idOferta, idPago, idMascota, fechaInicio, fechaFinal, atencionValue, null, LocalDateTime.now(), null);
            servicioPorMascotaService.actualizarServicioPorMascota(servicioPorMascota);

            return Response.ok(Map.of("success", true, "message", "Servicio por mascota actualizado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al actualizar servicio por mascota: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/delete/servicio-por-mascota")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteServicioPorMascota(Map<String, Object> request) {
        try {
            Integer id = (Integer) request.get("id");

            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Falta el campo id"))
                        .build();
            }

            servicioPorMascotaService.eliminarServicioPorMascota(id);

            return Response.ok(Map.of("success", true, "message", "Servicio por mascota eliminado exitosamente")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Error al eliminar servicio por mascota: " + e.getMessage()))
                    .build();
        }
    }
}
