package diazhernan.carlos.socialsports.Clases;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String idEvento;    //100 caracteres
    private Usuario organizadorEvento;
    private String deporte;     //30 caracteres
    private String localidad;   //30 caracteres
    private String direccion;   //50 caracteres
    private Date fechaEvento;
    private String horaEvento;          //5 caracteres
    private Date fechaCreacionEvento;
    private int maximoParticipantes;
    private boolean instalacionesReservadas;
    private float costeEvento;
    private float precioPorParticipante;
    private String comentarios;             //300 caracteres
    private Requisitos requisitos;
    private boolean terminado;
    private ArrayList<Usuario> listaSolicitantes;
    private ArrayList<Usuario> listaDescartados;
    private ArrayList<Usuario> listaParticipantes;

    public Evento() {
        organizadorEvento = new Usuario();
        deporte = "";
        localidad = "";
        direccion = "";
        horaEvento = "";
        fechaEvento = null;
        fechaCreacionEvento = new Date();
        maximoParticipantes = -1;
        instalacionesReservadas = false;
        costeEvento = -1;
        precioPorParticipante = -1;
        comentarios = "";
        requisitos = new Requisitos();
        terminado = false;
        listaSolicitantes = new ArrayList<>();
        listaDescartados = new ArrayList<>();
        listaParticipantes = new ArrayList<>();
        idEvento = organizadorEvento.getEmailUsuario()+"__"+getFechaCreacionEvento().toString();
    }

    public Evento(String idEvento, Usuario organizadorEvento, String deporte, String localidad, String direccion, Date fechaEvento, String horaEvento, Date fechaCreacionEvento, int maximoParticipantes, boolean instalacionesReservadas, float costeEvento, float precioPorParticipante, String comentarios, Requisitos requisitos, boolean terminado, ArrayList<Usuario> listaSolicitantes, ArrayList<Usuario> listaDescartados, ArrayList<Usuario> listaParticipantes) {
        this.idEvento = idEvento;
        this.organizadorEvento = organizadorEvento;
        this.deporte = deporte;
        this.localidad = localidad;
        this.direccion = direccion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.fechaCreacionEvento = fechaCreacionEvento;
        this.maximoParticipantes = maximoParticipantes;
        this.instalacionesReservadas = instalacionesReservadas;
        this.costeEvento = costeEvento;
        this.precioPorParticipante = precioPorParticipante;
        this.comentarios = comentarios;
        this.requisitos = requisitos;
        this.terminado = terminado;
        this.listaSolicitantes = listaSolicitantes;
        this.listaDescartados = listaDescartados;
        this.listaParticipantes = listaParticipantes;
    }

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
        this.organizadorEvento = organizadorEvento;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public Date getFechaCreacionEvento() {
        return fechaCreacionEvento;
    }

    public void setFechaCreacionEvento(Date fechaCreacionEvento) {
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
        this.comentarios = comentarios;
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
        return "Evento{" +
                "idEvento='" + idEvento + '\'' +
                ", organizadorEvento=" + organizadorEvento +
                ", deporte='" + deporte + '\'' +
                ", localidad='" + localidad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaEvento=" + fechaEvento +
                ", horaEvento='" + horaEvento + '\'' +
                ", fechaCreacionEvento=" + fechaCreacionEvento +
                ", maximoParticipantes=" + maximoParticipantes +
                ", instalacionesReservadas=" + instalacionesReservadas +
                ", costeEvento=" + costeEvento +
                ", precioPorParticipante=" + precioPorParticipante +
                ", comentarios='" + comentarios + '\'' +
                ", requisitos=" + requisitos +
                ", listaSolicitantes=" + listaSolicitantes +
                ", listaDescartados=" + listaDescartados +
                ", listaParticipantes=" + listaParticipantes +
                '}';
    }
}
