// Funcionalidades para formularios y evaluaciones
let formularioActual = null;
let evaluacionActual = null;
let tiempoRestante = 0;
let timerInterval = null;

// Sistema de pestañas
function abrirTab(tabName) {
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

// Formularios
function iniciarFormulario(formId) {
    formularioActual = {
        id: formId,
        pasoActual: 1,
        totalPasos: 3,
        respuestas: {}
    };

    abrirModalFormulario();
}

function abrirModalFormulario() {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.id = 'formularioModal';
    modal.innerHTML = `
        <div class="modal-content form-modal">
            <div class="modal-header">
                <h3>Encuesta de Satisfacción - Física Básica</h3>
                <button class="close-modal" onclick="cerrarModalFormulario()">&times;</button>
            </div>
            
            <div class="form-progress">
                ${[1, 2, 3].map(paso => `
                    <div class="progress-step ${paso === 1 ? 'active' : ''} ${paso < 1 ? 'completed' : ''}">
                        <div class="step-number">${paso}</div>
                        <div class="step-label">Paso ${paso}</div>
                    </div>
                `).join('')}
            </div>
            
            <form id="formularioCompleto">
                <!-- Paso 1: Información General -->
                <div class="form-step active" id="paso1">
                    <div class="question-group">
                        <h4>1. ¿Cómo calificarías tu experiencia general en el curso de Física Básica?</h4>
                        <div class="rating-scale">
                            ${[1, 2, 3, 4, 5].map(puntuacion => `
                                <label class="rating-option">
                                    <input type="radio" name="experiencia_general" value="${puntuacion}" required>
                                    <div class="rating-circle">${puntuacion}</div>
                                    <span>${puntuacion === 1 ? 'Muy mala' : puntuacion === 5 ? 'Excelente' : ''}</span>
                                </label>
                            `).join('')}
                        </div>
                    </div>
                    
                    <div class="question-group">
                        <h4>2. ¿El contenido del curso cumplió con tus expectativas?</h4>
                        <div class="form-group">
                            <label>
                                <input type="radio" name="cumplio_expectativas" value="SI" required>
                                Sí, completamente
                            </label>
                            <label>
                                <input type="radio" name="cumplio_expectativas" value="PARCIALMENTE">
                                Parcialmente
                            </label>
                            <label>
                                <input type="radio" name="cumplio_expectativas" value="NO">
                                No cumplió
                            </label>
                        </div>
                    </div>
                </div>
                
                <!-- Paso 2: Contenido y Metodología -->
                <div class="form-step" id="paso2">
                    <div class="question-group">
                        <h4>3. Califica los siguientes aspectos del curso:</h4>
                        
                        <div class="form-group">
                            <label>Calidad del material didáctico</label>
                            <select class="form-control" name="calidad_material" required>
                                <option value="">Selecciona una calificación</option>
                                <option value="5">Excelente</option>
                                <option value="4">Muy bueno</option>
                                <option value="3">Bueno</option>
                                <option value="2">Regular</option>
                                <option value="1">Deficiente</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label>Claridad de las explicaciones</label>
                            <select class="form-control" name="claridad_explicaciones" required>
                                <option value="">Selecciona una calificación</option>
                                <option value="5">Excelente</option>
                                <option value="4">Muy bueno</option>
                                <option value="3">Bueno</option>
                                <option value="2">Regular</option>
                                <option value="1">Deficiente</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label>Utilidad de los ejercicios prácticos</label>
                            <select class="form-control" name="utilidad_ejercicios" required>
                                <option value="">Selecciona una calificación</option>
                                <option value="5">Excelente</option>
                                <option value="4">Muy bueno</option>
                                <option value="3">Bueno</option>
                                <option value="2">Regular</option>
                                <option value="1">Deficiente</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <!-- Paso 3: Comentarios Adicionales -->
                <div class="form-step" id="paso3">
                    <div class="question-group">
                        <h4>4. ¿Qué aspecto del curso consideras que podría mejorar?</h4>
                        <textarea class="form-control" name="aspectos_mejora" rows="4" placeholder="Escribe tus sugerencias..."></textarea>
                    </div>
                    
                    <div class="question-group">
                        <h4>5. ¿Recomendarías este curso a otros estudiantes?</h4>
                        <div class="form-group">
                            <label>
                                <input type="radio" name="recomendacion" value="SI" required>
                                Sí, definitivamente
                            </label>
                            <label>
                                <input type="radio" name="recomendacion" value="PROBABLEMENTE">
                                Probablemente
                            </label>
                            <label>
                                <input type="radio" name="recomendacion" value="NO">
                                No lo recomendaría
                            </label>
                        </div>
                    </div>
                </div>
                
                <div class="form-navigation">
                    <button type="button" class="btn btn-outline" id="btnAnterior" onclick="anteriorPaso()" style="display: none;">
                        <i class="fas fa-arrow-left"></i> Anterior
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSiguiente" onclick="siguientePaso()">
                        Siguiente <i class="fas fa-arrow-right"></i>
                    </button>
                    <button type="submit" class="btn btn-success" id="btnEnviar" style="display: none;">
                        <i class="fas fa-paper-plane"></i> Enviar Formulario
                    </button>
                </div>
            </form>
        </div>
    `;

    document.body.appendChild(modal);
    modal.style.display = 'flex';

    // Configurar el formulario
    document.getElementById('formularioCompleto').addEventListener('submit', function(e) {
        e.preventDefault();
        enviarFormulario();
    });
}

function cerrarModalFormulario() {
    const modal = document.getElementById('formularioModal');
    if (modal) {
        if (formularioActual && Object.keys(formularioActual.respuestas).length > 0) {
            if (confirm('Tienes respuestas sin enviar. ¿Estás seguro de que quieres salir?')) {
                modal.remove();
                formularioActual = null;
            }
        } else {
            modal.remove();
            formularioActual = null;
        }
    }
}

function siguientePaso() {
    const pasoActual = formularioActual.pasoActual;
    const formulario = document.getElementById('formularioCompleto');

    // Validar campos requeridos del paso actual
    const inputsRequeridos = document.querySelectorAll(`#paso${pasoActual} [required]`);
    let valido = true;

    inputsRequeridos.forEach(input => {
        if (!input.value) {
            valido = false;
            input.classList.add('error');
        } else {
            input.classList.remove('error');
        }
    });

    if (!valido) {
        alert('Por favor completa todos los campos requeridos antes de continuar.');
        return;
    }

    // Guardar respuestas del paso actual
    guardarRespuestasPaso(pasoActual);

    // Avanzar al siguiente paso
    formularioActual.pasoActual++;

    if (formularioActual.pasoActual > formularioActual.totalPasos) {
        formularioActual.pasoActual = formularioActual.totalPasos;
    }

    actualizarVistaFormulario();
}

function anteriorPaso() {
    formularioActual.pasoActual--;

    if (formularioActual.pasoActual < 1) {
        formularioActual.pasoActual = 1;
    }

    actualizarVistaFormulario();
}

function actualizarVistaFormulario() {
    // Ocultar todos los pasos
    document.querySelectorAll('.form-step').forEach(paso => {
        paso.classList.remove('active');
    });

    // Mostrar paso actual
    document.getElementById(`paso${formularioActual.pasoActual}`).classList.add('active');

    // Actualizar progreso
    document.querySelectorAll('.progress-step').forEach((step, index) => {
        const pasoNumero = index + 1;
        step.classList.remove('active', 'completed');

        if (pasoNumero === formularioActual.pasoActual) {
            step.classList.add('active');
        } else if (pasoNumero < formularioActual.pasoActual) {
            step.classList.add('completed');
        }
    });

    // Actualizar botones de navegación
    document.getElementById('btnAnterior').style.display = formularioActual.pasoActual > 1 ? 'block' : 'none';
    document.getElementById('btnSiguiente').style.display = formularioActual.pasoActual < formularioActual.totalPasos ? 'block' : 'none';
    document.getElementById('btnEnviar').style.display = formularioActual.pasoActual === formularioActual.totalPasos ? 'block' : 'none';
}

function guardarRespuestasPaso(paso) {
    const formulario = document.getElementById('formularioCompleto');
    const datos = new FormData(formulario);

    for (let [key, value] of datos.entries()) {
        formularioActual.respuestas[key] = value;
    }
}

function enviarFormulario() {
    // Guardar respuestas finales
    guardarRespuestasPaso(formularioActual.pasoActual);

    // Simular envío al servidor
    console.log('Respuestas del formulario:', formularioActual.respuestas);

    // Guardar en localStorage
    const formulariosCompletados = JSON.parse(localStorage.getItem('formulariosCompletados') || '[]');
    formulariosCompletados.push({
        id: formularioActual.id,
        respuestas: formularioActual.respuestas,
        fecha: new Date().toISOString()
    });
    localStorage.setItem('formulariosCompletados', JSON.stringify(formulariosCompletados));

    // Mostrar confirmación
    alert('¡Formulario enviado exitosamente! Gracias por tu feedback.');

    // Cerrar modal y actualizar interfaz
    cerrarModalFormulario();
    location.reload();
}

// Evaluaciones
function iniciarEvaluacion(evalId) {
    if (!confirm('¿Estás listo para comenzar la evaluación? Una vez iniciada, el temporizador comenzará.')) {
        return;
    }

    evaluacionActual = {
        id: evalId,
        preguntas: generarPreguntasEvaluacion(),
        respuestas: {},
        tiempoInicio: new Date(),
        tiempoRestante: 90 * 60 // 90 minutos en segundos
    };

    iniciarTemporizador();
    abrirModalEvaluacion();
}

function generarPreguntasEvaluacion() {
    // Generar preguntas de ejemplo
    return [
        {
            id: 1,
            tipo: 'opcion_multiple',
            enunciado: '¿Cuál de las siguientes es una magnitud vectorial?',
            opciones: [
                'Temperatura',
                'Masa',
                'Velocidad',
                'Tiempo'
            ],
            respuestaCorrecta: 2
        },
        {
            id: 2,
            tipo: 'opcion_multiple',
            enunciado: 'La primera ley de Newton establece que:',
            opciones: [
                'F = m × a',
                'Todo cuerpo permanece en reposo o MRU a menos que una fuerza actúe sobre él',
                'Por cada acción hay una reacción igual y opuesta',
                'La energía no se crea ni se destruye, solo se transforma'
            ],
            respuestaCorrecta: 1
        },
        // Más preguntas...
    ];
}

function iniciarTemporizador() {
    tiempoRestante = evaluacionActual.tiempoRestante;

    timerInterval = setInterval(() => {
        tiempoRestante--;

        if (tiempoRestante <= 0) {
            finalizarEvaluacionAutomaticamente();
            return;
        }

        actualizarTemporizador();
    }, 1000);
}

function actualizarTemporizador() {
    const minutos = Math.floor(tiempoRestante / 60);
    const segundos = tiempoRestante % 60;

    const timerElement = document.getElementById('evaluationTimer');
    if (timerElement) {
        timerElement.textContent = `${minutos.toString().padStart(2, '0')}:${segundos.toString().padStart(2, '0')}`;

        // Cambiar color según el tiempo restante
        if (tiempoRestante <= 300) { // 5 minutos
            timerElement.parentElement.className = 'evaluation-timer timer-danger';
        } else if (tiempoRestante <= 600) { // 10 minutos
            timerElement.parentElement.className = 'evaluation-timer timer-warning';
        }
    }
}

function abrirModalEvaluacion() {
    // Implementación similar al modal de formulario pero para evaluaciones
    alert('Modal de evaluación en desarrollo...');
}

// Otras funciones
function verDetallesFormulario(formId) {
    alert(`Detalles del formulario: ${formId}`);
}

function descargarFormulario(formId) {
    alert(`Descargando formulario: ${formId}`);
}

function verInstrucciones(evalId) {
    alert(`Instrucciones para: ${evalId}`);
}

function verResultadosEvaluacion(evalId) {
    alert(`Resultados de: ${evalId}`);
}

function revisarEvaluacion(evalId) {
    alert(`Revisando evaluación: ${evalId}`);
}

function verDetallesResultado(resultadoId) {
    alert(`Detalles del resultado: ${resultadoId}`);
}

function generarReporteCompleto() {
    alert('Generando reporte completo de resultados...');
}

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    // Configurar filtros
    const filtroEstado = document.getElementById('filtroEstadoForm');
    const filtroCurso = document.getElementById('filtroCursoEval');

    if (filtroEstado) {
        filtroEstado.addEventListener('change', filtrarFormularios);
    }

    if (filtroCurso) {
        filtroCurso.addEventListener('change', filtrarEvaluaciones);
    }
});

function filtrarFormularios() {
    const estado = document.getElementById('filtroEstadoForm').value;
    // Implementar filtrado de formularios
}

function filtrarEvaluaciones() {
    const curso = document.getElementById('filtroCursoEval').value;
    // Implementar filtrado de evaluaciones
}