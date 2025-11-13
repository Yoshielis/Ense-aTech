package com.ADAenseatech.modelos;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String id;
    private String titulo;
    private String descripcion;
    private String materia;
    private List<Lecciones> lecciones; // Cambiado a singular
    private List<String> estudiantesInscritos;
    private String profesorId;
    private double progreso;

    // Constructor vacío
    public Curso() {
        this.lecciones = new ArrayList<>();
        this.estudiantesInscritos = new ArrayList<>();
        this.progreso = 0.0;
    }

    // Constructor con parámetros
    public Curso(String id, String titulo, String descripcion, String materia, String profesorId) {
        this();
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.materia = materia;
        this.profesorId = profesorId;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public List<Lecciones> getLecciones() {
        return lecciones;
    }

    public void setLecciones(List<Lecciones> lecciones) {
        this.lecciones = lecciones != null ? lecciones : new ArrayList<>();
        // Establecer la referencia bidireccional
        if (this.lecciones != null) {
            for (Lecciones lecciones1 : this.lecciones) {
                lecciones.setCurso(this);
            }
        }
    }

    public List<String> getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    public void setEstudiantesInscritos(List<String> estudiantesInscritos) {
        this.estudiantesInscritos = estudiantesInscritos != null ? estudiantesInscritos : new ArrayList<>();
    }

    public String getProfesorId() {
        return profesorId;
    }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }


    public void setProfesorId(String profesorId) {
        this.profesorId = profesorId;
    }

    public double getProgreso() {
        return progreso;
    }

    public void setProgreso(double progreso) {
        if (progreso < 0.0) {
            this.progreso = 0.0;
        } else if (progreso > 100.0) {
            this.progreso = 100.0;
        } else {
            this.progreso = progreso;
        }
    }

    // Métodos de utilidad
    public void agregarLeccion(Lecciones leccion) {
        if (leccion != null && this.lecciones != null) {
            this.lecciones.add(leccion);
            leccion.setCurso(this); // Establecer relación bidireccional
        }
    }

    public void removerLeccion(Leccion leccion) {
        if (leccion != null && this.lecciones != null) {
            this.lecciones.remove(leccion);
            leccion.setCurso(null); // Remover relación
        }
    }

    public void agregarEstudiante(String estudianteId) {
        if (estudianteId != null && !estudianteId.trim().isEmpty() && this.estudiantesInscritos != null) {
            this.estudiantesInscritos.add(estudianteId);
        }
    }

    public boolean tieneEstudiante(String estudianteId) {
        return this.estudiantesInscritos != null && this.estudiantesInscritos.contains(estudianteId);
    }

    // Calcular progreso automáticamente basado en lecciones completadas
    public void calcularProgreso() {
        if (lecciones == null || lecciones.isEmpty()) {
            this.progreso = 0.0;
            return;
        }

        long leccionesCompletadas = lecciones.stream()
                .filter(Leccion::isCompletada)
                .count();

        this.progreso = (double) leccionesCompletadas / lecciones.size() * 100.0;
    }

    // Resto de los métodos permanecen igual...
    public static List<Curso> ordenarCursosPorProgreso(List<Curso> cursos) {
        if (cursos == null || cursos.isEmpty()) {
            return new ArrayList<>();
        }

        List<Curso> cursosOrdenados = new ArrayList<>(cursos);
        cursosOrdenados.removeIf(curso -> curso == null);

        // Ordenamiento burbuja optimizado
        int n = cursosOrdenados.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                Curso cursoActual = cursosOrdenados.get(j);
                Curso cursoSiguiente = cursosOrdenados.get(j + 1);

                double progresoActual = cursoActual != null ? cursoActual.getProgreso() : 0.0;
                double progresoSiguiente = cursoSiguiente != null ? cursoSiguiente.getProgreso() : 0.0;

                if (progresoActual < progresoSiguiente) {
                    Curso temp = cursosOrdenados.get(j);
                    cursosOrdenados.set(j, cursosOrdenados.get(j + 1));
                    cursosOrdenados.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        return cursosOrdenados;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", materia='" + materia + '\'' +
                ", lecciones=" + (lecciones != null ? lecciones.size() : 0) +
                ", estudiantesInscritos=" + (estudiantesInscritos != null ? estudiantesInscritos.size() : 0) +
                ", profesorId='" + profesorId + '\'' +
                ", progreso=" + progreso +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return id != null ? id.equals(curso.id) : curso.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}