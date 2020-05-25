package diazhernan.carlos.socialsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import com.google.android.material.tabs.TabLayout;

import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsParticipants;
import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsRequests;
import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsSettings;

public class EventSettings extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Button buttonSave;
    private Button buttonDelete;
    private Button buttonFinalize;
    private Button buttonSubscribe;
    private Button buttonUnsubscribe;
    private TableRow rowDeleteFinalize;
    private EventSettingsSettings eventSettingsSettings;
    private EventSettingsParticipants eventSettingsParticipants;
    private EventSettingsRequests eventSettingsRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings);
        tabLayout = findViewById(R.id.tabsEventSettings);
        toolbar = findViewById(R.id.toolbarEventSettings);
        toolbar.setTitle(Funcionalidades.eventoSeleccionado.getDeporte()+" - "+Funcionalidades.eventoSeleccionado.getLocalidad());
        rowDeleteFinalize = findViewById(R.id.rowEventSettings);
        buttonSave = findViewById(R.id.buttonEventSettingsSave);
        buttonDelete = findViewById(R.id.buttonEventSettingsDelete);
        buttonFinalize = findViewById(R.id.buttonEventSettingsFinalize);
        buttonSubscribe = findViewById(R.id.buttonEventSettingsSubscribe);
        buttonUnsubscribe = findViewById(R.id.buttonEventSettingsUnSubscribe);
        buttonSubscribe.setVisibility(View.GONE);
        buttonUnsubscribe.setVisibility(View.GONE);
        if (!Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
            rowDeleteFinalize.setVisibility(View.GONE);
            buttonSave.setVisibility(View.GONE);
            if (!Funcionalidades.suscrito(Funcionalidades.eventoSeleccionado,this)
                    && !Funcionalidades.participante(Funcionalidades.eventoSeleccionado,this)
                    && !Funcionalidades.baneado(Funcionalidades.eventoSeleccionado,this))
                buttonSubscribe.setVisibility(View.VISIBLE);
            else if (!Funcionalidades.baneado(Funcionalidades.eventoSeleccionado,this))
                buttonUnsubscribe.setVisibility(View.VISIBLE);
        }
        eventSettingsSettings = new EventSettingsSettings();
        eventSettingsParticipants = new EventSettingsParticipants();
        eventSettingsRequests = new EventSettingsRequests();
        Funcionalidades.showSelectedFragment(R.id.containerEventSettings,getSupportFragmentManager(),eventSettingsParticipants);
        Funcionalidades.showSelectedFragment(R.id.containerEventSettings,getSupportFragmentManager(),eventSettingsRequests);
        Funcionalidades.showSelectedFragment(R.id.containerEventSettings,getSupportFragmentManager(),eventSettingsSettings);

        buttonSave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getApplication());
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                v.setFocusableInTouchMode(false);
                guardarCambiosEvento();
            }
        });
        buttonDelete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getApplication());
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                v.setFocusableInTouchMode(false);
                eliminarEvento();
            }
        });
        buttonFinalize.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getApplication());
            }
        });
        buttonFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                v.setFocusableInTouchMode(false);
                //TODO mostrar mensaje de confirmación y en caso afirmativo finalizar evento y volver a la pantalla anterior.
                Funcionalidades.eventoSeleccionado.setTerminado(true);
            }
        });
        buttonSubscribe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getApplication());
            }
        });
        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                v.setFocusableInTouchMode(false);
                mandarSolicitud();
                //TODO hacer algo mas
            }
        });
        buttonUnsubscribe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getApplication());
            }
        });
        buttonUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                v.setFocusableInTouchMode(false);
                eliminarSolicitud();
                //TODO hacer algo mas
            }
        });
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
    }

    private void guardarCambiosEvento() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo actualizar datos del evento y guardarlos en la BBDD.
    }

    private void mandarSolicitud() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo insertar en BBDD del evento la nueva solicitud
        if (!Funcionalidades.suscrito(Funcionalidades.eventoSeleccionado,this)) {
            Funcionalidades.eventoSeleccionado.getListaSolicitantes().add(LoginActivity.usuario);
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.messaje_request_sent),getApplicationContext());
            buttonSubscribe.setVisibility(View.GONE);
            tabLayout.getTabAt(2).select();
        }
    }

    private void eliminarSolicitud() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo actualizar la lista de participantes o solicitudes del evento
    }

    private void eliminarEvento() {
        //TODO mostrar mensaje de confirmación y en caso afirmativo eliminar el evento y volver a la pantalla anterior
        Funcionalidades.eliminarEvento(Funcionalidades.eventoSeleccionado,this);
    }
}
