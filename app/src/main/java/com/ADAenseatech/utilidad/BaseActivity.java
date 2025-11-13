package com.ADAenseatech.utilidad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Método para configurar toolbar
    protected void configurarToolbarBasico(String titulo, int toolbarId, int titleId, int backButtonId) {
        try {
            toolbar = findViewById(toolbarId);
            toolbarTitle = findViewById(titleId);
            btnBack = findViewById(backButtonId);

            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }

            if (toolbarTitle != null) {
                toolbarTitle.setText(titulo);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error configurando toolbar", Toast.LENGTH_SHORT).show();
        }
    }

    // Configurar regreso simple
    protected void configurarRegresoSimple() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }

    // Configurar regreso con diálogo
    protected void configurarRegresoAlLogin() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (isTaskRoot()) {
                    mostrarDialogoRegresoLogin();
                } else {
                    onBackPressed();
                }
            });
        }
    }

    private void mostrarDialogoRegresoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar sesión");
        builder.setMessage("¿Quieres cerrar sesión y volver al login?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            limpiarSesion();
            navegarAlLogin();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    protected void limpiarSesion() {
        getSharedPreferences("SesionUsuario", MODE_PRIVATE).edit().clear().apply();
    }

    protected void navegarAlLogin() {
        try {
            // Intent genérico - se redirigirá a la actividad de login
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al navegar al login", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            mostrarDialogoRegresoLogin();
        } else {
            super.onBackPressed();
        }
    }
}