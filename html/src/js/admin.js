// Funcionalidades específicas para el administrador
let usuariosData = [];

// Mostrar/ocultar secciones
function mostrarSeccion(seccion) {
    // Ocultar todas las secciones
    document.querySelectorAll('.admin-section').forEach(section => {
        section.classList.remove('active');
    });

    // Mostrar la sección seleccionada
    document.getElementById(`seccion-${seccion}`).classList.add('active');

    // Cargar datos específicos de la sección
    switch(seccion) {
        case 'usuarios':
            cargarUsuarios();
            break;
        case 'cursos':
            cargarCursos();
            break;
        case 'reportes':
            cargarReportes();
            break;
    }
}

// Gestión de usuarios
function cargarUsuarios() {
    // Datos de ejemplo - en una aplicación real esto vendría de una API
    usuariosData = [
        {
            id: 1,
            nombre: 'Ana García',
            email: 'ana.garcia@email.com',
            tipo: 'ESTUDIANTE',
            estado: 'ACTIVO',
            ultimoAcceso: '2023-11-10 14:30',
            fechaRegistro: '2023-08-15'
        },
        {
            id: 2,
            nombre: 'Carlos López',
            email: 'carlos.lopez@email.com',
            tipo: 'PROFESOR',
            estado: 'ACTIVO',
            ultimoAcceso: '2023-11-10 09:15',
            fechaRegistro: '2023-07-20'
        },
        {
            id: 3,
            nombre: 'María Rodríguez',
            email: 'maria.rodriguez@email.com',
            tipo: 'TUTOR',
            estado: 'ACTIVO',
            ultimoAcceso: '2023-11-09 16:45',
            fechaRegistro: '2023-09-05'
        },
        {
            id: 4,
            nombre: 'Pedro Martínez',
            email: 'pedro.martinez@email.com',
            tipo: 'ESTUDIANTE',
            estado: 'INACTIVO',
            ultimoAcceso: '2023-10-28 11:20',
            fechaRegistro: '2023-08-30'
        },
        {
            id: 5,
            nombre: 'Laura Hernández',
            email: 'laura.hernandez@email.com',
            tipo: 'ADMIN',
            estado: 'ACTIVO',
            ultimoAcceso: '2023-11-10 08:00',
            fechaRegistro: '2023-06-10'
        }
    ];

    mostrarUsuariosEnTabla(usuariosData);

    // Configurar filtros
    document.getElementById('buscarUsuario').addEventListener('input', filtrarUsuarios);
    document.getElementById('filtroTipoUsuario').addEventListener('change', filtrarUsuarios);
    document.getElementById('filtroEstado').addEventListener('change', filtrarUsuarios);
}

