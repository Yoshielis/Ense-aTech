package com.ADAenseatech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.adaptcurso.Adaptcurso;
import com.ADAenseatech.modelos.Curso;
import java.util.ArrayList;
import java.util.List;

public class HomeTutor extends AppCompatActivity {

    private TextView tvBienvenida;
    private RecyclerView rvCursosTutor;
    private Button btnSubirContenido, btnEvaluarEstudiantes, btnCambioEstudiante;

    private Adaptcurso adaptadorCursos;
    private List<Curso> listaCursosTutor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tutor);

        inicializarVistas();
        cargarDatosUsuario();
        configurarCursosTutor();
        configurarBotones();
    }

    private void inicializarVistas() {
        tvBienvenida = findViewById(R.id.tvBienvenida);
        rvCursosTutor = findViewById(R.id.rvCursosTutor);
        btnSubirContenido = findViewById(R.id.btnSubirContenido);
        btnEvaluarEstudiantes = findViewById(R.id.btnEvaluarEstudiantes);
        btnCambioEstudiante = findViewById(R.id.btnCambioEstudiante);
    }

    private void cargarDatosUsuario() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        String nombreUsuario = prefs.getString("usuarioNombre", "Tutor");
        tvBienvenida.setText("Bienvenido, Tutor " + nombreUsuario);
    }

    private void configurarCursosTutor() {
        listaCursosTutor.clear();
        listaCursosTutor.addAll(generarCursosTutor());

        adaptadorCursos = new Adaptcurso(listaCursosTutor, new Adaptcurso.OnCursoClickListener() {
            @Override
            public void onCursoClick(Curso curso) {
                onCursoSeleccionado(curso);
            }
        });

        rvCursosTutor.setLayoutManager(new LinearLayoutManager(this));
        rvCursosTutor.setAdapter(adaptadorCursos);
    }

    private List<Curso> generarCursosTutor() {
        List<Curso> cursos = new ArrayList<>();

        // Cursos que el tutor supervisa
        cursos.add(new Curso("C1", "Física Básica - Grupo A",
                "Supervisión de estudiantes en física básica", "Física", "T1"));

        cursos.add(new Curso("C3", "Cálculo Diferencial - Grupo B",
                "Tutoría de cálculo para estudiantes", "Cálculo", "T1"));

        return cursos;
    }

    private void configurarBotones() {
        btnSubirContenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Subir contenido - Próximamente");
            }
        });

        btnEvaluarEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Evaluar estudiantes - Próximamente");
            }
        });

        btnCambioEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar a modo estudiante
                SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("usuarioTipo", "ESTUDIANTE");
                editor.apply();

                Intent intent = new Intent(HomeTutor.this, HomeEstudiante.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void onCursoSeleccionado(Curso curso) {
        Intent intent = new Intent(this, DetalleCurso.class);
        intent.putExtra("CURSO_ID", curso.getId());
        startActivity(intent);
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
