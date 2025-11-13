package com.ADAenseatech;

public class Nota {
    private String titulo;
    private String contenido;
    private String curso;
    private String fecha;

    public Nota() {}

    public Nota(String titulo, String contenido, String curso, String fecha){
        this.titulo = titulo;
        this.contenido = contenido;
        this.curso = curso;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
