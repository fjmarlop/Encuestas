package es.fjmarlop.encuestas.entidades;

import java.util.ArrayList;

public class Pregunta {

    private int idPregunta;
    private String enunciado;
    private ArrayList<String> respuestas;
    private int numeroRespuestas;

    public Pregunta() { }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public ArrayList<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<String> respuestas) {
        this.respuestas = respuestas;
    }

    public int getNumeroRespuestas() {
        return numeroRespuestas;
    }

    public void setNumeroRespuestas(int numeroRespuestas) {
        this.numeroRespuestas = numeroRespuestas;
    }
}
