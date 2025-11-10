package com.ADAenseatech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.adaptcurso.Adaptcurso;
import com.ADAenseatech.modelos.Curso;
import com.ADAenseatech.modelos.Lecciones;
import com.ADAenseatech.modelos.Preguntas;
import com.ADAenseatech.modelos.Referencia;
import java.util.ArrayList;
import java.util.List;

public class HomeEstudiante extends AppCompatActivity {

    private TextView tvBienvenida;
    private RecyclerView rvCursos;
    private Button btnApuntes, btnFormularios;

    private Adaptcurso adaptadorCursos;
    private List<Curso> listaCursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_estudiante);

        // Inicializar SIN usar las variables de clase
        try {
            // Usa variables locales temporalmente
            TextView tvBienvenida = findViewById(R.id.tvBienvenida);
            RecyclerView rvCursos = findViewById(R.id.rvCursos);
            Button btnApuntes = findViewById(R.id.btnApuntes);
            Button btnFormularios = findViewById(R.id.btnFormularios);

            System.out.println("DEBUG: Vistas encontradas:");
            System.out.println("tvBienvenida: " + (tvBienvenida != null));
            System.out.println("rvCursos: " + (rvCursos != null));
            System.out.println("btnApuntes: " + (btnApuntes != null));
            System.out.println("btnFormularios: " + (btnFormularios != null));

            // Si se encontraron, configura la app
            if (tvBienvenida != null && rvCursos != null) {
                cargarDatosUsuario(tvBienvenida);
                configurarListaCursos(rvCursos);
                configurarBotones(btnApuntes, btnFormularios);
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarDatosUsuario(TextView tvBienvenida) {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        String nombreUsuario = prefs.getString("usuarioNombre", "Estudiante");
        tvBienvenida.setText("Bienvenido, " + nombreUsuario);
    }

    private void configurarListaCursos(RecyclerView rvCursos) {
        listaCursos.clear();
        listaCursos.addAll(generarCursosEjemplo());

        adaptadorCursos = new Adaptcurso(listaCursos, new Adaptcurso.OnCursoClickListener() {
            @Override
            public void onCursoClick(Curso curso) {
                onCursoSeleccionado(curso);
            }
        });

        rvCursos.setLayoutManager(new LinearLayoutManager(this));
        rvCursos.setAdapter(adaptadorCursos);
    }

    private void configurarBotones(Button btnApuntes, Button btnFormularios) {
        btnApuntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeEstudiante.this, Apuntes.class));
            }
        });

        btnFormularios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeEstudiante.this, Formularios.class));
            }
        });
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

        // Agregar referencia
        List<Referencia> referencias = new ArrayList<>();
        referencias.add(new Referencia("Libro de Física General", "http://ejemplo.com", "PDF"));
        leccionFisica.setReferencias(referencias);

        // Agregar pregunta
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

    private void configurarBotones() {
        btnApuntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeEstudiante.this, Apuntes.class));
            }
        });

        btnFormularios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeEstudiante.this, Formularios.class));
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