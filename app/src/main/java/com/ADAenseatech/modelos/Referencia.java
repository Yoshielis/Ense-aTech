package com.ADAenseatech.modelos;

public class Referencia {
    private String titulo;
    private String url;
    private String tipo; // PDF, VIDEO, ARTICULO

    public Referencia() {}

    public Referencia(String titulo, String url, String tipo) {
        this.titulo = titulo;
        this.url = url;
        this.tipo = tipo;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}