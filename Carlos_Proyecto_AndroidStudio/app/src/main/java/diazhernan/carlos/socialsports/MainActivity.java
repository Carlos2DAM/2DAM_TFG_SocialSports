package diazhernan.carlos.socialsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import diazhernan.carlos.socialsports.fragments.home.Home;
import diazhernan.carlos.socialsports.fragments.myevents.MyEvents;
import diazhernan.carlos.socialsports.fragments.newevent.NewEvent;
import diazhernan.carlos.socialsports.fragments.newevent.NewEventDescription;
import diazhernan.carlos.socialsports.fragments.searchevent.SearchEvent;
import diazhernan.carlos.socialsports.fragments.userconfig.UserConfig;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Home home = new Home();
    private NewEvent newEvent = new NewEvent();
    private SearchEvent searchEvent = new SearchEvent();
    private MyEvents myEvents = new MyEvents();
    private UserConfig userConfig = new UserConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (BottomNavigationView)findViewById(R.id.menu_nav_main);
        showSelectedFragment(home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.menu_home):
                        showSelectedFragment(home);
                        break;
                    case (R.id.menu_new_event):
                        showSelectedFragment(newEvent);
                        break;
                    case (R.id.menu_search_event):
                        showSelectedFragment(searchEvent);
                        break;
                    case (R.id.menu_my_events):
                        showSelectedFragment(myEvents);
                        break;
                    case (R.id.menu_user):
                        showSelectedFragment(userConfig);
                        break;
                }
                return true;
            }
        });
    }

    //Metodo para elegir que fragment mostrar en pantalla.
    private void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
