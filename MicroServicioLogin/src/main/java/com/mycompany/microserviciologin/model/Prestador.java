/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.model;

import java.time.LocalDateTime;

public class Prestador extends Cliente {
    private String tipoPrestador;

    // Constructor vacío
    public Prestador() {
    }

    // Constructor completo
    public Prestador(int id, String correo, String contrasena, boolean confirmacion, String nid,
                     LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                     String tipoPrestador) {
        super(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo);
        this.tipoPrestador = tipoPrestador;
    }

    // Getters y setters
    public String getTipoPrestador() { return tipoPrestador; }
    public void setTipoPrestador(String tipoPrestador) { this.tipoPrestador = tipoPrestador; }
}
