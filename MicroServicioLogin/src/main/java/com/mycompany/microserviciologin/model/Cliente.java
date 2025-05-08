/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.model;

import java.time.LocalDateTime;

public class Cliente extends Usuario {
    public Cliente(){
        
    }
    public Cliente(int id, String correo, String contrasena, boolean confirmacion, String nid,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean activo) {
        super(id, correo, contrasena, confirmacion, nid, createdAt, updatedAt, deletedAt, activo);
    }
}
