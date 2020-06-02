package diazhernan.carlos.socialsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.fragments.home.Home;
import diazhernan.carlos.socialsports.fragments.myevents.MyEvents;
import diazhernan.carlos.socialsports.fragments.newevent.NewEvent;
import diazhernan.carlos.socialsports.fragments.searchevent.SearchEvent;
import diazhernan.carlos.socialsports.fragments.userconfig.UserConfig;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Home home;
    private NewEvent newEvent;
    private SearchEvent searchEvent;
    private MyEvents myEvents;
    private UserConfig userConfig;
    public static ArrayList<Evento> listaEventos;  // TODO borrar, codigo provisional

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarAmigos();    //TODO Linea de pruebas eliminar
        agregarEventos();   //TODO Linea de pruebas eliminar
        navigationView = findViewById(R.id.menu_nav_main);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.menu_home):
                        if (home == null)
                            home = new Home();
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),home);
                        break;
                    case (R.id.menu_new_event):
                        newEvent = new NewEvent();
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),newEvent);
                        break;
                    case (R.id.menu_search_event):
                        searchEvent = new SearchEvent();
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),searchEvent);
                        break;
                    case (R.id.menu_my_events):
                        myEvents = new MyEvents();
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),myEvents);
                        break;
                    case (R.id.menu_user):
                        userConfig = new UserConfig();
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),userConfig);
                        break;
                }
                return true;
            }
        });

        if (LoginActivity.usuario.getNombreUsuario().equals("") || LoginActivity.usuario.getGeneroUsuario().equals("") || LoginActivity.usuario.getFechaNacimientoUsuario() == null) {
            navigationView.setSelectedItemId(R.id.menu_user);
        }
        else {
            navigationView.setSelectedItemId(R.id.menu_home);
        }
        //TODO calcular puntuaciones del usuario.
    }

    private void agregarAmigos() {      //TODO función de pruebas eliminar
        LoginActivity.usuario.setListaAmigos(new ArrayList<Usuario>());
        LoginActivity.usuario.getListaAmigos().add(new Usuario("d@mail.es","","dimitri","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        LoginActivity.usuario.getListaAmigos().add(new Usuario("e@mail.es","","antonio","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        LoginActivity.usuario.getListaAmigos().add(new Usuario("f@mail.es","","petra","Female","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        LoginActivity.usuario.getListaAmigos().add(new Usuario("q@mail.es","","juan","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        LoginActivity.usuario.getListaAmigos().add(new Usuario("a@mail.es","","luisa","Female","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        LoginActivity.usuario.getListaAmigos().add(new Usuario("w@mail.es","","felipe","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        LoginActivity.usuario.getListaAmigos().add(new Usuario("z@mail.es","","maria","Female","aaa111",new Date(),new Date(),4,4,"d.jpg"));
    }

    private void agregarEventos() {     //TODO función de pruebas eliminar
        listaEventos = new ArrayList<>();
        Evento evento = new Evento();
        evento.setDeporte("Futbol");
        evento.setLocalidad("Fuenlabrada");
        evento.setOrganizadorEvento(LoginActivity.usuario.getListaAmigos().get(4));
        evento.setMaximoParticipantes(22);
        evento.setFechaEvento(new Date(120,6,12));
        evento.getListaSolicitantes().add(LoginActivity.usuario);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Baloncesto");
        evento.setLocalidad("Madrid");
        evento.setOrganizadorEvento(LoginActivity.usuario.getListaAmigos().get(5));
        evento.setMaximoParticipantes(10);
        evento.setInstalacionesReservadas(true);
        evento.setPrecioPorParticipante(3.5f);
        evento.getListaDescartados().add(LoginActivity.usuario);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Padel");
        evento.setLocalidad("Alcorcón");
        evento.setOrganizadorEvento(LoginActivity.usuario.getListaAmigos().get(6));
        evento.setMaximoParticipantes(4);
        evento.setInstalacionesReservadas(true);
        evento.setPrecioPorParticipante(3f);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Tenis");
        evento.setLocalidad("Móstoles");
        //evento.setOrganizadorEvento(listaAmigos.get(2));
        evento.setOrganizadorEvento(LoginActivity.usuario);
        evento.setMaximoParticipantes(1);
        evento.getListaParticipantes().add(LoginActivity.usuario);
        evento.getListaParticipantes().add(LoginActivity.usuario.getListaAmigos().get(2));
        evento.getListaParticipantes().add(LoginActivity.usuario.getListaAmigos().get(1));
        evento.getListaParticipantes().add(LoginActivity.usuario.getListaAmigos().get(0));
        evento.getListaParticipantes().add(LoginActivity.usuario.getListaAmigos().get(6));
        evento.getListaSolicitantes().add(LoginActivity.usuario.getListaAmigos().get(3));
        evento.getListaSolicitantes().add(LoginActivity.usuario.getListaAmigos().get(4));
        evento.getListaSolicitantes().add(LoginActivity.usuario.getListaAmigos().get(5));
        evento.setFechaEvento(new Date(120,5,30));
        //evento.setTerminado(true);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Correr");
        evento.setLocalidad("Fuenlabrada");
        evento.setOrganizadorEvento(LoginActivity.usuario);
        evento.setMaximoParticipantes(-1);
        evento.setFechaEvento(new Date(120,6,2));
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Bicicleta");
        evento.setLocalidad("Fuenlabrada");
        evento.setOrganizadorEvento(LoginActivity.usuario);
        evento.setMaximoParticipantes(-1);
        evento.setFechaEvento(new Date());
        evento.setTerminado(true);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Waterpolo");
        evento.setLocalidad("Carranque");
        evento.setOrganizadorEvento(LoginActivity.usuario);
        evento.setMaximoParticipantes(-1);
        evento.setFechaEvento(new Date());
        evento.setTerminado(true);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Badminton");
        evento.setLocalidad("Madrid");
        evento.setOrganizadorEvento(LoginActivity.usuario);
        evento.setMaximoParticipantes(-1);
        evento.setFechaEvento(new Date());
        evento.setTerminado(true);
        listaEventos.add(evento);
    }
}
