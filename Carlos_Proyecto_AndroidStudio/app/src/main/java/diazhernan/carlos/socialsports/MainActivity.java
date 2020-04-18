package diazhernan.carlos.socialsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import diazhernan.carlos.socialsports.fragments.home.Home;
import diazhernan.carlos.socialsports.fragments.myevents.MyEvents;
import diazhernan.carlos.socialsports.fragments.newevent.NewEvent;
import diazhernan.carlos.socialsports.fragments.searchevent.SearchEvent;
import diazhernan.carlos.socialsports.fragments.userconfig.UserConfig;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Home home = new Home();
    private NewEvent newEvent;
    private SearchEvent searchEvent = new SearchEvent();
    private MyEvents myEvents = new MyEvents();
    private UserConfig userConfig = new UserConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),searchEvent);
                        break;
                    case (R.id.menu_my_events):
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),myEvents);
                        break;
                    case (R.id.menu_user):
                        Funcionalidades.showSelectedFragment(R.id.container,getSupportFragmentManager(),userConfig);
                        break;
                }
                return true;
            }
        });
    }

}
