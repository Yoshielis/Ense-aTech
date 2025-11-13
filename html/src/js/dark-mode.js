// Manejo del modo oscuro
document.addEventListener('DOMContentLoaded', function() {
    const savedTheme = localStorage.getItem('theme') || 'light-mode';
    document.body.className = savedTheme;

    // Crear botón de cambio de tema si no existe
    if (!document.querySelector('.theme-toggle')) {
        createThemeToggle();
    }
});

function createThemeToggle() {
    const headerContent = document.querySelector('.header-content');
    if (headerContent) {
        const themeToggle = document.createElement('button');
        themeToggle.className = 'theme-toggle';
        themeToggle.innerHTML = '<i class="fas fa-moon"></i>';
        themeToggle.title = 'Cambiar tema';

        // Insertar antes del primer botón de navegación
        const firstNavItem = document.querySelector('nav ul li:first-child');
        if (firstNavItem) {
            firstNavItem.parentNode.insertBefore(themeToggle, firstNavItem);
        } else {
            headerContent.appendChild(themeToggle);
        }

        themeToggle.addEventListener('click', toggleTheme);
    }
}

function toggleTheme() {
    const body = document.body;
    const themeToggle = document.querySelector('.theme-toggle');

    if (body.classList.contains('light-mode')) {
        body.classList.replace('light-mode', 'dark-mode');
        themeToggle.innerHTML = '<i class="fas fa-sun"></i>';
        localStorage.setItem('theme', 'dark-mode');
    } else {
        body.classList.replace('dark-mode', 'light-mode');
        themeToggle.innerHTML = '<i class="fas fa-moon"></i>';
        localStorage.setItem('theme', 'light-mode');
    }
}