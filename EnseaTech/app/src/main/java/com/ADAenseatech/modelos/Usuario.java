package com.ADAenseatech.modelos;
import java.util.ArrayList;
import java.util.List;
public class Usuario {
    private String id;
    private String name;
    private String email;
    private String UsuarioType; // STUDENT, TEACHER, TUTOR, ADMIN
    private List<String> enrolledCourses;
    private int points;
    private List<String> achievements;

    // Algoritmo de búsqueda lineal para encontrar usuario
    public static Usuario findUsuarioById(List<Usuario> Usuarios, String UsuarioId) {
        for (Usuario Usuario : Usuarios) {
            if (Usuario.getId().equals(UsuarioId)) {
                return Usuario;
            }
        }
        return null;
    }

    // Algoritmo de ordenación por puntos (bubble sort)
    public static List<Usuario> sortUsuariosByPoints(List<Usuario> Usuarios) {
        List<Usuario> sortedUsuarios = new ArrayList<>(Usuarios);
        int n = sortedUsuarios.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedUsuarios.get(j).getPoints() < sortedUsuarios.get(j + 1).getPoints()) {
                    // Intercambiar
                    Usuario temp = sortedUsuarios.get(j);
                    sortedUsuarios.set(j, sortedUsuarios.get(j + 1));
                    sortedUsuarios.set(j + 1, temp);
                }
            }
        }
        return sortedUsuarios;
    }

    // Constructores, getters y setters
    public Usuario() {
        this.enrolledCourses = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.points = 0;
    }

    public Usuario(String id, String name, String email, String UsuarioType) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.UsuarioType = UsuarioType;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsuarioType() { return UsuarioType; }
    public void setUsuarioType(String UsuarioType) { this.UsuarioType = UsuarioType; }

    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<String> enrolledCourses) { this.enrolledCourses = enrolledCourses; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public List<String> getAchievements() { return achievements; }
    public void setAchievements(List<String> achievements) { this.achievements = achievements; }
}
