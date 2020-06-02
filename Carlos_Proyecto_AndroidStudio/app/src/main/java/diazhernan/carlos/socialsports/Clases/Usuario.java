package diazhernan.carlos.socialsports.Clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Usuario implements Serializable {

    private String emailUsuario;        //50 caracteres
    private String paswordUsuario;      //128 caracteres
    private String nombreUsuario;       //50 caracteres
    private String apellidosUsuario;    //50 caracteres
    private String generoUsuario;       //6 caracteres (Male o Female)
    private String direccionUsuario;    //60 caracteres
    private Date fechaNacimientoUsuario;
    private Date fechaAltaUsuario;
    private float reputacionParticipanteUsuario;
    private float reputacionOrganizadorUsuario;
    private String fotoPerfilUsuario;               //80 caracteres
    private boolean isOnlineNow;
    private ArrayList<Usuario> listaAmigos;
    private ArrayList<Usuario> listaBloqueados;

    public Usuario() {
        emailUsuario = "";
        paswordUsuario = "";
        nombreUsuario = "";
        apellidosUsuario = "";
        generoUsuario = "";
        direccionUsuario = "";
        fechaNacimientoUsuario = new Date();
        fechaAltaUsuario = new Date();
        reputacionParticipanteUsuario = -1;
        reputacionOrganizadorUsuario = -1;
        fotoPerfilUsuario = "";
        isOnlineNow = false;
        listaAmigos = new ArrayList<>();
        listaBloqueados = new ArrayList<>();
    }

    public Usuario(String emailUsuario, String paswordUsuario, String nombreUsuario, String generoUsuario, String direccionUsuario, Date fechaNacimientoUsuario, Date fechaAltaUsuario, float reputacionParticipanteUsuario, float reputacionOrganizadorUsuario, String fotoPerfilUsuario) {
        this.emailUsuario = emailUsuario;
        this.paswordUsuario = paswordUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = "";
        this.generoUsuario = generoUsuario;
        this.direccionUsuario = direccionUsuario;
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.fechaAltaUsuario = fechaAltaUsuario;
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
        this.fotoPerfilUsuario = fotoPerfilUsuario;
        this.isOnlineNow = false;
        listaAmigos = new ArrayList<>();
        listaBloqueados = new ArrayList<>();
    }

    public Usuario(String emailUsuario, String paswordUsuario, String nombreUsuario, String apellidosUsuario, String generoUsuario, String direccionUsuario, Date fechaNacimientoUsuario, Date fechaAltaUsuario, float reputacionParticipanteUsuario, float reputacionOrganizadorUsuario, String fotoPerfilUsuario, boolean isOnlineNow, ArrayList<Usuario> listaAmigos, ArrayList<Usuario> listaBloqueados) {
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
        this.isOnlineNow = isOnlineNow;
        this.listaAmigos = listaAmigos;
        this.listaBloqueados = listaBloqueados;
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

    public float getReputacionParticipanteUsuario() {
        return reputacionParticipanteUsuario;
    }

    public void setReputacionParticipanteUsuario(float reputacionParticipanteUsuario) {
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
    }

    public float getReputacionOrganizadorUsuario() {
        return reputacionOrganizadorUsuario;
    }

    public void setReputacionOrganizadorUsuario(float reputacionOrganizadorUsuario) {
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
    }

    public String getFotoPerfilUsuario() {
        return fotoPerfilUsuario;
    }

    public void setFotoPerfilUsuario(String fotoPerfilUsuario) {
        this.fotoPerfilUsuario = fotoPerfilUsuario;
    }

    public boolean isOnlineNow() {
        return isOnlineNow;
    }

    public void setOnlineNow(boolean onlineNow) {
        isOnlineNow = onlineNow;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "emailUsuario='" + emailUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", generoUsuario='" + generoUsuario + '\'' +
                ", reputacionParticipanteUsuario=" + reputacionParticipanteUsuario +
                '}';
    }
}
