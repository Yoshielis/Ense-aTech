package com.ADAenseatech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// IMPORTACIÓN CORREGIDA - asegúrate de que la ruta del paquete sea correcta
import com.ADAenseatech.modelos.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Spinner spinnerTipoUsuario;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVistas();
        configurarSpinnerTipoUsuario();
        configurarBotonLogin();

        // Opcional: Verificar si ya hay una sesión activa
        verificarSesionActiva();
    }

    private void inicializarVistas() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerTipoUsuario = findViewById(R.id.spinnerTipoUsuario);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void configurarSpinnerTipoUsuario() {
        String[] tiposUsuario = {"ESTUDIANTE", "PROFESOR", "TUTOR", "ADMIN"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tiposUsuario);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoUsuario.setAdapter(adapter);
    }

    private void configurarBotonLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String tipoUsuario = spinnerTipoUsuario.getSelectedItem().toString();

                if (validarEntrada(email, password)) {
                    realizarLogin(email, password, tipoUsuario);
                }
            }
        });
    }

    private boolean validarEntrada(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Por favor complete todos los campos");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarMensaje("Por favor ingrese un email válido");
            return false;
        }

        if (password.length() < 4) {
            mostrarMensaje("La contraseña debe tener al menos 4 caracteres");
            return false;
        }

        return true;
    }

    private void realizarLogin(String email, String password, String tipoUsuario) {
        // Simulación de login - en una app real aquí harías la autenticación
        // Generar un ID único para el usuario
        String usuarioId = generarUsuarioId(email, tipoUsuario);
        String nombre = obtenerNombreDesdeEmail(email);

        // Crear instancia de Usuario
        Usuario usuario = new Usuario(usuarioId, nombre, email, tipoUsuario);

        // Guardar sesión y redirigir
        guardarSesionUsuario(usuario);
        redirigirAHome(tipoUsuario);

        mostrarMensaje("Bienvenido " + nombre);
    }

    private String generarUsuarioId(String email, String tipoUsuario) {
        // Generar un ID simple basado en email y tipo
        return tipoUsuario.toLowerCase() + "_" + email.hashCode();
    }

    private String obtenerNombreDesdeEmail(String email) {
        // Extraer la parte antes del @ como nombre
        String nombre = email.split("@")[0];
        return capitalizarPrimeraLetra(nombre);
    }

    private String capitalizarPrimeraLetra(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    private void guardarSesionUsuario(Usuario usuario) {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuarioId", usuario.getId());
        editor.putString("usuarioNombre", usuario.getNombre());
        editor.putString("usuarioEmail", usuario.getEmail());
        editor.putString("usuarioTipo", usuario.getTipoUsuario());
        editor.putBoolean("sesionActiva", true);
        editor.apply();
    }

    private void verificarSesionActiva() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        boolean sesionActiva = prefs.getBoolean("sesionActiva", false);

        if (sesionActiva) {
            String tipoUsuario = prefs.getString("usuarioTipo", "ESTUDIANTE");
            redirigirAHome(tipoUsuario);
        }
    }

    private void redirigirAHome(String tipoUsuario) {
        Intent intent;
        switch (tipoUsuario) {
            case "ESTUDIANTE":
                intent = new Intent(this, HomeEstudiante.class);
                break;
            case "PROFESOR":
                intent = new Intent(this, HomeProfesor.class);
                break;
            case "TUTOR":
                intent = new Intent(this, HomeTutor.class);
                break;
            case "ADMIN":
                intent = new Intent(this, HomeAdmin.class);
                break;
            default:
                intent = new Intent(this, HomeEstudiante.class);
        }
        startActivity(intent);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}