// JavaScript para detalle del curso
document.addEventListener('DOMContentLoaded', function() {
    cargarDetalleCurso();
});

function cargarDetalleCurso() {
    // Obtener parámetros de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const cursoId = urlParams.get('curso') || 'fisica-basica';

    const cursos = {
        'fisica-basica': {
            titulo: 'Física Básica',
            descripcion: 'Aprende los fundamentos de la física con experimentos caseros',
            progreso: 65,
            instructor: 'Prof. María González',
            duracion: '8 semanas',
            nivel: 'Básico',
            estudiantes: 245
        },
        'calculo-diferencial': {
            titulo: 'Cálculo Diferencial',
            descripcion: 'Aprende derivadas y sus aplicaciones en problemas reales',
            progreso: 42,
            instructor: 'Prof. Ana Martínez',
            duracion: '6 semanas',
            nivel: 'Básico',
            estudiantes: 312
        }
    };

    const curso = cursos[cursoId] || cursos['fisica-basica'];

    // Actualizar interfaz
    document.getElementById('cursoTitulo').textContent = curso.titulo;
    document.getElementById('cursoDescripcion').textContent = curso.descripcion;
    document.querySelector('.progress-fill').style.width = curso.progreso + '%';
    document.querySelector('.curso-progress span').textContent = curso.progreso + '% completado';
}

function continuarCurso() {
    window.location.href = 'lecciones.html?curso=fisica-basica';
}

function descargarMaterial() {
    alert('Descargando material del curso...');
}

function verForo() {
    alert('Redirigiendo al foro del curso...');
}