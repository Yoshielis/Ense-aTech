package com.ADAenseatech.modelos;

public class Referencia {
    private String title;
    private String url;
    private String type; // PDF, VIDEO, ARTICLE

    public Referencia() {
    }

    public Referencia(String title, String url, String type) {
        this.title = title;
        this.url = url;
        this.type = type;
    }

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}