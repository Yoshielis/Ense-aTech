// Sistema de gestión de perfil de usuario
let usuario = null;
let configuracion = {};

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    cargarDatosUsuario();
    cargarConfiguracion();
    inicializarGraficas();
});

function cargarDatosUsuario() {
    const usuarioGuardado = localStorage.getItem('currentUser');
    if (usuarioGuardado) {
        usuario = JSON.parse(usuarioGuardado);
        mostrarDatosUsuario();
    } else {
        // Datos de ejemplo
        usuario = {
            id: 1,
            nombre: 'Ana García',
            email: 'ana.garcia@email.com',
            tipo: 'ESTUDIANTE',
            fechaRegistro: '2023-08-15',
            perfil: {
                nombres: 'Ana',
                apellidos: 'García',
                telefono: '+52 55 1234 5678',
                fechaNacimiento: '2000-05-15',
                pais: 'MX',
                intereses: ['Física', 'Matemáticas', 'Química', 'Programación']
            }
        };
        localStorage.setItem('currentUser', JSON.stringify(usuario));
    }
}

function mostrarDatosUsuario() {
    if (!usuario) return;

    document.getElementById('profileName').textContent = usuario.nombre;
    document.getElementById('profileType').textContent = obtenerTipoUsuario(usuario.tipo);
    document.getElementById('profileEmail').textContent = usuario.email;
    document.getElementById('memberSince').textContent = formatearFecha(usuario.fechaRegistro);

    // Formulario
    document.getElementById('profileFirstName').value = usuario.perfil?.nombres || '';
    document.getElementById('profileLastName').value = usuario.perfil?.apellidos || '';
    document.getElementById('profileEmailInput').value = usuario.email;
    document.getElementById('profilePhone').value = usuario.perfil?.telefono || '';
    document.getElementById('profileBirthdate').value = usuario.perfil?.fechaNacimiento || '';
    document.getElementById('profileCountry').value = usuario.perfil?.pais || '';
}

function obtenerTipoUsuario(tipo) {
    const tipos = {
        'ESTUDIANTE': 'Estudiante',
        'PROFESOR': 'Profesor',
        'TUTOR': 'Tutor',
        'ADMIN': 'Administrador'
    };
    return tipos[tipo] || tipo;
}

function formatearFecha(fechaStr) {
    const fecha = new Date(fechaStr);
    const opciones = { year: 'numeric', month: 'long' };
    return fecha.toLocaleDateString('es-ES', opciones);
}

function abrirTabPerfil(tabName) {
    // Ocultar todos los contenidos de pestañas
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    // Desactivar todos los botones de pestañas
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    // Mostrar la pestaña actual y activar el botón
    document.getElementById(tabName).classList.add('active');
    event.currentTarget.classList.add('active');
}

function habilitarEdicionPerfil() {
    // Habilitar campos del formulario
    const campos = [
        'profileFirstName',
        'profileLastName',
        'profilePhone',
        'profileBirthdate',
        'profileCountry'
    ];

    campos.forEach(campo => {
        document.getElementById(campo).disabled = false;
    });

    // Mostrar botones de acción
    document.getElementById('profileActions').style.display = 'flex';

    // Ocultar botón de editar
    event.currentTarget.style.display = 'none';
}

function cancelarEdicionPerfil() {
    // Deshabilitar campos del formulario
    const campos = [
        'profileFirstName',
        'profileLastName',
        'profilePhone',
        'profileBirthdate',
        'profileCountry'
    ];

    campos.forEach(campo => {
        document.getElementById(campo).disabled = true;
    });

    // Ocultar botones de acción
    document.getElementById('profileActions').style.display = 'none';

    // Mostrar botón de editar
    document.querySelector('.profile-header .btn').style.display = 'block';

    // Restaurar valores originales
    mostrarDatosUsuario();
}

// Manejo del formulario de perfil
document.getElementById('profileForm').addEventListener('submit', function(e) {
    e.preventDefault();
    guardarPerfil();
});

