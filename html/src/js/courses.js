// Funcionalidades para la página de cursos
function descargarCurso(cursoId) {
    // Simular descarga de curso
    const cursosDisponibles = {
        'fisica-basica': {
            nombre: 'Física Básica',
            archivo: 'fisica-basica-material.zip',
            tamaño: '45 MB'
        },
        'fisica-avanzada': {
            nombre: 'Física Avanzada',
            archivo: 'fisica-avanzada-material.zip',
            tamaño: '68 MB'
        },
        'calculo-diferencial': {
            nombre: 'Cálculo Diferencial',
            archivo: 'calculo-diferencial-material.zip',
            tamaño: '52 MB'
        },
        'calculo-integral': {
            nombre: 'Cálculo Integral',
            archivo: 'calculo-integral-material.zip',
            tamaño: '61 MB'
        },
        'calculo-multivariable': {
            nombre: 'Cálculo Multivariable',
            archivo: 'calculo-multivariable-material.zip',
            tamaño: '78 MB'
        }
    };

    const curso = cursosDisponibles[cursoId];
    if (curso) {
        // Mostrar modal de confirmación de descarga
        const modal = document.createElement('div');
        modal.className = 'modal';
        modal.innerHTML = `
            <div class="modal-content">
                <div class="modal-header">
                    <h3>Descargar Material del Curso</h3>
                    <button class="close-modal">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Estás a punto de descargar el material del curso: <strong>${curso.nombre}</strong></p>
                    <p><strong>Archivo:</strong> ${curso.archivo}</p>
                    <p><strong>Tamaño:</strong> ${curso.tamaño}</p>
                    <div class="download-options">
                        <h4>Formatos disponibles:</h4>
                        <div class="format-options">
                            <label>
                                <input type="radio" name="format" value="pdf" checked>
                                PDF Completo
                            </label>
                            <label>
                                <input type="radio" name="format" value="zip">
                                Paquete ZIP (Incluye videos y ejercicios)
                            </label>
                        </div>
                    </div>
                </div>
                <div class="modal-actions">
                    <button type="button" class="btn btn-outline" onclick="cerrarModalDescarga()">Cancelar</button>
                    <button type="button" class="btn btn-primary" onclick="iniciarDescarga('${cursoId}')">Descargar</button>
                </div>
            </div>
        `;

        modal.id = 'descargaModal';
        document.body.appendChild(modal);
        modal.style.display = 'flex';

        // Configurar eventos del modal
        document.querySelector('#descargaModal .close-modal').addEventListener('click', cerrarModalDescarga);
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                cerrarModalDescarga();
            }
        });
    }
}

function cerrarModalDescarga() {
    const modal = document.getElementById('descargaModal');
    if (modal) {
        modal.remove();
    }
}

function iniciarDescarga(cursoId) {
    // Simular proceso de descarga
    const format = document.querySelector('input[name="format"]:checked').value;

    // Mostrar progreso de descarga
    const modal = document.getElementById('descargaModal');
    const modalBody = modal.querySelector('.modal-body');
    const modalActions = modal.querySelector('.modal-actions');

    modalBody.innerHTML = `
        <div class="download-progress">
            <h4>Descargando material...</h4>
            <div class="progress-bar">
                <div class="progress-fill" id="downloadProgress" style="width: 0%"></div>
            </div>
            <p id="progressText">Preparando descarga...</p>
        </div>
    `;

    modalActions.style.display = 'none';

    // Simular progreso de descarga
    let progress = 0;
    const progressInterval = setInterval(() => {
        progress += 5;
        document.getElementById('downloadProgress').style.width = progress + '%';
        document.getElementById('progressText').textContent = `Descargando... ${progress}%`;

        if (progress >= 100) {
            clearInterval(progressInterval);
            setTimeout(() => {
                modalBody.innerHTML = `
                    <div class="download-complete">
                        <i class="fas fa-check-circle" style="color: var(--success); font-size: 3rem; margin-bottom: 1rem;"></i>
                        <h4>¡Descarga completada!</h4>
                        <p>El material del curso ha sido descargado exitosamente.</p>
                    </div>
                `;

                modalActions.style.display = 'flex';
                modalActions.innerHTML = '<button type="button" class="btn btn-primary" onclick="cerrarModalDescarga()">Cerrar</button>';

                // Registrar descarga en el historial
                registrarDescarga(cursoId, format);
            }, 500);
        }
    }, 100);
}

function registrarDescarga(cursoId, formato) {
    const historialDescargas = JSON.parse(localStorage.getItem('historialDescargas') || '[]');
    const descarga = {
        cursoId: cursoId,
        formato: formato,
        fecha: new Date().toISOString()
    };

    historialDescargas.push(descarga);
    localStorage.setItem('historialDescargas', JSON.stringify(historialDescargas));
}

// Filtrado de cursos
document.addEventListener('DOMContentLoaded', function() {
    const categoriaFilter = document.getElementById('categoriaFilter');
    const nivelFilter = document.getElementById('nivelFilter');

    if (categoriaFilter && nivelFilter) {
        categoriaFilter.addEventListener('change', filtrarCursos);
        nivelFilter.addEventListener('change', filtrarCursos);
    }
});

function filtrarCursos() {
    const categoria = document.getElementById('categoriaFilter').value;
    const nivel = document.getElementById('nivelFilter').value;

    const cursos = document.querySelectorAll('.course-card');

    cursos.forEach(curso => {
        let mostrar = true;

        // Filtrar por categoría
        if (categoria) {
            const categoriaCurso = curso.closest('.course-category').querySelector('h2').textContent;
            if (!categoriaCurso.toLowerCase().includes(categoria.toLowerCase())) {
                mostrar = false;
            }
        }

        // Filtrar por nivel
        if (nivel && mostrar) {
            const nivelCurso = curso.querySelector('.level-badge').textContent.toLowerCase();
            if (nivel.toLowerCase() !== nivelCurso) {
                mostrar = false;
            }
        }

        // Mostrar u ocultar curso
        curso.style.display = mostrar ? 'block' : 'none';
    });
}