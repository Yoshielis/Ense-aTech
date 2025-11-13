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
    public void setCursosInscritos(List<String> cursosInscritos) {
        this.cursosInscritos = cursosInscritos != null ? cursosInscritos : new ArrayList<>();
    }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public List<String> getLogros() { return logros; }
    public void setLogros(List<String> logros) {
        this.logros = logros != null ? logros : new ArrayList<>();
    }
}