function guardarPerfil() {
    if (!usuario) return;

    // Actualizar datos del usuario
    usuario.perfil = usuario.perfil || {};
    usuario.perfil.nombres = document.getElementById('profileFirstName').value;
    usuario.perfil.apellidos = document.getElementById('profileLastName').value;
    usuario.perfil.telefono = document.getElementById('profilePhone').value;
    usuario.perfil.fechaNacimiento = document.getElementById('profileBirthdate').value;
    usuario.perfil.pais = document.getElementById('profileCountry').value;

    // Actualizar nombre completo
    usuario.nombre = `${usuario.perfil.nombres} ${usuario.perfil.apellidos}`;

    // Guardar en localStorage
    localStorage.setItem('currentUser', JSON.stringify(usuario));

    // Actualizar interfaz
    mostrarDatosUsuario();
    cancelarEdicionPerfil();

    alert('Perfil actualizado correctamente');
}

function cambiarAvatar() {
    // En una aplicación real, aquí se subiría una imagen
    alert('Funcionalidad de cambio de avatar en desarrollo');
}

// Sistema de configuración
function cargarConfiguracion() {
    const configGuardada = localStorage.getItem('configuracionUsuario');
    if (configGuardada) {
        configuracion = JSON.parse(configGuardada);
    } else {
        // Configuración por defecto
        configuracion = {
            tema: 'auto',
            fontSize: 'medium',
            notificaciones: {
                email: true,
                anuncios: true,
                deadlines: false
            },
            privacidad: {
                perfil: 'students',
                mostrarProgreso: true
            }
        };
        guardarConfiguracion();
    }

    aplicarConfiguracion();
}

function aplicarConfiguracion() {
    // Aplicar tema
    document.getElementById('themePreference').value = configuracion.tema;
    aplicarTema(configuracion.tema);

    // Aplicar tamaño de fuente
    document.getElementById('fontSize').value = configuracion.fontSize;
    aplicarTamañoFuente(configuracion.fontSize);

    // Aplicar notificaciones
    document.getElementById('notifEmail').checked = configuracion.notificaciones.email;
    document.getElementById('notifAnnouncements').checked = configuracion.notificaciones.anuncios;
    document.getElementById('notifDeadlines').checked = configuracion.notificaciones.deadlines;

    // Aplicar privacidad
    document.getElementById('profileVisibility').value = configuracion.privacidad.perfil;
    document.getElementById('showProgress').checked = configuracion.privacidad.mostrarProgreso;
}

function cambiarTema(tema) {
    configuracion.tema = tema;
    aplicarTema(tema);
}

function aplicarTema(tema) {
    if (tema === 'auto') {
        // Detectar preferencia del sistema
        if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            document.body.className = 'dark-mode';
        } else {
            document.body.className = 'light-mode';
        }
    } else {
        document.body.className = tema + '-mode';
    }
}

function cambiarTamañoFuente(tamaño) {
    configuracion.fontSize = tamaño;

    const sizes = {
        'small': '14px',
        'medium': '16px',
        'large': '18px'
    };

    document.documentElement.style.fontSize = sizes[tamaño];
}

function guardarConfiguracion() {
    // Recoger valores actuales
    configuracion.notificaciones.email = document.getElementById('notifEmail').checked;
    configuracion.notificaciones.anuncios = document.getElementById('notifAnnouncements').checked;
    configuracion.notificaciones.deadlines = document.getElementById('notifDeadlines').checked;

    configuracion.privacidad.perfil = document.getElementById('profileVisibility').value;
    configuracion.privacidad.mostrarProgreso = document.getElementById('showProgress').checked;

    // Guardar en localStorage
    localStorage.setItem('configuracionUsuario', JSON.stringify(configuracion));

    alert('Configuración guardada correctamente');
}

function restablecerConfiguracion() {
    if (confirm('¿Estás seguro de que quieres restablecer la configuración a los valores por defecto?')) {
        localStorage.removeItem('configuracionUsuario');
        cargarConfiguracion();
        alert('Configuración restablecida');
    }
}

// Gráficas de progreso
function inicializarGraficas() {
    inicializarGraficaProgresoCursos();
    inicializarGraficaActividadSemanal();
}

function inicializarGraficaProgresoCursos() {
    const ctx = document.getElementById('courseProgressChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Física Básica', 'Cálculo', 'Química', 'Álgebra', 'Programación'],
            datasets: [{
                label: 'Progreso (%)',
                data: [85, 78, 92, 65, 70],
                backgroundColor: [
                    'rgba(67, 97, 238, 0.8)',
                    'rgba(63, 55, 201, 0.8)',
                    'rgba(76, 201, 240, 0.8)',
                    'rgba(248, 150, 30, 0.8)',
                    'rgba(72, 149, 239, 0.8)'
                ],
                borderColor: [
                    'rgba(67, 97, 238, 1)',
                    'rgba(63, 55, 201, 1)',
                    'rgba(76, 201, 240, 1)',
                    'rgba(248, 150, 30, 1)',
                    'rgba(72, 149, 239, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100
                }
            }
        }
    });
}

