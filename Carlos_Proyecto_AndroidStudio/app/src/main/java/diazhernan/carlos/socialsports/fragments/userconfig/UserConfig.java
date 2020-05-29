package diazhernan.carlos.socialsports.fragments.userconfig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;

public class UserConfig extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private BottomNavigationView navigationView;
    private UserConfigSettings userConfigSettings;
    private UserConfigFriends userConfigFriends;
    private UserConfigBanned userConfigBanned;

    public UserConfig() {
        userConfigSettings = new UserConfigSettings();
        userConfigFriends = new UserConfigFriends();
        userConfigBanned = new UserConfigBanned();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_config, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = getActivity().findViewById(R.id.tabsUserConfig);
        toolbar = getActivity().findViewById(R.id.toolBarUserConfig);
        toolbar.inflateMenu(R.menu.user_menu);
        navigationView = getActivity().findViewById(R.id.navigationUserConfig);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                realizarAccionSeleccionada(menuItem);
                return true;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                realizarAccionSeleccionada(item);
                return true;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_configuration))) {
                    Funcionalidades.showSelectedFragment(R.id.containerUserConfig, getActivity().getSupportFragmentManager(), userConfigSettings);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_amigos))) {
                    Funcionalidades.showSelectedFragment(R.id.containerUserConfig, getActivity().getSupportFragmentManager(), userConfigFriends);
                }
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_bloqueados))) {
                    Funcionalidades.showSelectedFragment(R.id.containerUserConfig, getActivity().getSupportFragmentManager(), userConfigBanned);
                }
                Funcionalidades.esconderTeclado(getActivity(),getContext(),getView());
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

        Funcionalidades.showSelectedFragment(R.id.containerUserConfig, getActivity().getSupportFragmentManager(), userConfigSettings);
    }

    private void realizarAccionSeleccionada(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemUserMenuSave:
                guardarCambios();
                break;
            case R.id.itemUserMenuLogout:
                logout();
                break;
            case R.id.itemUserMenuDelete:
                eliminarCuentaDelUsuario();
                break;
        }
    }

    private void guardarCambios() {
        //TODO gardar acmbios en los datos del usuario
    }

    private void logout() {
        //TODO salir a la pantalla de logueo
    }

    private void eliminarCuentaDelUsuario() {
        //TODO eliminar la cuenta del usuario
    }
}
