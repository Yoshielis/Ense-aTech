package com.ADAenseatech;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.adaptcurso.AdaptReferencias;
import com.ADAenseatech.modelos.Lecciones;
import com.ADAenseatech.modelos.Referencia;
import java.util.ArrayList;
import java.util.List;

public class Leccion extends AppCompatActivity {

    private TextView tvTituloLeccion, tvDescripcionLeccion, tvExperimento;
    private VideoView videoView;
    private RecyclerView rvReferencias;
    private Button btnPreguntas, btnCompletarLeccion;

    private AdaptReferencias adaptadorReferencias;
    private List<Referencia> listaReferencias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leccion);

        inicializarVistas();
        cargarLeccion();
        configurarReferencias();
        configurarBotones();
    }

    private void inicializarVistas() {
        tvTituloLeccion = findViewById(R.id.tvTituloLeccion);
        tvDescripcionLeccion = findViewById(R.id.tvDescripcionLeccion);
        tvExperimento = findViewById(R.id.tvExperimento);
        videoView = findViewById(R.id.videoView);
        rvReferencias = findViewById(R.id.rvReferencias);
        btnPreguntas = findViewById(R.id.btnPreguntas);
        btnCompletarLeccion = findViewById(R.id.btnCompletarLeccion);
    }

    private void cargarLeccion() {
        String leccionId = getIntent().getStringExtra("LECCION_ID");
        Lecciones leccion = obtenerLeccionPorId(leccionId);

        if (leccion != null) {
            mostrarInformacionLeccion(leccion);
        }
    }

    private Lecciones obtenerLeccionPorId(String leccionId) {
        // Simulación - en una app real esto vendría de una base de datos
        Lecciones leccion = new Lecciones();
        leccion.setId("L1");
        leccion.setTitulo("Introducción a la Física");
        leccion.setDescripcion("En esta lección aprenderás los conceptos básicos de la física y el método científico.");
        leccion.setDescripcionExperimento("Experimento: Mide la densidad de diferentes objetos caseros usando una balanza y calculando su volumen.");

        List<Referencia> referencias = new ArrayList<>();
        referencias.add(new Referencia("Libro: Física Universitaria", "https://ejemplo.com/libro", "PDF"));
        referencias.add(new Referencia("Video: Método Científico", "https://youtube.com/ejemplo", "VIDEO"));
        leccion.setReferencias(referencias);

        return leccion;
    }

    private void mostrarInformacionLeccion(Lecciones leccion) {
        tvTituloLeccion.setText(leccion.getTitulo());
        tvDescripcionLeccion.setText(leccion.getDescripcion());
        tvExperimento.setText(leccion.getDescripcionExperimento());

        listaReferencias.clear();
        listaReferencias.addAll(leccion.getReferencias());
        adaptadorReferencias.notifyDataSetChanged();
    }

    private void configurarReferencias() {
        adaptadorReferencias = new AdaptReferencias(listaReferencias);
        rvReferencias.setLayoutManager(new LinearLayoutManager(this));
        rvReferencias.setAdapter(adaptadorReferencias);
    }

    private void configurarBotones() {
        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPreguntas();
            }
        });

        btnCompletarLeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completarLeccion();
            }
        });
    }

    private void mostrarPreguntas() {
        Toast.makeText(this, "Preguntas y ejercicios de la lección", Toast.LENGTH_LONG).show();
    }

    private void completarLeccion() {
        Toast.makeText(this, "¡Lección completada! +10 puntos", Toast.LENGTH_SHORT).show();
        btnCompletarLeccion.setEnabled(false);
        btnCompletarLeccion.setText("Lección Completada");
    }
}
