package com.ADAenseatech.modelos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String tipoUsuario; // ESTUDIANTE, PROFESOR, TUTOR, ADMIN
    private List<String> cursosInscritos;
    private int puntos;
    private List<String> logros;

    public Usuario() {
        this.cursosInscritos = new ArrayList<>();
        this.logros = new ArrayList<>();
    }

    public Usuario(String id, String nombre, String email, String tipoUsuario) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    // Algoritmo de búsqueda lineal
    public static Usuario buscarUsuarioPorId(List<Usuario> usuarios, String usuarioId) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(usuarioId)) {
                return usuario;
            }
        }
        return null;
    }

    // Algoritmo de ordenación por puntos (bubble sort)
    public static List<Usuario> ordenarUsuariosPorPuntos(List<Usuario> usuarios) {
        List<Usuario> usuariosOrdenados = new ArrayList<>(usuarios);
        int n = usuariosOrdenados.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (usuariosOrdenados.get(j).getPuntos() < usuariosOrdenados.get(j + 1).getPuntos()) {
                    Usuario temp = usuariosOrdenados.get(j);
                    usuariosOrdenados.set(j, usuariosOrdenados.get(j + 1));
                    usuariosOrdenados.set(j + 1, temp);
                }
            }
        }
        return usuariosOrdenados;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public List<String> getCursosInscritos() { return cursosInscritos; }
    public void setCursosInscritos(List<String> cursosInscritos) { this.cursosInscritos = cursosInscritos; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public List<String> getLogros() { return logros; }
    public void setLogros(List<String> logros) { this.logros = logros; }
}