package diazhernan.carlos.socialsports.Clases;

import java.io.Serializable;

public class PuntuacionEvento implements Serializable {

    private String emailUsuarioEmisor;   //foreign key          | Primary key
    private String idEventoFinalizado;  //foreign key           | Primary key
    private float calificacion;

    public PuntuacionEvento() {
    }

    public PuntuacionEvento(String emailUsuarioEmisor, String idEventoFinalizado, float calificacion) {
        this.emailUsuarioEmisor = emailUsuarioEmisor;
        this.idEventoFinalizado = idEventoFinalizado;
        this.calificacion = calificacion;
    }

    public String getEmailUsuarioEmisor() {
        return emailUsuarioEmisor;
    }

    public void setEmailUsuarioEmisor(String emailUsuarioEmisor) {
        this.emailUsuarioEmisor = emailUsuarioEmisor;
    }

    public String getIdEventoFinalizado() {
        return idEventoFinalizado;
    }

    public void setIdEventoFinalizado(String idEventoFinalizado) {
        this.idEventoFinalizado = idEventoFinalizado;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "PuntuacionEvento{" + emailUsuarioEmisor + " - " + idEventoFinalizado + " - " + calificacion + "}\n";
    }
}
