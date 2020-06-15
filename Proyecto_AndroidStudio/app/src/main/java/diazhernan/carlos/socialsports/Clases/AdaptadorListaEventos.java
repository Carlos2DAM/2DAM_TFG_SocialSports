package diazhernan.carlos.socialsports.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;

public class AdaptadorListaEventos extends ArrayAdapter<Evento> {

    private int layoutFila;
    private Context context;

    public AdaptadorListaEventos(@NonNull Context context, int resource, int textViewResourceId, @NonNull Evento[] objects) {
        super(context, resource, textViewResourceId, objects);
        layoutFila = resource;
        this.context = context;
    }

    public AdaptadorListaEventos(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Evento> objects) {
        super(context, resource, textViewResourceId, objects);
        layoutFila = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View fila = inflater.inflate(layoutFila, parent, false);

        TextView deporte = fila.findViewById(R.id.textItemEventoDeporte);
        TextView localidad = fila.findViewById(R.id.textItemEventoLocalidad);
        TextView fecha = fila.findViewById(R.id.textItemEventoFecha);
        TextView reserva = fila.findViewById(R.id.textItemEventoReserva);
        TextView precio = fila.findViewById(R.id.textItemEventoPrecio);
        TextView organizador = fila.findViewById(R.id.textItemEventoOrganizador);
        TextView estado = fila.findViewById(R.id.textItemEventoEstado);
        Evento evento = getItem(position);

        deporte.setText(evento.getDeporte().toUpperCase());
        if(evento.getLocalidad() != null) localidad.setText(evento.getLocalidad().toUpperCase());
        if (evento.getFechaEvento() != null)
            fecha.setText(Funcionalidades.dateToString(evento.getFechaEvento()));
        else
            fecha.setText(context.getResources().getString(R.string.sin_fecha));
        if (!evento.isTerminado()) {
            if (evento.isInstalacionesReservadas())
                reserva.setText(context.getResources().getString(R.string.reserved));
            else
                reserva.setText(context.getResources().getString(R.string.no_reserved));
            if (evento.getPrecioPorParticipante() > -1)
                precio.setText(Float.toString(evento.getPrecioPorParticipante())+" "+context.getResources().getString(R.string.moneda));
            else
                precio.setText("0.0 "+context.getResources().getString(R.string.moneda));
            if (evento.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                organizador.setText(context.getResources().getString(R.string.organizer)+" "+context.getResources().getString(R.string.me));
            else
                organizador.setText(context.getResources().getString(R.string.organizer)+" "+evento.getOrganizadorEvento().getNombreUsuario());
            if (evento.getMaximoParticipantes() > 0 && evento.getMaximoParticipantes() <= evento.getListaParticipantes().size()) {
                estado.setText(context.getResources().getString(R.string.full));
                estado.setTextColor(context.getResources().getColor(R.color.full));
            } else {
                estado.setText(context.getResources().getString(R.string.vacancies));
                estado.setTextColor(context.getResources().getColor(R.color.vacancies));
            }
        }
        else {
            reserva.setText(context.getResources().getString(R.string.rate_participants));
            reserva.setTextColor(context.getResources().getColor(R.color.puntuar));
            precio.setVisibility(View.GONE);
            organizador.setVisibility(View.GONE);
            estado.setVisibility(View.GONE);
        }

        return fila;
    }
}
