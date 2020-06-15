package diazhernan.carlos.socialsports.fragments.eventsettings;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.AdaptadorListaUsuarios;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.R;


public class EventSettingsRequests extends Fragment {

    private Usuario usuarioSeleccionado;
    private ListView listViewSolicitudes;
    private ArrayList<Usuario> listaSolicitantes;
    private AlertDialog.Builder menuOpciones;
    private String[] opciones;

    public EventSettingsRequests() {
        listaSolicitantes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_settings_requests, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Funcionalidades.eresOrganizador(Funcionalidades.eventoSeleccionado)) {
            opciones = new String[]{getResources().getString(R.string.opcion_aceptar_solicitud)
                    , getResources().getString(R.string.opcion_rechazar_solicitud)
                    , getResources().getString(R.string.opcion_bloqueo_de_evento)
                    , getResources().getString(R.string.opcion_bloqueo_permanente)
                    , getResources().getString(R.string.opcion_solicitud_de_amistad)};
        }
        else {
            opciones = new String[]{getResources().getString(R.string.opcion_solicitud_de_amistad)};
        }
        menuOpciones = new AlertDialog.Builder(getContext());
        if (Funcionalidades.eresOrganizador(Funcionalidades.eventoSeleccionado)) {
            menuOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            aceptarSolicitud();
                            break;
                        case 1:
                            eliminarSolicitud();
                            break;
                        case 2:
                            bloquearSolicitud();
                            break;
                        case 3:
                            bloquearSolicitantePermanentemente();
                            break;
                        case 4:
                            agregarAmigo();
                            break;
                    }
                    if (listaSolicitantes != null)
                        mostrarListaUsuarios(listaSolicitantes);
                }
            });
        }
        else {
            menuOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            agregarAmigo();
                            break;
                    }
                }
            });
        }
        listViewSolicitudes = getActivity().findViewById(R.id.listEventSettingsRequest);
        listaSolicitantes = Funcionalidades.eventoSeleccionado.getListaSolicitantes();
        if (listaSolicitantes != null)
            mostrarListaUsuarios(listaSolicitantes);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listaSolicitantes != null)
            mostrarListaUsuarios(listaSolicitantes);
    }

    private void mostrarListaUsuarios(ArrayList<Usuario> arrayList) {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(getContext(), R.layout.item_lista_usuarios,
                R.id.textItemUsuarioNombre, arrayList);
        listViewSolicitudes.setAdapter(adapter);
        listViewSolicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = listaSolicitantes.get(position);
                if (!Funcionalidades.soyYo(usuarioSeleccionado)) {
                    menuOpciones.setTitle(usuarioSeleccionado.getNombreUsuario() +
                            " " + usuarioSeleccionado.getApellidosUsuario());
                    menuOpciones.show();
                }
            }
        });
    }

    private void aceptarSolicitud() {
        //Funcionalidades.eliminarSolicitante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.insertarParticipante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
    }

    private void eliminarSolicitud() {
        Funcionalidades.eliminarSolicitante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
    }

    private void bloquearSolicitud() {
        Funcionalidades.eliminarSolicitante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.bloquearUsuarioAlEvento(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
    }

    private void bloquearSolicitantePermanentemente() {
        Funcionalidades.eliminarSolicitante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.bloquearUsuarioAlEvento(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.bloquearUsuarioPermanentemente(usuarioSeleccionado);
        Funcionalidades.eliminarAmigo(usuarioSeleccionado);
    }

    private void agregarAmigo() {
        //Funcionalidades.eliminarBloqueoPermanentemente(usuarioSeleccionado);
        Funcionalidades.insertarAmigo(usuarioSeleccionado);
    }
}
