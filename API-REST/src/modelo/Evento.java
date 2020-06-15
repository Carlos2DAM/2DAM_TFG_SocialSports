package modelo;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
@XmlRootElement
public class Evento{

	private String idEvento;    
    private Usuario organizadorEvento;
    private String deporte;    
    private String localidad;  
    private String direccion;
    private String fechaEvento;
    private String horaEvento;         
    private String fechaCreacionEvento;
    private int maximoParticipantes;
    private boolean instalacionesReservadas;
    private float costeEvento;
    private float precioPorParticipante;
    private String comentarios;          
    private Requisitos requisitos;
    private boolean terminado;
    private ArrayList<Usuario> listaSolicitantes;
    private ArrayList<Usuario> listaDescartados;
    private ArrayList<Usuario> listaParticipantes;

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public Usuario getOrganizadorEvento() {
        return organizadorEvento;
    }

    public void setOrganizadorEvento(Usuario organizadorEvento) {
    	if(organizadorEvento == null) this.organizadorEvento = new Usuario();
    	else this.organizadorEvento = organizadorEvento;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
    	if(deporte == null) this.deporte = "";
    	else this.deporte = deporte;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
    	if(localidad == null) this.localidad = "";
    	else this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if(direccion == null) this.direccion = "";
        else this.direccion = direccion;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
    	if(horaEvento == null) this.horaEvento = "";
    	else this.horaEvento = horaEvento;
    }

    public String getFechaCreacionEvento() {
        return fechaCreacionEvento;
    }

    public void setFechaCreacionEvento(String fechaCreacionEvento) {
        this.fechaCreacionEvento = fechaCreacionEvento;
    }

    public int getMaximoParticipantes() {
        return maximoParticipantes;
    }

    public void setMaximoParticipantes(int maximoParticipantes) {
        this.maximoParticipantes = maximoParticipantes;
    }

    public boolean isInstalacionesReservadas() {
        return instalacionesReservadas;
    }

    public void setInstalacionesReservadas(boolean instalacionesReservadas) {
        this.instalacionesReservadas = instalacionesReservadas;
    }

    public float getCosteEvento() {
        return costeEvento;
    }

    public void setCosteEvento(float costeEvento) {
        this.costeEvento = costeEvento;
    }

    public float getPrecioPorParticipante() {
        return precioPorParticipante;
    }

    public void setPrecioPorParticipante(float precioPorParticipante) {
        this.precioPorParticipante = precioPorParticipante;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
    	if(comentarios == null) this.comentarios = "";
    	else this.comentarios = comentarios;
    }

    public Requisitos getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Requisitos requisitos) {
        this.requisitos = requisitos;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public ArrayList<Usuario> getListaSolicitantes() {
        return listaSolicitantes;
    }

    public void setListaSolicitantes(ArrayList<Usuario> listaSolicitantes) {
        this.listaSolicitantes = listaSolicitantes;
    }

    public ArrayList<Usuario> getListaDescartados() {
        return listaDescartados;
    }

    public void setListaDescartados(ArrayList<Usuario> listaDescartados) {
        this.listaDescartados = listaDescartados;
    }

    public ArrayList<Usuario> getListaParticipantes() {
        return listaParticipantes;
    }

    public void setListaParticipantes(ArrayList<Usuario> listaParticipantes) {
    	this.listaParticipantes = listaParticipantes;
    }


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Evento [idEvento=");
		builder.append(idEvento);
		builder.append(", organizadorEvento=");
		builder.append(organizadorEvento);
		builder.append(", deporte=");
		builder.append(deporte);
		builder.append(", localidad=");
		builder.append(localidad);
		builder.append(", direccion=");
		builder.append(direccion);
		builder.append(", fechaEvento=");
		builder.append(fechaEvento);
		builder.append(", horaEvento=");
		builder.append(horaEvento);
		builder.append(", fechaCreacionEvento=");
		builder.append(fechaCreacionEvento);
		builder.append(", maximoParticipantes=");
		builder.append(maximoParticipantes);
		builder.append(", instalacionesReservadas=");
		builder.append(instalacionesReservadas);
		builder.append(", costeEvento=");
		builder.append(costeEvento);
		builder.append(", precioPorParticipante=");
		builder.append(precioPorParticipante);
		builder.append(", comentarios=");
		builder.append(comentarios);
		builder.append(", requisitos=");
		builder.append(requisitos);
		builder.append(", terminado=");
		builder.append(terminado);
		builder.append(", listaSolicitantes=");
		builder.append(listaSolicitantes);
		builder.append(", listaDescartados=");
		builder.append(listaDescartados);
		builder.append(", listaParticipantes=");
		builder.append(listaParticipantes);
		builder.append("]");
		return builder.toString();
	}
    
}
