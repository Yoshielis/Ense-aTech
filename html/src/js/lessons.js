// Funcionalidades para lecciones interactivas
let leccionActual = {
    id: 'L1',
    titulo: 'Introducción a la Física',
    descripcion: 'Conceptos básicos y método científico',
    progreso: 25,
    completada: false
};

// Datos para el experimento virtual
const objetosExperimento = {
    'cubo_hierro': {
        nombre: 'Cubo de Hierro',
        masa: 7800, // gramos
        volumen: 1000, // cm³
        densidad: 7.8 // g/cm³
    },
    'esfera_aluminio': {
        nombre: 'Esfera de Aluminio',
        masa: 2700,
        volumen: 1000,
        densidad: 2.7
    },
    'cilindro_madera': {
        nombre: 'Cilindro de Madera',
        masa: 600,
        volumen: 1000,
        densidad: 0.6
    }
};

// Respuestas correctas del quiz
const respuestasCorrectas = {
    1: 1, // Pregunta 1, opción correcta: índice 1
    2: 5  // Pregunta 2, opción correcta: índice 5
};

// Inicializar lección
document.addEventListener('DOMContentLoaded', function() {
    cargarLeccion();
    actualizarProgreso();
});

function cargarLeccion() {
    document.getElementById('lessonTitle').textContent = leccionActual.titulo;
    document.getElementById('lessonDescription').textContent = leccionActual.descripcion;
    document.getElementById('lessonProgress').style.width = leccionActual.progreso + '%';
}

function realizarMedicion() {
    const objetoSeleccionado = document.getElementById('objectSelect').value;
    const objeto = objetosExperimento[objetoSeleccionado];

    if (objeto) {
        // Mostrar resultados
        document.getElementById('massResult').textContent = `Masa: ${objeto.masa} g`;
        document.getElementById('volumeResult').textContent = `Volumen: ${objeto.volumen} cm³`;
        document.getElementById('densityResult').textContent = `Densidad: ${objeto.densidad} g/cm³`;

        // Análisis de la densidad
        let analisis = '';
        if (objeto.densidad > 5) {
            analisis = 'Este objeto es muy denso, típico de metales como el hierro.';
        } else if (objeto.densidad > 1) {
            analisis = 'Densidad media, común en metales ligeros como el aluminio.';
        } else {
            analisis = 'Baja densidad, característica de materiales como la madera.';
        }

        document.getElementById('densityAnalysis').innerHTML = `<p><strong>Análisis:</strong> ${analisis}</p>`;
        document.getElementById('experimentResults').style.display = 'block';

        // Incrementar progreso si es la primera vez que se realiza el experimento
        if (leccionActual.progreso < 50) {
            leccionActual.progreso = 50;
            actualizarProgreso();
        }
    }
}

function seleccionarOpcion(elemento, indiceOpcion) {
    // Deseleccionar todas las opciones de la misma pregunta
    const pregunta = elemento.closest('.quiz-question');
    pregunta.querySelectorAll('.quiz-option').forEach(opcion => {
        opcion.classList.remove('selected');
        opcion.querySelector('input[type="radio"]').checked = false;
    });

    // Seleccionar la opción clickeada
    elemento.classList.add('selected');
    elemento.querySelector('input[type="radio"]').checked = true;
}

