package com.ADAenseatech.utilidad;
import com.ADAenseatech.modelos.Curso;
import com.ADAenseatech.modelos.Lecciones;
import java.util.List;
public class CalculoProgreso {

    // Algoritmo para calcular progreso del curso
    public static double calcularProgresoCurso(Curso curso) {
        List<Lecciones> lecciones = curso.getLecciones();
        if (lecciones.isEmpty()) return 0.0;

        double progresoTotal = 0.0;
        int leccionesCompletadas = 0;

        // Usando ciclo for tradicional
        for (int i = 0; i < lecciones.size(); i++) {
            Lecciones leccion = lecciones.get(i);
            progresoTotal += leccion.getProgreso();
            if (leccion.isCompletada()) {
                leccionesCompletadas++;
            }
        }

        // Dos métodos de cálculo
        double progresoPromedio = progresoTotal / lecciones.size();
        double ratioCompletacion = (double) leccionesCompletadas / lecciones.size() * 100;

        return (progresoPromedio + ratioCompletacion) / 2;
    }

    // Algoritmo para predecir tiempo de finalización
    public static int predecirTiempoCompletar(Curso curso, double horasEstudioPorDia) {
        double progresoRestante = 100 - curso.getProgreso();
        if (progresoRestante <= 0) return 0;

        double diasEstimados = progresoRestante / (horasEstudioPorDia * 5);
        return (int) Math.ceil(diasEstimados);
    }
}