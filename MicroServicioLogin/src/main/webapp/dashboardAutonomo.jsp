```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.microserviciologin.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Autonomo</title>
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
        
        .dropdown-menu {
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            border: none;
            padding: 10px 0;
        }
        
        .dropdown-item {
            padding: 10px 20px;
            font-weight: 500;
            color: var(--text-color);
            transition: all 0.2s;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .dropdown-item:hover {
            background-color: rgba(78, 115, 223, 0.1);
            color: var(--primary-color);
        }
        
        .dropdown-item i {
            width: 20px;
            text-align: center;
        }
        
        .dropdown-divider {
            margin: 5px 0;
        }
        
        .logout-item {
            color: #e74c3c !important;
        }
        
        .logout-item:hover {
            background-color: rgba(231, 76, 60, 0.1) !important;
        }
        
        .dropdown-toggle {
            background-color: var(--primary-color);
            border: none;
            padding: 12px 25px;
            font-weight: 600;
            border-radius: 8px;
            transition: all 0.3s;
        }
        
        .dropdown-toggle:hover {
            background-color: var(--accent-color);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        
        .main-content {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
            padding: 30px;
            margin-top: 20px;
        }
        
        .card-header {
            background-color: var(--primary-color) !important;
            color: white !important;
        }
        
        .response-success { color: var(--success-color); font-weight: bold; }
        .response-error { color: #e74c3c; font-weight: bold; }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="user-card">
            <h1 class="welcome-title">
                Bienvenido, <%= ((Usuario) session.getAttribute("usuario")).getCorreo() %>
                <span class="badge bg-primary rounded-pill"><%= request.getParameter("role") %></span>
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
            
            <!-- Menú desplegable -->
            <div class="dropdown mt-4">
                <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-bars me-2"></i> Menú de opciones
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li><a class="dropdown-item" href="#" onclick="cargarFormActualizar()"><i class="fas fa-user-edit"></i> Editar perfil</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarFormCrearServicio()"><i class="fas fa-plus-circle"></i> Crear Servicio</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarFormActualizarServicio()"><i class="fas fa-edit"></i> Actualizar Servicio</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarFormEliminarServicio()"><i class="fas fa-trash-alt"></i> Eliminar Servicio</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarListaServicios()"><i class="fas fa-list-ul"></i> Listar Servicios</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarFormObtenerServicio()"><i class="fas fa-search"></i> Obtener Servicio por ID</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarFormCrearOferta()"><i class="fas fa-tag"></i> Crear oferta</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarListaOfertas()"><i class="fas fa-list-ul"></i> Mis ofertas</a></li>
                    <li><a class="dropdown-item" href="#" onclick="cargarPacientesPorServicio()"><i class="fas fa-users"></i> Pacientes por servicio</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item logout-item" href="logout"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a></li>
                </ul>
            </div>
        </div>
            
        <div class="main-content">
            <div class="container-fluid">
                <h1 class="mb-4 bg-dark text-white p-3 rounded">Bienvenido al Panel</h1>

                <div class="card shadow mb-4">
                    <div class="card-body">
                        <h3 class="card-title"></h3>
                        <p class="card-text">Selecciona una opción del menú para comenzar.</p>
                    </div>
                </div>
                
                <!-- Contenedor dinámico para formularios -->
                <div id="containerActions"></div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Activar dropdowns
        document.addEventListener('DOMContentLoaded', function() {
            var dropdownElementList = [].slice.call(document.querySelectorAll('.dropdown-toggle'));
            var dropdownList = dropdownElementList.map(function (dropdownToggleEl) {
                return new bootstrap.Dropdown(dropdownToggleEl);
            });
        });

        const servletUrl = 'http://localhost:8080/MicroServicioLogin/ServicioServlet';
        const idPrestador = <%= ((Usuario) session.getAttribute("usuario")).getId() %>; // Asumiendo que Usuario tiene getId()

        function cargarFormActualizar() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Editar Información</h3>
                    </header>
                    <div class="card-body">
                        <form>
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" placeholder="Nombre" required>
                            </div>
                            <div class="d-flex justify-content-between">
                            <div class="mb-3">
                                <label class="form-label">Apellido</label>
                                <input type="text" class="form-control" id="apellido" placeholder="Apellido" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Correo Electrónico</label>
                                <input type="email" class="form-control" id="correo" placeholder="Correo Electrónico" required>
                            </div>
                            </div>
                            <div class="d-flex justify-content-between">
                            <div class="mb-3">
                                <label class="form-label">Teléfono</label>
                                <input type="tel" class="form-control" id="telefono" placeholder="Teléfono" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Dirección</label>
                                <input type="text" class="form-control" id="direccion" placeholder="Dirección" required>
                            </div>
                            </div>
                            <button type="submit" class="btn btn-success">Guardar Cambios</button>
                        </form>
                    </div>
                </div>`;
        }

        function cargarFormCrearServicio() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Crear Nuevo Servicio</h3>
                    </header>
                    <div class="card-body">
                        <form onsubmit="crearServicio(event)">
                            <div class="mb-3">
                                <label class="form-label">Nombre del Servicio</label>
                                <input type="text" class="form-control" id="nombreServicio" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción</label>
                                <textarea class="form-control" id="descripcionServicio" rows="3" required></textarea>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">ID Categoría</label>
                                <input type="number" class="form-control" id="idCategoriaServicio" required>
                            </div>
                            <button type="submit" class="btn btn-success">Crear Servicio</button>
                        </form>
                        <div id="crearResponse" class="mt-3"></div>
                    </div>
                </div>`;
        }

        function cargarFormActualizarServicio() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Actualizar Servicio</h3>
                    </header>
                    <div class="card-body">
                        <form onsubmit="actualizarServicio(event)">
                            <div class="mb-3">
                                <label class="form-label">ID del Servicio</label>
                                <input type="number" class="form-control" id="idServicio" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre del Servicio</label>
                                <input type="text" class="form-control" id="nombreServicio" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción</label>
                                <textarea class="form-control" id="descripcionServicio" rows="3" required></textarea>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">ID Categoría</label>
                                <input type="number" class="form-control" id="idCategoriaServicio" required>
                            </div>
                            <button type="submit" class="btn btn-success">Actualizar Servicio</button>
                        </form>
                        <div id="actualizarResponse" class="mt-3"></div>
                    </div>
                </div>`;
        }

        function cargarFormEliminarServicio() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Eliminar Servicio</h3>
                    </header>
                    <div class="card-body">
                        <form onsubmit="eliminarServicio(event)">
                            <div class="mb-3">
                                <label class="form-label">ID del Servicio</label>
                                <input type="number" class="form-control" id="idServicio" required>
                            </div>
                            <button type="submit" class="btn btn-danger">Eliminar Servicio</button>
                        </form>
                        <div id="eliminarResponse" class="mt-3"></div>
                    </div>
                </div>`;
        }

        function cargarListaServicios() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Lista de Servicios</h3>
                    </header>
                    <div class="card-body">
                        <button class="btn btn-primary mb-3" onclick="listarServicios()">Cargar Lista</button>
                        <div id="listaResponse" class="table-responsive"></div>
                    </div>
                </div>`;
        }

        function cargarFormObtenerServicio() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Obtener Servicio por ID</h3>
                    </header>
                    <div class="card-body">
                        <form onsubmit="obtenerServicio(event)">
                            <div class="mb-3">
                                <label class="form-label">ID del Servicio</label>
                                <input type="number" class="form-control" id="idServicio" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Obtener Servicio</button>
                        </form>
                        <div id="obtenerResponse" class="mt-3"></div>
                    </div>
                </div>`;
        }

        function cargarFormCrearOferta() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Crear Nueva Oferta</h3>
                    </header>
                    <div class="card-body">
                        <form>
                            <div class="mb-3">
                                <label class="form-label">Servicio</label>
                                <select class="form-select" id="servicioOferta" required>
                                    <option value="" selected disabled>Seleccione un servicio</option>
                                    <!-- Opciones dinámicas deberían cargarse aquí -->
                                </select>
                            </div>
                            <div class="d-flex justify-content-between">
                            <div class="mb-3">
                                <label class="form-label">Descuento (%)</label>
                                <input type="number" class="form-control" id="descuentoOferta" min="1" max="100" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Fecha de Inicio</label>
                                <input type="date" class="form-control" id="fechaInicioOferta" required>
                            </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Fecha de Fin</label>
                                <input type="date" class="form-control" id="fechaFinOferta" required>
                            </div>
                            <button type="submit" class="btn btn-success">Crear Oferta</button>
                        </form>
                    </div>
                </div>`;
        }

        function cargarListaOfertas() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Mis Ofertas</h3>
                    </header>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Servicio</th>
                                        <th>Descuento</th>
                                        <th>Precio Final</th>
                                        <th>Válido hasta</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Datos dinámicos deberían cargarse aquí -->
                                    <tr>
                                        <td>Baño completo</td>
                                        <td>20%</td>
                                        <td>$40,000</td>
                                        <td>15/12/2023</td>
                                        <td>
                                            <button class="btn btn-sm btn-primary me-2">Editar</button>
                                            <button class="btn btn-sm btn-danger">Eliminar</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>`;
        }

        function cargarPacientesPorServicio() {
            const container = document.getElementById("containerActions");
            container.innerHTML = `
                <div class="card shadow mb-4">
                    <header class="card-header">
                        <h3 class="h4 py-2 px-3 mb-0">Pacientes por Servicio</h3>
                    </header>
                    <div class="card-body">
                        <div class="mb-3">
                            <label class="form-label">Seleccione un servicio</label>
                            <select class="form-select" id="servicioPacientes" onchange="filtrarPacientes()">
                                <option value="" selected disabled>Seleccione un servicio</option>
                                <option value="1">Baño completo</option>
                                <option value="2">Corte de pelo</option>
                                <option value="3">Consulta veterinaria</option>
                            </select>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped" id="tablaPacientes">
                                <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Mascota</th>
                                        <th>Fecha</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Datos dinámicos deberían cargarse aquí -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>`;
        }

        function filtrarPacientes() {
            console.log("Filtrando pacientes por servicio...");
        }

        async function crearServicio(event) {
            event.preventDefault();
            const data = {
                nombre: document.getElementById('nombreServicio').value,
                descripcion: document.getElementById('descripcionServicio').value,
                idPrestador: idPrestador,
                idCategoria: parseInt(document.getElementById('idCategoriaServicio').value)
            };
            try {
                const response = await fetch(servletUrl + '?action=create', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                const result = await response.json();
                document.getElementById('crearResponse').innerHTML = 
                    `<p class="${result.success ? 'response-success' : 'response-error'}">${result.message}${result.id ? ' ID: ' + result.id : ''}</p>`;
            } catch (error) {
                document.getElementById('crearResponse').innerHTML = 
                    `<p class="response-error">Error: ${error.message}</p>`;
            }
        }

        async function actualizarServicio(event) {
            event.preventDefault();
            const data = {
                id: parseInt(document.getElementById('idServicio').value),
                nombre: document.getElementById('nombreServicio').value,
                descripcion: document.getElementById('descripcionServicio').value,
                idPrestador: idPrestador,
                idCategoria: parseInt(document.getElementById('idCategoriaServicio').value)
            };
            try {
                const response = await fetch(servletUrl + '?action=update', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                const result = await response.json();
                document.getElementById('actualizarResponse').innerHTML = 
                    `<p class="${result.success ? 'response-success' : 'response-error'}">${result.message}</p>`;
            } catch (error) {
                document.getElementById('actualizarResponse').innerHTML = 
                    `<p class="response-error">Error: ${error.message}</p>`;
            }
        }

        async function eliminarServicio(event) {
            event.preventDefault();
            const data = {
                id: parseInt(document.getElementById('idServicio').value)
            };
            try {
                const response = await fetch(servletUrl + '?action=delete', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                const result = await response.json();
                document.getElementById('eliminarResponse').innerHTML = 
                    `<p class="${result.success ? 'response-success' : 'response-error'}">${result.message}</p>`;
            } catch (error) {
                document.getElementById('eliminarResponse').innerHTML = 
                    `<p class="response-error">Error: ${error.message}</p>`;
            }
        }

        async function listarServicios() {
            try {
                console.log("Iniciando solicitud para listar servicios...");
                const response = await fetch(servletUrl + '?action=list', {
                    method: 'GET',
                    headers: { 'Accept': 'application/json' } // Eliminamos Content-Type para GET
                });
                console.log("Respuesta recibida, estado:", response.status);
                const result = await response.json();
                console.log("Datos recibidos:", result);

                const container = document.getElementById('listaResponse');
                if (!container) {
                    console.error("El elemento 'listaResponse' no se encuentra en el DOM");
                    return;
                }

                if (result.success && result.servicios && result.servicios.length > 0) {
                    let table = `
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>ID Prestador</th>
                                    <th>ID Categoría</th>
                                    <th>Creado</th>
                                    <th>Actualizado</th>
                                </tr>
                            </thead>
                            <tbody>`;
                    result.servicios.forEach(s => {
                        // Convertir createdAt y updatedAt a formato legible
                        const createdAt = s.createdAt ? new Date(s.createdAt[0], s.createdAt[1] - 1, s.createdAt[2], s.createdAt[3], s.createdAt[4], s.createdAt[5]).toLocaleString() : 'N/A';
                        const updatedAt = s.updatedAt ? new Date(s.updatedAt[0], s.updatedAt[1] - 1, s.updatedAt[2], s.updatedAt[3], s.updatedAt[4], s.updatedAt[5]).toLocaleString() : 'N/A';
                        table += `
                            <tr>
                                <td>${s.id}</td>
                                <td>${s.nombre}</td>
                                <td>${s.descripcion}</td>
                                <td>${s.idPrestador}</td>
                                <td>${s.idCategoria}</td>
                                <td>${createdAt}</td>
                                <td>${updatedAt}</td>
                            </tr>`;
                    });
                    table += `</tbody></table>`;
                    container.innerHTML = table;
                    console.log("Tabla renderizada con éxito");
                } else {
                    container.innerHTML = `<p class="response-error">${result.message || 'No se encontraron servicios'}</p>`;
                    console.log("No se encontraron servicios o success es false");
                }
            } catch (error) {
                console.error("Error al listar servicios:", error);
                const container = document.getElementById('listaResponse');
                if (container) {
                    container.innerHTML = `<p class="response-error">Error: ${error.message}</p>`;
                }
            }
        }

        async function obtenerServicio(event) {
            event.preventDefault();
            const id = parseInt(document.getElementById('idServicio').value);
            try {
                const response = await fetch(servletUrl + '?action=get&id=' + id, {
                    method: 'GET',
                    headers: { 'Accept': 'application/json' }
                });
                const result = await response.json();
                const container = document.getElementById('obtenerResponse');
                if (result.success && result.servicio) {
                    const s = result.servicio;
                    const createdAt = s.createdAt ? new Date(s.createdAt[0], s.createdAt[1] - 1, s.createdAt[2], s.createdAt[3], s.createdAt[4], s.createdAt[5]).toLocaleString() : 'N/A';
                    const updatedAt = s.updatedAt ? new Date(s.updatedAt[0], s.updatedAt[1] - 1, s.updatedAt[2], s.updatedAt[3], s.updatedAt[4], s.updatedAt[5]).toLocaleString() : 'N/A';
                    container.innerHTML = `
                        <div class="card">
                            <div class="card-body">
                                <p><strong>ID:</strong> ${s.id}</p>
                                <p><strong>Nombre:</strong> ${s.nombre}</p>
                                <p><strong>Descripción:</strong> ${s.descripcion}</p>
                                <p><strong>ID Prestador:</strong> ${s.idPrestador}</p>
                                <p><strong>ID Categoría:</strong> ${s.idCategoria}</p>
                                <p><strong>Creado:</strong> ${createdAt}</p>
                                <p><strong>Actualizado:</strong> ${updatedAt}</p>
                            </div>
                        </div>`;
                } else {
                    container.innerHTML = `<p class="response-error">${result.message}</p>`;
                }
            } catch (error) {
                document.getElementById('obtenerResponse').innerHTML = 
                    `<p class="response-error">Error: ${error.message}</p>`;
            }
        }
    </script>
</body>
</html>
```

