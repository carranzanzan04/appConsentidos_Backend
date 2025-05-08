/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciocategorias.service;


import com.mycompany.microserviciocategorias.dao.CategoriaDeServicioDAO;
import com.mycompany.microserviciocategorias.model.CategoriaDeServicio;

import java.util.List;

public class CategoriaDeServicioService {
    private final CategoriaDeServicioDAO categoriaDAO;

    public CategoriaDeServicioService(CategoriaDeServicioDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public int crearCategoria(CategoriaDeServicio categoria) {
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().trim().isEmpty() ||
            categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty() ||
            categoria.getIdAdministrador() <= 0) {
            throw new IllegalArgumentException("Los campos nombre, descripcion e id_administrador son obligatorios");
        }
        categoria.setCreatedAt(java.time.LocalDateTime.now());
        return categoriaDAO.crearCategoria(categoria);
    }

    public CategoriaDeServicio obtenerCategoriaPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0");
        }
        CategoriaDeServicio categoria = categoriaDAO.obtenerCategoriaPorId(id);
        if (categoria == null) {
            throw new RuntimeException("No se encontró la categoría con ID " + id);
        }
        return categoria;
    }

    public List<CategoriaDeServicio> obtenerTodasLasCategorias() {
        return categoriaDAO.obtenerTodasLasCategorias();
    }

    public void actualizarCategoria(CategoriaDeServicio categoria) {
        if (categoria == null || categoria.getId() <= 0 ||
            categoria.getNombre() == null || categoria.getNombre().trim().isEmpty() ||
            categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty() ||
            categoria.getIdAdministrador() <= 0) {
            throw new IllegalArgumentException("Los campos id, nombre, descripcion e id_administrador son obligatorios");
        }
        categoriaDAO.actualizarCategoria(categoria);
    }

    public void eliminarCategoria(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0");
        }
        categoriaDAO.eliminarCategoria(id);
    }
}