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
    private Home home = new Home();
    private NewEvent newEvent;
    private SearchEvent searchEvent;
    private MyEvents myEvents;
    private UserConfig userConfig;
    public static ArrayList<Usuario> listaAmigos;
    public static ArrayList<Evento> listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarAmigos();
        agregarEventos();
        navigationView = findViewById(R.id.menu_nav_main);
        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.menu_home):
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
    }

    private void agregarAmigos() {
        listaAmigos = new ArrayList<>();
        listaAmigos.add(new Usuario("d@mail.es","","dimitri","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        listaAmigos.add(new Usuario("e@mail.es","","antonio","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        listaAmigos.add(new Usuario("f@mail.es","","petra","Female","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        listaAmigos.add(new Usuario("q@mail.es","","juan","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        listaAmigos.add(new Usuario("a@mail.es","","luisa","Female","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        listaAmigos.add(new Usuario("w@mail.es","","felipe","Male","aaa111",new Date(),new Date(),4,4,"d.jpg"));
        listaAmigos.add(new Usuario("z@mail.es","","maria","Female","aaa111",new Date(),new Date(),4,4,"d.jpg"));
    }

    private void agregarEventos() {
        listaEventos = new ArrayList<>();
        Evento evento = new Evento();
        evento.setDeporte("Futbol");
        evento.setLocalidad("Fuenlabrada");
        evento.setOrganizadorEvento(listaAmigos.get(4));
        evento.setMaximoParticipantes(22);
        evento.setFechaEvento(new Date(120,6,12));
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Baloncesto");
        evento.setLocalidad("Madrid");
        evento.setOrganizadorEvento(listaAmigos.get(5));
        evento.setMaximoParticipantes(10);
        evento.setInstalacionesReservadas(true);
        evento.setPrecioPorParticipante(3.5f);
        listaEventos.add(evento);
        evento = new Evento();
        evento.setDeporte("Tenis");
        evento.setLocalidad("Móstoles");
        evento.setOrganizadorEvento(LoginActivity.usuario);
        evento.setMaximoParticipantes(1);
        evento.getListaParticipantes().add(LoginActivity.usuario);
        evento.setFechaEvento(new Date(120,5,30));
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
