package diazhernan.carlos.socialsports.fragments.userconfig;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.APIService;
import diazhernan.carlos.socialsports.Clases.AdaptadorListaUsuarios;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;
import diazhernan.carlos.socialsports.RETROFIT;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserConfigFriends extends Fragment {

    private ListView listViewAmigos;
    private Usuario usuarioSeleccionado;
    private AlertDialog.Builder menuOpciones;
    private String[] opcionesAmigos;

    public UserConfigFriends() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_config_friends, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        opcionesAmigos = new String[]{getResources().getString(R.string.opcion_eliminar_amigo)
                ,getResources().getString(R.string.opcion_bloqueo_permanente)};
        menuOpciones = new AlertDialog.Builder(getContext());
        menuOpciones.setItems(opcionesAmigos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        eliminarAmigo();
                        break;
                    case 1:
                        bloqueaoPermanebte();
                        break;
                }
                mostrarListaAmigos(LoginActivity.usuario.getListaAmigos());
            }
        });
        listViewAmigos = getActivity().findViewById(R.id.listUserConfigFriends);
        obtenerListaAmigos(LoginActivity.usuario.getEmailUsuario());
        //mostrarListaAmigos(LoginActivity.usuario.getListaAmigos());
    }

    @Override
    public void onResume() {
        super.onResume();
        obtenerListaAmigos(LoginActivity.usuario.getEmailUsuario());
        //mostrarListaAmigos(LoginActivity.usuario.getListaAmigos());
    }

    private void mostrarListaAmigos(final ArrayList<Usuario> arrayList)
    {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(getContext(), R.layout.item_lista_usuarios,
                R.id.textItemUsuarioNombre, arrayList);
        listViewAmigos.setAdapter(adapter);
        listViewAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = LoginActivity.usuario.getListaAmigos().get(position);
                menuOpciones.setTitle(usuarioSeleccionado.getNombreUsuario() +
                        " " + usuarioSeleccionado.getApellidosUsuario());
                menuOpciones.show();
            }
        });
    }

    private void eliminarAmigo() {

        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.eliminarAmigo("Bearer " + LoginActivity.token,
                LoginActivity.usuario.getEmailUsuario(),
                usuarioSeleccionado.getEmailUsuario()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 204){
                    LoginActivity.usuario.getListaAmigos().remove(usuarioSeleccionado);
                    mostrarListaAmigos(LoginActivity.usuario.getListaAmigos());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void bloqueaoPermanebte() {
        if (LoginActivity.usuario.getListaAmigos().contains(usuarioSeleccionado))
            LoginActivity.usuario.getListaAmigos().remove(usuarioSeleccionado);
        LoginActivity.usuario.getListaBloqueados().add(usuarioSeleccionado);
        //TODO actualizar lista de amigos y de bloqueados del usuario de la BBDD
    }

    private void obtenerListaAmigos(String correo){
        RETROFIT retrofit = new RETROFIT();
        APIService service = retrofit.getAPIService();
        service.listaAmigos("Bearer " + LoginActivity.token, correo).enqueue(new Callback<ArrayList<Usuario>>() {
            @Override
            public void onResponse(Call<ArrayList<Usuario>> call, Response<ArrayList<Usuario>> response) {

                if(response.isSuccessful()){
                    LoginActivity.usuario.setListaAmigos(new ArrayList<Usuario>());
                    for(Usuario usuario : response.body()){
                        LoginActivity.usuario.getListaAmigos().add(usuario);
                    }
                    mostrarListaAmigos(LoginActivity.usuario.getListaAmigos());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Usuario>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