#### Cambios Realizados:
1. **Corrección del Encabezado `Content-Type`**:
   - Eliminé el encabezado `Content-Type` en las solicitudes GET (`listarServicios` y `obtenerServicio`), ya que no es necesario y podría causar problemas. Mantuve `Accept: application/json` para indicar que esperamos una respuesta JSON.

2. **Depuración con `console.log`**:
   - Agregué mensajes de depuración para confirmar que:
     - La solicitud se inicia.
     - La respuesta se recibe y cuál es su estado.
     - Los datos se procesan correctamente.
     - La tabla se renderiza o se muestra un mensaje de error.

3. **Manejo de Fechas (`createdAt` y `updatedAt`)**:
   - El endpoint devuelve `createdAt` y `updatedAt` como arreglos de valores (`[año, mes, día, hora, minuto, segundo, nanosegundo]`).
   - Convertí estos arreglos a objetos `Date` usando `new Date(año, mes - 1, día, hora, minuto, segundo)` (el mes se ajusta restando 1 porque en JavaScript los meses van de 0 a 11).
   - Usé `toLocaleString()` para mostrar las fechas en un formato legible.
   - Agregué columnas para `createdAt` y `updatedAt` en la tabla de `listarServicios` y las mostré también en `obtenerServicio`.

4. **Corrección de Errores**:
   - En la función `eliminarServicio`, corregí el error tipográfico `index.html` por `innerHTML`:
     ```javascript
     document.getElementById('eliminarResponse').innerHTML = 
         `<p class="${result.success ? 'response-success' : 'response-error'}">${result.message}</p>`;
     ```

