package com.ADAenseatech.modelos;

import java.util.ArrayList;
import java.util.List;

public class Lecciones {
    private String id;
    private String titulo;
    private String descripcion;
    private String urlVideo;
    private List<Referencia> referencias;
    private List<Preguntas> preguntas; // Cambiado a singular
    private String descripcionExperimento;
    private boolean completada;
    private double progreso;
    private Curso curso;

    public Lecciones() {
        this.referencias = new ArrayList<>();
        this.preguntas = new ArrayList<>();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrlVideo() { return urlVideo; }
    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo; }

    public List<Referencia> getReferencias() { return referencias; }
    public void setReferencias(List<Referencia> referencias) { this.referencias = referencias; }

    public List<Preguntas> getPreguntas() { return preguntas; }
    public void setPreguntas(List<Preguntas> preguntas) { this.preguntas = preguntas; }

    public String getDescripcionExperimento() { return descripcionExperimento; }
    public void setDescripcionExperimento(String descripcionExperimento) { this.descripcionExperimento = descripcionExperimento; }

    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }

    public double getProgreso() { return progreso; }
    public void setProgreso(double progreso) { this.progreso = progreso; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

}