<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            border-top: 5px solid #4e73df;
        }
        
        h2 {
            color: #4e73df;
            text-align: center;
            margin-bottom: 25px;
            font-size: 28px;
        }
        
        .error-message {
            color: #e74c3c;
            background: #fde8e8;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            font-size: 14px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }
        
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
            box-sizing: border-box;
        }
        
        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #4e73df;
            outline: none;
            box-shadow: 0 0 0 3px rgba(78, 115, 223, 0.2);
        }
        
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #4e73df;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        input[type="submit"]:hover {
            background-color: #3a5bd9;
            transform: translateY(-2px);
        }
        
        .register-link {
            text-align: center;
            margin-top: 20px;
            color: #555;
        }
        
        .register-link a {
            color: #4e73df;
            text-decoration: none;
            font-weight: 600;
        }
        
        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Iniciar Sesión</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error").equals("invalid_credentials") ? 
                    "Credenciales inválidas. Intente de nuevo." : "Rol desconocido." %>
            </div>
        <% } %>
        
        <form action="login" method="post">
            <div class="form-group">
                <label for="correo">Correo:</label>
                <input type="email" id="correo" name="correo" required>
            </div>
            
            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required>
            </div>
            
            <input type="submit" value="Iniciar Sesión">
        </form>
        
        <div class="register-link">
            ¿No tienes cuenta? <a href="/registro">Regístrate aquí</a>
        </div>
    </div>
</body>
</html>