package com.ADAenseatech;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Spinner spinnerUserType;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupLoginButton();
    }

    private void initializeViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupLoginButton() {
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String userType = spinnerUserType.getSelectedItem().toString();

            if (validateInput(email, password)) {
                performLogin(email, password, userType);
            }
        });
    }

    private boolean validateInput(String email, String password) {
        // Algoritmo de validación simple
        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Por favor complete todos los campos");
            return false;
        }

        // Validación de email básica
        if (!email.contains("@") || !email.contains(".")) {
            showMessage("Por favor ingrese un email válido");
            return false;
        }

        return true;
    }

    private void performLogin(String email, String password, String userType) {
        // Simulación de login - en una app real aquí iría la autenticación
        User user = new User("1", "Usuario Ejemplo", email, userType);

        saveUserSession(user);
        redirectToDashboard(userType);
    }

    private void saveUserSession(User user) {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", user.getId());
        editor.putString("userName", user.getName());
        editor.putString("userEmail", user.getEmail());
        editor.putString("userType", user.getUserType());
        editor.apply();
    }

    private void redirectToDashboard(String userType) {
        Intent intent;
        switch (userType) {
            case "STUDENT":
                intent = new Intent(this, HomeEstudiante.class);
                break;
            case "TEACHER":
                intent = new Intent(this, TeacherDashboardActivity.class);
                break;
            case "TUTOR":
                intent = new Intent(this, TutorDashboardActivity.class);
                break;
            case "ADMIN":
                intent = new Intent(this, AdminDashboardActivity.class);
                break;
            default:
                intent = new Intent(this, HomeEstudiante.class);
        }
        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}