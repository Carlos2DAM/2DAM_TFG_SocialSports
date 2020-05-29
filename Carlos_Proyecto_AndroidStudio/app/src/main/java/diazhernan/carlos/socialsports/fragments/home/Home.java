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

import diazhernan.carlos.socialsports.LoginActivity;
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
}
