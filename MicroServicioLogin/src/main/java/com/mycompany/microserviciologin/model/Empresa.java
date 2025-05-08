/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.model;

import java.time.LocalDateTime;

public class Empresa extends Prestador {
    private String nombre;

    // Constructor vacío
    public Empresa() {
    }

    // Constructor completo
    public Empresa(int id, String correo, String contrasena, boolean confirmacion, String nid,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                   String nombre) {
        super(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, "Empresa");
        this.nombre = nombre;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}