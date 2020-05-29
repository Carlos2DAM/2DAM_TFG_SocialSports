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
                    Funcionalidades.showSelectedFragment(R.id.containerEventSettings, getSupportFragmentManager(), eventSettingsParticipants);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_event_settings_requests))) {
                    Funcionalidades.showSelectedFragment(R.id.containerEventSettings, getSupportFragmentManager(), eventSettingsRequests);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
                break;
            case R.id.itemMenuEventFinalize:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                //TODO mostrar mensaje de confirmación y en caso afirmativo finalizar evento y volver a la pantalla anterior.
                Funcionalidades.eventoSeleccionado.setTerminado(true);
                Funcionalidades.mostrarMensaje("Evento Finalizado",this);
                break;
            case R.id.itemMenuSubscribe:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                mandarSolicitud();
                //TODO hacer algo mas
                break;
            case R.id.itemMenuUnsubscribe:
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),toolbar);
                eliminarSolicitud();
                //TODO hacer algo mas
                break;
        }
    }

    private void guardarCambiosEvento() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo actualizar datos del evento y guardarlos en la BBDD.
        Funcionalidades.mostrarMensaje("Guardado Realizado",this);
    }

    private void mandarSolicitud() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo insertar en BBDD del evento la nueva solicitud
        if (!Funcionalidades.eresSolicitante(Funcionalidades.eventoSeleccionado)) {
            Funcionalidades.eventoSeleccionado.getListaSolicitantes().add(LoginActivity.usuario);
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.messaje_request_sent),getApplicationContext());
            toolbar.inflateMenu(R.menu.event_unsubscribe_menu);
            tabLayout.getTabAt(2).select();
        }
        Funcionalidades.mostrarMensaje("Solicitud Enviada",this);
    }

    private void eliminarSolicitud() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo actualizar la lista de participantes o solicitudes del evento
        toolbar.inflateMenu(R.menu.event_subscribe_menu);
        Funcionalidades.mostrarMensaje("Solicitud Eliminada",this);
    }

    private void eliminarEvento() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo eliminar el evento y volver a la pantalla anterior
        Funcionalidades.eliminarEvento(Funcionalidades.eventoSeleccionado,this);
        Funcionalidades.mostrarMensaje("Evento Eliminado",this);
    }
}