function verificarQuiz() {
    let todasCorrectas = true;
    let preguntasRespondidas = 0;

    // Verificar pregunta 1
    const respuestaQ1 = document.querySelector('input[name="q1"]:checked');
    const resultadoQ1 = document.getElementById('resultQ1');

    if (respuestaQ1) {
        preguntasRespondidas++;
        const esCorrecta = parseInt(respuestaQ1.value) === respuestasCorrectas[1];

        if (esCorrecta) {
            resultadoQ1.className = 'quiz-result correct';
            resultadoQ1.innerHTML = '<i class="fas fa-check"></i> ¡Correcto! La física estudia las propiedades de la materia y la energía.';
        } else {
            resultadoQ1.className = 'quiz-result incorrect';
            resultadoQ1.innerHTML = '<i class="fas fa-times"></i> Incorrecto. La física estudia las propiedades de la materia y la energía.';
            todasCorrectas = false;
        }
        resultadoQ1.style.display = 'block';
    }

    // Verificar pregunta 2
    const respuestaQ2 = document.querySelector('input[name="q2"]:checked');
    const resultadoQ2 = document.getElementById('resultQ2');

    if (respuestaQ2) {
        preguntasRespondidas++;
        const esCorrecta = parseInt(respuestaQ2.value) === respuestasCorrectas[2];

        if (esCorrecta) {
            resultadoQ2.className = 'quiz-result correct';
            resultadoQ2.innerHTML = '<i class="fas fa-check"></i> ¡Correcto! La fuerza es un concepto fundamental en física.';
        } else {
            resultadoQ2.className = 'quiz-result incorrect';
            resultadoQ2.innerHTML = '<i class="fas fa-times"></i> Incorrecto. La fuerza es un concepto fundamental en física.';
            todasCorrectas = false;
        }
        resultadoQ2.style.display = 'block';
    }

    // Si no se respondieron todas las preguntas
    if (preguntasRespondidas < 2) {
        alert('Por favor responde todas las preguntas antes de verificar.');
        return;
    }

    // Si todas son correctas, incrementar progreso y mostrar botón de completar
    if (todasCorrectas && leccionActual.progreso < 100) {
        leccionActual.progreso = 100;
        actualizarProgreso();

        document.getElementById('btnSiguiente').style.display = 'none';
        document.getElementById('btnCompletar').style.display = 'inline-block';

        // Mostrar mensaje de felicitación
        setTimeout(() => {
            alert('¡Felicidades! Has completado todas las actividades de esta lección.');
        }, 500);
    }
}

function actualizarProgreso() {
    document.getElementById('lessonProgress').style.width = leccionActual.progreso + '%';
    document.querySelector('.lesson-progress span').textContent = leccionActual.progreso + '% completado';

    // Guardar progreso en localStorage
    const progresoLecciones = JSON.parse(localStorage.getItem('progresoLecciones') || '{}');
    progresoLecciones[leccionActual.id] = leccionActual.progreso;
    localStorage.setItem('progresoLecciones', JSON.stringify(progresoLecciones));
}

function descargarRecurso(nombreArchivo) {
    // Simular descarga de recurso
    alert(`Descargando: ${nombreArchivo}\nEn una aplicación real, se descargaría el archivo.`);

    // Registrar descarga
    const descargas = JSON.parse(localStorage.getItem('descargasRecursos') || '[]');
    descargas.push({
        archivo: nombreArchivo,
        fecha: new Date().toISOString(),
        leccion: leccionActual.id
    });
    localStorage.setItem('descargasRecursos', JSON.stringify(descargas));
}

function descargarLeccionCompleta() {
    // Simular descarga de lección completa
    alert('Descargando material completo de la lección...\nSe incluirán todos los recursos en un archivo ZIP.');

    // Registrar descarga completa
    const descargasCompletas = JSON.parse(localStorage.getItem('descargasCompletas') || '[]');
    descargasCompletas.push({
        leccion: leccionActual.id,
        fecha: new Date().toISOString()
    });
    localStorage.setItem('descargasCompletas', JSON.stringify(descargasCompletas));
}

function completarLeccion() {
    leccionActual.completada = true;

    // Guardar en el progreso del usuario
    const usuario = JSON.parse(localStorage.getItem('currentUser') || '{}');
    const progresoUsuario = JSON.parse(localStorage.getItem('progresoUsuario') || '{}');

    if (!progresoUsuario.leccionesCompletadas) {
        progresoUsuario.leccionesCompletadas = [];
    }

    if (!progresoUsuario.leccionesCompletadas.includes(leccionActual.id)) {
        progresoUsuario.leccionesCompletadas.push(leccionActual.id);

        // Otorgar puntos
        if (!progresoUsuario.puntos) {
            progresoUsuario.puntos = 0;
        }
        progresoUsuario.puntos += 10;

        localStorage.setItem('progresoUsuario', JSON.stringify(progresoUsuario));

        alert(`¡Lección completada! Has ganado 10 puntos.\nPuntos totales: ${progresoUsuario.puntos}`);
    }

    // Redirigir a la siguiente lección o al listado de cursos
    setTimeout(() => {
        window.location.href = 'cursos.html';
    }, 2000);
}

function anteriorLeccion() {
    alert('Navegando a la lección anterior...');
    // En una aplicación real, cargaría la lección anterior
}

function siguienteLeccion() {
    alert('Navegando a la siguiente lección...');
    // En una aplicación real, cargaría la siguiente lección
}