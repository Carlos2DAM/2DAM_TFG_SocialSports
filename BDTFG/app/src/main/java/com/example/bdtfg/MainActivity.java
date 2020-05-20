package com.example.bdtfg;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnRegistrar = findViewById(R.id.btnRegistrar);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etContrasenia = findViewById(R.id.etContrasenia);
        final RadioButton rbOrganizador = findViewById(R.id.rbOrganizador);
        final RadioButton rbParticipante = findViewById(R.id.rbParticipante);

        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = etEmail.getText().toString();
                String contrasenia = etContrasenia.getText().toString();
                String organizador = rbOrganizador.isChecked() ? "Organizador" : "Participante";
                String participante = rbParticipante.isChecked() ? "Participante" : "Organizador";
                new datos(MainActivity.this).execute(email,contrasenia,organizador, participante);
            }
        });
    }

    public static class datos extends AsyncTask <String, Void, String>
    {
        private WeakReference<Context> contexto;

        public datos (Context contexto)
        {
            this.contexto = new WeakReference<>(contexto);
        }

        public String doInBackground(String... params)
        {
            String registrar_url = "http://bdtfg.eu5.org/Registro.php";
            String resultado;

            try
            {
                URL url = new URL(registrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));

                String email = params[0];
                String contrasenia = params[1];
                String reputacion = params[2];

                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("contrasenia", "UTF-8") + "=" + URLEncoder.encode(contrasenia, "UTF-8")
                        + "&" + URLEncoder.encode("reputacion", "UTF-8") + "=" + URLEncoder.encode(reputacion, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder stringBuilder = new StringBuilder();

                String linea;
                while ((linea = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(linea);
                }

                resultado = stringBuilder.toString();

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            }
            catch (MalformedURLException e)
            {
                Log.d("BDTFG","Se ha utilizado una URL con formato incorrecto");
                resultado = "Se ha producido un error.";
            }
            catch (IOException  e)
            {
                Log.d("BDTFG","Error inesperado; posibles problemas de conexión de red.");
                resultado = "Se ha producido un error. Compruebe su conexión a internet";
            }
            return resultado;
        }

        protected void onPostExecute(String resultado)
        {
            Toast.makeText(contexto.get(), resultado, Toast.LENGTH_LONG).show();
        }

    }
}