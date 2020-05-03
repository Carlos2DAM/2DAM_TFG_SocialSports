package diazhernan.carlos.socialsports;

import java.util.ArrayList;
import java.util.Date;

public class Usuario {

    private long idUsuario;
    private String emailUsuario;
    private String paswordUsuario;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String generoUsuario;
    private String direccionUsuario;
    private Date fechaNacimientoUsuario;
    private Date fechaAltaUsuario;
    private double reputacionParticipanteUsuario;
    private double reputacionOrganizadorUsuario;
    private String fotoPerfilUsuario;
    private ArrayList<Usuario> listaAmigos = new ArrayList<>();
    private ArrayList<Usuario> listaBloqueados = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(long idUsuario, String emailUsuario, String paswordUsuario, String nombreUsuario, String generoUsuario, String direccionUsuario, Date fechaNacimientoUsuario, Date fechaAltaUsuario, double reputacionParticipanteUsuario, double reputacionOrganizadorUsuario, String fotoPerfilUsuario) {
        this.idUsuario = idUsuario;
        this.emailUsuario = emailUsuario;
        this.paswordUsuario = paswordUsuario;
        this.nombreUsuario = nombreUsuario;
        this.generoUsuario = generoUsuario;
        this.direccionUsuario = direccionUsuario;
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.fechaAltaUsuario = fechaAltaUsuario;
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
        this.fotoPerfilUsuario = fotoPerfilUsuario;
    }

    public Usuario(long idUsuario, String emailUsuario, String paswordUsuario, String nombreUsuario, String apellidosUsuario, String generoUsuario, String direccionUsuario, Date fechaNacimientoUsuario, Date fechaAltaUsuario, double reputacionParticipanteUsuario, double reputacionOrganizadorUsuario, String fotoPerfilUsuario, ArrayList<Usuario> listaAmigos, ArrayList<Usuario> listaBloqueados) {
        this.idUsuario = idUsuario;
        this.emailUsuario = emailUsuario;
        this.paswordUsuario = paswordUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.generoUsuario = generoUsuario;
        this.direccionUsuario = direccionUsuario;
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.fechaAltaUsuario = fechaAltaUsuario;
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
        this.fotoPerfilUsuario = fotoPerfilUsuario;
        this.listaAmigos = listaAmigos;
        this.listaBloqueados = listaBloqueados;
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

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
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

    public ArrayList<Usuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(ArrayList<Usuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public ArrayList<Usuario> getListaBloqueados() {
        return listaBloqueados;
    }

    public void setListaBloqueados(ArrayList<Usuario> listaBloqueados) {
        this.listaBloqueados = listaBloqueados;
    }
}
