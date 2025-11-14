// Sistema completo de modo oscuro
document.addEventListener('DOMContentLoaded', function() {
    inicializarModoOscuro();
    crearToggleModoOscuro();
});

function inicializarModoOscuro() {
    const configuracion = JSON.parse(localStorage.getItem('configuracionUsuario') || '{}');
    const temaPreferido = configuracion.tema || 'auto';

    aplicarTema(temaPreferido);
}

function crearToggleModoOscuro() {
    // Buscar o crear el botón de toggle
    let toggle = document.querySelector('.theme-toggle');

    if (!toggle) {
        toggle = document.createElement('button');
        toggle.className = 'theme-toggle';
        toggle.innerHTML = '<i class="fas fa-moon"></i>';
        toggle.title = 'Cambiar tema';

        // Insertar en el header
        const header = document.querySelector('.header-content');
        if (header) {
            const nav = header.querySelector('nav');
            if (nav) {
                header.insertBefore(toggle, nav);
            } else {
                header.appendChild(toggle);
            }
        }
    }

    toggle.addEventListener('click', toggleModoOscuro);
    actualizarIconoToggle();
}

function toggleModoOscuro() {
    const estaOscuro = document.body.classList.contains('dark-mode');

    if (estaOscuro) {
        document.body.classList.replace('dark-mode', 'light-mode');
        localStorage.setItem('temaManual', 'light');
    } else {
        document.body.classList.replace('light-mode', 'dark-mode');
        localStorage.setItem('temaManual', 'dark');
    }

    actualizarIconoToggle();

    // Actualizar configuración si existe
    const configuracion = JSON.parse(localStorage.getItem('configuracionUsuario') || '{}');
    if (configuracion.tema) {
        configuracion.tema = estaOscuro ? 'light' : 'dark';
        localStorage.setItem('configuracionUsuario', JSON.stringify(configuracion));
    }
}

function actualizarIconoToggle() {
    const toggle = document.querySelector('.theme-toggle');
    if (toggle) {
        const estaOscuro = document.body.classList.contains('dark-mode');
        toggle.innerHTML = estaOscuro ? '<i class="fas fa-sun"></i>' : '<i class="fas fa-moon"></i>';
        toggle.title = estaOscuro ? 'Cambiar a modo claro' : 'Cambiar a modo oscuro';
    }
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

    actualizarIconoToggle();
}

// Exportar funciones para uso global
window.toggleModoOscuro = toggleModoOscuro;
window.aplicarTema = aplicarTema;