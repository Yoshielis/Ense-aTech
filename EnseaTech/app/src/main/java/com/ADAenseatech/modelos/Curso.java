package com.ADAenseatech.modelos;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class Curso {
    private String id;
    private String title;
    private String description;
    private String subject; // Física, Cálculo, etc.
    private List<Lecciones> Leccioness;
    private List<String> enrolledStudents;
    private String teacherId;
    private double progress; // Progreso general del curso

    // Algoritmo de búsqueda binaria para encontrar curso
    public static Curso findCursoById(List<Curso> Cursos, String CursoId) {
        // Primero ordenamos por ID
        List<Curso> sortedCursos = sortCursosById(Cursos);

        int left = 0;
        int right = sortedCursos.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Curso midCurso = sortedCursos.get(mid);

            int comparison = midCurso.getId().compareTo(CursoId);
            if (comparison == 0) {
                return midCurso;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // QuickSort para ordenar cursos por ID
    public static List<Curso> sortCursosById(List<Curso> Cursos) {
        if (Cursos.size() <= 1) {
            return new ArrayList<>(Cursos);
        }

        List<Curso> sorted = new ArrayList<>(Cursos);
        quickSort(sorted, 0, sorted.size() - 1);
        return sorted;
    }

    private static void quickSort(List<Curso> Cursos, int low, int high) {
        if (low < high) {
            int pi = partition(Cursos, low, high);
            quickSort(Cursos, low, pi - 1);
            quickSort(Cursos, pi + 1, high);
        }
    }

    private static int partition(List<Curso> Cursos, int low, int high) {
        String pivot = Cursos.get(high).getId();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (Cursos.get(j).getId().compareTo(pivot) <= 0) {
                i++;
                Collections.swap(Cursos, i, j);
            }
        }
        Collections.swap(Cursos, i + 1, high);
        return i + 1;
    }

    // Constructores, getters y setters
    public Curso() {
        this.Leccioness = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    public Curso(String id, String title, String description, String subject, String teacherId) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.teacherId = teacherId;
        this.progress = 0.0;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public List<Lecciones> getLeccioness() { return Leccioness; }
    public void setLeccioness(List<Lecciones> Leccioness) { this.Leccioness = Leccioness; }

    public List<String> getEnrolledStudents() { return enrolledStudents; }
    public void setEnrolledStudents(List<String> enrolledStudents) { this.enrolledStudents = enrolledStudents; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public double getProgress() { return progress; }
    public void setProgress(double progress) { this.progress = progress; }
}
