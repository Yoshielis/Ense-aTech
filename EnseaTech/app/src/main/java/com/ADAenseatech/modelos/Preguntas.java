package com.ADAenseatech.modelos;
import java.util.ArrayList;
import java.util.List;

public class Preguntas {
    private String id;
    private String PreguntasText;
    private List<String> options;
    private int correctAnswer;
    private String explanation;

    public Preguntas() {
        this.options = new ArrayList<>();
    }

    public Preguntas(String id, String PreguntasText, List<String> options, int correctAnswer, String explanation) {
        this();
        this.id = id;
        this.PreguntasText = PreguntasText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreguntasText() {
        return PreguntasText;
    }

    public void setPreguntasText(String PreguntasText) {
        this.PreguntasText = PreguntasText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