5. **Validación del Contenedor**:
   - Agregué una verificación para asegurarme de que el elemento `listaResponse` exista en el DOM antes de intentar actualizarlo.

### Instrucciones para Probar
1. **Actualizar el Archivo**:
   - Reemplaza tu `dashboardAutonomo.jsp` con el código actualizado (artifact_version_id: `f1a2b3c4-d5e6-4f78-9012-345678901234`).
   - Asegúrate de que esté en el directorio correcto (`webapps/MicroServicioLogin/`).

2. **Acceder a la Página**:
   - Navega a:
     ```
     http://localhost:8080/MicroServicioLogin/dashboardAutonomo.jsp?role=prestador
     ```
   - Selecciona "Listar Servicios" en el menú desplegable.
   - Haz clic en "Cargar Lista".

3. **Depurar con la Consola del Navegador**:
   - Abre la consola del navegador (F12, pestaña "Console").
   - Deberías ver mensajes como:
     ```
     Iniciando solicitud para listar servicios...
     Respuesta recibida, estado: 200
     Datos recibidos: {servicios: Array(4), success: true}
     Tabla renderizada con éxito
     ```
   - Si hay un error, verás un mensaje como:
     ```
     Error al listar servicios: [detalle del error]
     ```

4. **Verificar la Tabla**:
   - La tabla debería mostrar los servicios con las columnas: ID, Nombre, Descripción, ID Prestador, ID Categoría, Creado, y Actualizado.
   - Las fechas (`createdAt` y `updatedAt`) deberían mostrarse en un formato legible, por ejemplo: `4/29/2025, 9:41:11 PM`.

