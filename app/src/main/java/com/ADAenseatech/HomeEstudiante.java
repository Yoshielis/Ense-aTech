package com.ADAenseatech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.adaptcurso.AdaptCurso;
import com.ADAenseatech.modelos.Curso;
import com.ADAenseatech.modelos.Lecciones;
import com.ADAenseatech.modelos.Preguntas;
import com.ADAenseatech.modelos.Referencia;
import com.ADAenseatech.utilidad.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class HomeEstudiante extends BaseActivity {

    private TextView tvBienvenida;
    private RecyclerView rvCursos;
    private Button btnApuntes, btnFormularios;
    private AdaptCurso adaptadorCursos;
    private List<Curso> listaCursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_estudiante);

        // Configurar toolbar y regreso
        configurarToolbarBasico("Inicio Estudiante");
        configurarRegresoAlLogin(); // ← Esto hace todo automáticamente

        inicializarVistas();
        cargarDatosUsuario();
        configurarListaCursos();
        configurarBotones();
    }

    private void inicializarVistas() {
        tvBienvenida = findViewById(R.id.tvBienvenida);
        rvCursos = findViewById(R.id.rvCursos);
        btnApuntes = findViewById(R.id.btnApuntes);
        btnFormularios = findViewById(R.id.btnFormularios);
    }

    private void cargarDatosUsuario() {
        try {
            SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
            String nombreUsuario = prefs.getString("usuarioNombre", "Estudiante");
            if (tvBienvenida != null) {
                tvBienvenida.setText("Bienvenido, " + nombreUsuario);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error cargando datos usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarListaCursos() {
        try {
            listaCursos.clear();
            listaCursos.addAll(generarCursosEjemplo());

            adaptadorCursos = new Adaptcurso(listaCursos, new Adaptcurso.OnCursoClickListener() {
                @Override
                public void onCursoClick(Curso curso) {
                    onCursoSeleccionado(curso);
                }
            });

            if (rvCursos != null) {
                rvCursos.setLayoutManager(new LinearLayoutManager(this));
                rvCursos.setAdapter(adaptadorCursos);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error configurando lista cursos", Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarBotones() {
        try {
            if (btnApuntes != null) {
                btnApuntes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeEstudiante.this, Apuntes.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }

            if (btnFormularios != null) {
                btnFormularios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeEstudiante.this, Formularios.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error configurando botones", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Curso> generarCursosEjemplo() {
        List<Curso> cursos = new ArrayList<>();

        // Curso de Física
        Curso cursoFisica = new Curso("C1", "Física Básica",
                "Aprende los fundamentos de la física con experimentos caseros", "Física", "P1");

        Lecciones leccionFisica = new Lecciones();
        leccionFisica.setId("L1");
        leccionFisica.setTitulo("Introducción a la Física");
        leccionFisica.setDescripcion("Conceptos básicos y método científico");
        leccionFisica.setDescripcionExperimento("Experimento: Medición de densidad con objetos caseros");

        List<Referencia> referencias = new ArrayList<>();
        referencias.add(new Referencia("Libro de Física General", "http://ejemplo.com", "PDF"));
        leccionFisica.setReferencias(referencias);

        List<Preguntas> preguntas = new ArrayList<>();
        List<String> opciones = new ArrayList<>();
        opciones.add("Ciencia que estudia la materia");
        opciones.add("Ciencia que estudia la energía");
        opciones.add("Ciencia que estudia las propiedades de la materia y energía");
        opciones.add("Ciencia que estudia los números");

        preguntas.add(new Preguntas("Q1", "¿Qué es la física?", opciones, 2,
                "La física estudia las propiedades de la materia y la energía"));
        leccionFisica.setPreguntas(preguntas);

        List<Lecciones> lecciones = new ArrayList<>();
        lecciones.add(leccionFisica);
        cursoFisica.setLecciones(lecciones);
        cursos.add(cursoFisica);

        // Curso de Cálculo
        Curso cursoCalculo = new Curso("C2", "Cálculo Diferencial",
                "Aprende derivadas y sus aplicaciones", "Cálculo", "P2");
        cursos.add(cursoCalculo);

        return cursos;
    }

    private void onCursoSeleccionado(Curso curso) {
        Intent intent = new Intent(this, DetalleCurso.class);
        intent.putExtra("CURSO_ID", curso.getId());
        startActivity(intent);
        try {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception e) {
            // Ignorar error de animaciones
        }
    }
}