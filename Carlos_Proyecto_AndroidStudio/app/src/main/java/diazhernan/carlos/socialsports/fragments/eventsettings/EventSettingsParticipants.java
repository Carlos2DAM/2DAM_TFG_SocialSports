package diazhernan.carlos.socialsports.fragments.eventsettings;

import android.os.Bundle;

import androidx.annotation.Nullable;
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

public class EventSettingsParticipants extends Fragment {

    private ListView listViewParticipantes;

    public EventSettingsParticipants() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_settings_participants, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewParticipantes = getActivity().findViewById(R.id.listEventSettingsParticipants);
        mostrarListaUsuarios(Funcionalidades.eventoSeleccionado.getListaParticipantes());
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarListaUsuarios(Funcionalidades.eventoSeleccionado.getListaParticipantes());
    }

    private void mostrarListaUsuarios(ArrayList<Usuario> arrayList) {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(getContext(), R.layout.item_lista_usuarios,
                R.id.textItemUsuarioNombre, arrayList);
        listViewParticipantes.setAdapter(adapter);
        listViewParticipantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
