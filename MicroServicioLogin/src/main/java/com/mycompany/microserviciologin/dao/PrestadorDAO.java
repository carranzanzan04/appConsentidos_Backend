/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.dao;

import com.mycompany.microserviciologin.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class PrestadorDAO {

    public void crearPrestador(int idcliente) {
        String sql = "INSERT INTO prestadores (id, created_at) VALUES (?, ?)";
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, idcliente);
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                stmt.executeUpdate();
                con.commit();
            }
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error al hacer rollback", rollbackEx);
            }
            throw new RuntimeException("Error al crear prestador", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión", closeEx);
                }
            }
        }
    }

   
}
