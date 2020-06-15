package modelo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	
	private String emailUsuario; 
	private String passwordUsuario;
    private String nombreUsuario;       
    private String apellidosUsuario;
    private String generoUsuario;       
    private String direccionUsuario; 
    private String fechaNacimientoUsuario;
    private String fechaAltaUsuario;
    private float reputacionParticipanteUsuario;
    private float reputacionOrganizadorUsuario;
    private String fotoPerfilUsuario;              
    private ArrayList<Usuario> listaAmigos;
    private ArrayList<Usuario> listaBloqueados;

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

	public String getNombreUsuario() {
        return nombreUsuario;
    }
	
    public String getPasswordUsuario() {
		return passwordUsuario;
	}

	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
    	if(nombreUsuario == null) this.nombreUsuario = "";
    	else this.nombreUsuario = nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
    	if(apellidosUsuario == null) this.apellidosUsuario = "";
    	else this.apellidosUsuario = apellidosUsuario;
    }

    public String getGeneroUsuario() {
        return generoUsuario;
    }

    public void setGeneroUsuario(String generoUsuario) {
    	if(generoUsuario == null) this.generoUsuario = "";
    	else this.generoUsuario = generoUsuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
    	if(direccionUsuario == null) this.direccionUsuario = "";
    	else this.direccionUsuario = direccionUsuario;
    }

    public String getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(String fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public String getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(String fechaAltaUsuario) {
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
