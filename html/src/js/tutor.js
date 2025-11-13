// Funcionalidades específicas para el tutor
function cambiarModoEstudiante() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

    // Verificar que el usuario es tutor
    if (currentUser.type !== 'TUTOR') {
        alert('Esta función solo está disponible para tutores');
        return;
    }

    // Guardar estado de tutor temporalmente
    localStorage.setItem('modoOriginal', currentUser.type);

    // Cambiar a modo estudiante
    const userEstudiante = {
        ...currentUser,
        type: 'ESTUDIANTE',
        esTutor: true // Marcar que originalmente es tutor
    };

    localStorage.setItem('currentUser', JSON.stringify(userEstudiante));

    // Redirigir al dashboard de estudiante
    window.location.href = 'home-estudiante.html';
}

function crearGrupoTutoria() {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.id = 'nuevoGrupoModal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Crear Nuevo Grupo de Tutoría</h3>
                <button class="close-modal">&times;</button>
            </div>
            <form id="nuevoGrupoForm">
                <div class="form-group">
                    <label for="grupoNombre">Nombre del Grupo</label>
                    <input type="text" id="grupoNombre" class="form-control" placeholder="Ej: Física Básica - Grupo C" required>
                </div>
                
                <div class="form-group">
                    <label for="grupoCurso">Curso Asociado</label>
                    <select id="grupoCurso" class="form-control" required>
                        <option value="">Seleccione un curso</option>
                        <option value="FISICA_BASICA">Física Básica</option>
                        <option value="CALCULO_DIFERENCIAL">Cálculo Diferencial</option>
                        <option value="CALCULO_INTEGRAL">Cálculo Integral</option>
                        <option value="QUIMICA_GENERAL">Química General</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="grupoDescripcion">Descripción</label>
                    <textarea id="grupoDescripcion" class="form-control" rows="3" placeholder="Objetivos del grupo de tutoría..." required></textarea>
                </div>
                
                <div class="form-group">
                    <label for="grupoCapacidad">Capacidad Máxima</label>
                    <input type="number" id="grupoCapacidad" class="form-control" min="3" max="15" value="8" required>
                </div>
                
                <div class="form-group">
                    <label for="grupoHorario">Horario de Tutorías</label>
                    <input type="text" id="grupoHorario" class="form-control" placeholder="Ej: Lunes y Miércoles 4:00-6:00 PM" required>
                </div>
                
                <div class="form-actions">
                    <button type="button" class="btn btn-outline" onclick="cerrarModalGrupo()">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Crear Grupo</button>
                </div>
            </form>
        </div>
    `;

    document.body.appendChild(modal);
    modal.style.display = 'flex';

    // Configurar eventos del modal
    document.querySelector('.close-modal').addEventListener('click', cerrarModalGrupo);
    document.getElementById('nuevoGrupoForm').addEventListener('submit', function(e) {
        e.preventDefault();
        guardarNuevoGrupo();
    });

    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            cerrarModalGrupo();
        }
    });
}

function cerrarModalGrupo() {
    const modal = document.getElementById('nuevoGrupoModal');
    if (modal) {
        modal.remove();
    }
}

function guardarNuevoGrupo() {
    const nombre = document.getElementById('grupoNombre').value;
    const curso = document.getElementById('grupoCurso').value;
    const descripcion = document.getElementById('grupoDescripcion').value;
    const capacidad = document.getElementById('grupoCapacidad').value;
    const horario = document.getElementById('grupoHorario').value;

    // Simular guardado del grupo
    const nuevoGrupo = {
        id: 'G' + Date.now(),
        nombre: nombre,
        curso: curso,
        descripcion: descripcion,
        capacidad: parseInt(capacidad),
        horario: horario,
        estudiantes: [],
        fechaCreacion: new Date().toISOString()
    };

    // Guardar en localStorage
    let grupos = JSON.parse(localStorage.getItem('gruposTutoria') || '[]');
    grupos.push(nuevoGrupo);
    localStorage.setItem('gruposTutoria', JSON.stringify(grupos));

    alert('¡Grupo de tutoría creado exitosamente!');
    cerrarModalGrupo();
    location.reload();
}

function verDetallesGrupo(grupoId) {
    // Redirigir a página de detalles del grupo
    window.location.href = `grupo-tutoria.html?id=${grupoId}`;
}

function generarReporte(grupoId) {
    // Simular generación de reporte
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Generar Reporte del Grupo</h3>
                <button class="close-modal">&times;</button>
            </div>
            <div class="modal-body">
                <p>Seleccione el tipo de reporte a generar:</p>
                
                <div class="report-options">
                    <label>
                        <input type="radio" name="reportType" value="progreso" checked>
                        Reporte de Progreso General
                    </label>
                    <label>
                        <input type="radio" name="reportType" value="asistencia">
                        Reporte de Asistencia a Tutorías
                    </label>
                    <label>
                        <input type="radio" name="reportType" value="rendimiento">
                        Análisis de Rendimiento Individual
                    </label>
                </div>
                
                <div class="date-range" style="margin-top: 1rem;">
                    <label>Rango de fechas:</label>
                    <div style="display: flex; gap: 1rem; margin-top: 0.5rem;">
                        <input type="date" id="fechaInicio" class="form-control">
                        <input type="date" id="fechaFin" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-actions">
                <button type="button" class="btn btn-outline" onclick="cerrarModalReporte()">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="descargarReporte('${grupoId}')">Generar Reporte</button>
            </div>
        </div>
    `;

    modal.id = 'reporteModal';
    document.body.appendChild(modal);
    modal.style.display = 'flex';

    document.querySelector('.close-modal').addEventListener('click', cerrarModalReporte);
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            cerrarModalReporte();
        }
    });
}

