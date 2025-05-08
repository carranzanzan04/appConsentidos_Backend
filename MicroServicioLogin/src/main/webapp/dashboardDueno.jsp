<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.microserviciologin.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #f8f9fc;
            --accent-color: #2e59d9;
            --text-color: #5a5c69;
            --success-color: #1cc88a;
        }
        
        body {
            background-color: var(--secondary-color);
            font-family: 'Nunito', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
        }
        
        .dashboard-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .user-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
            padding: 30px;
            margin-bottom: 30px;
            border-left: 5px solid var(--primary-color);
        }
        
        .welcome-title {
            color: var(--primary-color);
            font-weight: 700;
            margin-bottom: 20px;
        }
        
        .user-info {
            color: var(--text-color);
            font-size: 18px;
            margin-bottom: 15px;
        }
        
        .info-label {
            font-weight: 600;
            color: var(--primary-color);
        }
        
        .logout-btn {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 10px 25px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        
        .logout-btn:hover {
            background-color: var(--accent-color);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        
        .role-badge {
            background-color: var(--primary-color);
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
            display: inline-block;
            margin-left: 10px;
        }
        
        @media (max-width: 768px) {
            .dashboard-container {
                padding: 15px;
            }
            
            .user-card {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="user-card">
            <h1 class="welcome-title">
                Bienvenido, <%= ((Usuario) session.getAttribute("usuario")).getCorreo() %>
                <span class="role-badge"><%= request.getParameter("role") %></span>
            </h1>
            
            <% 
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                if (usuario instanceof Persona) {
                    Persona persona = (Persona) usuario;
            %>
                <p class="user-info"><span class="info-label">Nombre completo:</span> <%= persona.getNombre() %> <%= persona.getApellido() %></p>
            <% 
                } else if (usuario instanceof Empresa) {
                    Empresa empresa = (Empresa) usuario;
            %>
                <p class="user-info"><span class="info-label">Nombre de la empresa:</span> <%= empresa.getNombre() %></p>
            <% } %>
            
            <a href="logout" class="logout-btn">
                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
            </a>
        </div>
        
        <!-- Sección de contenido adicional puede ir aquí -->
        <div class="row">
            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">Acciones rápidas</h5>
                        <div class="d-grid gap-2">
                            <button class="btn btn-primary">Mi perfil</button>
                            <button class="btn btn-success">Configuración</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">Actividad reciente</h5>
                        <p>No hay actividad reciente para mostrar.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Puedes añadir funcionalidad JavaScript aquí si es necesario
        console.log("Dashboard cargado correctamente");
    </script>
</body>
</html>