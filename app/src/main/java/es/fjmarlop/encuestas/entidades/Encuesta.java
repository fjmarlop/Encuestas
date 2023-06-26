package es.fjmarlop.encuestas.entidades;

import java.io.Serializable;
import java.util.HashMap;

public class Encuesta implements Serializable {

    private int edad;
    private String genero;
    private String provincia;
    private HashMap<String,String> mapa;

    public Encuesta() {
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public HashMap<String, String> getMapa() {
        return mapa;
    }

    public void setMapa(HashMap<String, String> mapa) {
        this.mapa = mapa;
    }
}
