package diazhernan.carlos.socialsports.fragments.searchevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class SearchEvent extends Fragment {

    private TabLayout tabLayout;
    private Button buttonFiltrar;
    private ArrayList<Evento> listaEventosFiltrados;
    private SearchEventsFilters searchEventsFilters;
    private SearchEventsResults searchEventsResults;

    public SearchEvent() {
        listaEventosFiltrados = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_event, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = getActivity().findViewById(R.id.tabsSearchEvents);
        buttonFiltrar = getActivity().findViewById(R.id.buttonFilter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_search_events_filters))) {
                    buttonFiltrar.setVisibility(View.VISIBLE);
                    searchEventsFilters = new SearchEventsFilters();
                    Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent, getActivity().getSupportFragmentManager(), searchEventsFilters);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_search_events_results))) {
                    buttonFiltrar.setVisibility(View.GONE);
                    searchEventsResults = new SearchEventsResults();
                    searchEventsResults.setListaEventosFiltrados(listaEventosFiltrados);
                    Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent, getActivity().getSupportFragmentManager(), searchEventsResults);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEventosFiltrados();
                tabLayout.getTabAt(1).select();
            }
        });
        searchEventsFilters = new SearchEventsFilters();
        Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent,getActivity().getSupportFragmentManager(),searchEventsFilters);
    }

    private void listaEventosFiltrados() {
        //TODO cargar de la BBDD la lista de los eventos no finalizados que cumplan los filtros (toUpperCase())
        listaEventosFiltrados = Funcionalidades.eventosPendientes(MainActivity.listaEventos);
    }
}
