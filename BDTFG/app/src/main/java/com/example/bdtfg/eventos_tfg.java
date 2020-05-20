package com.example.bdtfg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
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

public class eventos_tfg extends AppCompatActivity
{

    EditText fech;
    EditText hora;
    Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_tfg);

        final Button crearEvento = findViewById(R.id.btnEvento);
        final EditText etEmailEvento = findViewById(R.id.etEmailEvento);
        final EditText depor = findViewById(R.id.etDeporte);
        final EditText local = findViewById(R.id.etLocalidad);
        final EditText direc = findViewById(R.id.etDireccion);
        fech = findViewById(R.id.etFechaEvento);
        hora = findViewById(R.id.etHora);
        final EditText max = findViewById(R.id.etNumParticipantes);
        final RadioButton instal = findViewById(R.id.rbSi);
        final EditText cost = findViewById(R.id.etCosteEvento);
        final EditText prec = findViewById(R.id.etPrecio);
        final EditText coment = findViewById(R.id.etComentarios);
        final RadioButton term = findViewById(R.id.rbTerminado);

        final EditText edadMin = findViewById(R.id.etEdadMinima);
        final EditText edadMax = findViewById(R.id.etEdadMaxima);
        final RadioButton genero = findViewById(R.id.rbMale);
        final EditText repu = findViewById(R.id.etReputacionNecesaria);


        fech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(eventos_tfg.this, date, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(eventos_tfg.this, hour, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(eventos_tfg.this)).show();
            }
        });

        crearEvento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String organizadorEvento = etEmailEvento.getText().toString();
                String deporte = depor.getText().toString();
                String localidad = local.getText().toString();
                String direccion = direc.getText().toString();
                String fechaEvento = fech.getText().toString();
                String horaEvento = hora.getText().toString();
                String maximoParticipantes = max.getText().toString();
                String instalacionesReservadas = instal.isChecked() ? "Si" : "No";
                String costeEvento = cost.getText().toString();
                String precioPorParticipante = prec.getText().toString();
                String comentarios = coment.getText().toString();
                String terminado = term.isChecked() ? "Si" : "No";

                String edadMinima = edadMin.getText().toString();
                String edadMaxima = edadMax.getText().toString();
                String requisitoDeGenero = genero.isChecked() ? "male" : "female";
                String reputacionNecesaria = repu.getText().toString();

                new datosEvento(eventos_tfg.this).execute(organizadorEvento, deporte, localidad, direccion, fechaEvento, horaEvento,
                        maximoParticipantes,instalacionesReservadas, costeEvento, precioPorParticipante,comentarios, terminado);

                new datosRequisitos(eventos_tfg.this).execute(edadMinima,edadMaxima,requisitoDeGenero,reputacionNecesaria);
            }
        });
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

            fech.setText(sdf.format(calendario.getTime()));
        }
    };

    TimePickerDialog.OnTimeSetListener hour = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendario.set(Calendar.MINUTE, minute);
            actualizarInput();
        }

        private void actualizarInput() {
            String formatoDeHora = "hh:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(formatoDeHora);

            hora.setText(sdf.format(calendario.getTime()));
        }

    };

    public static class datosEvento extends AsyncTask<String, Void, String>
    {
        private WeakReference<Context> contexto;

        public datosEvento(Context contexto)
        {
            this.contexto = new WeakReference<>(contexto);
        }

        public String doInBackground(String... params)
        {
            String registrar_url = "http://bdtfg.eu5.org/registroEvento.php";
            String resultado;

            try
            {
                URL url = new URL(registrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));

                String organizadorEvento = params[0];
                String deporte = params[1];
                String localidad = params[2];
                String direccion = params[3];
                String fechaEvento = params[4];
                String horaEvento = params[5];
                String maximoParticipantes = params[6];
                String instalacionesReservadas = params[7];
                String costeEvento = params[8];
                String precioPorParticipante = params[9];
                String comentarios = params[10];
                String terminado = params[11];

                String data = URLEncoder.encode("organizadorEvento", "UTF-8") + "=" + URLEncoder.encode(organizadorEvento, "UTF-8")
                        + "&" + URLEncoder.encode("deporte", "UTF-8") + "=" + URLEncoder.encode(deporte, "UTF-8")
                        + "&" + URLEncoder.encode("localidad", "UTF-8") + "=" + URLEncoder.encode(localidad, "UTF-8")
                        + "&" + URLEncoder.encode("direccion", "UTF-8") + "=" + URLEncoder.encode(direccion, "UTF-8")
                        + "&" + URLEncoder.encode("fechaEvento", "UTF-8") + "=" + URLEncoder.encode(fechaEvento, "UTF-8")
                        + "&" + URLEncoder.encode("horaEvento", "UTF-8") + "=" + URLEncoder.encode(horaEvento, "UTF-8")
                        + "&" + URLEncoder.encode("maximoParticipantes", "UTF-8") + "=" + URLEncoder.encode(maximoParticipantes, "UTF-8")
                        + "&" + URLEncoder.encode("instalacionesReservadas", "UTF-8") + "=" + URLEncoder.encode(instalacionesReservadas, "UTF-8")
                        + "&" + URLEncoder.encode("costeEvento", "UTF-8") + "=" + URLEncoder.encode(costeEvento, "UTF-8")
                        + "&" + URLEncoder.encode("precioPorParticipante", "UTF-8") + "=" + URLEncoder.encode(precioPorParticipante, "UTF-8")
                        + "&" + URLEncoder.encode("comentarios", "UTF-8") + "=" + URLEncoder.encode(comentarios, "UTF-8")
                        + "&" + URLEncoder.encode("terminado", "UTF-8") + "=" + URLEncoder.encode(terminado, "UTF-8");

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
            catch (IOException e)
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

    public static class datosRequisitos extends AsyncTask<String, Void, String> {
        private WeakReference<Context> contexto;

        public datosRequisitos(Context contexto) {
            this.contexto = new WeakReference<>(contexto);
        }

        public String doInBackground(String... params) {
            String registrar_url = "http://bdtfg.eu5.org/registroRequisitos.php";
            String resultado;

            try {
                URL url = new URL(registrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));

                String edadMinima = params[0];
                String edadMaxima = params[1];
                String requisitoDeGenero = params[2];
                String reputacionNecesaria = params[3];

                String data = URLEncoder.encode("edadMinima", "UTF-8") + "=" + URLEncoder.encode(edadMinima, "UTF-8")
                        + "&" + URLEncoder.encode("edadMaxima", "UTF-8") + "=" + URLEncoder.encode(edadMaxima, "UTF-8")
                        + "&" + URLEncoder.encode("requisitoDeGenero", "UTF-8") + "=" + URLEncoder.encode(requisitoDeGenero, "UTF-8")
                        + "&" + URLEncoder.encode("reputacionNecesaria", "UTF-8") + "=" + URLEncoder.encode(reputacionNecesaria, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder stringBuilder = new StringBuilder();

                String linea;
                while ((linea = bufferedReader.readLine()) != null) {
                    stringBuilder.append(linea);
                }

                resultado = stringBuilder.toString();

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                Log.d("BDTFG", "Se ha utilizado una URL con formato incorrecto");
                resultado = "Se ha producido un error.";
            } catch (IOException e) {
                Log.d("BDTFG", "Error inesperado; posibles problemas de conexión de red.");
                resultado = "Se ha producido un error. Compruebe su conexión a internet";
            }
            return resultado;
        }

        protected void onPostExecute(String resultado) {
            Toast toast = Toast.makeText(contexto.get(), resultado, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }
}
