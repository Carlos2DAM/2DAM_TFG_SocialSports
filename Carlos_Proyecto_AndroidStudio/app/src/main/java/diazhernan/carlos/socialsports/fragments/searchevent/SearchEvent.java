package diazhernan.carlos.socialsports.fragments.searchevent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class SearchEvent extends Fragment {

    private TabLayout tabLayout;
    private BottomNavigationView navigationView;
    private ArrayList<Evento> listaEventosFiltrados;
    private SearchEventsFilters searchEventsFilters;
    private SearchEventsResults searchEventsResults;

    public SearchEvent() {
        listaEventosFiltrados = new ArrayList<>();
        searchEventsFilters = new SearchEventsFilters();
        searchEventsResults = new SearchEventsResults();
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
        navigationView = getActivity().findViewById(R.id.navigationSearchEvent);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_search_events_filters))) {
                    navigationView.setVisibility(View.VISIBLE);
                    Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent, getActivity().getSupportFragmentManager(), searchEventsFilters);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_search_events_results))) {
                    navigationView.setVisibility(View.GONE);
                    Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent, getActivity().getSupportFragmentManager(), searchEventsResults);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Funcionalidades.esconderTeclado(getActivity(),getContext(),getView());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Funcionalidades.esconderTeclado(getActivity(),getContext(),getView());
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itemSearchMenuSearch:
                        Funcionalidades.esconderTeclado(getActivity(),getContext(),tabLayout);
                        buscarEventosFiltrados();
                        searchEventsResults = new SearchEventsResults(listaEventosFiltrados);
                        tabLayout.getTabAt(1).select();
                        break;
                    case R.id.itemSearchMenuClean:
                        Funcionalidades.esconderTeclado(getActivity(),getContext(),tabLayout);
                        searchEventsFilters = new SearchEventsFilters();
                        Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent,getActivity().getSupportFragmentManager(),searchEventsFilters);
                        break;
                }
                return true;
            }
        });

        Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent,getActivity().getSupportFragmentManager(),searchEventsFilters);
    }

    private void buscarEventosFiltrados() {
        listaEventosFiltrados = new ArrayList<>();
        for (Evento evento: Funcionalidades.eventosPendientes(MainActivity.listaEventos)) {
            String fecha = searchEventsFilters.getEditFecha().getText().toString();
            if (fecha.equals("") || fecha.equals(Funcionalidades.dateToString(evento.getFechaEvento())))
                listaEventosFiltrados.add(evento);
        }
        //TODO cargar de la BBDD la lista de los eventos no finalizados que cumplan los filtros (toUpperCase())
    }
}
