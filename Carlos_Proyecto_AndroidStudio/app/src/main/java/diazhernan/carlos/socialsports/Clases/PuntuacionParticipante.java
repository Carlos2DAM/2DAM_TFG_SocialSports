package diazhernan.carlos.socialsports.Clases;

public class PuntuacionParticipante {

    private String emailUsuarioEmisor;   //foreign key          | Primary key
    private String emailUsuarioPuntuado;    //foreign key       | Primary key
    private String idEventoFinalizado;  //foreign key           | Primary key
    private float calificacion;

    public PuntuacionParticipante() {
    }

    public PuntuacionParticipante(String emailUsuarioEmisor, String emailUsuarioPuntuado, String idEventoFinalizado, float calificacion) {
        this.emailUsuarioEmisor = emailUsuarioEmisor;
        this.emailUsuarioPuntuado = emailUsuarioPuntuado;
        this.idEventoFinalizado = idEventoFinalizado;
        this.calificacion = calificacion;
    }

    public String getEmailUsuarioEmisor() {
        return emailUsuarioEmisor;
    }

    public void setEmailUsuarioEmisor(String emailUsuarioEmisor) {
        this.emailUsuarioEmisor = emailUsuarioEmisor;
    }

    public String getEmailUsuarioPuntuado() {
        return emailUsuarioPuntuado;
    }

    public void setEmailUsuarioPuntuado(String emailUsuarioPuntuado) {
        this.emailUsuarioPuntuado = emailUsuarioPuntuado;
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
        return "Puntuacion{" + emailUsuarioEmisor + "-" + emailUsuarioPuntuado + "-" + idEventoFinalizado + "-" + calificacion + "}\n";
    }
}
