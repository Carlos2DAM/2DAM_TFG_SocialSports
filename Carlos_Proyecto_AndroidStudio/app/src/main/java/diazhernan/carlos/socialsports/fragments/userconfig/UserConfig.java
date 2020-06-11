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

import java.io.IOException;
import java.util.Date;

import diazhernan.carlos.socialsports.APIService;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;
import diazhernan.carlos.socialsports.RETROFIT;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                Funcionalidades.esconderTeclado(getActivity(),getContext(),toolbar);
                realizarAccionSeleccionada(menuItem);
                return true;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Funcionalidades.esconderTeclado(getActivity(),getContext(),toolbar);
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
        String passwordNew = userConfigSettings.getNewpass();
        String passwordNewRepeat = userConfigSettings.getRepeatpass();
        if (!passwordNew.equals(passwordNewRepeat)) {
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_passwords_diferentes),getContext());
            return;
        }

        String email = LoginActivity.usuario.getEmailUsuario();
        String nombreNew = userConfigSettings.getNombre().toUpperCase();
        String nombreOld = LoginActivity.usuario.getNombreUsuario();
        if (!nombreNew.equals(nombreOld)) {
            actualizarNombreUsuarioBBDD(email,nombreNew);
            /*if (Funcionalidades.actualizarNombreUsuario(email,nombreNew))
                LoginActivity.usuario.setNombreUsuario(nombreNew);
            else {
                Funcionalidades.mostrarMensaje(getActivity().getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                return;
            }*/
        }

        String apellidosNew = userConfigSettings.getApellido().toUpperCase();
        String apellidosOld = LoginActivity.usuario.getApellidosUsuario();
        if (!apellidosNew.equals(apellidosOld)) {
            actualizarApellidosUsuarioBBDD(email,apellidosNew);
            /*if (Funcionalidades.actualizarApellidosUsuario(email,apellidosNew))
                LoginActivity.usuario.setApellidosUsuario(apellidosNew);
            else {
                Funcionalidades.mostrarMensaje(getActivity().getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                return;
            }*/
        }

        String direccionNew = userConfigSettings.getDireccion();
        String direccionOld = LoginActivity.usuario.getDireccionUsuario();
        if (!direccionNew.equals(direccionOld)) {
            actualizarDireccionUsuarioBBDD(email,direccionNew);
            /*if (Funcionalidades.actualizarDireccionUsuario(email,direccionNew))
                LoginActivity.usuario.setDireccionUsuario(direccionNew);
            else {
                Funcionalidades.mostrarMensaje(getActivity().getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                return;
            }
            */
        }

        String generoNew = userConfigSettings.getGenero();
        String generoOld = LoginActivity.usuario.getGeneroUsuario();
        if (!generoNew.isEmpty() && !generoNew.equals(generoOld)) {
            actualizarGeneroUsuarioBBDD(email,generoNew);
            /*if (Funcionalidades.actualizarGeneroUsuario(email,generoNew))
                LoginActivity.usuario.setGeneroUsuario(generoNew);
            else {
                Funcionalidades.mostrarMensaje(getActivity().getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                return;
            }*/
        }

        if (!passwordNew.isEmpty()) {
            actualizarPasswordUsuarioBBDD(email,passwordNew);
            /*if (Funcionalidades.actualizarPasswordUsuario(email,passwordNew))
                LoginActivity.usuario.setPasswordUsuario(passwordNew);
            else {
                Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                return;
            }*/
        }

        Date fechaNew = userConfigSettings.getBirthdate();
        Date fechaOld = LoginActivity.usuario.getFechaNacimientoUsuario();
        if (fechaNew != null && fechaNew != fechaOld) {
            actualizarFechaNacimientoUsuarioBBDD(email,Funcionalidades.dateToString2(fechaNew));
            /*if (Funcionalidades.actualizarNacimientoUsuario(email,fechaNew))
                LoginActivity.usuario.setFechaNacimientoUsuario(fechaNew);
            else {
                Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                return;
            }*/
        }

        //TODO cargar foto de perfil.

        //Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_guardados),getContext());
    }

    private void logout() {
        getActivity().finish();
    }

    private void eliminarCuentaDelUsuario() {
        eliminararUsuarioBBDD(LoginActivity.usuario);
        /*if (Funcionalidades.eliminarUsuario(LoginActivity.usuario)) {
            Funcionalidades.mostrarMensaje(getActivity().getResources().getString(R.string.mensaje_usuario_eliminado),getContext());
            logout();
        }
        else
            Funcionalidades.mostrarMensaje(getActivity().getResources().getString(R.string.mensaje_usuario_no_eliminado),getContext());*/
    }

//------------- FUNCIONES PARA CONECTAR CON LA BBDD DEL SERVIDOR -------------------------------------------------------------------------------------

    public void actualizarNombreUsuarioBBDD(String email,String nombre) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.putNombre("Bearer " + LoginActivity.token, email, nombre).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        LoginActivity.usuario.setNombreUsuario(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void actualizarApellidosUsuarioBBDD(String email,String apellidos) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.putApellidos("Bearer " + LoginActivity.token, email, apellidos).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        LoginActivity.usuario.setApellidosUsuario(response.body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void actualizarDireccionUsuarioBBDD(String email,String direccion) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.putDireccion("Bearer " + LoginActivity.token, email, direccion).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        LoginActivity.usuario.setDireccionUsuario(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void actualizarGeneroUsuarioBBDD(String email,String genero) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.putGenero("Bearer " + LoginActivity.token, email, genero).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        LoginActivity.usuario.setGeneroUsuario(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void actualizarFechaNacimientoUsuarioBBDD(String email,String fecha) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.putFechaNacimiento("Bearer " + LoginActivity.token, email, fecha).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        String f = response.body().string();
                        LoginActivity.usuario.setFechaNacimientoUsuario(Funcionalidades.StringToDate(f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void actualizarPasswordUsuarioBBDD(String email,String password) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.putPassword("Bearer " + LoginActivity.token, email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        LoginActivity.usuario.setPasswordUsuario(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_cambios_no_guardados), getContext());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void eliminararUsuarioBBDD(Usuario usuario) {
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();

        service.borrarUsuario("Bearer " + LoginActivity.token, usuario.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 204) {
                    Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_usuario_eliminado),getContext());
                    logout();
                }else{
                    Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_usuario_no_eliminado),getContext());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
