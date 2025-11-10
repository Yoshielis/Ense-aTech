package com.ADAenseatech.modelos;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String id;
    private String titulo;
    private String descripcion;
    private String materia;
    private List<Lecciones> lecciones;
    private List<String> estudiantesInscritos;
    private String profesorId;
    private double progreso;

    public Curso() {
        this.lecciones = new ArrayList<>();
        this.estudiantesInscritos = new ArrayList<>();
    }

    public Curso(String id, String titulo, String descripcion, String materia, String profesorId) {
        this();
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.materia = materia;
        this.profesorId = profesorId;
    }

    // Algoritmo de búsqueda lineal
    public static Curso buscarCursoPorId(List<Curso> cursos, String cursoId) {
        for (Curso curso : cursos) {
            if (curso.getId().equals(cursoId)) {
                return curso;
            }
        }
        return null;
    }

    // Algoritmo de ordenación por progreso
    public static List<Curso> ordenarCursosPorProgreso(List<Curso> cursos) {
        List<Curso> cursosOrdenados = new ArrayList<>(cursos);
        int n = cursosOrdenados.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (cursosOrdenados.get(j).getProgreso() < cursosOrdenados.get(j + 1).getProgreso()) {
                    Curso temp = cursosOrdenados.get(j);
                    cursosOrdenados.set(j, cursosOrdenados.get(j + 1));
                    cursosOrdenados.set(j + 1, temp);
                }
            }
        }
        return cursosOrdenados;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public List<Lecciones> getLecciones() { return lecciones; }
    public void setLecciones(List<Lecciones> lecciones) { this.lecciones = lecciones; }

    public List<String> getEstudiantesInscritos() { return estudiantesInscritos; }
    public void setEstudiantesInscritos(List<String> estudiantesInscritos) { this.estudiantesInscritos = estudiantesInscritos; }

    public String getProfesorId() { return profesorId; }
    public void setProfesorId(String profesorId) { this.profesorId = profesorId; }

    public double getProgreso() { return progreso; }
    public void setProgreso(double progreso) { this.progreso = progreso; }
}