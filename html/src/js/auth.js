// Manejo del formulario de login
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const userType = document.getElementById('userType').value;

            // Validaciones
            let isValid = true;

            // Validar email
            if (!validateEmail(email)) {
                document.getElementById('emailError').style.display = 'block';
                document.getElementById('email').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('emailError').style.display = 'none';
                document.getElementById('email').classList.remove('error');
            }

            // Validar contraseña
            if (password.length < 4) {
                document.getElementById('passwordError').style.display = 'block';
                document.getElementById('password').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('passwordError').style.display = 'none';
                document.getElementById('password').classList.remove('error');
            }

            // Validar tipo de usuario
            if (!userType) {
                document.getElementById('userTypeError').style.display = 'block';
                document.getElementById('userType').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('userTypeError').style.display = 'none';
                document.getElementById('userType').classList.remove('error');
            }

            if (isValid) {
                // Simular login exitoso
                const userData = {
                    id: generateUserId(email, userType),
                    name: getNameFromEmail(email),
                    email: email,
                    type: userType
                };

                // Guardar en localStorage
                localStorage.setItem('currentUser', JSON.stringify(userData));
                localStorage.setItem('isLoggedIn', 'true');

                // Redirigir según tipo de usuario
                redirectToDashboard(userType);
            }
        });
    }

    // Verificar si ya hay una sesión activa
    checkActiveSession();
});

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function generateUserId(email, userType) {
    return userType.toLowerCase() + '_' + Math.abs(email.hashCode());
}

// Extender String para tener método hashCode
String.prototype.hashCode = function() {
    let hash = 0;
    for (let i = 0; i < this.length; i++) {
        const char = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + char;
        hash = hash & hash;
    }
    return hash;
};

function getNameFromEmail(email) {
    const name = email.split('@')[0];
    return name.charAt(0).toUpperCase() + name.slice(1);
}

function redirectToDashboard(userType) {
    switch(userType) {
        case 'ESTUDIANTE':
            window.location.href = 'home-estudiante.html';
            break;
        case 'PROFESOR':
            window.location.href = 'home-profesor.html';
            break;
        case 'TUTOR':
            window.location.href = 'home-tutor.html';
            break;
        case 'ADMIN':
            window.location.href = 'home-admin.html';
            break;
        default:
            window.location.href = 'home-estudiante.html';
    }
}

function checkActiveSession() {
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

    if (isLoggedIn === 'true' && currentUser.type) {
        redirectToDashboard(currentUser.type);
    }
}