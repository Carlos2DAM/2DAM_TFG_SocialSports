package diazhernan.carlos.socialsports.fragments.newevent;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewEvent extends Fragment {

    public NewEvent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_event, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        showSelectedFragment(new NewEventDescription());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_description)))
                    showSelectedFragment(new NewEventDescription());
                else if (tab.getText().toString().equals(getResources().getString(R.string.tab_specify)))
                    showSelectedFragment(new NewEventSpecify());
                else if (tab.getText().toString().equals(getResources().getString(R.string.tab_requirements)))
                    showSelectedFragment(new NewEventRequirements());
                else if (tab.getText().toString().equals(getResources().getString(R.string.tab_invite)))
                    showSelectedFragment(new NewEventInvite());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //Metodo para elegir que fragment mostrar en pantalla.
    private void showSelectedFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}
