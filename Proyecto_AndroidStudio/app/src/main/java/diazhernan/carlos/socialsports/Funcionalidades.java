package diazhernan.carlos.socialsports;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.FiltroDeEvento;
import diazhernan.carlos.socialsports.Clases.PuntuacionEvento;
import diazhernan.carlos.socialsports.Clases.PuntuacionParticipante;
import diazhernan.carlos.socialsports.Clases.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Funcionalidades extends AppCompatActivity {

    public static Evento eventoSeleccionado;

    public static void cambiarColoresTexto(EditText et, Application application){
        if (et.isFocused()) {
            et.setTextColor(application.getResources().getColor(R.color.colorAccent));
            et.getBackground().setTint(application.getResources().getColor(R.color.colorAccent));
        }
        else {
            et.setTextColor(application.getResources().getColor(R.color.colorElements));
            et.getBackground().setTint(application.getResources().getColor(R.color.colorElements));
        }
    }

    public static void cambiarColoresBoton(Button button, Application application){
        if (button.isFocused()) {
            button.setTextColor(application.getResources().getColor(R.color.colorAccent));
            button.setBackground(application.getResources().getDrawable(R.drawable.boton1_selected));
        }
        else {
            button.setTextColor(application.getResources().getColor(R.color.colorElements));
            button.setBackground(application.getResources().getDrawable(R.drawable.boton1));
        }
    }

    public static void cambiarColoresBotonSimple(Button button, Application application){
        if (button.isFocused())
            button.setTextColor(application.getResources().getColor(R.color.colorAccent));
        else
            button.setTextColor(application.getResources().getColor(R.color.colorElements));
    }

    public static void mostrarMensaje(String mensaje, Context context){
        Toast toast = Toast.makeText(context,mensaje,Toast.LENGTH_LONG);
        toast.show();
    }

    //Mostrar un fragment determinado en un elemento de tipo "R.id.container"(int) determindo.
    public static void showSelectedFragment(int container, FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public static void esconderTeclado(Activity a, Context c, View v) {
        InputMethodManager imm = (InputMethodManager)a.getSystemService(c.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void esconderTeclado(Object object, View v) {
        InputMethodManager imm = (InputMethodManager)object;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String dateToString(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("E dd MMM yyyy");
        if (fecha!=null)
            return formato.format(fecha);
        return "";
    }

    public static String dateToString2(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fecha!=null)
            return formato.format(fecha);
        return "";
    }

    public static Date StringToDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fecha!=null && !fecha.isEmpty()) {
            try {
                return formato.parse(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String dateToStringLargo(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        if (fecha!=null)
            return formato.format(fecha);
        return "";
    }

    public static ArrayList<Evento> eventosPendientes(ArrayList<Evento> arrayList) {
        ArrayList<Evento> pendientes = new ArrayList<>();
        if (arrayList != null) {
            for (Evento evento : arrayList) {
                if (!evento.isTerminado()) {
                    if (evento.getFechaEvento() == null || evento.getFechaEvento().after(new Date()))
                        pendientes.add(evento);
                    else {
                        evento.setTerminado(true);
                        actualizarTerminarEvento(evento.getIdEvento(), true);
                    }
                }
            }
        }
        return pendientes;
    }

    public static ArrayList<Evento> eventosFinalizados(ArrayList<Evento> arrayList) {
        ArrayList<Evento> finalizados = new ArrayList<>();
        if (arrayList != null) {
            for (Evento evento : arrayList) {
                if (evento.isTerminado())
                    finalizados.add(evento);
                else if (evento.getFechaEvento() != null && evento.getFechaEvento().before(new Date())) {
                    finalizados.add(evento);
                    evento.setTerminado(true);
                    actualizarTerminarEvento(evento.getIdEvento(), true);
                }
            }
        }
        return finalizados;
    }

    public static boolean eresSolicitante(Evento ev) {
        for (Usuario usuario: ev.getListaSolicitantes()) {
            if (usuario.getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                return true;
        }
        return false;
    }

    public static boolean eresParticipante(Evento ev) {
        for (Usuario usuario: ev.getListaParticipantes()) {
            if (usuario.getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                return true;
        }
        return false;
    }

    public static boolean eresOrganizador(Evento ev) {
        return LoginActivity.usuario.getEmailUsuario().equals(ev.getOrganizadorEvento().getEmailUsuario());
    }

    public static boolean soyYo(Usuario usuario) {
        return LoginActivity.usuario.getEmailUsuario().equals(usuario.getEmailUsuario());
    }

    public static int calcularEdad(Date fecha) {
        if (fecha==null)
            return 0;
        return (int) (((new Date().getTime() - fecha.getTime()) / 86400000) / 365);
    }

    public static boolean estasBaneado(Evento ev,Context c) {
        if(ev.getListaDescartados() != null) {
            for (Usuario usuario : ev.getListaDescartados()) {
                if (usuario.getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
                    mostrarMensaje(c.getResources().getString(R.string.has_been_baned), c);
                    return true;
                }
            }
        }
        return false;
    }
    /*
    public static ArrayList<Evento> buscarEventosFiltrados(Usuario usuario, FiltroDeEvento filtro) {

        ArrayList<Evento> listaFiltrada = eventosFuturosVacantes();

        int edad = calcularEdad(LoginActivity.usuario.getFechaNacimientoUsuario());

        if (!listaFiltrada.isEmpty())
            listaFiltrada.removeAll(eventosNoCumploRequisitoEdadMinima(edad));

        if (!listaFiltrada.isEmpty())
            listaFiltrada.removeAll(eventosNoCumploRequisitoEdadMaxima(edad));

        if (!listaFiltrada.isEmpty())
            listaFiltrada.removeAll(eventosNoCumploRequisitoDeGenero(LoginActivity.usuario.getGeneroUsuario()));

        if (!listaFiltrada.isEmpty())
            listaFiltrada.removeAll(eventosNoCumploRequisitoReputacion(LoginActivity.usuario.getReputacionParticipanteUsuario()));

        if (!filtro.getSport().equals("") && !listaFiltrada.isEmpty()) {
            listaFiltrada.retainAll(filtrarPorDeporte(filtro.getSport()));
        }
        if (!filtro.getLocation().equals("") && !listaFiltrada.isEmpty()) {
            listaFiltrada.retainAll(filtrarPorLocalidad(filtro.getLocation()));
        }
        if (filtro.getFechaDelEvento() != null && !listaFiltrada.isEmpty()) {
            listaFiltrada.retainAll(filtrarPorFecha(filtro.getFechaDelEvento()));
        }
        if (!filtro.getHoraDelEvento().equals("") && !listaFiltrada.isEmpty()) {
            listaFiltrada.retainAll(filtrarPorHora(filtro.getHoraDelEvento()));
        }
        if (filtro.getReputation() > 0 && !listaFiltrada.isEmpty()) {
            listaFiltrada.retainAll(filtrarPorReputacion(filtro.getReputation()));
        }
        if (filtro.isReserved() && !listaFiltrada.isEmpty()) {
            listaFiltrada.retainAll(filtrarPorReserva(true));
        }
        listaFiltrada = eventosPendientes(listaFiltrada);

        return listaFiltrada;
    }*/

    public static void actualizarTerminarEvento(String idEvento, boolean terminado) {
        RETROFIT retrofit = new RETROFIT();
        retrofit.getAPIService().actualizarTerminarEvento("Bearer " + LoginActivity.token, idEvento, terminado).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void bloquearUsuarioPermanentemente(Usuario usuario) {
        if (!LoginActivity.usuario.getListaBloqueados().contains(usuario)) {
            LoginActivity.usuario.getListaBloqueados().add(usuario);
            LoginActivity.usuario.getListaAmigos().remove(usuario);
        }
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.bloquearUsuario("Bearer " + LoginActivity.token, LoginActivity.usuario.getEmailUsuario(), usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static boolean usuarioBloqueadoPermanentemente(String emailUsuario, String emailOrganizador, Context c) {
        //TODO Consultar si un usuario esta en la listaBloqueados de un usuarioOrganizador. devolver true o false.
        /**
         *  if (    serverUsuarioBloqueado(emailUsuario, emailOrganizador)     ) {
         *      mostrarMensaje(c.getResources().getString(R.string.has_been_baned),c);
         *      return true;
         *   }
         */
        return false;
    }

    public static void eliminarAmigo(Usuario usuario) {
        if (LoginActivity.usuario.getListaAmigos().contains(usuario))
            LoginActivity.usuario.getListaAmigos().remove(usuario);

        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.eliminarAmigo("Bearer " + LoginActivity.token,
                LoginActivity.usuario.getEmailUsuario(),
                usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void eliminarBloqueoPermanentemente(Usuario usuario) {
        if (LoginActivity.usuario.getListaBloqueados().contains(usuario))
            LoginActivity.usuario.getListaBloqueados().remove(usuario);

        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.quitarBloqueo("Bearer " + LoginActivity.token,
                LoginActivity.usuario.getEmailUsuario(),usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void insertarAmigo(Usuario usuario) {
        if (!LoginActivity.usuario.getListaAmigos().contains(usuario)) {
            LoginActivity.usuario.getListaAmigos().add(usuario);
            LoginActivity.usuario.getListaBloqueados().remove(usuario);
        }

        RETROFIT retrofit = new RETROFIT();
        retrofit.getAPIService().agregarAmigo("Bearer " + LoginActivity.token,
                LoginActivity.usuario.getEmailUsuario(),
                usuario.getEmailUsuario())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public static void actualizarFechaEvento(String idEvento, Date fecha) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarFechaEvento("Bearer " + LoginActivity.token, idEvento, dateToString2(fecha)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void actualizarHoraEvento(String idEvento, String hora) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarHoraEvento("Bearer " + LoginActivity.token, idEvento, hora).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void actualizarDireccionEvento(String idEvento, String direccion) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarDireccionEvento("Bearer " + LoginActivity.token, idEvento, direccion).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void actualizarMaxParticipantesEvento(String idEvento, int maxParticipants) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarMaxParticipantesEvento("Bearer " + LoginActivity.token, idEvento, maxParticipants).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void eliminarParticipante(Evento evento, Usuario usuario) {
        for (int i = 0; i < evento.getListaParticipantes().size(); i++) {
            if (evento.getListaParticipantes().get(i).getEmailUsuario().equals(usuario.getEmailUsuario())) {
                evento.getListaParticipantes().remove(i);
                break;
            }
        }
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.eliminarParticipante("Bearer " + LoginActivity.token, evento.getIdEvento(), usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void insertarParticipante(Evento evento,Usuario usuario) {
        if (!evento.getListaParticipantes().contains(usuario)) {
            evento.getListaParticipantes().add(usuario);
            evento.getListaSolicitantes().remove(usuario);
        }
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.insertarParticipante("Bearer " + LoginActivity.token, evento.getIdEvento(), usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarReservaEvento(String idEvento, boolean reserva) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarReserva("Bearer " + LoginActivity.token, idEvento, reserva).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarCosteEvento(String idEvento, float coste) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarCoste("Bearer " + LoginActivity.token, idEvento, coste).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarPrecioEvento(String idEvento, float precio) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarPrecio("Bearer " + LoginActivity.token, idEvento, precio).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarComentariosEvento(String idEvento, String comment) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarComentarios("Bearer " + LoginActivity.token, idEvento, comment).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarEdadMinEvento(String idEvento, int edad) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarEdadMinima("Bearer " + LoginActivity.token, idEvento, edad).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarEdadMaxEvento(String idEvento, int edad) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarEdadMaxima("Bearer " + LoginActivity.token, idEvento, edad).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarGeneroEvento(String idEvento, String genero) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarGenero("Bearer " + LoginActivity.token, idEvento, genero).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void actualizarReputacionEvento(String idEvento, float reputacion) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.actualizarReputacion("Bearer " + LoginActivity.token, idEvento, reputacion).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void insertarSolicitante(Evento evento,Usuario usuario) {
        if (!evento.getListaSolicitantes().contains(usuario)) {
            evento.getListaSolicitantes().add(usuario);
        }

        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.insertarSolicitante("Bearer " + LoginActivity.token,
                evento.getIdEvento(), usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public static void eliminarSolicitante(Evento evento,Usuario usuario) {
        for (int i = 0; i < evento.getListaSolicitantes().size(); i++) {
            if (evento.getListaSolicitantes().get(i).getEmailUsuario().equals(usuario.getEmailUsuario())) {
                evento.getListaSolicitantes().remove(i);
                break;
            }
        }
            RETROFIT retrofit = new RETROFIT();
            APIService service = retrofit.getAPIService();
            service.eliminarSolicitante("Bearer " + LoginActivity.token, evento.getIdEvento(), usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
    }

    public static void bloquearUsuarioAlEvento(Evento evento,Usuario usuario) {
        if (!evento.getListaDescartados().contains(usuario)) {
            evento.getListaDescartados().add(usuario);

            RETROFIT retrofit = new RETROFIT();
            APIService service = retrofit.getAPIService();
            service.bloquearSolicitud("Bearer " + LoginActivity.token, evento.getIdEvento(), usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }
}
