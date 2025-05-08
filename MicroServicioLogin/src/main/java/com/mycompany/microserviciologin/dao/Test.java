package com.mycompany.microserviciologin.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import com.mycompany.microserviciologin.model.Empresa;
import com.mycompany.microserviciologin.model.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author SARA CARRANZA
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Usuario e=new Empresa(0, "e7@gmail.com", "1111111111", true, "33445566", LocalDateTime.now(), null, null, true, "e7");
        UsuarioDAO ud= new UsuarioDAO();
        int idu=ud.crearUsuario(e);
        System.out.println(idu);
        ClienteDAO cd=new ClienteDAO();
        int idc=cd.registrarCliente(idu);
        System.out.println(idc);
        PrestadorDAO pd= new PrestadorDAO();
        pd.crearPrestador(idc);
        EmpresaDAO edao=new EmpresaDAO();
        edao.registrarEmpresa(idc, (Empresa) e);
    }
    
}
