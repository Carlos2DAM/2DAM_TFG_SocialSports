package diazhernan.carlos.socialsports.Clases;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String idEvento;
    private long organizadorEvento;
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
    ArrayList<Usuario> listaSolicitantes;
    ArrayList<Usuario> listaDescartados;
    ArrayList<Usuario> listaParticipantes;
}
