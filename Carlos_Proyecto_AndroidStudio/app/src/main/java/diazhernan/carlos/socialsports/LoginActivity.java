package diazhernan.carlos.socialsports;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

import diazhernan.carlos.socialsports.Clases.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    public static Usuario usuario = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText) v, getApplication());
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText) v, getApplication());
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (comprobarDatosLoginCorrectos())
                    cargarAplicacionUsuario();
                loadingProgressBar.setVisibility(View.GONE);
                return true;
            }
        });

        loginButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button) v, getApplication());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setFocusableInTouchMode(true);
                loginButton.requestFocus();
                loginButton.setFocusableInTouchMode(false);
                if (comprobarDatosLoginCorrectos())
                    cargarAplicacionUsuario();
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean comprobarDatosLoginCorrectos(){
        loadingProgressBar.setVisibility(View.VISIBLE);

        usuario = new Usuario(21,"carlos@mail.es","carlos",
                "Carlos","Diaz Hernan","Male",
                "calle Oviedo 37", new Date(83,11,21),
                new Date(),4.5f, 5f,
                "carlos.jpg",null,null);

        return true;
    }

    private void cargarAplicacionUsuario(){
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }
}
