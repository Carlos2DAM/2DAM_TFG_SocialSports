package diazhernan.carlos.socialsports.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import diazhernan.carlos.socialsports.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserConfig extends Fragment {


    public UserConfig() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_config, container, false);
    }

}