function cerrarModalReporte() {
    const modal = document.getElementById('reporteModal');
    if (modal) {
        modal.remove();
    }
}

function descargarReporte(grupoId) {
    const reportType = document.querySelector('input[name="reportType"]:checked').value;
    const fechaInicio = document.getElementById('fechaInicio').value;
    const fechaFin = document.getElementById('fechaFin').value;

    // Simular generación y descarga de reporte
    alert(`Reporte ${reportType} generado para el grupo ${grupoId}\nPeríodo: ${fechaInicio} a ${fechaFin}`);
    cerrarModalReporte();
}

// Verificar si el tutor puede volver a su modo original
document.addEventListener('DOMContentLoaded', function() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    const modoOriginal = localStorage.getItem('modoOriginal');

    // Si el usuario es estudiante pero originalmente era tutor, mostrar opción para volver
    if (currentUser.type === 'ESTUDIANTE' && currentUser.esTutor) {
        mostrarBotonVolverTutor();
    }

    // Cargar datos específicos del tutor
    if (currentUser.type === 'TUTOR') {
        cargarDatosTutor();
    }
});

function mostrarBotonVolverTutor() {
    const nav = document.querySelector('nav ul');
    if (nav) {
        const botonVolver = document.createElement('li');
        botonVolver.innerHTML = `<button class="btn btn-primary" onclick="volverModoTutor()">Volver a Modo Tutor</button>`;
        nav.insertBefore(botonVolver, nav.firstChild);
    }
}

function volverModoTutor() {
    const modoOriginal = localStorage.getItem('modoOriginal');
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

    if (modoOriginal === 'TUTOR') {
        const userTutor = {
            ...currentUser,
            type: 'TUTOR',
            esTutor: true
        };

        localStorage.setItem('currentUser', JSON.stringify(userTutor));
        localStorage.removeItem('modoOriginal');

        window.location.href = 'home-tutor.html';
    }
}

function cargarDatosTutor() {
    // Cargar grupos de tutoría del localStorage
    const grupos = JSON.parse(localStorage.getItem('gruposTutoria') || '[]');

    // Aquí podrías actualizar la interfaz con los grupos reales
    console.log('Grupos de tutoría cargados:', grupos);
}