package diazhernan.carlos.socialsports.fragments.newevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.R;

public class NewEvent extends Fragment {

    private NewEventDescription newEventDescription = new NewEventDescription();
    private NewEventSpecify newEventSpecify = new NewEventSpecify();
    private NewEventRequirements newEventRequirements = new NewEventRequirements();
    private NewEventInvite newEventInvite =new NewEventInvite();
    private TabLayout tabLayout;
    private Button createButton;
    private Button nextButton;
    private Button previousButton;

    public NewEvent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_event, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Funcionalidades.showSelectedFragment(R.id.newEventContainer,getActivity().getSupportFragmentManager(),newEventDescription);
        createButton = getActivity().findViewById(R.id.createtNewEventButton);
        nextButton = getActivity().findViewById(R.id.buttonNext);
        previousButton = getActivity().findViewById(R.id.buttonPrevious);
        tabLayout = getActivity().findViewById(R.id.tabs);
        activarBotonNext();

        createButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBoton((Button)v,getActivity().getApplication());
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
            }
        });

        nextButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBotonSimple((Button) v,getActivity().getApplication());
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
                tabLayout.getTabAt(tabLayout.getSelectedTabPosition()+1).select();
            }
        });

        previousButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresBotonSimple((Button) v,getActivity().getApplication());
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
                tabLayout.getTabAt(tabLayout.getSelectedTabPosition()-1).select();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getResources().getString(R.string.tab_description))) {
                    Funcionalidades.showSelectedFragment(R.id.newEventContainer,getActivity().getSupportFragmentManager(),newEventDescription);
                    activarBotonNext();
                }
                else if (tab.getText().toString().equals(getResources().getString(R.string.tab_specify))) {
                    Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventSpecify);
                    activarBotonNextPrevious();
                }
                else if (tab.getText().toString().equals(getResources().getString(R.string.tab_requirements))) {
                    Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventRequirements);
                    activarBotonNextPrevious();
                }
                else if (tab.getText().toString().equals(getResources().getString(R.string.tab_invite))) {
                    Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventInvite);
                    activarBotonPrevious();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void activarBotonNext(){
        nextButton.setVisibility(View.VISIBLE);
        previousButton.setVisibility(View.GONE);
    }
    private void activarBotonPrevious(){
        nextButton.setVisibility(View.GONE);
        previousButton.setVisibility(View.VISIBLE);
    }
    private void activarBotonNextPrevious(){
        nextButton.setVisibility(View.VISIBLE);
        previousButton.setVisibility(View.VISIBLE);
    }
}
