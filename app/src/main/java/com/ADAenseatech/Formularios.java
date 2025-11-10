package com.ADAenseatech;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Formularios extends AppCompatActivity {

    private EditText etNombre, etEmail, etComentarios;
    private Spinner spinnerTipoConsulta;
    private RadioGroup radioGroupUrgencia;
    private CheckBox cbNotificaciones, cbTerminos;
    private Button btnEnviar, btnLimpiarForm;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formularios);

        inicializarVistas();
        configurarSpinner();
        configurarBotones();
    }

    private void inicializarVistas() {
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etComentarios = findViewById(R.id.etComentarios);
        spinnerTipoConsulta = findViewById(R.id.spinnerTipoConsulta);
        radioGroupUrgencia = findViewById(R.id.radioGroupUrgencia);
        cbNotificaciones = findViewById(R.id.cbNotificaciones);
        cbTerminos = findViewById(R.id.cbTerminos);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnLimpiarForm = findViewById(R.id.btnLimpiarForm);
        tvResultado = findViewById(R.id.tvResultado);
    }

    private void configurarSpinner() {
        String[] tiposConsulta = {
                "Seleccione tipo de consulta",
                "Problema técnico",
                "Duda académica",
                "Sugerencia",
                "Reporte de contenido",
                "Otro"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, tiposConsulta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoConsulta.setAdapter(adapter);
    }

    private void configurarBotones() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarYEnviarFormulario();
            }
        });

        btnLimpiarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarFormulario();
            }
        });
    }

    private void validarYEnviarFormulario() {
        // Validación de campos
        if (!validarCampos()) {
            return;
        }

        // Procesar formulario
        String resultado = procesarFormulario();
        tvResultado.setText(resultado);
        tvResultado.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Formulario enviado correctamente", Toast.LENGTH_SHORT).show();
    }

    private boolean validarCampos() {
        String nombre = etNombre.getText().toString();
        String email = etEmail.getText().toString();
        String tipoConsulta = spinnerTipoConsulta.getSelectedItem().toString();

        // Algoritmo de validación
        if (nombre.isEmpty()) {
            mostrarError("Por favor ingrese su nombre");
            return false;
        }

        if (email.isEmpty() || !email.contains("@")) {
            mostrarError("Por favor ingrese un email válido");
            return false;
        }

        if (tipoConsulta.equals("Seleccione tipo de consulta")) {
            mostrarError("Por favor seleccione un tipo de consulta");
            return false;
        }

        if (radioGroupUrgencia.getCheckedRadioButtonId() == -1) {
            mostrarError("Por favor seleccione el nivel de urgencia");
            return false;
        }

        if (!cbTerminos.isChecked()) {
            mostrarError("Debe aceptar los términos y condiciones");
            return false;
        }

        return true;
    }

    private String procesarFormulario() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("RESUMEN DEL FORMULARIO:\n\n");

        // Algoritmo: Procesar datos usando diferentes estructuras
        resultado.append("Nombre: ").append(etNombre.getText().toString()).append("\n");
        resultado.append("Email: ").append(etEmail.getText().toString()).append("\n");
        resultado.append("Tipo de consulta: ").append(spinnerTipoConsulta.getSelectedItem().toString()).append("\n");

        // Procesar radio buttons
        int urgenciaId = radioGroupUrgencia.getCheckedRadioButtonId();
        RadioButton radioUrgencia = findViewById(urgenciaId);
        resultado.append("Urgencia: ").append(radioUrgencia.getText()).append("\n");

        // Procesar checkboxes
        List<String> opcionesSeleccionadas = new ArrayList<>();
        if (cbNotificaciones.isChecked()) {
            opcionesSeleccionadas.add("Recibir notificaciones");
        }
        if (cbTerminos.isChecked()) {
            opcionesSeleccionadas.add("Acepta términos");
        }

        resultado.append("Opciones: ");
        // Algoritmo: Unir elementos de lista
        for (int i = 0; i < opcionesSeleccionadas.size(); i++) {
            resultado.append(opcionesSeleccionadas.get(i));
            if (i < opcionesSeleccionadas.size() - 1) {
                resultado.append(", ");
            }
        }
        resultado.append("\n");

        resultado.append("Comentarios: ").append(etComentarios.getText().toString());

        return resultado.toString();
    }

    private void limpiarFormulario() {
        etNombre.setText("");
        etEmail.setText("");
        etComentarios.setText("");
        spinnerTipoConsulta.setSelection(0);
        radioGroupUrgencia.clearCheck();
        cbNotificaciones.setChecked(false);
        cbTerminos.setChecked(false);
        tvResultado.setVisibility(View.GONE);
        etNombre.requestFocus();
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
