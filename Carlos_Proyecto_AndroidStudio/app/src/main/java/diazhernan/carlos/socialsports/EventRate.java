package diazhernan.carlos.socialsports;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.AdaptadorListaUsuarios;
import diazhernan.carlos.socialsports.Clases.PuntuacionEvento;
import diazhernan.carlos.socialsports.Clases.PuntuacionParticipante;
import diazhernan.carlos.socialsports.Clases.Usuario;

public class EventRate extends AppCompatActivity {

    private Usuario usuarioSeleccionado;
    private ListView listViewParticipants;
    private RatingBar ratingBarOrganizer;
    private TextView textOrganizer;
    private TextView textParticipants;
    private View viewDivider;
    private Button buttonSubmit;
    private boolean haSidoPuntuado;
    public static ArrayList<PuntuacionParticipante> listaPuntuaciones;
    private ArrayList<Usuario> listaParticipantes;
    private AlertDialog.Builder menuOpciones;
    private String[] opcionesOrganizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rate);
        listaPuntuaciones = new ArrayList<>();
        listaParticipantes = new ArrayList<>();
        opcionesOrganizador = new String[]{getResources().getString(R.string.opcion_bloqueo_permanente)
                ,getResources().getString(R.string.opcion_solicitud_de_amistad)};
        listViewParticipants = findViewById(R.id.listRateParticipants);
        ratingBarOrganizer = findViewById(R.id.ratingRateOrganizer);
        textOrganizer = findViewById(R.id.textRateOrganizer);
        textParticipants = findViewById(R.id.textRateParticipants);
        viewDivider = findViewById(R.id.dividerEventRate);
        buttonSubmit = findViewById(R.id.buttonEventRateSend);
        if (Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
            ratingBarOrganizer.setVisibility(View.GONE);
            ratingBarOrganizer.setEnabled(false);
            textOrganizer.setVisibility(View.GONE);
            viewDivider.setVisibility(View.GONE);
        }
        buttonSubmit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button) v, getApplication());
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
                enviarPuntuaciones();
            }
        });
        haSidoPuntuado = eventoHasidoPuntuado();
        if (haSidoPuntuado)
            deshabilitarPuntuar();
        menuOpciones = new AlertDialog.Builder(this);
        menuOpciones.setItems(opcionesOrganizador, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        bloquearSolicitantePermanentemente();
                        break;
                    case 1:
                        agregarAmigo();
                        break;
                }
            }
        });
        listaParticipantes = Funcionalidades.eventoSeleccionado.getListaParticipantes();
        mostrarListaParticipantes(listaParticipantes);
    }

    private void mostrarListaParticipantes(ArrayList<Usuario> arrayList)
    {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(this, R.layout.item_lista_usuarios,
                R.id.textItemEventoDeporte, arrayList, haSidoPuntuado);
        listViewParticipants.setAdapter(adapter);
        if (haSidoPuntuado) {
            listViewParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    usuarioSeleccionado = listaParticipantes.get(position);
                    if (!Funcionalidades.soyYo(usuarioSeleccionado)) {
                        menuOpciones.setTitle(usuarioSeleccionado.getNombreUsuario() +
                                " " + usuarioSeleccionado.getApellidosUsuario());
                        menuOpciones.show();
                    }
                }
            });
        }
    }

    private boolean eventoHasidoPuntuado() {
        //TODO comprobar en la base de datos si el evento ya ha sido puntuado. (select count(login.user, idEvento) > 0)
        return false;
    }

    private void enviarPuntuaciones() {
        //TODO mostrar mensaje de confirmacion y enviar puntuaciones al servidor para insertar en la BBDD.
        if (ratingBarOrganizer.isEnabled()) {
            PuntuacionEvento puntuacionEvento = new PuntuacionEvento(LoginActivity.usuario.getEmailUsuario(),
                    Funcionalidades.eventoSeleccionado.getIdEvento(), ratingBarOrganizer.getRating());
        }
        haSidoPuntuado = true;
        deshabilitarPuntuar();
        mostrarListaParticipantes(listaParticipantes);
    }

    private void deshabilitarPuntuar() {
        buttonSubmit.setVisibility(View.GONE);
        textOrganizer.setText(getResources().getString(R.string.organizer_has_been_rated));
        textParticipants.setText(getResources().getString(R.string.participants_has_been_rated));
    }

    private void bloquearSolicitantePermanentemente() {
        Funcionalidades.bloquearUsuarioPermanentemente(usuarioSeleccionado);
        Funcionalidades.eliminarAmigo(usuarioSeleccionado);
    }

    private void agregarAmigo() {
        Funcionalidades.eliminarBloqueoPermanentemente(usuarioSeleccionado);
        Funcionalidades.insertarAmigo(usuarioSeleccionado);
    }
}
