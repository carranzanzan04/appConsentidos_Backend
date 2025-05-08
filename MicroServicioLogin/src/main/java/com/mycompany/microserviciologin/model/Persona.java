/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SARA CARRANZA
 */
package com.mycompany.microserviciologin.model;

import java.time.LocalDateTime;

public abstract class Persona extends Usuario {
    private String nombre;
    private String apellido;

    public Persona(int id, String correo, String contrasena, boolean confirmacion, String nid,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                   String nombre, String apellido) {
        super(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo);
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}