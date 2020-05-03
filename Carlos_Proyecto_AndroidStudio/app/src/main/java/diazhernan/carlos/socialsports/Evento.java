package diazhernan.carlos.socialsports;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String idEvento;
    private String deporte;
    private String localidad;
    private String direccion;
    private Date fechaEvento;
    private String horaEvento;
    private Date fechaCreacionEvento;
    private int maximoParticipantes;
    private boolean instalacionesReservadas;
    private float costeEvento;
    private float precioPorParticipante;
    private String comentarios;
    private int edadMinima;
    private int edadMaxima;
    private String requisitoDeGenero;
    private float reputacionNecesaria;
    ArrayList<Usuario> listaParticipantes;
    ArrayList<Usuario> listaInvitados;
    ArrayList<Usuario> listaDescartados;
}
