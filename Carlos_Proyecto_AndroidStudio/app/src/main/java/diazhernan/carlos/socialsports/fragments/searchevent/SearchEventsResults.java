package diazhernan.carlos.socialsports.fragments.searchevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.AdaptadorListaEventos;
import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.R;

public class SearchEventsResults extends Fragment {

    private ListView listViewEventos;
    private ArrayList<Evento> listaEventosFiltrados;

    public SearchEventsResults() {
        listaEventosFiltrados = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_events_results, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewEventos = getActivity().findViewById(R.id.listSearchEventsResults);
        mostrarListaEventos(listaEventosFiltrados);
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarListaEventos(listaEventosFiltrados);
    }

    private void mostrarListaEventos(ArrayList<Evento> arrayList)
    {
        AdaptadorListaEventos adapter = new AdaptadorListaEventos(getContext(), R.layout.item_lista_eventos,
                R.id.textItemEventoDeporte, arrayList);
        listViewEventos.setAdapter(adapter);
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void setListaEventosFiltrados(ArrayList<Evento> listaEventosFiltrados) {
        this.listaEventosFiltrados = listaEventosFiltrados;
    }
}
