package diazhernan.carlos.socialsports.Clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.List;

import diazhernan.carlos.socialsports.EventRate;
import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;

public class AdaptadorListaUsuarios extends ArrayAdapter<Usuario> {

    private int layoutFila;
    private Context context;
    private boolean bloquearPuntuar;

    public AdaptadorListaUsuarios(@NonNull Context context, int resource, int textViewResourceId, @NonNull Usuario[] objects) {
        super(context, resource, textViewResourceId, objects);
        layoutFila = resource;
        this.context = context;
        bloquearPuntuar = true;
    }

    public AdaptadorListaUsuarios(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Usuario> objects) {
        super(context, resource, textViewResourceId, objects);
        layoutFila = resource;
        this.context = context;
        bloquearPuntuar = true;
    }

    public AdaptadorListaUsuarios(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Usuario> objects, boolean bloquear) {
        super(context, resource, textViewResourceId, objects);
        layoutFila = resource;
        this.context = context;
        bloquearPuntuar = bloquear;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Usuario usuario = getItem(position);
        if (!LoginActivity.usuario.getEmailUsuario().equals(usuario.getEmailUsuario()) || bloquearPuntuar) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View fila = inflater.inflate(layoutFila, parent, false);

            usuario = getItem(position);
            RatingBar ratingBar = fila.findViewById(R.id.ratingItemUsuarioReputation);
            ratingBar.setIsIndicator(bloquearPuntuar);
            if (bloquearPuntuar)
                ratingBar.setRating(usuario.getReputacionParticipanteUsuario());
            else {
                ratingBar.setNextFocusDownId(EventRate.listaPuntuaciones.size());
                PuntuacionParticipante puntuacion = new PuntuacionParticipante(LoginActivity.usuario.getEmailUsuario(),
                        usuario.getEmailUsuario(),Funcionalidades.eventoSeleccionado.getIdEvento(),0f);
                EventRate.listaPuntuaciones.add(puntuacion);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        EventRate.listaPuntuaciones.get(ratingBar.getNextFocusDownId()).setCalificacion(rating);
                    }
                });
            }
            TextView textNombre = fila.findViewById(R.id.textItemUsuarioNombre);
            textNombre.setText(usuario.getNombreUsuario().toUpperCase() + " " + usuario.getApellidosUsuario().toUpperCase());
            TextView textGenero = fila.findViewById(R.id.textItemUsuarioGenero);
            TextView textEdad = fila.findViewById(R.id.textItemUsuarioEdad);
            ImageView image = fila.findViewById(R.id.imageItemUsuario);

            if (usuario.getGeneroUsuario() != null)
                textGenero.setText(usuario.getGeneroUsuario().toUpperCase());
            else
                textGenero.setText(context.getResources().getString(R.string.undefined_gender).toUpperCase());
            int edad = -1;
            if (usuario.getFechaNacimientoUsuario() != null)
                edad = Funcionalidades.calcularEdad(usuario.getFechaNacimientoUsuario());
            if (edad != -1)
                textEdad.setText(edad + " " + context.getResources().getString(R.string.years_old).toUpperCase());
            else
                textEdad.setText(context.getResources().getString(R.string.unknow_age).toUpperCase());

            if(!usuario.getFotoPerfilUsuario().equals("") && usuario.getFotoPerfilUsuario() != null){
                byte[] data = Base64.decode(usuario.getFotoPerfilUsuario().getBytes(), Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                image.setImageBitmap(decodedBitmap);
            }

            return fila;
        }
        return new View(getContext());
    }
}
