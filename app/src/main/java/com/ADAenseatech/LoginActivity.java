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
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
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

        if (!email.contains("@") || !email.contains(".")) {
            mostrarMensaje("Por favor ingrese un email válido");
            return false;
        }

        return true;
    }

    private void realizarLogin(String email, String password, String tipoUsuario) {
        // Simulación de login
        Usuario usuario = new Usuario("1", "Usuario Ejemplo", email, tipoUsuario);

        guardarSesionUsuario(usuario);
        redirigirAHome(tipoUsuario);
    }

    private void guardarSesionUsuario(Usuario usuario) {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuarioId", usuario.getId());
        editor.putString("usuarioNombre", usuario.getNombre());
        editor.putString("usuarioEmail", usuario.getEmail());
        editor.putString("usuarioTipo", usuario.getTipoUsuario());
        editor.apply();
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