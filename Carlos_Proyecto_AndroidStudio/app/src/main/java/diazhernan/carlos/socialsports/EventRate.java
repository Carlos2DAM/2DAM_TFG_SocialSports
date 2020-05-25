package diazhernan.carlos.socialsports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    private ListView listViewParticipants;
    private RatingBar ratingBarOrganizer;
    private TextView textOrganizer;
    private TextView textParticipants;
    private View viewDivider;
    private Button buttonSubmit;
    private boolean haSidoPuntuado;
    public static ArrayList<PuntuacionParticipante> listaPuntuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rate);
        listaPuntuaciones = new ArrayList<>();
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
        mostrarListaParticipantes(Funcionalidades.eventoSeleccionado.getListaParticipantes());
    }

    private void mostrarListaParticipantes(ArrayList<Usuario> arrayList)
    {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(this, R.layout.item_lista_usuarios,
                R.id.textItemEventoDeporte, arrayList, haSidoPuntuado);
        listViewParticipants.setAdapter(adapter);
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
        mostrarListaParticipantes(Funcionalidades.eventoSeleccionado.getListaParticipantes());
    }

    private void deshabilitarPuntuar() {
        buttonSubmit.setVisibility(View.GONE);
        textOrganizer.setText(getResources().getString(R.string.organizer_has_been_rated));
        textParticipants.setText(getResources().getString(R.string.participants_has_been_rated));
    }
}
