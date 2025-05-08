/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.microserviciologin.controller;
import com.mycompany.microserviciologin.service.UsuarioService;
import com.mycompany.microserviciologin.model.Usuario;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.microserviciologin.dao.UsuarioDAO;
import java.sql.SQLException;

public class VerificarContrasena {
   public static void main(String[] args) throws SQLException {
       /* String contrasenaIngresada = "11111111"; // Reemplaza con la contraseña que estás usando
        String hashAlmacenado = "$2a$12$FnN9Qn5vtBUQFLMWUjQyhOyIqcQAR7rWVrk8bDh9P7MYEDLmBaZ06"; // Reemplaza con el hash de la base de datos

        BCrypt.Result result = BCrypt.verifyer().verify(contrasenaIngresada.toCharArray(), hashAlmacenado);
        System.out.println("Resultado de la verificación: " + result.verified);
        if (!result.verified) {
            System.out.println("La contraseña no coincide con el hash almacenado.");
        } else {
            System.out.println("La contraseña coincide.");
        }*/
       UsuarioDAO usuariodao =new UsuarioDAO();
       UsuarioService us=new UsuarioService(usuariodao);
       Usuario u ;
       u=us.authenticate("pedro@gmail.com","11111111");
       System.out.println(u);
    } 
   
}