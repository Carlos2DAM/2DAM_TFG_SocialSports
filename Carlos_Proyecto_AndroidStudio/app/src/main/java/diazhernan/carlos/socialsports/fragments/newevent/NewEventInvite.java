package diazhernan.carlos.socialsports.fragments.newevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class NewEventInvite extends Fragment {

    private ArrayList<String> listaInvitarAmigos;
    private LinearLayout viewListaAmigos;
    private Button buttonAll;
    private Button buttonNobody;

    public NewEventInvite() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_event_invite, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listaInvitarAmigos = new ArrayList<>();
        viewListaAmigos = getActivity().findViewById(R.id.viewListaAmigos);
        buttonAll = getActivity().findViewById(R.id.buttonInviteAll);
        buttonNobody = getActivity().findViewById(R.id.buttonInviteNobody);
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarTodosAmigos(true);
            }
        });
        buttonNobody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarTodosAmigos(false);
            }
        });
        for (int i=0; i<MainActivity.listaAmigos.size(); i++) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId((int)MainActivity.listaAmigos.get(i).getIdUsuario());
            checkBox.setText(MainActivity.listaAmigos.get(i).getNombreUsuario());
            checkBox.setHint(MainActivity.listaAmigos.get(i).getEmailUsuario());
            checkBox.setTextColor(getResources().getColor(R.color.colorElements));
            checkBox.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorElements));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
                        if (!listaInvitarAmigos.contains(buttonView.getHint().toString()))
                            listaInvitarAmigos.add(buttonView.getHint().toString());
                    }
                    else {
                        buttonView.setButtonTintList(getResources().getColorStateList(R.color.colorElements));
                        if (listaInvitarAmigos.contains(buttonView.getHint().toString()))
                            listaInvitarAmigos.remove(buttonView.getHint().toString());
                    }
                }
            });
            viewListaAmigos.addView(checkBox);
        }
    }

    public void seleccionarTodosAmigos(boolean selec) {
        for (int i=0; i<MainActivity.listaAmigos.size(); i++) {
            CheckBox checkBox = getActivity().findViewById((int)MainActivity.listaAmigos.get(i).getIdUsuario());
            checkBox.setChecked(selec);
            if (selec)
                checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
            else
                checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorElements));
        }
    }
}
