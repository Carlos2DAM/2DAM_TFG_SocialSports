package diazhernan.carlos.socialsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.fragments.eventsettings.EventSettingsSettings;

public class EventSettings extends AppCompatActivity {

    private Evento evento;
    private Toolbar toolbar;
    private Button buttonSave;
    private EventSettingsSettings eventSettingsSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings);
        toolbar = findViewById(R.id.toolbarEventSettings);
        evento = (Evento) getIntent().getSerializableExtra("eventoSet");
        toolbar.setTitle(evento.getDeporte()+" - "+evento.getLocalidad());
        buttonSave = findViewById(R.id.buttonEventSettingsSave);
        if (!evento.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
            buttonSave.setVisibility(View.GONE);
        eventSettingsSettings = new EventSettingsSettings(evento);
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
    }

    private void guardarCambiosEvento() {
        //TODO actualizar datos del evento y guardarlos en la BBDD.
    }
}
