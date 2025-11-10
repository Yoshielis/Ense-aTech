package com.ADAenseatech;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.adaptcurso.AdaptNotas;
import com.ADAenseatech.modelos.Nota;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Apuntes extends AppCompatActivity {

    private TextView tvTituloApuntes;
    private Spinner spinnerCursos;
    private EditText etTituloNota, etContenidoNota;
    private Button btnGuardarNota, btnLimpiar;
    private RecyclerView rvNotas;

    private AdaptNotas adaptadorNotas;
    private List<Nota> listaNotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apuntes);

        inicializarVistas();
        configurarSpinnerCursos();
        configurarListaNotas();
        configurarBotones();
        cargarNotasGuardadas();
    }

    private void inicializarVistas() {
        tvTituloApuntes = findViewById(R.id.tvTituloApuntes);
        spinnerCursos = findViewById(R.id.spinnerCursos);
        etTituloNota = findViewById(R.id.etTituloNota);
        etContenidoNota = findViewById(R.id.etContenidoNota);
        btnGuardarNota = findViewById(R.id.btnGuardarNota);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        rvNotas = findViewById(R.id.rvNotas);
    }

    private void configurarSpinnerCursos() {
        String[] cursos = {"Física Básica", "Cálculo Diferencial", "Química General", "Álgebra Lineal"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, cursos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCursos.setAdapter(adapter);
    }

    private void configurarListaNotas() {
        adaptadorNotas = new AdaptNotas(listaNotas);
        rvNotas.setLayoutManager(new LinearLayoutManager(this));
        rvNotas.setAdapter(adaptadorNotas);
    }

    private void configurarBotones() {
        btnGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNota();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarFormulario();
            }
        });
    }

    private void guardarNota() {
        String titulo = etTituloNota.getText().toString();
        String contenido = etContenidoNota.getText().toString();
        String curso = spinnerCursos.getSelectedItem().toString();

        if (titulo.isEmpty() || contenido.isEmpty()) {
            Toast.makeText(this, "Por favor complete título y contenido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear nueva nota
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        Nota nuevaNota = new Nota(titulo, contenido, curso, fecha);

        // Algoritmo: Insertar ordenado por fecha (más reciente primero)
        insertarNotaOrdenada(nuevaNota);

        // Guardar en SharedPreferences
        guardarNotasEnPreferencias();

        limpiarFormulario();
        Toast.makeText(this, "Nota guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    // Algoritmo de inserción ordenada
    private void insertarNotaOrdenada(Nota nuevaNota) {
        int posicion = 0;
        // Encontrar posición correcta (orden descendente por fecha)
        while (posicion < listaNotas.size()) {
            // Comparar fechas - asumiendo formato "dd/MM/yyyy HH:mm"
            if (nuevaNota.getFecha().compareTo(listaNotas.get(posicion).getFecha()) > 0) {
                break;
            }
            posicion++;
        }
        listaNotas.add(posicion, nuevaNota);
        adaptadorNotas.notifyItemInserted(posicion);
    }

    private void limpiarFormulario() {
        etTituloNota.setText("");
        etContenidoNota.setText("");
        etTituloNota.requestFocus();
    }

    private void cargarNotasGuardadas() {
        // Cargar notas desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("NotasUsuario", MODE_PRIVATE);
        int numeroNotas = prefs.getInt("numeroNotas", 0);

        // Algoritmo: Cargar usando ciclo while
        int i = 0;
        while (i < numeroNotas) {
            String titulo = prefs.getString("nota_titulo_" + i, "");
            String contenido = prefs.getString("nota_contenido_" + i, "");
            String curso = prefs.getString("nota_curso_" + i, "");
            String fecha = prefs.getString("nota_fecha_" + i, "");

            if (!titulo.isEmpty()) {
                listaNotas.add(new Nota(titulo, contenido, curso, fecha));
            }
            i++;
        }

        adaptadorNotas.notifyDataSetChanged();
    }

    private void guardarNotasEnPreferencias() {
        SharedPreferences prefs = getSharedPreferences("NotasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("numeroNotas", listaNotas.size());

        // Algoritmo: Guardar usando ciclo for
        for (int i = 0; i < listaNotas.size(); i++) {
            Nota nota = listaNotas.get(i);
            editor.putString("nota_titulo_" + i, nota.getTitulo());
            editor.putString("nota_contenido_" + i, nota.getContenido());
            editor.putString("nota_curso_" + i, nota.getCurso());
            editor.putString("nota_fecha_" + i, nota.getFecha());
        }

        editor.apply();
    }
}
