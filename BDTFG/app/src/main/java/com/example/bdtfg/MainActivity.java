package com.example.bdtfg;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity
{

    ImageView Foto;
    EditText etFecha;
    Calendar calendario = Calendar.getInstance();
    private Button evento;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnRegistrar = findViewById(R.id.btnRegistrar);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etContrasenia = findViewById(R.id.etContrasenia);
        final EditText etNombre = findViewById(R.id.etNombre);
        final EditText etApellidos = findViewById(R.id.etApellidos);
        final RadioButton Masculino = findViewById(R.id.rbMasculino);
        final EditText etDireccion = findViewById(R.id.etDireccion);
        etFecha = findViewById(R.id.etFecha);
        Foto = findViewById(R.id.ivPerfil);
        final EditText Participante = findViewById(R.id.etParticipante);
        final EditText Organizador = findViewById(R.id.etUsuario);
        final Button btnSeleccionar = findViewById(R.id.btnSeleccionar);

        evento = findViewById(R.id.eventos);

        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirEventos();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String emailUsuario = etEmail.getText().toString();
                String passwordUsuario = etContrasenia.getText().toString();
                String nombreUsuario = etNombre.getText().toString();
                String apellidosUsuario = etApellidos.getText().toString();
                String masculino = Masculino.isChecked() ? "Masculino" : "Femenino";
                String direccionUsuario = etDireccion.getText().toString();
                String fechaNacimientoUsuario = etFecha.getText().toString();
                String reputacionParticipanteUsuario = Participante.getText().toString();
                String reputacionOrganizadorUsuario = Organizador.getText().toString();
                String fotoPerfilUsuario = Foto.toString();
                new datos(MainActivity.this).execute(emailUsuario, passwordUsuario, nombreUsuario, apellidosUsuario, masculino, direccionUsuario,
                        fechaNacimientoUsuario,reputacionParticipanteUsuario, reputacionOrganizadorUsuario, fotoPerfilUsuario);
            }
        });

        btnSeleccionar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cargarImagen();
            }

            });

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(MainActivity.this, date, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    public void abrirEventos()
    {
        Intent intent = new Intent(this, eventos_tfg.class);
        startActivity(intent);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }

        private void actualizarInput() {
            String formatoDeFecha = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha);

            etFecha.setText(sdf.format(calendario.getTime()));
        }

    };

    private void cargarImagen ()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK)
        {
            Uri path = data.getData();
            Foto.setImageURI(path);
        }
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

                String emailUsuario = params[0];
                String passwordUsuario = params[1];
                String nombreUsuario = params[2];
                String apellidosUsuario = params[3];
                String generoUsuario = params[4];
                String direccionUsuario = params[5];
                String fechaNacimientoUsuario = params[6];
                String reputacionParticipanteUsuario = params[7];
                String reputacionOrganizadorUsuario = params[8];
                String fotoPerfilUsuario = params[9];

                String data = URLEncoder.encode("emailUsuario", "UTF-8") + "=" + URLEncoder.encode(emailUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("passwordUsuario", "UTF-8") + "=" + URLEncoder.encode(passwordUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("nombreUsuario", "UTF-8") + "=" + URLEncoder.encode(nombreUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("apellidosUsuario", "UTF-8") + "=" + URLEncoder.encode(apellidosUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("generoUsuario", "UTF-8") + "=" + URLEncoder.encode(generoUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("direccionUsuario", "UTF-8") + "=" + URLEncoder.encode(direccionUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("fechaNacimientoUsuario", "UTF-8") + "=" + URLEncoder.encode(fechaNacimientoUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("reputacionParticipanteUsuario", "UTF-8") + "=" + URLEncoder.encode(reputacionParticipanteUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("reputacionOrganizadorUsuario", "UTF-8") + "=" + URLEncoder.encode(reputacionOrganizadorUsuario, "UTF-8")
                        + "&" + URLEncoder.encode("fotoPerfilUsuario", "UTF-8") + "=" + URLEncoder.encode(fotoPerfilUsuario, "UTF-8");

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