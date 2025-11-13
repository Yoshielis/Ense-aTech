package com.ADAenseatech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeAdmin extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton btnBack;
    private TextView tvBienvenida;
    private Button btnGestionUsuarios, btnGraficasProgreso, btnOrganizarProfesores;
    private Button btnNoticiasCampus, btnForos, btnReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_admin);

        inicializarVistas();
        cargarDatosUsuario();
        configurarBotones();
        configurarToolbar();
    }
    private void configurarToolbar() {
        try {
            toolbar = findViewById(R.id.toolbar);
            toolbarTitle = findViewById(R.id.toolbar_title);
            btnBack = findViewById(R.id.btnBack);

            // Verificar que las vistas se encontraron
            if (toolbar == null) {
                Toast.makeText(this, "Error: No se encontró el Toolbar", Toast.LENGTH_SHORT).show();
                return;
            }

            setSupportActionBar(toolbar);

            // Configurar botón de regreso
            if (btnBack != null) {
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

            // Configurar título
            if (toolbarTitle != null) {
                toolbarTitle.setText("Inicio Administrador");
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error configurando toolbar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void inicializarVistas() {
        tvBienvenida = findViewById(R.id.tvBienvenida);
        btnGestionUsuarios = findViewById(R.id.btnGestionUsuarios);
        btnGraficasProgreso = findViewById(R.id.btnGraficasProgreso);
        btnOrganizarProfesores = findViewById(R.id.btnOrganizarProfesores);
        btnNoticiasCampus = findViewById(R.id.btnNoticiasCampus);
        btnForos = findViewById(R.id.btnForos);
        btnReportes = findViewById(R.id.btnReportes);
    }

    private void cargarDatosUsuario() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        String nombreUsuario = prefs.getString("usuarioNombre", "Administrador");
        tvBienvenida.setText("Bienvenido, Administrador " + nombreUsuario);
    }

    private void configurarBotones() {
        btnGestionUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Gestión de usuarios - Próximamente");
            }
        });

        btnGraficasProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Gráficas de progreso - Próximamente");
            }
        });

        btnOrganizarProfesores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Organizar profesores - Próximamente");
            }
        });

        btnNoticiasCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Noticias del campus - Próximamente");
            }
        });

        btnForos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Gestión de foros - Próximamente");
            }
        });

        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Generar reportes - Próximamente");
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } catch (Exception e) {
            // Si no existen las animaciones, simplemente ignora el error
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
