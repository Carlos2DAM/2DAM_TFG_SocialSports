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

import diazhernan.carlos.socialsports.APIService;
import diazhernan.carlos.socialsports.Clases.AdaptadorListaUsuarios;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;
import diazhernan.carlos.socialsports.RETROFIT;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventSettingsParticipants extends Fragment {

    private Usuario usuarioSeleccionado;
    private ListView listViewParticipantes;
    private ArrayList<Usuario> listaParticipantes;
    private AlertDialog.Builder menuOpciones;
    private String[] opciones;

    public EventSettingsParticipants() {
        listaParticipantes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_settings_participants, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Funcionalidades.eresOrganizador(Funcionalidades.eventoSeleccionado)) {
            opciones = new String[]{getResources().getString(R.string.opcion_eliminar_participante)
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
                            eliminarParticipante();
                            break;
                        case 1:
                            bloquearSolicitud();
                            break;
                        case 2:
                            bloquearSolicitantePermanentemente();
                            break;
                        case 3:
                            agregarAmigo();
                            break;
                    }
                    if (listaParticipantes != null)
                        mostrarListaUsuarios(listaParticipantes);
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
        listViewParticipantes = getActivity().findViewById(R.id.listEventSettingsParticipants);
        listaParticipantes = Funcionalidades.eventoSeleccionado.getListaParticipantes();
        if (listaParticipantes != null)
            mostrarListaUsuarios(listaParticipantes);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listaParticipantes != null)
            mostrarListaUsuarios(listaParticipantes);
    }

    private void mostrarListaUsuarios(ArrayList<Usuario> arrayList) {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(getContext(), R.layout.item_lista_usuarios,
                R.id.textItemUsuarioNombre, arrayList);
        listViewParticipantes.setAdapter(adapter);
        listViewParticipantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = listaParticipantes.get(position);
                if (!Funcionalidades.soyYo(usuarioSeleccionado)) {
                    menuOpciones.setTitle(usuarioSeleccionado.getNombreUsuario() +
                            " " + usuarioSeleccionado.getApellidosUsuario());
                    menuOpciones.show();
                }
            }
        });
    }

    private void eliminarParticipante() {
        //Funcionalidades.eliminarParticipante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.eliminarParticipante("Bearer " + LoginActivity.token, Funcionalidades.eventoSeleccionado.getIdEvento(), usuarioSeleccionado.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Funcionalidades.eventoSeleccionado.getListaParticipantes().remove(usuarioSeleccionado);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void bloquearSolicitud() {
        Funcionalidades.eliminarParticipante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.bloquearUsuarioAlEvento(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
    }

    private void bloquearSolicitantePermanentemente() {
        Funcionalidades.eliminarParticipante(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.bloquearUsuarioAlEvento(Funcionalidades.eventoSeleccionado,usuarioSeleccionado);
        Funcionalidades.bloquearUsuarioPermanentemente(usuarioSeleccionado);
        Funcionalidades.eliminarAmigo(usuarioSeleccionado);
    }

    private void agregarAmigo() {
        Funcionalidades.eliminarBloqueoPermanentemente(usuarioSeleccionado);
        Funcionalidades.insertarAmigo(usuarioSeleccionado);
    }
}
