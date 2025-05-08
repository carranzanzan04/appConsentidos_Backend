/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.mycompany.microservicioservicios.model;

import java.time.LocalDateTime;

public class Oferta {
    private int id;
    private double costo;
    private int numeroTurnos;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int idServicio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Constructor vacío
    public Oferta() {
    }

    // Constructor completo
    public Oferta(int id, double costo, int numeroTurnos, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                  int idServicio, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.costo = costo;
        this.numeroTurnos = numeroTurnos;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idServicio = idServicio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public int getNumeroTurnos() { return numeroTurnos; }
    public void setNumeroTurnos(int numeroTurnos) { this.numeroTurnos = numeroTurnos; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
