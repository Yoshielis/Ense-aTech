// Sistema de gestión de apuntes y notas
let notas = [];
let notaActual = null;
let modoEdicion = false;

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    cargarNotas();
    configurarEventos();
});

function cargarNotas() {
    // Cargar notas del localStorage
    const notasGuardadas = localStorage.getItem('notasUsuario');
    if (notasGuardadas) {
        notas = JSON.parse(notasGuardadas);
    } else {
        // Datos de ejemplo
        notas = [
            {
                id: 1,
                titulo: 'Leyes de Newton - Resumen',
                contenido: '# Leyes de Newton\n\n## Primera Ley (Inercia)\n- Todo cuerpo permanece en reposo o MRU a menos que una fuerza actúe sobre él\n- Ejemplo: Un libro sobre una mesa\n\n## Segunda Ley (Fuerza)\n- F = m × a\n- La aceleración es proporcional a la fuerza aplicada\n\n## Tercera Ley (Acción-Reacción)\n- Por cada acción hay una reacción igual y opuesta\n- Ejemplo: Cohete expulsando gases',
                curso: 'FISICA',
                fecha: '2023-11-08',
                etiquetas: ['física', 'newton', 'leyes'],
                fechaModificacion: '2023-11-08'
            },
            {
                id: 2,
                titulo: 'Derivadas - Fórmulas importantes',
                contenido: '## Derivadas Básicas\n\n- d/dx(xⁿ) = n·xⁿ⁻¹\n- d/dx(sen x) = cos x\n- d/dx(cos x) = -sen x\n- d/dx(eˣ) = eˣ\n- d/dx(ln x) = 1/x\n\n## Reglas de Derivación\n\n### Regla del Producto\n(f·g)\' = f\'·g + f·g\'\n\n### Regla del Cociente\n(f/g)\' = (f\'·g - f·g\')/g²\n\n### Regla de la Cadena\n(f(g(x)))\' = f\'(g(x))·g\'(x)',
                curso: 'CALCULO',
                fecha: '2023-11-05',
                etiquetas: ['cálculo', 'derivadas', 'fórmulas'],
                fechaModificacion: '2023-11-05'
            }
        ];
        guardarNotas();
    }

    mostrarListaNotas();
}

function guardarNotas() {
    localStorage.setItem('notasUsuario', JSON.stringify(notas));
}

function configurarEventos() {
    // Filtros
    document.getElementById('filtroCursoNota').addEventListener('change', filtrarNotas);
    document.getElementById('buscarNota').addEventListener('input', filtrarNotas);

    // Auto-guardado
    document.getElementById('notaContenido').addEventListener('input', function() {
        if (modoEdicion && notaActual) {
            guardarCambiosTemporales();
        }
    });
}

function mostrarListaNotas() {
    const lista = document.getElementById('listaNotas');
    lista.innerHTML = '';

    notas.forEach(nota => {
        const elemento = document.createElement('div');
        elemento.className = 'note-item';
        elemento.innerHTML = `
            <h4>${nota.titulo}</h4>
            <div class="note-preview">${nota.contenido.substring(0, 100)}...</div>
            <div class="note-meta">
                <span>${obtenerNombreCurso(nota.curso)}</span>
                <span>${formatearFecha(nota.fecha)}</span>
            </div>
        `;

        elemento.addEventListener('click', () => cargarNota(nota.id));

        lista.appendChild(elemento);
    });
}

function obtenerNombreCurso(codigo) {
    const cursos = {
        'FISICA': 'Física',
        'CALCULO': 'Cálculo',
        'QUIMICA': 'Química'
    };
    return cursos[codigo] || codigo;
}

function formatearFecha(fechaStr) {
    const fecha = new Date(fechaStr);
    return fecha.toLocaleDateString('es-ES');
}

function cargarNota(id) {
    const nota = notas.find(n => n.id === id);
    if (!nota) return;

    notaActual = nota;

    // Actualizar interfaz
    document.getElementById('notaTitulo').value = nota.titulo;
    document.getElementById('notaContenido').value = nota.contenido;
    document.getElementById('notaCurso').value = nota.curso;
    document.getElementById('notaFecha').value = formatearFecha(nota.fecha);

    // Actualizar etiquetas
    mostrarEtiquetas(nota.etiquetas);

    // Habilitar botones
    document.getElementById('btnEditar').disabled = false;
    document.getElementById('btnEliminar').disabled = false;

    // Resaltar elemento en la lista
    document.querySelectorAll('.note-item').forEach(item => {
        item.classList.remove('active');
    });
    event.currentTarget.classList.add('active');

    // Mostrar vista previa
    mostrarVistaPrevia(nota.contenido);
}

function mostrarVistaPrevia(contenido) {
    const vistaPrevia = document.getElementById('vistaPrevia');
    vistaPrevia.innerHTML = convertirMarkdown(contenido);
    vistaPrevia.style.display = 'block';
    document.getElementById('notaContenido').style.display = 'none';
}

