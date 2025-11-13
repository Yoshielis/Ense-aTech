package com.ADAenseatech;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton btnBack;
    private TextView tvBienvenida;
    private RecyclerView rvCursos;
    private Button btnApuntes, btnFormularios;

    private Adaptcurso adaptadorCursos;
    private List<Curso> listaCursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_estudiante);

        // Configurar Toolbar personalizado
        configurarToolbar();

        // Inicializar vistas
        inicializarVistas();

        // Configurar funcionalidad
        cargarDatosUsuario();
        configurarListaCursos();
        configurarBotones();
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
                toolbarTitle.setText("Inicio Estudiante");
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error configurando toolbar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarVistas() {
        try {
            tvBienvenida = findViewById(R.id.tvBienvenida);
            rvCursos = findViewById(R.id.rvCursos);
            btnApuntes = findViewById(R.id.btnApuntes);
            btnFormularios = findViewById(R.id.btnFormularios);

            // Verificar que todas las vistas se encontraron
            if (tvBienvenida == null) Toast.makeText(this, "tvBienvenida no encontrado", Toast.LENGTH_SHORT).show();
            if (rvCursos == null) Toast.makeText(this, "rvCursos no encontrado", Toast.LENGTH_SHORT).show();
            if (btnApuntes == null) Toast.makeText(this, "btnApuntes no encontrado", Toast.LENGTH_SHORT).show();
            if (btnFormularios == null) Toast.makeText(this, "btnFormularios no encontrado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Error inicializando vistas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } catch (Exception e) {
            // Si no existen las animaciones, simplemente ignora el error
        }
    }

    // Tus métodos existentes...
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