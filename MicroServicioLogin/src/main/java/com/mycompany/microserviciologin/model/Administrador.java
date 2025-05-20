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

public class Administrador extends Persona {

    /**
     *
     */
    public Administrador(){
        
    }
    public Administrador(int id, String correo, String contrasena, boolean confirmacion, String nid,
                         LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo,
                         String nombre, String apellido) {
        super(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo, nombre, apellido);
    }
}