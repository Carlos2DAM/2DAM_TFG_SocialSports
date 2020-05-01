package modelo;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	
	private long id;
	private String nombre, contrasena, correo, genero, direccion, fotoPerfil;
	private Date fechaNacimiento, alta;
	double reputacionParticipante, reputacionOrganizador;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	/*public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Date getAlta() {
		return alta;
	}
	public void setAlta(Date alta) {
		this.alta = alta;
	}*/
	public double getReputacionParticipante() {
		return reputacionParticipante;
	}
	public void setReputacionParticipante(double reputacionParticipante) {
		this.reputacionParticipante = reputacionParticipante;
	}
	public double getReputacionOrganizador() {
		return reputacionOrganizador;
	}
	public void setReputacionOrganizador(double reputacionOrganizador) {
		this.reputacionOrganizador = reputacionOrganizador;
	}
}
