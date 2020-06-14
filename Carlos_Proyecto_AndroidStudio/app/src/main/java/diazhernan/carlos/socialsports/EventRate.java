package diazhernan.carlos.socialsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.AdaptadorListaUsuarios;
import diazhernan.carlos.socialsports.Clases.PuntuacionEvento;
import diazhernan.carlos.socialsports.Clases.PuntuacionParticipante;
import diazhernan.carlos.socialsports.Clases.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRate extends AppCompatActivity {

    private Usuario usuarioSeleccionado;
    private ListView listViewParticipants;
    private RatingBar ratingBarOrganizer;
    private TextView textOrganizer;
    private TextView textParticipants;
    private View viewDivider;
    private Toolbar toolbar;
    private BottomNavigationView navigationView;
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
        navigationView = findViewById(R.id.navigationEventRate);
        toolbar = findViewById(R.id.toolbarEventRate);
        toolbar.setTitle(Funcionalidades.eventoSeleccionado.getDeporte()+" - "+Funcionalidades.eventoSeleccionado.getLocalidad());
        if (Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
            ratingBarOrganizer.setVisibility(View.GONE);
            ratingBarOrganizer.setEnabled(false);
            textOrganizer.setVisibility(View.GONE);
            viewDivider.setVisibility(View.GONE);
        }
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemMenuRate)
                    enviarPuntuaciones();
                return true;
            }
        });

        haSidoPuntuado(Funcionalidades.eventoSeleccionado.getIdEvento());
        /*
        if (haSidoPuntuado)
            deshabilitarPuntuar();*/
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
        if (listaParticipantes != null)
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

    private void enviarPuntuaciones() {
        if (ratingBarOrganizer.isEnabled()) {
            PuntuacionEvento puntuacionEvento = new PuntuacionEvento(LoginActivity.usuario.getEmailUsuario(),
                    Funcionalidades.eventoSeleccionado.getIdEvento(), ratingBarOrganizer.getRating());
            Funcionalidades.enviarPuntuacionEvento(puntuacionEvento);
        }
        for (PuntuacionParticipante puntuacion: listaPuntuaciones) {
            Funcionalidades.enviarPuntuacionParticipante(puntuacion);
        }
        haSidoPuntuado = true;
        deshabilitarPuntuar();
        if (listaParticipantes != null)
            mostrarListaParticipantes(listaParticipantes);
    }

    private void deshabilitarPuntuar() {
        navigationView.setVisibility(View.GONE);
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

    public void haSidoPuntuado(String idEvento) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.getHaSidoPuntuado("Bearer " + LoginActivity.token, idEvento).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Funcionalidades.mostrarMensaje(Integer.toString(response.code()),getApplicationContext());
                if(response.isSuccessful()) {
                    if (response.body())
                        Funcionalidades.mostrarMensaje("Se ha puntuado",getApplicationContext());
                    else
                        Funcionalidades.mostrarMensaje("NO se ha puntuado",getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Funcionalidades.mostrarMensaje("onFailure",getApplicationContext());
            }
        });
    }
}
