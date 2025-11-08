package com.ADAenseatech.modelos;
import java.util.ArrayList;
import java.util.List;
public class Lecciones {
    private String id;
    private String title;
    private String description;
    private String videoUrl;
    private List<Referencia> Referencias;
    private List<Preguntas> Preguntass;
    private String experimentDescription;
    private boolean completed;
    private double progress;

    public Lecciones() {
        this.Referencias = new ArrayList<>();
        this.Preguntass = new ArrayList<>();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public List<Referencia> getReferencia() { return Referencias; }
    public void setReferencia(List<Referencia> Referencias) { this.Referencias = Referencias; }

    public List<Preguntas> getPreguntass() { return Preguntass; }
    public void setPreguntass(List<Preguntas> Preguntass) { this.Preguntass = Preguntass; }

    public String getExperimentDescription() { return experimentDescription; }
    public void setExperimentDescription(String experimentDescription) { this.experimentDescription = experimentDescription; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public double getProgress() { return progress; }
    public void setProgress(double progress) { this.progress = progress; }
}
