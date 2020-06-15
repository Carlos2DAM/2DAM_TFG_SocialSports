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

////////////////////////------------------------------------------------------------------------------------------------------

    public static boolean guardarEvento(Evento evento) {
        //TODO Insertar un nuevo evento en la BBDD.
        //insertarEvento(evento);
        return true;
    }

    public static boolean enviarInvitaciones(Evento evento, ArrayList<String> listaInvitados) {
        //TODO Añadir al evento los participantes cuyos emails coinciden con la lista de emails "listaInvitados".
        // Devolverá true o false si pudo realizar la operacion o no.
        //return serverInvitarAmigos(evento.listaInvitados);
        return true;
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
        //TODO Consultar si nuestro usuario se encuentra en la lista de descartados del evento.
        /**
         *  if (serverIsUsuarioDescartado(evento, LoginActivity.usuario.getEmailUsuario())) {
         *      ev.getListaDescartados().add(LoginActivity.usuario);
         *      return true;
         *  }
         */
        return false;
    }

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
    }

    public static ArrayList<Evento> eventosFuturosVacantes() {
        /**  TODO Obtener todos los eventos que aún no han finalizado (evento.finalizado = 0) y que
         *        aún no están llenos (evento.maxParticipantes < evento.listaParticipantes.size()).
         *        No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
         */
        // return serverEventosFuturosVacantes();
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> eventosNoCumploRequisitoEdadMinima(int edad) {
        // TODO Obtener los eventos en los que el usuarios ¡¡¡NO!!! cumple la edad mínima.
        //  where (evento.requisito.edadMinima > edad)
        //  No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverEventosNoEdadMin(edad);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> eventosNoCumploRequisitoEdadMaxima(int edad) {
        /** TODO Obtener los eventos en los que el usuarios ¡¡¡NO!!! cumple la edad máxima
         *       (Cuidado, si la edad máxima es -1, no se requiere edad maxima en el evento).
         *       where (evento.requisito.edadMaxima != -1) and (evento.requisito.edadMaxima < edad)
         *       No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
         */
        // return serverEventosNoEdadMax(edad);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> eventosNoCumploRequisitoDeGenero(String genero) {
        /** TODO Obtener los eventos en los que el usuarios ¡¡¡NO!!! cumple el requisito de Género
         *       (Cuidado, si el evento tiene a NULL el requisito, es que no se necesita requisito)
         *       where (evento.requisito.genero != null) and (evento.requisito.genero != genero)
         *       No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
         */
        // return serverEventosNoGenero(genero);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> eventosNoCumploRequisitoReputacion(float reputacion) {
        // TODO Obtener los eventos en los que el usuarios tiene una reputación MENOR que la requerida por el evento.
        //  where (evento.requisito.reputacion > reputacion)
        //  No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverEventosNoReputacion(reputacion);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> filtrarPorDeporte(String deporte) {
        //  TODO Obtener una lista de eventos, filtrando por el deporte del evento.
        //   No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverFiltrarDeporte(deporte);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> filtrarPorLocalidad(String localidad) {
        //  TODO Obtener una lista de eventos, filtrando por la localidad del evento.
        //   No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverFiltrarLocalidad(localidad);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> filtrarPorFecha(Date Fecha) {
        //  TODO Obtener una lista de eventos, filtrando por la Fecha del evento.
        //   No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverFiltrarFecha(Fecha);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> filtrarPorHora(String Hora) {
        //  TODO Obtener una lista de eventos, filtrando por la Hora del evento.
        //   No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverFiltrarHora(Hora);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> filtrarPorReputacion(float Reputacion) {
        //  TODO Obtener una lista de eventos, filtrando por la Reputación del organizador (Reputación como organizador).
        //   No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverFiltrarReputacion(Reputacion);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> filtrarPorReserva(boolean Reserva) {
        //  TODO Obtener una lista de eventos, filtrandolos por el estado de la reserva.
        //   No puede devolver NULL, como mínimo inicializada a new ArrayList<Evento>()
        // return serverFiltrarReserva(Reserva);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static boolean actualizarNacimientoUsuario(String email, Date fecha) {
        // TODO Busca al usuario cuyo email paso por parámetro, y modifica su fecha de nacimiento.
        //  Devuelve true o false como resultado de la operación.
        //return serverActualizarNacimiento(email, fecha);
        return true;    //TODO Borrar esta línea provisional.
    }

    public static boolean actualizarPasswordUsuario(String email, String password) {
        // TODO Busca al usuario cuyo email paso por parámetro, y modifica su password.
        //  Devuelve true o false como resultado de la operación.
        //return serverActualizarPassword(email, password);
        return true;    //TODO Borrar esta línea provisional.
    }

    public static boolean actualizarFotoUsuario(String email, String foto) {
        // TODO Busca al usuario cuyo email paso por parámetro, y modifica su foto.
        //  Devuelve true o false como resultado de la operación.
        //return serverActualizarFoto(email, foto);
        return true;    //TODO Borrar esta línea provisional.
    }

//------------------------------------------CONTINUACION------------------------------------------------------------------------

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

    public static ArrayList<Evento> obtenerEventosPendientes(String email) {
        /**  TODO Obtener de la BBDD los eventos NO TERMINADOS en los que el usuario sea el organizador o
         *        participante o solicitante.
         */
        //return serverEventosPendientes(email);

        //return eventosPendientes(serverEventosPendientes(email));
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static ArrayList<Evento> obtenerEventosFinalizados(String email) {
        /**  TODO Obtener de la BBDD los eventos TERMINADOS hace menos de un mes en los que el usuario
         *        sea el organizador o participante. (pero no solicitante).
         */
        //return serverEventosFinalizados(email);
        return new ArrayList<>();   //TODO Borrar esta línea provisional.
    }

    public static boolean usuarioHaPuntuadoEvento(String email, String idEvento) {
        //TODO comprobar en la base de datos si el evento ya ha sido puntuado por ese usuario.
        //return serverUsuarioPuntuadoEvento(email,idEvento)
        return false;
    }

    public static void enviarPuntuacionParticipante(PuntuacionParticipante puntuacionP) {
        //TODO insertar en la TablaPuntuacionesParticipantes la puntuacion enviada por un usuario a
        // otro en un evento.

    }

    public static void enviarPuntuacionEvento(PuntuacionEvento puntuacionE) {
        //TODO insertar en la TablaPuntuacionesEventos la puntuacion de usuario a un evento.
        //serverPuntuarParticipante(puntuacionE);
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
        //TODO insertar un usuarioBloqueado en la lista de Bloqueados de otro Usuario.
        //serverBloquearUsuario(emailBloqueado, emailUsuario);
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

    public static Usuario buscarUsuario(ArrayList<Usuario> lista, String email) {
        for (Usuario usuario: lista) {
            if (usuario.getEmailUsuario().equals(email))
                return usuario;
        }

        return null;
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

    public static void eliminarEvento(String idEvento) {
        //TODO eliminar completamente el evento de la BBDD.
        //serverEliminarEvento(evento.getIdEvento());
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

    public static int calcularReputacionParticipante(String email) {
        // TODO Calcular la reputación de la tablaPuntuacionesParticipantes. Si nadie nos ha puntuado aún, se devuelve un 4.
        // return serverCalcularReputacionParticipante(email);
        return 4;
    }

    public static int calcularReputacionOrganizador(String email) {
        // TODO Calcular la reputación de la tablaPuntuacionesEventos. Si nadie nos ha puntuado aún, se devuelve un 4.
        // return serverCalcularReputacionOrganizador(email);
        return 4;
    }
}
