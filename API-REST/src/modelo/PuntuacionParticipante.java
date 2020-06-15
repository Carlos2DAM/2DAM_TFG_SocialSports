package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PuntuacionParticipante {
	
	private String emailUsuarioEmisor;   
	private String emailUsuarioPuntuado; 
	private String idEventoFinalizado; 
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
}
