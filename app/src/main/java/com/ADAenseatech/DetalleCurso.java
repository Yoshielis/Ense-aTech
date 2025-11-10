package com.ADAenseatech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.adaptcurso.AdaptLecciones;
import com.ADAenseatech.modelos.Curso;
import com.ADAenseatech.modelos.Lecciones;
import java.util.ArrayList;
import java.util.List;

public class DetalleCurso extends AppCompatActivity {

    private TextView tvTituloCurso, tvDescripcionCurso, tvProgresoCurso;
    private RecyclerView rvLecciones;
    private Button btnExperimentos, btnForoCurso;

    private AdaptLecciones adaptadorLecciones;
    private List<Lecciones> listaLessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_curso);

        inicializarVistas();
        cargarDetalleCurso();
        configurarLecciones();
        configurarBotones();
    }

    private void inicializarVistas() {
        tvTituloCurso = findViewById(R.id.tvTituloCurso);
        tvDescripcionCurso = findViewById(R.id.tvDescripcionCurso);
        tvProgresoCurso = findViewById(R.id.tvProgresoCurso);
        rvLecciones = findViewById(R.id.rvLecciones);
        btnExperimentos = findViewById(R.id.btnExperimentos);
        btnForoCurso = findViewById(R.id.btnForoCurso);
    }

    private void cargarDetalleCurso() {
        String cursoId = getIntent().getStringExtra("CURSO_ID");
        Curso curso = obtenerCursoPorId(cursoId);

        if (curso != null) {
            mostrarInformacionCurso(curso);
        }
    }

    private Curso obtenerCursoPorId(String cursoId) {
        // Simulación - en una app real esto vendría de una base de datos
        List<Curso> todosCursos = generarCursosEjemplo();
        for (Curso curso : todosCursos) {
            if (curso.getId().equals(cursoId)) {
                return curso;
            }
        }
        return null;
    }

    private void mostrarInformacionCurso(Curso curso) {
        tvTituloCurso.setText(curso.getTitulo());
        tvDescripcionCurso.setText(curso.getDescripcion());
        tvProgresoCurso.setText("Progreso General: " + curso.getProgreso() + "%");

        listaLessons.clear();
        listaLessons.addAll(curso.getLecciones());
        adaptadorLecciones.notifyDataSetChanged();
    }

    private void configurarLecciones() {
        adaptadorLecciones = new AdaptLecciones(listaLessons, new AdaptLecciones.OnLeccionClickListener() {
            @Override
            public void onLeccionClick(Lecciones leccion) {
                onLeccionSeleccionada(leccion);
            }
        });

        rvLecciones.setLayoutManager(new LinearLayoutManager(this));
        rvLecciones.setAdapter(adaptadorLecciones);
    }

    private List<Curso> generarCursosEjemplo() {
        List<Curso> cursos = new ArrayList<>();

        Curso cursoFisica = new Curso("C1", "Física Básica",
                "Aprende los fundamentos de la física", "Física", "P1");

        List<Lecciones> leccionesFisica = new ArrayList<>();

        Lecciones leccion1 = new Lecciones();
        leccion1.setId("L1");
        leccion1.setTitulo("Introducción a la Física");
        leccion1.setDescripcion("Conceptos básicos y método científico");
        leccion1.setProgreso(25.0);

        Lecciones leccion2 = new Lecciones();
        leccion2.setId("L2");
        leccion2.setTitulo("Movimiento y Velocidad");
        leccion2.setDescripcion("Estudio del movimiento rectilíneo");
        leccion2.setProgreso(0.0);

        leccionesFisica.add(leccion1);
        leccionesFisica.add(leccion2);
        cursoFisica.setLecciones(leccionesFisica);
        cursos.add(cursoFisica);

        return cursos;
    }

    private void configurarBotones() {
        btnExperimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarExperimentosCaseros();
            }
        });

        btnForoCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirForoCurso();
            }
        });
    }

    private void onLeccionSeleccionada(Lecciones leccion) {
        Intent intent = new Intent(this, Leccion.class);
        intent.putExtra("LECCION_ID", leccion.getId());
        startActivity(intent);
    }

    private void mostrarExperimentosCaseros() {
        Toast.makeText(this, "Experimentos caseros para este curso", Toast.LENGTH_LONG).show();
    }

    private void abrirForoCurso() {
        Toast.makeText(this, "Foro del curso", Toast.LENGTH_SHORT).show();
    }
}