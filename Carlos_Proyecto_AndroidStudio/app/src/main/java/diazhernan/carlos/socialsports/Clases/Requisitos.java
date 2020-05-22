package diazhernan.carlos.socialsports.Clases;

import java.io.Serializable;

public class Requisitos implements Serializable {

    private int edadMinima;
    private int edadMaxima;
    private String requisitoDeGenero;   //6 caracteres (Male o Female)
    private float reputacionNecesaria;

    public Requisitos() {
        edadMinima = -1;
        edadMaxima = -1;
        requisitoDeGenero = null;
        reputacionNecesaria = -1;
    }

    public Requisitos(int edadMinima, int edadMaxima, String requisitoDeGenero, float reputacionNecesaria) {
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.requisitoDeGenero = requisitoDeGenero;
        this.reputacionNecesaria = reputacionNecesaria;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public int getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(int edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public String getRequisitoDeGenero() {
        return requisitoDeGenero;
    }

    public void setRequisitoDeGenero(String requisitoDeGenero) {
        this.requisitoDeGenero = requisitoDeGenero;
    }

    public float getReputacionNecesaria() {
        return reputacionNecesaria;
    }

    public void setReputacionNecesaria(float reputacionNecesaria) {
        this.reputacionNecesaria = reputacionNecesaria;
    }

    @Override
    public String toString() {
        return "Requisitos{" +
                "edadMinima=" + edadMinima +
                ", edadMaxima=" + edadMaxima +
                ", requisitoDeGenero='" + requisitoDeGenero + '\'' +
                ", reputacionNecesaria=" + reputacionNecesaria +
                '}';
    }
}
