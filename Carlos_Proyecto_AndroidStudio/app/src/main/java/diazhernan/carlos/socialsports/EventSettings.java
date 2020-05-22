package diazhernan.carlos.socialsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import diazhernan.carlos.socialsports.Clases.Evento;

public class EventSettings extends AppCompatActivity {

    private Evento evento;
    private Toolbar toolbar;
    private Button buttonSave;

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
    }
}
