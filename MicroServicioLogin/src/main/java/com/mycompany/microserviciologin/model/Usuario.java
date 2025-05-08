/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.model;

import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private String correo;
    private String contrasena;
    private boolean confirmacion;
    private String nid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean activo;

    
    // Constructor
    public Usuario(){
        
    }
    public Usuario(int id, String correo, String contrasena, boolean confirmacion, String nid,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.confirmacion = confirmacion;
        this.nid = nid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public boolean isConfirmacion() { return confirmacion; }
    public void setConfirmacion(boolean confirmacion) { this.confirmacion = confirmacion; }
    public String getNid() { return nid; }
    public void setNid(String nid) { this.nid = nid; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}