function convertirMarkdown(texto) {
    // Conversión básica de Markdown a HTML
    return texto
        .replace(/^# (.*$)/gim, '<h1>$1</h1>')
        .replace(/^## (.*$)/gim, '<h2>$1</h2>')
        .replace(/^### (.*$)/gim, '<h3>$1</h3>')
        .replace(/\*\*(.*?)\*\*/gim, '<strong>$1</strong>')
        .replace(/\*(.*?)\*/gim, '<em>$1</em>')
        .replace(/^- (.*$)/gim, '<ul><li>$1</li></ul>')
        .replace(/^\d+\. (.*$)/gim, '<ol><li>$1</li></ol>')
        .replace(/\n/g, '<br>');
}

function crearNuevaNota() {
    const nuevaNota = {
        id: Date.now(),
        titulo: 'Nueva Nota',
        contenido: '',
        curso: '',
        fecha: new Date().toISOString().split('T')[0],
        etiquetas: [],
        fechaModificacion: new Date().toISOString()
    };

    notas.unshift(nuevaNota);
    guardarNotas();
    mostrarListaNotas();
    cargarNota(nuevaNota.id);
    habilitarEdicion();
}

function habilitarEdicion() {
    modoEdicion = true;

    // Habilitar campos
    document.getElementById('notaTitulo').disabled = false;
    document.getElementById('notaContenido').disabled = false;
    document.getElementById('notaCurso').disabled = false;
    document.getElementById('inputTag').disabled = false;

    // Mostrar/ocultar botones
    document.getElementById('btnEditar').style.display = 'none';
    document.getElementById('btnGuardar').style.display = 'inline-block';
    document.getElementById('btnCancelar').style.display = 'inline-block';

    // Cambiar a modo edición
    document.getElementById('vistaPrevia').style.display = 'none';
    document.getElementById('notaContenido').style.display = 'block';

    // Enfocar el título
    document.getElementById('notaTitulo').focus();
}

function cancelarEdicion() {
    modoEdicion = false;

    // Deshabilitar campos
    document.getElementById('notaTitulo').disabled = true;
    document.getElementById('notaContenido').disabled = true;
    document.getElementById('notaCurso').disabled = true;
    document.getElementById('inputTag').disabled = true;

    // Mostrar/ocultar botones
    document.getElementById('btnEditar').style.display = 'inline-block';
    document.getElementById('btnGuardar').style.display = 'none';
    document.getElementById('btnCancelar').style.display = 'none';

    // Restaurar valores originales
    if (notaActual) {
        cargarNota(notaActual.id);
    }
}

function guardarNota() {
    if (!notaActual) return;

    // Actualizar datos de la nota
    notaActual.titulo = document.getElementById('notaTitulo').value;
    notaActual.contenido = document.getElementById('notaContenido').value;
    notaActual.curso = document.getElementById('notaCurso').value;
    notaActual.fechaModificacion = new Date().toISOString();

    guardarNotas();
    mostrarListaNotas();
    cancelarEdicion();

    alert('Nota guardada correctamente');
}

function guardarCambiosTemporales() {
    // Para auto-guardado en tiempo real
    if (notaActual && modoEdicion) {
        notaActual.titulo = document.getElementById('notaTitulo').value;
        notaActual.contenido = document.getElementById('notaContenido').value;
        notaActual.curso = document.getElementById('notaCurso').value;

        // No guardar en localStorage inmediatamente para evitar sobrecarga
    }
}

function eliminarNota() {
    if (!notaActual) return;

    if (confirm('¿Estás seguro de que quieres eliminar esta nota? Esta acción no se puede deshacer.')) {
        notas = notas.filter(n => n.id !== notaActual.id);
        guardarNotas();
        mostrarListaNotas();

        // Limpiar editor
        document.getElementById('notaTitulo').value = '';
        document.getElementById('notaContenido').value = '';
        document.getElementById('notaCurso').value = '';
        document.getElementById('notaFecha').value = '';
        document.getElementById('listaEtiquetas').innerHTML = '';

        document.getElementById('btnEditar').disabled = true;
        document.getElementById('btnEliminar').disabled = true;

        notaActual = null;
    }
}

// Funciones de formato de texto
function formatearTexto(comando) {
    const textarea = document.getElementById('notaContenido');
    const inicio = textarea.selectionStart;
    const fin = textarea.selectionEnd;
    const textoSeleccionado = textarea.value.substring(inicio, fin);

    let textoFormateado = '';

    switch(comando) {
        case 'bold':
            textoFormateado = `**${textoSeleccionado}**`;
            break;
        case 'italic':
            textoFormateado = `*${textoSeleccionado}*`;
            break;
        case 'underline':
            textoFormateado = `<u>${textoSeleccionado}</u>`;
            break;
    }

    textarea.value = textarea.value.substring(0, inicio) + textoFormateado + textarea.value.substring(fin);
    textarea.focus();
}

function insertarLista(tipo) {
    const textarea = document.getElementById('notaContenido');
    const inicio = textarea.selectionStart;

    let itemLista = tipo === 'ul' ? '- ' : '1. ';

    textarea.value = textarea.value.substring(0, inicio) + itemLista + textarea.value.substring(inicio);
    textarea.focus();
}

function insertarEnlace() {
    const url = prompt('Ingresa la URL:');
    const texto = prompt('Ingresa el texto del enlace:') || url;

    if (url) {
        const textarea = document.getElementById('notaContenido');
        const inicio = textarea.selectionStart;

        textarea.value = textarea.value.substring(0, inicio) + `[${texto}](${url})` + textarea.value.substring(inicio);
        textarea.focus();
    }
}

function insertarImagen() {
    const url = prompt('Ingresa la URL de la imagen:');
    const alt = prompt('Texto alternativo:') || 'Imagen';

    if (url) {
        const textarea = document.getElementById('notaContenido');
        const inicio = textarea.selectionStart;

        textarea.value = textarea.value.substring(0, inicio) + `![${alt}](${url})` + textarea.value.substring(inicio);
        textarea.focus();
    }
}

// Sistema de etiquetas
function mostrarEtiquetas(etiquetas) {
    const lista = document.getElementById('listaEtiquetas');
    lista.innerHTML = '';

    etiquetas.forEach(etiqueta => {
        const elemento = document.createElement('span');
        elemento.className = 'tag';
        elemento.innerHTML = `
            ${etiqueta}
            <button class="tag-remove" onclick="eliminarEtiqueta('${etiqueta}')">&times;</button>
        `;
        lista.appendChild(elemento);
    });
}

function agregarEtiqueta() {
    const input = document.getElementById('inputTag');
    const etiqueta = input.value.trim();

    if (etiqueta && notaActual) {
        if (!notaActual.etiquetas.includes(etiqueta)) {
            notaActual.etiquetas.push(etiqueta);
            mostrarEtiquetas(notaActual.etiquetas);
            input.value = '';
            guardarNotas();
        }
    }
}

function eliminarEtiqueta(etiqueta) {
    if (notaActual) {
        notaActual.etiquetas = notaActual.etiquetas.filter(e => e !== etiqueta);
        mostrarEtiquetas(notaActual.etiquetas);
        guardarNotas();
    }
}

// Plantillas
function usarPlantilla(tipo) {
    let contenido = '';

    switch(tipo) {
        case 'cornell':
            contenido = `# Tema: [Escribe el tema aquí]

## Notas Principales
- [Punto importante 1]
- [Punto importante 2]
- [Punto importante 3]

## Resumen
[Escribe un resumen breve de lo aprendido]

## Preguntas
- [Pregunta clave 1]
- [Pregunta clave 2]`;
            break;

        case 'mapa_mental':
            contenido = `# Tema Central

## Rama 1
- Idea 1.1
- Idea 1.2

## Rama 2  
- Idea 2.1
- Idea 2.2

## Conexiones
- [Cómo se relacionan las ideas]`;
            break;

        case 'qdr':
            contenido = `# [Tema]

## Preguntas
1. [Pregunta principal]
2. [Pregunta secundaria]

## Desarrollo
[Explicación detallada y ejemplos]

## Resumen
[Conclusiones y puntos clave]`;
            break;
    }

    if (notaActual) {
        notaActual.contenido = contenido;
        cargarNota(notaActual.id);
    } else {
        crearNuevaNota();
        notaActual.contenido = contenido;
        cargarNota(notaActual.id);
    }
}

// Filtrado
function filtrarNotas() {
    const curso = document.getElementById('filtroCursoNota').value;
    const busqueda = document.getElementById('buscarNota').value.toLowerCase();

    const notasFiltradas = notas.filter(nota => {
        const coincideCurso = !curso || nota.curso === curso;
        const coincideBusqueda = !busqueda ||
            nota.titulo.toLowerCase().includes(busqueda) ||
            nota.contenido.toLowerCase().includes(busqueda) ||
            nota.etiquetas.some(etiqueta => etiqueta.toLowerCase().includes(busqueda));

        return coincideCurso && coincideBusqueda;
    });

    // Actualizar lista
    const lista = document.getElementById('listaNotas');
    lista.innerHTML = '';

    notasFiltradas.forEach(nota => {
        const elemento = document.createElement('div');
        elemento.className = 'note-item';
        elemento.innerHTML = `
            <h4>${nota.titulo}</h4>
            <div class="note-preview">${nota.contenido.substring(0, 100)}...</div>
            <div class="note-meta">
                <span>${obtenerNombreCurso(nota.curso)}</span>
                <span>${formatearFecha(nota.fecha)}</span>
            </div>
        `;

        elemento.addEventListener('click', () => cargarNota(nota.id));

        lista.appendChild(elemento);
    });
}