function inicializarGraficaActividadSemanal() {
    const ctx = document.getElementById('weeklyActivityChart').getContext('2d');

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb', 'Dom'],
            datasets: [{
                label: 'Minutos de estudio',
                data: [120, 90, 150, 180, 210, 60, 45],
                borderColor: 'rgba(67, 97, 238, 1)',
                backgroundColor: 'rgba(67, 97, 238, 0.1)',
                borderWidth: 2,
                fill: true,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Funciones de seguridad
function abrirModalCambioContrasena() {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Cambiar Contraseña</h3>
                <button class="close-modal">&times;</button>
            </div>
            <form id="cambioContrasenaForm">
                <div class="form-group">
                    <label for="contrasenaActual">Contraseña Actual</label>
                    <input type="password" id="contrasenaActual" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="nuevaContrasena">Nueva Contraseña</label>
                    <input type="password" id="nuevaContrasena" class="form-control" required minlength="6">
                    <small>Mínimo 6 caracteres</small>
                </div>
                <div class="form-group">
                    <label for="confirmarContrasena">Confirmar Nueva Contraseña</label>
                    <input type="password" id="confirmarContrasena" class="form-control" required>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn btn-outline" onclick="cerrarModal()">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
                </div>
            </form>
        </div>
    `;

    modal.id = 'cambioContrasenaModal';
    document.body.appendChild(modal);
    modal.style.display = 'flex';

    document.querySelector('.close-modal').addEventListener('click', cerrarModal);
    document.getElementById('cambioContrasenaForm').addEventListener('submit', function(e) {
        e.preventDefault();
        cambiarContrasena();
    });

    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            cerrarModal();
        }
    });
}

function cerrarModal() {
    const modal = document.getElementById('cambioContrasenaModal');
    if (modal) {
        modal.remove();
    }
}

function cambiarContrasena() {
    const contrasenaActual = document.getElementById('contrasenaActual').value;
    const nuevaContrasena = document.getElementById('nuevaContrasena').value;
    const confirmarContrasena = document.getElementById('confirmarContrasena').value;

    if (nuevaContrasena !== confirmarContrasena) {
        alert('Las contraseñas no coinciden');
        return;
    }

    if (nuevaContrasena.length < 6) {
        alert('La nueva contraseña debe tener al menos 6 caracteres');
        return;
    }

    // Simular cambio de contraseña
    alert('Contraseña cambiada exitosamente');
    cerrarModal();
}

function verSesionesActivas() {
    alert('Funcionalidad de sesiones activas en desarrollo');
}

function exportarDatos() {
    if (confirm('¿Estás seguro de que quieres exportar todos tus datos? Se generará un archivo con tu información.')) {
        // Simular exportación
        const datos = {
            usuario: usuario,
            progreso: JSON.parse(localStorage.getItem('progresoUsuario') || '{}'),
            notas: JSON.parse(localStorage.getItem('notasUsuario') || '[]'),
            formularios: JSON.parse(localStorage.getItem('formulariosCompletados') || '[]')
        };

        const blob = new Blob([JSON.stringify(datos, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `datos_ada_entech_${usuario.id}.json`;
        a.click();
        URL.revokeObjectURL(url);

        alert('Datos exportados correctamente');
    }
}

function eliminarCuenta() {
    if (confirm('¿ESTÁS ABSOLUTAMENTE SEGURO? Esta acción eliminará permanentemente tu cuenta y todos tus datos. Esta acción NO se puede deshacer.')) {
        if (prompt('Escribe "ELIMINAR" para confirmar:') === 'ELIMINAR') {
            // Simular eliminación de cuenta
            localStorage.removeItem('currentUser');
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('configuracionUsuario');
            localStorage.removeItem('notasUsuario');
            localStorage.removeItem('progresoUsuario');
            localStorage.removeItem('formulariosCompletados');

            alert('Tu cuenta ha sido eliminada. Serás redirigido a la página de inicio.');
            setTimeout(() => {
                window.location.href = '../index.html';
            }, 2000);
        }
    }
}

// Detectar cambios en el tema del sistema
if (window.matchMedia) {
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
        if (configuracion.tema === 'auto') {
            aplicarTema('auto');
        }
    });
}