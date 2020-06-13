package diazhernan.carlos.socialsports.fragments.searchevent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.APIService;
import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.FiltroDeEvento;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;
import diazhernan.carlos.socialsports.RETROFIT;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchEvent extends Fragment {

    private TabLayout tabLayout;
    private BottomNavigationView navigationView;
    private ArrayList<Evento> listaEventosFiltrados;
    private SearchEventsFilters searchEventsFilters;
    private SearchEventsResults searchEventsResults;
    private static FiltroDeEvento filtro;

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
                        /*searchEventsResults = new SearchEventsResults(listaEventosFiltrados);
                        tabLayout.getTabAt(1).select();*/
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
        filtro = obtenerFiltros();
        //listaEventosFiltrados = Funcionalidades.buscarEventosFiltrados(LoginActivity.usuario, filtro);

        /*
        Te envio una lista con aquellos eventos que CUMPLEN con los datos que has introducido
        (si no has introducido algunos datos se controla en el servidor) y una vez que has recibido esa lista
        voy quitando aquellos eventos con los que el usuario no cumple los requisitos
         */

        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.buscarEventos("Bearer " + LoginActivity.token,
                filtro.getSport(),
                filtro.getLocation(),
                Funcionalidades.dateToString2(filtro.getFechaDelEvento()),
                filtro.getHoraDelEvento(),
                filtro.isReserved(),
                filtro.getReputation()).enqueue(new Callback<ArrayList<Evento>>() {
            @Override
            public void onResponse(Call<ArrayList<Evento>> call, Response<ArrayList<Evento>> response) {
                if(response.isSuccessful()){

                    ArrayList<Evento> listaEventos = response.body();
                    int edad = Funcionalidades.calcularEdad(LoginActivity.usuario.getFechaNacimientoUsuario());

                    for(Evento evento:listaEventos){

                        //eventosNoCumploRequisitoEdadMaxima
                        if(evento.getRequisitos().getEdadMaxima() != -1 && edad > evento.getRequisitos().getEdadMaxima()){
                            listaEventos.remove(evento);
                            break;
                        }
                        //eventosNoCumploRequisitoEdadMinima
                        if(evento.getRequisitos().getEdadMinima() != -1 && edad < evento.getRequisitos().getEdadMinima()){
                            listaEventos.remove(evento);
                            break;
                        }
                        //eventosNoCumploRequisitoDeGenero
                        if(!evento.getRequisitos().getRequisitoDeGenero().equals("")){
                            if(!evento.getRequisitos().getRequisitoDeGenero().equals(LoginActivity.usuario.getGeneroUsuario())){
                                listaEventos.remove(evento);
                                break;
                            }
                        }
                        //eventosNoCumploRequisitoReputacion
                        if(LoginActivity.usuario.getReputacionParticipanteUsuario() < evento.getRequisitos().getReputacionNecesaria() && evento.getRequisitos().getReputacionNecesaria() != -1){
                            listaEventos.remove(evento);
                            break;
                        }

                        if(evento.getMaximoParticipantes() != -1 && evento.getMaximoParticipantes() <= evento.getListaParticipantes().size()){
                            listaEventos.remove(evento);
                        }
                    }

                    listaEventosFiltrados = listaEventos;
                    searchEventsResults = new SearchEventsResults(listaEventosFiltrados);
                    tabLayout.getTabAt(1).select();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Evento>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private FiltroDeEvento obtenerFiltros() {
        FiltroDeEvento filtro = new FiltroDeEvento();
        filtro.setSport(searchEventsFilters.getEditDeporte().toUpperCase());
        filtro.setLocation(searchEventsFilters.getEditLocalidad().toUpperCase());
        filtro.setFechaDelEvento(searchEventsFilters.getFecha());
        filtro.setHoraDelEvento(searchEventsFilters.getEditHora());
        filtro.setReserved(searchEventsFilters.getCheckReserva());
        if (searchEventsFilters.getCheckReputation())
            filtro.setReputation(searchEventsFilters.getRatingBarReputation());
        else
            filtro.setReputation(-1);
        return filtro;
    }
}
