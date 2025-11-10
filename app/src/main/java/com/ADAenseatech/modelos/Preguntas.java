package com.ADAenseatech.modelos;

import java.util.ArrayList;
import java.util.List;

public class Preguntas {
    private String id;
    private String textoPregunta;
    private List<String> opciones;
    private int respuestaCorrecta;
    private String explicacion;

    public Preguntas() {
        this.opciones = new ArrayList<>();
    }

    public Preguntas(String id, String textoPregunta, List<String> opciones, int respuestaCorrecta, String explicacion) {
        this();
        this.id = id;
        this.textoPregunta = textoPregunta;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.explicacion = explicacion;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTextoPregunta() { return textoPregunta; }
    public void setTextoPregunta(String textoPregunta) { this.textoPregunta = textoPregunta; }

    public List<String> getOpciones() { return opciones; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }

    public int getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(int respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }

    public String getExplicacion() { return explicacion; }
    public void setExplicacion(String explicacion) { this.explicacion = explicacion; }
}