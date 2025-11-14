// Cargar datos del usuario en el dashboard
document.addEventListener('DOMContentLoaded', function() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

    // Mostrar nombre de usuario
    const welcomeMessage = document.getElementById('welcomeMessage');
    if (welcomeMessage && currentUser.name) {
        welcomeMessage.textContent = `Bienvenido, ${currentUser.name}`;
    }

    // Manejar cierre de sesión
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });
    }

    // Verificar autenticación
    checkAuthentication();
});

function checkAuthentication() {
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

    if (isLoggedIn !== 'true' || !currentUser.type) {
        window.location.href = 'login.html';
        return;
    }

    // Verificar que el usuario está en el dashboard correcto
    const currentPage = window.location.pathname.split('/').pop();
    let expectedPage = '';

    switch(currentUser.type) {
        case 'ESTUDIANTE':
            expectedPage = 'home-estudiante.html';
            break;
        case 'PROFESOR':
            expectedPage = 'home-profesor.html';
            break;
        case 'TUTOR':
            expectedPage = 'home-tutor.html';
            break;
        case 'ADMIN':
            expectedPage = 'home-admin.html';
            break;
    }


}

function logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('isLoggedIn');
    window.location.href = 'login.html';
}