### Posibles Problemas y Soluciones
- **La Tabla Sigue Sin Mostrarse**:
  - **CORS**: Aunque el `ServicioServlet` tiene CORS configurado, asegúrate de que el microservicio `MicroservicioServicios` también lo tenga. Si no, agrega un filtro CORS como se mencionó anteriormente:
    ```java
    @Provider
    public class CorsFilter implements ContainerResponseFilter {
        @Override
        public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
                throws IOException {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        }
    }
    ```
  - **Error en el Formato de Datos**: Si el endpoint cambia su formato (por ejemplo, `servicios` pasa a ser `data`), ajusta la función `listarServicios`:
    ```javascript
    const servicios = result.data; // Cambia result.servicios por result.data si es necesario
    ```

- **Fechas No se Muestran Correctamente**:
  - Si el formato de `createdAt` o `updatedAt` cambia, ajusta la conversión. Por ejemplo, si el endpoint empieza a devolver cadenas ISO (`"2025-04-29T21:41:11"`), cambia el código a:
    ```javascript
    const createdAt = s.createdAt ? new Date(s.createdAt).toLocaleString() : 'N/A';
    ```

- **Error en la Consola**:
  - Si ves un error en la consola, como `Failed to fetch`, revisa la pestaña "Network" del navegador para ver el código de respuesta y el mensaje de error.
  - Si el error es `404`, verifica que el `ServicioServlet` esté desplegado correctamente en `MicroServicioLogin`.
  - Si el error es `500`, revisa los logs de Tomcat (`catalina.out`) para más detalles.

### Próximos Pasos
- Si los datos aún no se muestran, compárteme los mensajes de la consola del navegador y los logs del `ServicioServlet` para identificar el problema.
- Si deseas agregar más funcionalidad, como filtrar servicios por `idPrestador` o mostrar solo los servicios del usuario actual, indícalos, y lo implementaré.
- También puedo ayudarte a implementar las otras opciones del menú, como "Crear Oferta" o "Pacientes por Servicio", si lo necesitas.

¿Ves los datos en la tabla ahora? Si no, ¿puedes compartir los mensajes de la consola del navegador?
