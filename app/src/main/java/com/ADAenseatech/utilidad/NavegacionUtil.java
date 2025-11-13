package com.ADAenseatech.utilidad;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import com.ADAenseatech.R;

public class NavegacionUtil {

    // Configurar comportamiento del botón de regreso
    public static void configurarBotonRegreso(Activity actividad,
                                              Class<?> actividadDestino,
                                              String tituloDialogo,
                                              String mensajeDialogo) {

        ImageButton btnBack = actividad.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (actividad.isTaskRoot()) {
                    mostrarDialogoRegreso(actividad, actividadDestino, tituloDialogo, mensajeDialogo);
                } else {
                    actividad.onBackPressed();
                }
            });
        }
    }

    // Método genérico para mostrar diálogo de regreso
    private static void mostrarDialogoRegreso(Activity actividad,
                                              Class<?> actividadDestino,
                                              String titulo,
                                              String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Sí", (dialog, which) -> {
            navegarAActividad(actividad, actividadDestino);
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.show();
    }

    // Navegar a actividad específica
    public static void navegarAActividad(Activity actividadActual, Class<?> actividadDestino) {
        Intent intent = new Intent(actividadActual, actividadDestino);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        actividadActual.startActivity(intent);
        actividadActual.finish();
    }

    // Limpiar sesión
    public static void limpiarSesion(Activity actividad) {
        SharedPreferences prefs = actividad.getSharedPreferences("SesionUsuario", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}