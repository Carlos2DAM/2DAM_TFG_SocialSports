package diazhernan.carlos.socialsports.fragments.newevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewEventDescription extends Fragment {

    private EditText editSport;
    private EditText editLocation;

    public NewEventDescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_event_description, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editSport = getActivity().findViewById(R.id.editSport);
        editLocation = getActivity().findViewById(R.id.editLocation);

        editSport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
        editLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
    }
}
