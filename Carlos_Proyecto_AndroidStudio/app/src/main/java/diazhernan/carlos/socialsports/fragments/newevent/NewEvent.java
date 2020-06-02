package diazhernan.carlos.socialsports.fragments.newevent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.Requisitos;
import diazhernan.carlos.socialsports.Clases.Usuario;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class NewEvent extends Fragment {

    private NewEventDescription newEventDescription = new NewEventDescription();
    private NewEventSpecify newEventSpecify = new NewEventSpecify();
    private NewEventRequirements newEventRequirements = new NewEventRequirements();
    private NewEventInvite newEventInvite = new NewEventInvite();
    private TabLayout tabLayout;
    private Button createButton;
    private Button nextButton;
    private Button previousButton;
    private Evento eventoCreado = null;

    public NewEvent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_event, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createButton = getActivity().findViewById(R.id.createNewEventButton);
        nextButton = getActivity().findViewById(R.id.buttonNext);
        previousButton = getActivity().findViewById(R.id.buttonPrevious);
        tabLayout = getActivity().findViewById(R.id.tabs);
        Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventSpecify);
        Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventRequirements);
        Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventInvite);

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
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
                v.setFocusableInTouchMode(false);
                if (obtenerParametrosIntroducidos())
                    crearEvento();
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
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
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
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
                v.setFocusableInTouchMode(false);
                tabLayout.getTabAt(tabLayout.getSelectedTabPosition()-1).select();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Funcionalidades.esconderTeclado(getActivity(),getContext(),getView());
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
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
        Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventDescription);
        activarBotonNext();
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

    private boolean obtenerParametrosIntroducidos() {
        String idEv,deporte,localidad,direccion,horaEv,comments;
        deporte = newEventDescription.getDeporte().toUpperCase();
        localidad = newEventDescription.getLocalidad().toUpperCase();
        if (deporte.equals("") || localidad.equals("")) {
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_incomplete_data), getContext());
            return false;
        }
        Date fechaEv,fechaCreado=new Date();
        int maxParticipantes;
        boolean reserva;
        Requisitos requisitos = new Requisitos();
        float coste,precio;
        direccion = newEventSpecify.getDireccion().toUpperCase();
        fechaEv = newEventSpecify.getFechaEvento();
        horaEv = newEventSpecify.getHoraEvento();
        maxParticipantes = newEventSpecify.getNumParticipantes();
        reserva = newEventSpecify.getResevaRealizada();
        coste = newEventSpecify.getCosteReserva();
        precio = newEventSpecify.getPrecioIndividual();
        comments = newEventSpecify.getComentarios();
        requisitos.setEdadMinima(newEventRequirements.getEdadMinima());
        requisitos.setEdadMaxima(newEventRequirements.getEdadMaxima());
        requisitos.setRequisitoDeGenero(newEventRequirements.getGenero().toUpperCase());
        requisitos.setReputacionNecesaria(newEventRequirements.getReputacion());
        idEv = LoginActivity.usuario.getEmailUsuario()+"_"+Funcionalidades.dateToStringLargo(fechaCreado);
        ArrayList<Usuario> listaP = new ArrayList<>();
        if (newEventSpecify.getElOrganizadorEsParticipante())
            listaP.add(LoginActivity.usuario);
        eventoCreado = new Evento(idEv,LoginActivity.usuario,deporte,localidad,direccion,fechaEv,
                horaEv,fechaCreado,maxParticipantes,reserva,coste,precio,comments,requisitos,
                false,new ArrayList<Usuario>(),new ArrayList<Usuario>(),listaP);
        return true;
    }

    private void crearEvento() {
        if (Funcionalidades.guardarEvento(eventoCreado)) {
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_evento_creado),getContext());
            if (Funcionalidades.enviarInvitaciones(eventoCreado,newEventInvite.getListaInvitarAmigos())) {
                for (Usuario usuario: LoginActivity.usuario.getListaAmigos()) {
                    if (newEventInvite.getListaInvitarAmigos().contains(usuario.getEmailUsuario()))
                        eventoCreado.getListaParticipantes().add(usuario);
                }
            }
            MainActivity.listaEventos.add(eventoCreado);  // TODO borrar codigo provisional
            newEventDescription = new NewEventDescription();
            newEventSpecify = new NewEventSpecify();
            newEventRequirements = new NewEventRequirements();
            newEventInvite = new NewEventInvite();
            eventoCreado = null;
            Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventSpecify);
            Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventRequirements);
            Funcionalidades.showSelectedFragment(R.id.newEventContainer, getActivity().getSupportFragmentManager(), newEventInvite);
            tabLayout.getTabAt(0).select();
        }
        else
            Funcionalidades.mostrarMensaje(getResources().getString(R.string.mensaje_error_evento_creado),getContext());
    }
}
