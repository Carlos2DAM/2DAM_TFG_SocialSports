package diazhernan.carlos.socialsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsParticipants;
import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsRequests;
import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsSettings;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventSettings extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private BottomNavigationView navigationView;
    private EventSettingsSettings eventSettingsSettings;
    private EventSettingsParticipants eventSettingsParticipants;
    private EventSettingsRequests eventSettingsRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings);
        tabLayout = findViewById(R.id.tabsEventSettings);
        navigationView = findViewById(R.id.navigationEventSettings);
        toolbar = findViewById(R.id.toolbarEventSettings);
        toolbar.setTitle(Funcionalidades.eventoSeleccionado.getDeporte()+" - "+Funcionalidades.eventoSeleccionado.getLocalidad());
        eventSettingsSettings = new EventSettingsSettings();
        eventSettingsParticipants = new EventSettingsParticipants();
        eventSettingsRequests = new EventSettingsRequests();

        if (!Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
            if (!Funcionalidades.eresSolicitante(Funcionalidades.eventoSeleccionado)
                    && !Funcionalidades.eresParticipante(Funcionalidades.eventoSeleccionado)
                    && !Funcionalidades.estasBaneado(Funcionalidades.eventoSeleccionado,this)) {
                toolbar.inflateMenu(R.menu.event_subscribe_menu);
                navigationView.inflateMenu(R.menu.event_subscribe_menu);
            }
            else if (!Funcionalidades.estasBaneado(Funcionalidades.eventoSeleccionado,this)) {
                toolbar.inflateMenu(R.menu.event_unsubscribe_menu);
                navigationView.inflateMenu(R.menu.event_unsubscribe_menu);
            }
        }
        else {
            toolbar.inflateMenu(R.menu.event_organizer_menu);
            navigationView.inflateMenu(R.menu.event_organizer_menu);
        }
        Funcionalidades.showSelectedFragment(R.id.containerEventSettings,getSupportFragmentManager(),eventSettingsSettings);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_event_settings_sttings))) {
                    Funcionalidades.showSelectedFragment(R.id.containerEventSettings, getSupportFragmentManager(), eventSettingsSettings);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_event_settings_participants))) {
                    eventSettingsParticipants = new EventSettingsParticipants();
                    Funcionalidades.showSelectedFragment(R.id.containerEventSettings, getSupportFragmentManager(), eventSettingsParticipants);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_event_settings_requests))) {
                    eventSettingsRequests = new EventSettingsRequests();
                    Funcionalidades.showSelectedFragment(R.id.containerEventSettings, getSupportFragmentManager(), eventSettingsRequests);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                realizarAccionSeleccionada(item);
                return true;
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                realizarAccionSeleccionada(menuItem);
                return true;
            }
        });
    }

    private void realizarAccionSeleccionada(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemMenuEventSave:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                guardarCambiosEvento();
                break;
            case R.id.itemMenuEventDelete:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                eliminarEvento();
                finish();
                break;
            case R.id.itemMenuEventFinalize:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                Funcionalidades.eventoSeleccionado.setTerminado(true);
                Funcionalidades.actualizarTerminarEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),true);
                finish();
                Funcionalidades.mostrarMensaje("Evento Finalizado",this);
                break;
            case R.id.itemMenuSubscribe:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                mandarSolicitud();
                break;
            case R.id.itemMenuUnsubscribe:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                eliminarSolicitud();
                break;
        }
    }

    private void guardarCambiosEvento() {
        if (eventSettingsSettings.getFechaEvento() != null) {
            if (Funcionalidades.eventoSeleccionado.getFechaEvento() == null ||
                    eventSettingsSettings.getFechaEvento().compareTo(Funcionalidades.eventoSeleccionado.getFechaEvento()) != 0) {
                Funcionalidades.eventoSeleccionado.setFechaEvento(eventSettingsSettings.getFechaEvento());
                Funcionalidades.actualizarFechaEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getFechaEvento());
            }
        }

        if (!eventSettingsSettings.getHoraEvento().equals(Funcionalidades.eventoSeleccionado.getHoraEvento())) {
            Funcionalidades.eventoSeleccionado.setHoraEvento(eventSettingsSettings.getHoraEvento());
            Funcionalidades.actualizarHoraEvento(Funcionalidades.eventoSeleccionado.getIdEvento(), eventSettingsSettings.getHoraEvento());
        }

        if (!eventSettingsSettings.getDireccion().equals(Funcionalidades.eventoSeleccionado.getDireccion())) {
            Funcionalidades.eventoSeleccionado.setDireccion(eventSettingsSettings.getDireccion());
            Funcionalidades.actualizarDireccionEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getDireccion());
        }

        if (eventSettingsSettings.getNumParticipantes() != Funcionalidades.eventoSeleccionado.getMaximoParticipantes()) {
            Funcionalidades.eventoSeleccionado.setMaximoParticipantes(eventSettingsSettings.getNumParticipantes());
            Funcionalidades.actualizarMaxParticipantesEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getNumParticipantes());
        }

        if (eventSettingsSettings.getElOrganizadorEsParticipante() !=
                Funcionalidades.eventoSeleccionado.getListaParticipantes().contains(Funcionalidades.eventoSeleccionado.getOrganizadorEvento())) {
            if (eventSettingsSettings.getElOrganizadorEsParticipante())
                Funcionalidades.insertarParticipante(Funcionalidades.eventoSeleccionado,Funcionalidades.eventoSeleccionado.getOrganizadorEvento());
            else
                Funcionalidades.eliminarParticipante(Funcionalidades.eventoSeleccionado,Funcionalidades.eventoSeleccionado.getOrganizadorEvento());
        }

        if (eventSettingsSettings.getResevaRealizada() != Funcionalidades.eventoSeleccionado.isInstalacionesReservadas()) {
            Funcionalidades.eventoSeleccionado.setInstalacionesReservadas(eventSettingsSettings.getResevaRealizada());
            Funcionalidades.actualizarReservaEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getResevaRealizada());
        }

        if (eventSettingsSettings.getCosteReserva() != Funcionalidades.eventoSeleccionado.getCosteEvento()) {
            Funcionalidades.eventoSeleccionado.setCosteEvento(eventSettingsSettings.getCosteReserva());
            Funcionalidades.actualizarCosteEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getCosteReserva());
        }

        if (eventSettingsSettings.getPrecioIndividual() != Funcionalidades.eventoSeleccionado.getPrecioPorParticipante()) {
            Funcionalidades.eventoSeleccionado.setPrecioPorParticipante(eventSettingsSettings.getPrecioIndividual());
            Funcionalidades.actualizarPrecioEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getPrecioIndividual());
        }

        if (!eventSettingsSettings.getComentarios().equals(Funcionalidades.eventoSeleccionado.getComentarios())) {
            Funcionalidades.eventoSeleccionado.setComentarios(eventSettingsSettings.getComentarios());
            Funcionalidades.actualizarComentariosEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getComentarios());
        }

        if (eventSettingsSettings.getEdadMinima() != Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMinima()) {
            Funcionalidades.eventoSeleccionado.getRequisitos().setEdadMinima(eventSettingsSettings.getEdadMinima());
            Funcionalidades.actualizarEdadMinEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getEdadMinima());
        }

        if (eventSettingsSettings.getEdadMaxima() != Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMaxima()) {
            Funcionalidades.eventoSeleccionado.getRequisitos().setEdadMaxima(eventSettingsSettings.getEdadMaxima());
            Funcionalidades.actualizarEdadMaxEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getEdadMaxima());
        }

        if (eventSettingsSettings.getGenero() != Funcionalidades.eventoSeleccionado.getRequisitos().getRequisitoDeGenero()) {
            Funcionalidades.eventoSeleccionado.getRequisitos().setRequisitoDeGenero(eventSettingsSettings.getGenero());
            Funcionalidades.actualizarGeneroEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getGenero());
        }

        if (eventSettingsSettings.getReputacion() != Funcionalidades.eventoSeleccionado.getRequisitos().getReputacionNecesaria()) {
            Funcionalidades.eventoSeleccionado.getRequisitos().setReputacionNecesaria(eventSettingsSettings.getReputacion());
            Funcionalidades.actualizarReputacionEvento(Funcionalidades.eventoSeleccionado.getIdEvento(),eventSettingsSettings.getReputacion());
        }

        Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_guardado_correcto), this);
    }

    private void mandarSolicitud() {
        Funcionalidades.insertarSolicitante(Funcionalidades.eventoSeleccionado,LoginActivity.usuario);
        Funcionalidades.mostrarMensaje(getResources().getString(R.string.messaje_request_sent),getApplicationContext());
        toolbar.inflateMenu(R.menu.event_unsubscribe_menu);
        tabLayout.getTabAt(2).select();
    }

    private void eliminarSolicitud() {
        if (Funcionalidades.eresSolicitante(Funcionalidades.eventoSeleccionado)) {
            Funcionalidades.eliminarSolicitante(Funcionalidades.eventoSeleccionado, LoginActivity.usuario);
            tabLayout.getTabAt(2).select();
        }
        else if (Funcionalidades.eresParticipante(Funcionalidades.eventoSeleccionado)) {
            Funcionalidades.eliminarParticipante(Funcionalidades.eventoSeleccionado, LoginActivity.usuario);
            tabLayout.getTabAt(1).select();
        }
        toolbar.inflateMenu(R.menu.event_subscribe_menu);

        Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_request_removed),this);
    }

    private void eliminarEvento() {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.eliminarEvento("Bearer " + LoginActivity.token, Funcionalidades.eventoSeleccionado.getIdEvento()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_event_removed),getApplicationContext());
                    MainActivity.listaEventos.remove(Funcionalidades.eventoSeleccionado);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
