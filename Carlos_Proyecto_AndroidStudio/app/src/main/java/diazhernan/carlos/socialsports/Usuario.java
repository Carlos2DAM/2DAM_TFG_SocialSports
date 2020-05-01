package diazhernan.carlos.socialsports;

import java.util.Date;

public class Usuario {

    private long idUsuario;
    private String emailUsuario;
    private String paswordUsuario;
    private String nombreUsuario;
    private String generoUsuario;
    private String direccionUsuario;
    private Date fechaNacimientoUsuario;
    private Date fechaAltaUsuario;
    private double reputacionParticipanteUsuario;
    private double reputacionOrganizadorUsuario;
    private String fotoPerfilUsuario;

    public Usuario() {
    }

    public Usuario(long idUsuario, String emailUsuario, String nombreUsuario, String generoUsuario, String direccionUsuario, Date fechaNacimientoUsuario, Date fechaAltaUsuario, double reputacionParticipanteUsuario, double reputacionOrganizadorUsuario, String fotoPerfilUsuario) {
        this.idUsuario = idUsuario;
        this.emailUsuario = emailUsuario;
        this.nombreUsuario = nombreUsuario;
        this.generoUsuario = generoUsuario;
        this.direccionUsuario = direccionUsuario;
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.fechaAltaUsuario = fechaAltaUsuario;
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
        this.fotoPerfilUsuario = fotoPerfilUsuario;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPaswordUsuario() {
        return paswordUsuario;
    }

    public void setPaswordUsuario(String paswordUsuario) {
        this.paswordUsuario = paswordUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getGeneroUsuario() {
        return generoUsuario;
    }

    public void setGeneroUsuario(String generoUsuario) {
        this.generoUsuario = generoUsuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public Date getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(Date fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public Date getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(Date fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public double getReputacionParticipanteUsuario() {
        return reputacionParticipanteUsuario;
    }

    public void setReputacionParticipanteUsuario(double reputacionParticipanteUsuario) {
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
    }

    public double getReputacionOrganizadorUsuario() {
        return reputacionOrganizadorUsuario;
    }

    public void setReputacionOrganizadorUsuario(double reputacionOrganizadorUsuario) {
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
    }

    public String getFotoPerfilUsuario() {
        return fotoPerfilUsuario;
    }

    public void setFotoPerfilUsuario(String fotoPerfilUsuario) {
        this.fotoPerfilUsuario = fotoPerfilUsuario;
    }
}
