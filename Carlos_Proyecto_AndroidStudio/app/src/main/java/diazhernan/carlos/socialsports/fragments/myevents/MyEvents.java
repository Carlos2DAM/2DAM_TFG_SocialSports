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
import diazhernan.carlos.socialsports.EventSettings;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class MyEvents extends Fragment {

    private TabLayout tabLayout;
    private ListView listaEventos;

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
        listaEventos = getActivity().findViewById(R.id.myEventsListView);

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

    private void mostrarListaEventos(final ArrayList<Evento> arrayList)
    {
        AdaptadorListaEventos adapter = new AdaptadorListaEventos(getContext(), R.layout.item_lista_eventos,
                R.id.textItemEventoDeporte, arrayList);
        listaEventos.setAdapter(adapter);
        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento evento = arrayList.get(position);
                //if (!evento.isTerminado())
                Intent intent = new Intent(getContext(), EventSettings.class);
                intent.putExtra("eventoSet",evento);
                startActivity(intent);
            }
        });
    }
}