function mostrarUsuariosEnTabla(usuarios) {
    const tbody = document.getElementById('tablaUsuarios');
    tbody.innerHTML = '';

    usuarios.forEach(usuario => {
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td>
                <div class="user-info">
                    <strong>${usuario.nombre}</strong>
                    <small>ID: ${usuario.id}</small>
                </div>
            </td>
            <td>${usuario.email}</td>
            <td>
                <span class="user-type">${obtenerEtiquetaTipo(usuario.tipo)}</span>
            </td>
            <td>
                <span class="user-status status-${usuario.estado.toLowerCase()}">
                    ${obtenerEtiquetaEstado(usuario.estado)}
                </span>
            </td>
            <td>${formatearFecha(usuario.ultimoAcceso)}</td>
            <td>
                <div class="table-actions">
                    <button class="btn btn-sm btn-outline" onclick="editarUsuario(${usuario.id})" title="Editar">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-outline" onclick="verPerfilUsuario(${usuario.id})" title="Ver Perfil">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-outline" onclick="cambiarEstadoUsuario(${usuario.id})" title="Cambiar Estado">
                        <i class="fas fa-cog"></i>
                    </button>
                </div>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

function obtenerEtiquetaTipo(tipo) {
    const tipos = {
        'ESTUDIANTE': 'Estudiante',
        'PROFESOR': 'Profesor',
        'TUTOR': 'Tutor',
        'ADMIN': 'Administrador'
    };
    return tipos[tipo] || tipo;
}

function obtenerEtiquetaEstado(estado) {
    const estados = {
        'ACTIVO': 'Activo',
        'INACTIVO': 'Inactivo',
        'BLOQUEADO': 'Bloqueado'
    };
    return estados[estado] || estado;
}

function formatearFecha(fechaStr) {
    const fecha = new Date(fechaStr);
    return fecha.toLocaleDateString('es-ES') + ' ' + fecha.toLocaleTimeString('es-ES', {hour: '2-digit', minute:'2-digit'});
}

function filtrarUsuarios() {
    const busqueda = document.getElementById('buscarUsuario').value.toLowerCase();
    const tipoFiltro = document.getElementById('filtroTipoUsuario').value;
    const estadoFiltro = document.getElementById('filtroEstado').value;

    const usuariosFiltrados = usuariosData.filter(usuario => {
        const coincideBusqueda = usuario.nombre.toLowerCase().includes(busqueda) ||
            usuario.email.toLowerCase().includes(busqueda);
        const coincideTipo = !tipoFiltro || usuario.tipo === tipoFiltro;
        const coincideEstado = !estadoFiltro || usuario.estado === estadoFiltro;

        return coincideBusqueda && coincideTipo && coincideEstado;
    });

    mostrarUsuariosEnTabla(usuariosFiltrados);
}

function abrirModalNuevoUsuario() {
    // Cerrar modal existente si hay uno
    cerrarModalUsuario();

    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.id = 'nuevoUsuarioModal';
    modal.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 10000;
    `;

    modal.innerHTML = `
        <div class="modal-content" style="
            background-color: var(--card-bg);
            border-radius: 8px;
            padding: 2rem;
            width: 90%;
            max-width: 600px;
            max-height: 90vh;
            overflow-y: auto;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
            position: relative;
        ">
            <div class="modal-header" style="
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
                padding-bottom: 1rem;
                border-bottom: 1px solid var(--border-color);
            ">
                <h3 style="color: var(--primary); margin: 0;">Crear Nuevo Usuario</h3>
                <button class="close-modal" style="
                    background: none;
                    border: none;
                    font-size: 1.5rem;
                    cursor: pointer;
                    color: var(--text-color);
                    padding: 0;
                    width: 30px;
                    height: 30px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                ">&times;</button>
            </div>
            
            <form id="nuevoUsuarioForm" style="margin: 0;">
                <div class="user-form-grid" style="
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 1rem;
                    margin-bottom: 1.5rem;
                ">
                    <div class="form-group">
                        <label for="nuevoNombre" style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Nombre Completo</label>
                        <input type="text" id="nuevoNombre" class="form-control" required style="width: 100%;">
                    </div>
                    
                    <div class="form-group">
                        <label for="nuevoEmail" style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Email</label>
                        <input type="email" id="nuevoEmail" class="form-control" required style="width: 100%;">
                    </div>
                    
                    <div class="form-group">
                        <label for="nuevoTipo" style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Tipo de Usuario</label>
                        <select id="nuevoTipo" class="form-control" required style="width: 100%;">
                            <option value="">Seleccione tipo</option>
                            <option value="ESTUDIANTE">Estudiante</option>
                            <option value="PROFESOR">Profesor</option>
                            <option value="TUTOR">Tutor</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="nuevoEstado" style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Estado</label>
                        <select id="nuevoEstado" class="form-control" required style="width: 100%;">
                            <option value="ACTIVO">Activo</option>
                            <option value="INACTIVO">Inactivo</option>
                        </select>
                    </div>
                    
                    <div class="form-group" style="grid-column: 1 / -1;">
                        <label for="nuevaContrasena" style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Contraseña Temporal</label>
                        <input type="password" id="nuevaContrasena" class="form-control" required style="width: 100%;">
                        <small style="font-size: 0.875rem; opacity: 0.7;">El usuario deberá cambiar esta contraseña en su primer acceso</small>
                    </div>
                </div>
                
                <div class="form-actions" style="
                    display: flex;
                    gap: 1rem;
                    justify-content: flex-end;
                    margin-top: 1.5rem;
                    padding-top: 1.5rem;
                    border-top: 1px solid var(--border-color);
                ">
                    <button type="button" class="btn btn-outline" onclick="cerrarModalUsuario()" style="padding: 0.5rem 1.5rem;">Cancelar</button>
                    <button type="submit" class="btn btn-primary" style="padding: 0.5rem 1.5rem;">Crear Usuario</button>
                </div>
            </form>
        </div>
    `;

    document.body.appendChild(modal);

    // Configurar eventos
    document.querySelector('#nuevoUsuarioModal .close-modal').addEventListener('click', cerrarModalUsuario);
    document.getElementById('nuevoUsuarioForm').addEventListener('submit', function(e) {
        e.preventDefault();
        guardarNuevoUsuario();
    });

    // Cerrar modal al hacer clic fuera
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            cerrarModalUsuario();
        }
    });

    // Cerrar con ESC
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            cerrarModalUsuario();
        }
    });
}

function cerrarModalUsuario() {
    const modal = document.getElementById('nuevoUsuarioModal');
    if (modal) {
        // Remover event listener de ESC
        document.removeEventListener('keydown', cerrarModalUsuario);
        modal.remove();
    }

    // También limpiar cualquier otro modal que pueda estar abierto
    const modales = document.querySelectorAll('.modal');
    modales.forEach(modal => {
        if (modal.id !== 'nuevoUsuarioModal') { // Por si acaso hay otros modales
            modal.remove();
        }
    });
}

function guardarNuevoUsuario() {
    const nuevoUsuario = {
        id: Date.now(),
        nombre: document.getElementById('nuevoNombre').value,
        email: document.getElementById('nuevoEmail').value,
        tipo: document.getElementById('nuevoTipo').value,
        estado: document.getElementById('nuevoEstado').value,
        ultimoAcceso: new Date().toISOString(),
        fechaRegistro: new Date().toISOString()
    };

    // Agregar a los datos
    usuariosData.push(nuevoUsuario);

    // Actualizar tabla
    mostrarUsuariosEnTabla(usuariosData);

    alert('Usuario creado exitosamente');
    cerrarModalUsuario();
}

function editarUsuario(usuarioId) {
    const usuario = usuariosData.find(u => u.id === usuarioId);
    if (usuario) {
        alert(`Editar usuario: ${usuario.nombre}\nEsta funcionalidad está en desarrollo.`);
    }
}

function verPerfilUsuario(usuarioId) {
    const usuario = usuariosData.find(u => u.id === usuarioId);
    if (usuario) {
        // En una aplicación real, redirigiría a la página de perfil del usuario
        alert(`Perfil de ${usuario.nombre}\nTipo: ${usuario.tipo}\nEstado: ${usuario.estado}`);
    }
}

function cambiarEstadoUsuario(usuarioId) {
    const usuario = usuariosData.find(u => u.id === usuarioId);
    if (usuario) {
        const nuevoEstado = usuario.estado === 'ACTIVO' ? 'INACTIVO' : 'ACTIVO';
        if (confirm(`¿Está seguro de cambiar el estado de ${usuario.nombre} a ${nuevoEstado}?`)) {
            usuario.estado = nuevoEstado;
            mostrarUsuariosEnTabla(usuariosData);
            alert(`Estado de ${usuario.nombre} cambiado a ${nuevoEstado}`);
        }
    }
}

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    // Por defecto mostrar la sección de dashboard
    mostrarSeccion('dashboard');

    // Cargar datos iniciales
    cargarUsuarios();
});