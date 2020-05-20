package diazhernan.carlos.socialsports.fragments.searchevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class SearchEvent extends Fragment {

    private TabLayout tabLayout;
    private Button buttonFiltrar;
    private Button buttonClean;
    private TableRow rowButtons;
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
        buttonFiltrar = getActivity().findViewById(R.id.buttonFilterSearch);
        buttonClean = getActivity().findViewById(R.id.buttonFilterClean);
        rowButtons = getActivity().findViewById(R.id.rowSearchButtons);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_search_events_filters))) {
                    rowButtons.setVisibility(View.VISIBLE);
                    Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent, getActivity().getSupportFragmentManager(), searchEventsFilters);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_search_events_results))) {
                    rowButtons.setVisibility(View.GONE);
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

        buttonFiltrar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getActivity().getApplication());
            }
        });
        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
                v.setFocusableInTouchMode(false);
                buscarEventosFiltrados();
                searchEventsResults = new SearchEventsResults();
                searchEventsResults.setListaEventosFiltrados(listaEventosFiltrados);
                tabLayout.getTabAt(1).select();
            }
        });

        buttonClean.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getActivity().getApplication());
            }
        });
        buttonClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
                v.setFocusableInTouchMode(false);
                searchEventsFilters = new SearchEventsFilters();
                Funcionalidades.showSelectedFragment(R.id.contenedorSearchEvent,getActivity().getSupportFragmentManager(),searchEventsFilters);
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
