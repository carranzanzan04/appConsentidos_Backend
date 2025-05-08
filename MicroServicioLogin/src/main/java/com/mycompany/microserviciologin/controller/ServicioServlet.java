/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microserviciologin.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "ServicioServlet", urlPatterns = {"/ServicioServlet"})
public class ServicioServlet extends HttpServlet {

    private static final String API_BASE_URL = "http://localhost:8080/MicroservicioServicios/api/servicios";
    private static final Logger LOGGER = Logger.getLogger(ServicioServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Configurar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        LOGGER.log(Level.INFO, "Procesando solicitud GET: action={0}, id={1}", new Object[]{action, id});

        try {
            if ("list".equals(action)) {
                // Llamar al endpoint GET /servicios/list
                URL url = new URL(API_BASE_URL + "/list");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int responseCode = conn.getResponseCode();
                LOGGER.log(Level.INFO, "Código de respuesta para GET /list: {0}", responseCode);

                StringBuilder apiResponse = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(), "utf-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        apiResponse.append(line);
                    }
                }

                conn.disconnect();

                LOGGER.log(Level.INFO, "Respuesta del API para GET /list: {0}", apiResponse.toString());

                // Reenviar respuesta del API al cliente
                response.setStatus(responseCode);
                response.getWriter().write(apiResponse.toString());
            } else if ("get".equals(action) && id != null) {
                // Llamar al endpoint GET /servicios/{id}
                URL url = new URL(API_BASE_URL + "/" + id);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int responseCode = conn.getResponseCode();
                LOGGER.log(Level.INFO, "Código de respuesta para GET /{0}: {1}", new Object[]{id, responseCode});

                StringBuilder apiResponse = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(), "utf-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        apiResponse.append(line);
                    }
                }

                conn.disconnect();

                LOGGER.log(Level.INFO, "Respuesta del API para GET /{0}: {1}", new Object[]{id, apiResponse.toString()});

                // Reenviar respuesta del API al cliente
                response.setStatus(responseCode);
                response.getWriter().write(apiResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\":false,\"message\":\"Acción no válida o falta ID\"}");
                LOGGER.log(Level.WARNING, "Solicitud GET inválida: action={0}, id={1}", new Object[]{action, id});
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false,\"message\":\"Error: " + e.getMessage() + "\"}");
            LOGGER.log(Level.SEVERE, "Error procesando solicitud GET: {0}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Configurar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Leer el cuerpo de la solicitud
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        LOGGER.log(Level.INFO, "Procesando solicitud POST: action={0}, body={1}", new Object[]{action, requestBody});

        try {
            // Validar que el cuerpo no esté vacío
            if (requestBody == null || requestBody.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\":false,\"message\":\"Cuerpo de la solicitud vacío\"}");
                LOGGER.log(Level.WARNING, "Cuerpo de la solicitud POST vacío: action={0}", action);
                return;
            }

            String endpoint = "";
            if ("create".equals(action)) {
                endpoint = "/register/servicio";
            } else if ("update".equals(action)) {
                endpoint = "/update/servicio";
            } else if ("delete".equals(action)) {
                endpoint = "/delete/servicio";
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\":false,\"message\":\"Acción no válida\"}");
                LOGGER.log(Level.WARNING, "Solicitud POST inválida: action={0}", action);
                return;
            }

            // Reenviar solicitud al API REST
            URL url = new URL(API_BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            LOGGER.log(Level.INFO, "Código de respuesta para POST {0}: {1}", new Object[]{endpoint, responseCode});

            StringBuilder apiResponse = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(), "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    apiResponse.append(line);
                }
            }

            conn.disconnect();

            LOGGER.log(Level.INFO, "Respuesta del API para POST {0}: {1}", new Object[]{endpoint, apiResponse.toString()});

            // Reenviar respuesta del API al cliente
            response.setStatus(responseCode);
            response.getWriter().write(apiResponse.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false,\"message\":\"Error: " + e.getMessage() + "\"}");
            LOGGER.log(Level.SEVERE, "Error procesando solicitud POST: {0}", e.getMessage());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}