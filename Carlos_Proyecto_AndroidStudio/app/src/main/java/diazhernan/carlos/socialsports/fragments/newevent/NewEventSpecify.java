package diazhernan.carlos.socialsports.fragments.newevent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.R;

public class NewEventSpecify extends Fragment {

    private Button btnFecha;
    private CalendarView calendario;
    private EditText hora;
    private EditText minutos;
    private EditText participantes;
    private EditText direccion;
    private CheckBox reserva;
    private EditText coste;
    private EditText precio;
    private EditText comentarios;
    private Date date = new Date();

    public NewEventSpecify() {    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_event_specify, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnFecha = getActivity().findViewById(R.id.buttonSpecifyDate);
        calendario = getActivity().findViewById(R.id.calendarSpecifyDate);
        hora = getActivity().findViewById(R.id.editSpecifyHuor);
        minutos = getActivity().findViewById(R.id.editSpecifyMinutes);
        participantes = getActivity().findViewById(R.id.editSpecifyParticipants);
        direccion = getActivity().findViewById(R.id.editSpecifyAddress);
        reserva = getActivity().findViewById(R.id.checkSpecifyReserved);
        coste = getActivity().findViewById(R.id.editSpecifyCost);
        precio = getActivity().findViewById(R.id.editSpecifyPrice);
        comentarios = getActivity().findViewById(R.id.editSpecifyComments);
        calendario.setVisibility(View.GONE);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }
        });
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = new Date((year-1900),month,dayOfMonth);
                btnFecha.setVisibility(View.VISIBLE);
                mostrarFechaSeleccionada(date);
                view.setVisibility(View.GONE);
            }
        });
        hora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
                if (hora.getText().length()>0) {
                    if (Integer.parseInt(hora.getText().toString())>23)
                        hora.setText("23");
                    else if (hora.getText().length()<2)
                        hora.setText("0"+hora.getText().toString());
                }
            }
        });
        minutos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
                if (minutos.getText().length()>0) {
                    if (Integer.parseInt(minutos.getText().toString())>59)
                        minutos.setText("59");
                    else if (minutos.getText().length()<2)
                        minutos.setText("0"+minutos.getText().toString());
                }
            }
        });
        participantes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
        direccion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
        coste.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
        precio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
        comentarios.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Funcionalidades.cambiarColoresTexto((EditText)v,getActivity().getApplication());
            }
        });
        reserva.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    coste.setVisibility(View.VISIBLE);
                else
                    coste.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        calendario.setVisibility(View.GONE);
        btnFecha.setVisibility(View.VISIBLE);
        if (date != null)
            mostrarFechaSeleccionada(date);
        else
            mostrarFechaSeleccionada(new Date());
    }

    private void mostrarFechaSeleccionada(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("E dd MMM yyyy");
        btnFecha.setText(getActivity().getResources().getString(R.string.specify_date)+"   "+formato.format(fecha));
    }
}
