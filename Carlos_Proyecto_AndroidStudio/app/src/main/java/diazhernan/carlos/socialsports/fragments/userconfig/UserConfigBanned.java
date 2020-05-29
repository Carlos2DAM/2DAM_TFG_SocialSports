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

import diazhernan.carlos.socialsports.Clases.AdaptadorListaUsuarios;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;

public class UserConfigBanned extends Fragment {

    private ListView listViewBloqueados;
    private Usuario usuarioSeleccionado;
    private AlertDialog.Builder menuOpciones;
    private String[] opcionesBloqueados;

    public UserConfigBanned() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_config_banned, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        opcionesBloqueados = new String[]{getResources().getString(R.string.opcion_desbloquear)
                ,getResources().getString(R.string.opcion_solicitud_de_amistad)};
        menuOpciones = new AlertDialog.Builder(getContext());
        menuOpciones.setItems(opcionesBloqueados, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        desbloquear();
                        break;
                    case 1:
                        agregarAmigo();
                        break;
                }
                mostrarListaBloqueados(LoginActivity.usuario.getListaBloqueados());
            }
        });
        listViewBloqueados = getActivity().findViewById(R.id.listUserConfigBanned);
        mostrarListaBloqueados(LoginActivity.usuario.getListaBloqueados());
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarListaBloqueados(LoginActivity.usuario.getListaBloqueados());
    }

    private void mostrarListaBloqueados(final ArrayList<Usuario> arrayList)
    {
        AdaptadorListaUsuarios adapter = new AdaptadorListaUsuarios(getContext(), R.layout.item_lista_usuarios,
                R.id.textItemUsuarioNombre, arrayList);
        listViewBloqueados.setAdapter(adapter);
        listViewBloqueados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = LoginActivity.usuario.getListaBloqueados().get(position);
                menuOpciones.setTitle(usuarioSeleccionado.getNombreUsuario() +
                        " " + usuarioSeleccionado.getApellidosUsuario());
                menuOpciones.show();
            }
        });
    }

    private void desbloquear() {
        LoginActivity.usuario.getListaBloqueados().remove(usuarioSeleccionado);
        //TODO actualizar lista de bloqueados del usuario en la BBDD
    }

    private void agregarAmigo() {
        if (LoginActivity.usuario.getListaBloqueados().contains(usuarioSeleccionado))
            LoginActivity.usuario.getListaBloqueados().remove(usuarioSeleccionado);
        LoginActivity.usuario.getListaAmigos().add(usuarioSeleccionado);
        //TODO actualizar lista de amigos y de bloqueados del usuario de la BBDD
    }
}
