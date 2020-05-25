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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.Usuario;

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

    public static void cargarEventos() {
        MainActivity.listaEventos = new ArrayList<>();
        //TODO cargar eventos de la base de datos.
    }

    public static void cargarAmigos() {
        MainActivity.listaAmigos = new ArrayList<>();
        //TODO cargar amigos de la vase de datos.
    }

    public static boolean guardarEvento(Evento evento) {
        //TODO guardar nuevo evento en la BBDD.
        return true;
    }

    public static void enviarInvitaciones(Evento evento, ArrayList<String> listaInvitados) {
        //TODO enviar invitaciones al evento.
    }

    public static void guardarUsuarioOnlineNow(boolean estado) {
        //TODO actualizar la BBDD con usuario isOnlineNow = estado
    }

    public static String dateToString(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("E dd MMM yyyy");
        if (fecha!=null)
            return formato.format(fecha);
        return "";
    }

    public static ArrayList<Evento> eventosPendientes(ArrayList<Evento> arrayList) {
        ArrayList<Evento> pendientes = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (!arrayList.get(i).isTerminado()) {
                if (arrayList.get(i).getFechaEvento() == null || arrayList.get(i).getFechaEvento().after(new Date()))
                    pendientes.add(arrayList.get(i));
                else {
                    arrayList.get(i).setTerminado(true);
                    //TODO actualizar BBDD con este evento terminado = true  si no hay conexión a internet el programa debe continuar
                }
            }
        }
        return pendientes;
    }

    public static ArrayList<Evento> eventosFinalizados(ArrayList<Evento> arrayList) {
        ArrayList<Evento> finalizados = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isTerminado())
                finalizados.add(arrayList.get(i));
            else if (arrayList.get(i).getFechaEvento() != null && arrayList.get(i).getFechaEvento().before(new Date())) {
                finalizados.add(arrayList.get(i));
                arrayList.get(i).setTerminado(true);
                //TODO actualizar BBDD con este evento terminado = true  si no hay conexión a internet el programa debe continuar
            }
        }
        return finalizados;
    }

    public static boolean suscrito(Evento ev,Context c) {
        for (Usuario usuario: ev.getListaSolicitantes()) {
            if (usuario.getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                return true;
        }
        return false;
    }

    public static boolean participante(Evento ev,Context c) {
        for (Usuario usuario: ev.getListaParticipantes()) {
            if (usuario.getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                return true;
        }
        return false;
    }

    public static boolean baneado(Evento ev,Context c) {
        for (Usuario usuario: ev.getListaDescartados()) {
            if (usuario.getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
                mostrarMensaje(c.getResources().getString(R.string.has_been_baned),c);
                return true;
            }
        }
        return false;
    }

    public static void eliminarEvento(Evento evento, Context context) {
        Funcionalidades.mostrarMensaje("Implementar Borrado",context);
        //TODO eliminar evento de la BBDD y de mi lista
    }
}
