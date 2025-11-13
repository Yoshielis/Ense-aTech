// Manejo del formulario de registro
document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');

    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const fullName = document.getElementById('fullName').value;
            const email = document.getElementById('regEmail').value;
            const password = document.getElementById('regPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const userType = document.getElementById('regUserType').value;
            const acceptTerms = document.getElementById('acceptTerms').checked;

            // Validaciones
            let isValid = true;

            // Validar nombre
            if (fullName.trim().length < 2) {
                document.getElementById('nameError').style.display = 'block';
                document.getElementById('fullName').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('nameError').style.display = 'none';
                document.getElementById('fullName').classList.remove('error');
            }

            // Validar email
            if (!validateEmail(email)) {
                document.getElementById('regEmailError').style.display = 'block';
                document.getElementById('regEmail').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('regEmailError').style.display = 'none';
                document.getElementById('regEmail').classList.remove('error');
            }

            // Validar contraseña
            if (password.length < 6) {
                document.getElementById('regPasswordError').style.display = 'block';
                document.getElementById('regPassword').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('regPasswordError').style.display = 'none';
                document.getElementById('regPassword').classList.remove('error');
            }

            // Validar confirmación de contraseña
            if (password !== confirmPassword) {
                document.getElementById('confirmPasswordError').style.display = 'block';
                document.getElementById('confirmPassword').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('confirmPasswordError').style.display = 'none';
                document.getElementById('confirmPassword').classList.remove('error');
            }

            // Validar tipo de usuario
            if (!userType) {
                document.getElementById('regUserTypeError').style.display = 'block';
                document.getElementById('regUserType').classList.add('error');
                isValid = false;
            } else {
                document.getElementById('regUserTypeError').style.display = 'none';
                document.getElementById('regUserType').classList.remove('error');
            }

            // Validar términos
            if (!acceptTerms) {
                document.getElementById('termsError').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('termsError').style.display = 'none';
            }

            if (isValid) {
                // Simular registro exitoso
                const userData = {
                    id: generateUserId(email, userType),
                    name: fullName,
                    email: email,
                    type: userType
                };

                // Guardar en localStorage
                localStorage.setItem('currentUser', JSON.stringify(userData));
                localStorage.setItem('isLoggedIn', 'true');

                // Mostrar mensaje de éxito
                alert('¡Cuenta creada exitosamente! Bienvenido a ADA enTech');

                // Redirigir según tipo de usuario
                redirectToDashboard(userType);
            }
        });
    }
});

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function generateUserId(email, userType) {
    return userType.toLowerCase() + '_' + Math.abs(email.hashCode());
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