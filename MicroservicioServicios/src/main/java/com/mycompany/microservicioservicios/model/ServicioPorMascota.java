/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microservicioservicios.model;

import java.time.LocalDateTime;

public class ServicioPorMascota {
    private int id;
    private int idOferta;
    private int idPago;
    private int idMascota;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private boolean atencion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Constructor vacío
    public ServicioPorMascota() {
    }

    // Constructor completo
    public ServicioPorMascota(int id, int idOferta, int idPago, int idMascota, LocalDateTime fechaInicio,
                              LocalDateTime fechaFinal, boolean atencion, LocalDateTime createdAt,
                              LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.idOferta = idOferta;
        this.idPago = idPago;
        this.idMascota = idMascota;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.atencion = atencion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdOferta() { return idOferta; }
    public void setIdOferta(int idOferta) { this.idOferta = idOferta; }
    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }
    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFinal() { return fechaFinal; }
    public void setFechaFinal(LocalDateTime fechaFinal) { this.fechaFinal = fechaFinal; }
    public boolean isAtencion() { return atencion; }
    public void setAtencion(boolean atencion) { this.atencion = atencion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
