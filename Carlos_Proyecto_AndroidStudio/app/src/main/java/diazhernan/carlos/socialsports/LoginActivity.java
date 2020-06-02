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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import diazhernan.carlos.socialsports.Clases.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private ProgressBar loadingProgressBar;
    public static Usuario usuario = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        loadingProgressBar = findViewById(R.id.loading);

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                iniciarLogueo();
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
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                iniciarLogueo();
            }
        });

        registerButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button) v, getApplication());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
                Funcionalidades.esconderTeclado(getSystemService(INPUT_METHOD_SERVICE),v);
                iniciarRegistro();
            }
        });
    }

    private void iniciarLogueo() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        if (comprobarDatosLoginCorrectos(getResources().getString(R.string.action_sign_in_short))) {
            usuario = Funcionalidades.obtenerUsusarioBBDD(emailEditText.getText().toString(),passwordEditText.getText().toString());
            if (usuario != null)
                cargarAplicacionUsuario();
            else {
                Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_datos_incorrectos), this);
                limpiarCajas();
            }
        }
        loadingProgressBar.setVisibility(View.GONE);
    }

    private void iniciarRegistro() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        /*if (comprobarDatosLoginCorrectos(getResources().getString(R.string.action_register))) {
            if (Funcionalidades.comprobarExisteUsuario(emailEditText.getText().toString())) {
                Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_usuario_existe), this);
                limpiarCajas();
            }
            else {
                usuario = new Usuario(emailEditText.getText().toString(),passwordEditText.getText().toString(),
                        "","","","",
                        null,new Date(),4f,
                        4f,"",true,new ArrayList<Usuario>(),
                        new ArrayList<Usuario>());
                if (Funcionalidades.crearUsuarioBBDD(usuario)) {
                    Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_creado_nuevo_usuario),this);
                    cargarAplicacionUsuario();
                }
                else {
                    usuario = null;
                    Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_error_nuevo_usuario),this);
                }
            }
        }*/
        if (comprobarDatosLoginCorrectos(getResources().getString(R.string.action_register))) {
            RETROFIT retrofit = new RETROFIT();
            APIService service = retrofit.getAPIService();

            Map<String, String> mapa = new HashMap<>();
            mapa.put("correo", emailEditText.getText().toString());
            mapa.put("contrasena", passwordEditText.getText().toString());

            Call<ResponseBody> registro = service.postRegistro(mapa);

            registro.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int code = response.code();

                    if (code == 201) {
                        usuario = new Usuario();
                        usuario.setEmailUsuario(emailEditText.getText().toString());
                        usuario.setPaswordUsuario(emailEditText.getText().toString());
                        Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_creado_nuevo_usuario), getApplicationContext());
                        cargarAplicacionUsuario();
                    } else if (code == 409) {
                        Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_usuario_existe), getApplicationContext());
                        limpiarCajas();
                    } else {
                        Funcionalidades.mostrarMensaje(getResources().getString(R.string.login_error_nuevo_usuario), getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
        loadingProgressBar.setVisibility(View.GONE);
    }

    private boolean comprobarDatosLoginCorrectos(String botonPulsado){
        if (emailEditText.getText().toString().equals("")) {
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.usuario_incompleto)+" "+botonPulsado,this);
            return false;
        }
        else if (passwordEditText.getText().toString().equals("")) {
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.password_incompleto)+" "+botonPulsado, this);
            return false;
        }
        return true;
    }

    private void cargarAplicacionUsuario(){
        //usuario.setListaAmigos(Funcionalidades.cargarAmigos(usuario.getEmailUsuario())); //TODO Borrar esto seria redundante.
        //Funcionalidades.ponerUsuarioOnline(usuario,true);  TODO Funcionalidad desactivada.
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }

    private void limpiarCajas() {
        emailEditText.setText("");
        passwordEditText.setText("");
    }
}
