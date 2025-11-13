// Funcionalidades específicas para el profesor
function crearNuevoCurso() {
    // Crear modal para nuevo curso
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.id = 'nuevoCursoModal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Crear Nuevo Curso</h3>
                <button class="close-modal">&times;</button>
            </div>
            <form id="nuevoCursoForm">
                <div class="form-group">
                    <label for="cursoNombre">Nombre del Curso</label>
                    <input type="text" id="cursoNombre" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="cursoDescripcion">Descripción</label>
                    <textarea id="cursoDescripcion" class="form-control" rows="3" required></textarea>
                </div>
                
                <div class="form-group">
                    <label for="cursoCategoria">Categoría</label>
                    <select id="cursoCategoria" class="form-control" required>
                        <option value="">Seleccione categoría</option>
                        <option value="FISICA">Física</option>
                        <option value="CALCULO">Cálculo</option>
                        <option value="QUIMICA">Química</option>
                        <option value="MATEMATICAS">Matemáticas</option>
                        <option value="PROGRAMACION">Programación</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="cursoNivel">Nivel</label>
                    <select id="cursoNivel" class="form-control" required>
                        <option value="">Seleccione nivel</option>
                        <option value="BASICO">Básico</option>
                        <option value="INTERMEDIO">Intermedio</option>
                        <option value="AVANZADO">Avanzado</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>
                        <input type="checkbox" id="cursoPublico" checked>
                        Curso público (visible para todos los estudiantes)
                    </label>
                </div>
                
                <div class="form-actions">
                    <button type="button" class="btn btn-outline" onclick="cerrarModal()">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Crear Curso</button>
                </div>
            </form>
        </div>
    `;

    document.body.appendChild(modal);
    modal.style.display = 'flex';

    // Configurar eventos del modal
    document.querySelector('.close-modal').addEventListener('click', cerrarModal);
    document.getElementById('nuevoCursoForm').addEventListener('submit', function(e) {
        e.preventDefault();
        guardarNuevoCurso();
    });

    // Cerrar modal al hacer clic fuera
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            cerrarModal();
        }
    });
}

function cerrarModal() {
    const modal = document.getElementById('nuevoCursoModal');
    if (modal) {
        modal.remove();
    }
}

function guardarNuevoCurso() {
    const nombre = document.getElementById('cursoNombre').value;
    const descripcion = document.getElementById('cursoDescripcion').value;
    const categoria = document.getElementById('cursoCategoria').value;
    const nivel = document.getElementById('cursoNivel').value;
    const publico = document.getElementById('cursoPublico').checked;

    // Simular guardado del curso
    const nuevoCurso = {
        id: 'C' + Date.now(),
        nombre: nombre,
        descripcion: descripcion,
        categoria: categoria,
        nivel: nivel,
        publico: publico,
        fechaCreacion: new Date().toISOString()
    };

    // Guardar en localStorage
    let cursos = JSON.parse(localStorage.getItem('cursosProfesor') || '[]');
    cursos.push(nuevoCurso);
    localStorage.setItem('cursosProfesor', JSON.stringify(cursos));

    // Mostrar mensaje de éxito
    alert('¡Curso creado exitosamente!');

    // Cerrar modal y recargar la página
    cerrarModal();
    location.reload();
}

// Funcionalidad para gráficas de progreso
function inicializarGraficas() {
    const ctx = document.getElementById('progresoChart');
    if (ctx) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Física Básica', 'Cálculo', 'Química', 'Álgebra', 'Programación'],
                datasets: [{
                    label: 'Promedio de Aprobación (%)',
                    data: [85, 78, 92, 75, 88],
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
}

// Inicializar cuando la página cargue
document.addEventListener('DOMContentLoaded', function() {
    inicializarGraficas();
});