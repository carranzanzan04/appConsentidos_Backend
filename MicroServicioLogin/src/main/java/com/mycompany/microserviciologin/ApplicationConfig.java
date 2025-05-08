/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin;

import com.mycompany.microserviciologin.controller.AuthController;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(AuthController.class);
        System.out.println("Inicializando configuración REST - AuthController registrado");
    }
}
