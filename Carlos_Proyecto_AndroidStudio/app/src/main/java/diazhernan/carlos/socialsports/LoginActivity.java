package diazhernan.carlos.socialsports;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

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
                cambiarColoresTexto((EditText) v);
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cambiarColoresTexto((EditText) v);
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
                cambiarColoresBoton((Button) v);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobarDatosLoginCorrectos())
                    cargarAplicacionUsuario();
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void cambiarColoresTexto(EditText et){
        if (et.isFocused()) {
            et.setTextColor(getResources().getColor(R.color.colorAccent));
            et.getBackground().setTint(getResources().getColor(R.color.colorAccent));
        }
        else {
            et.setTextColor(getResources().getColor(R.color.colorElements));
            et.getBackground().setTint(getResources().getColor(R.color.colorElements));
        }
    }

    private void cambiarColoresBoton(Button button){
        if (button.isFocused()) {
            button.setTextColor(getResources().getColor(R.color.colorAccent));
            button.setBackground(getResources().getDrawable(R.drawable.boton1_selected));
        }
        else {
            button.setTextColor(getResources().getColor(R.color.colorElements));
            button.setBackground(getResources().getDrawable(R.drawable.boton1));
        }
    }

    private boolean comprobarDatosLoginCorrectos(){
        loadingProgressBar.setVisibility(View.VISIBLE);
        return true;
    }

    private void cargarAplicacionUsuario(){
        loginButton.setFocusableInTouchMode(true);
        loginButton.requestFocus();
        loginButton.setFocusableInTouchMode(false);
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }

    public static void mostrarMensaje(String mensaje, Context context){
        Toast toast = Toast.makeText(context,mensaje,Toast.LENGTH_LONG);
        toast.show();
    }
}
