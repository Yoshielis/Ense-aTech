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

public class HomeProfesor extends AppCompatActivity {

    private TextView tvBienvenida;
    private RecyclerView rvMisCursos;
    private Button btnCrearCurso, btnRevisarContenido, btnEvaluaciones;

    private Adaptcurso adaptadorCursos;
    private List<Curso> listaMisCursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_profesor);

        inicializarVistas();
        cargarDatosUsuario();
        configurarMisCursos();
        configurarBotones();
    }

    private void inicializarVistas() {
        tvBienvenida = findViewById(R.id.tvBienvenida);
        rvMisCursos = findViewById(R.id.rvMisCursos);
        btnCrearCurso = findViewById(R.id.btnCrearCurso);
        btnRevisarContenido = findViewById(R.id.btnRevisarContenido);
        btnEvaluaciones = findViewById(R.id.btnEvaluaciones);
    }

    private void cargarDatosUsuario() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        String nombreUsuario = prefs.getString("usuarioNombre", "Profesor");
        tvBienvenida.setText("Bienvenido, Profesor " + nombreUsuario);
    }

    private void configurarMisCursos() {
        listaMisCursos.clear();
        listaMisCursos.addAll(generarMisCursos());

        adaptadorCursos = new Adaptcurso(listaMisCursos, new Adaptcurso.OnCursoClickListener() {
            @Override
            public void onCursoClick(Curso curso) {
                onCursoSeleccionado(curso);
            }
        });

        rvMisCursos.setLayoutManager(new LinearLayoutManager(this));
        rvMisCursos.setAdapter(adaptadorCursos);
    }

    private List<Curso> generarMisCursos() {
        List<Curso> cursos = new ArrayList<>();

        cursos.add(new Curso("C1", "Física Básica",
                "Curso que imparto sobre fundamentos de física", "Física", "P1"));

        cursos.add(new Curso("C2", "Cálculo Avanzado",
                "Curso de cálculo para estudiantes avanzados", "Cálculo", "P1"));

        return cursos;
    }

    private void configurarBotones() {
        btnCrearCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Crear nuevo curso - Próximamente");
            }
        });

        btnRevisarContenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Revisión de contenido - Próximamente");
            }
        });

        btnEvaluaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Evaluaciones - Próximamente");
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
