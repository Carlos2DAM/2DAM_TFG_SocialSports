package diazhernan.carlos.socialsports.fragments.myevents;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.AdaptadorListaEventos;
import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.EventRate;
import diazhernan.carlos.socialsports.EventSettings;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class MyEvents extends Fragment {

    private TabLayout tabLayout;
    private ListView listViewEventos;
    private ArrayList<Evento> listaDeEventos = new ArrayList<>();

    public MyEvents() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_events, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = getActivity().findViewById(R.id.tabsMyEvents);
        listViewEventos = getActivity().findViewById(R.id.myEventsListView);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_my_events_pend)))
                    mostrarListaEventos(Funcionalidades.eventosPendientes(MainActivity.listaEventos));
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_my_events_final)))
                    mostrarListaEventos(Funcionalidades.eventosFinalizados(MainActivity.listaEventos));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
        tabLayout.getTabAt(0).select();
    }

    @Override
    public void onResume() {
        super.onResume();
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).select();
    }

    private void mostrarListaEventos(ArrayList<Evento> arrayList)
    {
        AdaptadorListaEventos adapter = new AdaptadorListaEventos(getContext(), R.layout.item_lista_eventos,
                R.id.textItemEventoDeporte, arrayList);
        listaDeEventos = arrayList;
        listViewEventos.setAdapter(adapter);
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento evento = listaDeEventos.get(position);
                Intent intent = null;
                if (!evento.isTerminado())
                    intent = new Intent(getContext(), EventSettings.class);
                else
                    intent = new Intent(getContext(), EventRate.class);
                Funcionalidades.eventoSeleccionado = evento;
                startActivity(intent);
            }
        });
    }
}
