package diazhernan.carlos.socialsports.fragments.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.MainActivity;
import diazhernan.carlos.socialsports.R;

public class Home extends Fragment {

    private TextView textDescripcion;
    private ViewFlipper flipper;
    private RatingBar ratingBarUser;

    public Home() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textDescripcion = getActivity().findViewById(R.id.textDescripcionFoto);
        ratingBarUser = getActivity().findViewById(R.id.ratingUserHome);
        flipper = getActivity().findViewById(R.id.flipperHome);
        obtenerProximosEventos();
        if (flipper.getChildCount()>1)
            flipper.startFlipping();
        flipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ImageView imagen = (ImageView) flipper.getCurrentView();
                textDescripcion.setText(imagen.getContentDescription());
            }
        });
        ImageView imagen = (ImageView) flipper.getCurrentView();
        textDescripcion.setText(imagen.getContentDescription());
        ratingBarUser.setRating(LoginActivity.usuario.getReputacionParticipanteUsuario());
    }

    private void obtenerProximosEventos() {
        ArrayList<Evento> nextEvents = Funcionalidades.eventosPendientes(MainActivity.listaEventos);
        for (Evento evento: nextEvents) {
            ImageView imagen = new ImageView(getContext());
            imagen.setAdjustViewBounds(true);
            imagen.setContentDescription(evento.getDeporte()+" - "+evento.getLocalidad()+" "+Funcionalidades.dateToString(evento.getFechaEvento()));
            imagen.setScaleType(ImageView.ScaleType.FIT_START);
            imagen.setImageResource(imagenAdecuada(evento.getDeporte()));
            flipper.addView(imagen);
        }

        if (nextEvents.size() < 1) {
            ImageView imagen = new ImageView(getContext());
            imagen.setAdjustViewBounds(true);
            imagen.setContentDescription(getResources().getString(R.string.wellcome)+"\n"+getResources().getString(R.string.no_events));
            imagen.setScaleType(ImageView.ScaleType.FIT_START);
            imagen.setImageResource(imagenAdecuada(""));
            flipper.addView(imagen);
        }
    }

    private int imagenAdecuada(String titulo) {
        if (titulo.toLowerCase().contains("futbol") || titulo.toLowerCase().contains("fútbol") ||
                titulo.toLowerCase().contains("soccer"))
            return R.drawable.futbol2;
        if (titulo.toLowerCase().contains("ciclismo") || titulo.toLowerCase().contains("bici") ||
                titulo.toLowerCase().contains("bicicleta"))
            return R.drawable.ciclismo;
        if (titulo.toLowerCase().contains("correr") || titulo.toLowerCase().contains("runnin") ||
                titulo.toLowerCase().contains("run"))
            return R.drawable.runin2;
        if (titulo.toLowerCase().contains("tenis") || titulo.toLowerCase().contains("tennis") ||
                titulo.toLowerCase().contains("raqueta"))
            return R.drawable.tenis;
        return R.drawable.defaultsport;
    }
}