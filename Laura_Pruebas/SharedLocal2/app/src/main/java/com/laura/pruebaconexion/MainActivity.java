package com.laura.pruebaconexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnDatos, btnRegistro;
    private EditText etIdUsuario, etUsername, etContrasena, etCorreo;
    private TextView tvMensaje, pantallaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDatos = findViewById(R.id.btnDatos);
        btnRegistro = findViewById(R.id.btnRegistro);
        etIdUsuario = findViewById(R.id.txtIdUsuario);
        tvMensaje = findViewById(R.id.tvMensaje);
        etUsername = findViewById(R.id.etUsername);
        etContrasena = findViewById(R.id.editText2);
        etCorreo = findViewById(R.id.editText3);
        pantallaRegistro = findViewById(R.id.pantallaRegistro);

        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int idUsuario = Integer.parseInt(etIdUsuario.getText().toString());
                    getResponse(idUsuario);
                }catch(Exception e){
                    e.printStackTrace();                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nombre = etUsername.getText().toString();
                    String contrasena = etContrasena.getText().toString();
                    String correo = etCorreo.getText().toString();
                    Usuario usuario = new Usuario(nombre, contrasena, correo);
                    post(usuario);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void post(Usuario usuario){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.152:8080/EjemploRest/rest/prueba/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        service.post(usuario).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    pantallaRegistro.setText("Registro OK");
                }else{
                    pantallaRegistro.setText("Error registro");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pantallaRegistro.setText(t.getMessage());
            }
        });
        }

    public void getResponse(int idUsuario) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.152:8080/EjemploRest/rest/prueba/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        service.getRespuesta(idUsuario).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    InputStream is = response.body().byteStream();
                    int n;
                    String s="";
                    try {
                        while((n = is.read()) != -1) {
                            s += (char) n;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showResponse(s);
                }else{
                    showResponse(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showResponse(t.getMessage());
            }
        });
    }

    public void showResponse(String response) {
        tvMensaje.setText(response);
    }
}
