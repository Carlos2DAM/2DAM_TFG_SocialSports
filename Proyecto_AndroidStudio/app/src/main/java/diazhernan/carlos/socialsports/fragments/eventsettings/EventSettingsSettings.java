package diazhernan.carlos.socialsports.fragments.eventsettings;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;
import diazhernan.carlos.socialsports.fragments.newevent.NewEventRequirements;

public class EventSettingsSettings extends Fragment {

    private LinearLayout rowOrganizerReputation;
    private RatingBar ratingBarOrganizer;
    private EditText editFecha;
    private EditText editHora;
    private EditText participantes;
    private EditText direccion;
    private CheckBox reserva;
    private EditText coste;
    private EditText precio;
    private CheckBox notParticipant;
    private EditText comentarios;
    private Date date = null;
    private Calendar newCalendar;
    private DatePickerDialog dialogoCalendario;
    private TimePickerDialog dialogoTime;
    private Time newTime = null;
    private int anio;
    private int mes;
    private int dia;
    private int hora;
    private int minutos;
    private CheckBox checkBoxMinAge;
    private CheckBox checkBoxMaxAge;
    private CheckBox checkBoxGender;
    private CheckBox checkBoxReputation;
    private TableRow rowMinAge;
    private TableRow rowMaxAge;
    private SeekBar barMinAge;
    private SeekBar barMaxAge;
    private RadioGroup groupGender;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private TextView textMinAge;
    private TextView textMaxAge;
    private TextView textReputation;
    private RatingBar ratingReputation;

    public EventSettingsSettings() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_settings_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rowOrganizerReputation = getActivity().findViewById(R.id.rowEventSettingsStars);
        ratingBarOrganizer = getActivity().findViewById(R.id.ratingSettingsOrganizerReputation);
        editFecha = getActivity().findViewById(R.id.editSettingsDate);
        editHora = getActivity().findViewById(R.id.editSettingsTime);
        participantes = getActivity().findViewById(R.id.editSettingsParticipants);
        direccion = getActivity().findViewById(R.id.editSettingsAddress);
        reserva = getActivity().findViewById(R.id.checkSettingsReserved);
        coste = getActivity().findViewById(R.id.editSettingsCost);
        precio = getActivity().findViewById(R.id.editSettingsPrice);
        notParticipant = getActivity().findViewById(R.id.checkSettingsNoParticipant);
        comentarios = getActivity().findViewById(R.id.editSettingsComments);

        checkBoxMinAge = getActivity().findViewById(R.id.checkSettingsMinAge);
        checkBoxMaxAge = getActivity().findViewById(R.id.checkSettingsMaxAge);
        checkBoxGender = getActivity().findViewById(R.id.checkSettingsGender);
        checkBoxReputation = getActivity().findViewById(R.id.checkSettingsReputation);
        rowMinAge = getActivity().findViewById(R.id.rowSettingsMinAge);
        rowMaxAge = getActivity().findViewById(R.id.rowSettingsMaxAge);
        barMinAge = getActivity().findViewById(R.id.seekBarSettingsMinAge);
        barMaxAge = getActivity().findViewById(R.id.seekBarSettingsMaxAge);
        groupGender = getActivity().findViewById(R.id.groupSettingsGender);
        radioMale = getActivity().findViewById(R.id.radioSettingsMale);
        radioFemale = getActivity().findViewById(R.id.radioSettingsFemale);
        textMinAge = getActivity().findViewById(R.id.textSettingsMinAge);
        textMaxAge = getActivity().findViewById(R.id.textSettingsMaxAge);
        textReputation = getActivity().findViewById(R.id.textSettingsReputation);
        ratingReputation = getActivity().findViewById(R.id.ratingSettingsReputation);
        barMaxAge.setProgress(NewEventRequirements.MaxAge);
        barMinAge.setProgress(NewEventRequirements.MinAge);
        rowMinAge.setVisibility(View.GONE);
        rowMaxAge.setVisibility(View.GONE);
        groupGender.setVisibility(View.GONE);
        ratingReputation.setEnabled(false);

        newCalendar = Calendar.getInstance();
        dialogoCalendario = new DatePickerDialog(getContext(), R.style.calenderDialogCustom, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newCalendar.set(year, monthOfYear, dayOfMonth, 22, 0, 0);
                date = newCalendar.getTime();
                anio = year;
                mes = monthOfYear;
                dia = dayOfMonth;
                if ((fechaCorrecta(newCalendar.getTime()) && newTime == null) || getFechaEvento() != null) {
                    editFecha.setText(Funcionalidades.dateToString(date));
                }
                else {
                    Funcionalidades.mostrarMensaje("Fecha errónea.", getContext());
                    date = null;
                    editFecha.setText("");
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dialogoTime = new TimePickerDialog(getContext(), R.style.timerDialogCustom, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora = hourOfDay;
                minutos = minute;
                newTime = new Time(0);
                if (date == null || (date != null && getFechaEvento() != null)) {
                    mostrarHoraSeleccionada();
                }
                else {
                    Funcionalidades.mostrarMensaje("Hora errónea.", getContext());
                    newTime = null;
                    editHora.setText("");
                }
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MINUTE),true);

        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                    dialogoCalendario.show();
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
            }
        });
        editHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario()))
                    dialogoTime.show();
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
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
                Funcionalidades.esconderTeclado(getActivity(),getContext(),getView());
                if (isChecked)
                    coste.setVisibility(View.VISIBLE);
                else
                    coste.setVisibility(View.INVISIBLE);
            }
        });
        notParticipant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Funcionalidades.esconderTeclado(getActivity(),getContext(),getView());
            }
        });

        checkBoxMinAge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rowMinAge.setVisibility(View.VISIBLE);
                    textMinAge.setText(Integer.toString(barMinAge.getProgress()));
                }
                else {
                    rowMinAge.setVisibility(View.GONE);
                    barMinAge.setProgress(NewEventRequirements.MinAge);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        barMaxAge.setMin(NewEventRequirements.MinAge);
                    }
                }
            }
        });
        checkBoxMaxAge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rowMaxAge.setVisibility(View.VISIBLE);
                    textMaxAge.setText(Integer.toString(barMaxAge.getProgress()));
                }
                else {
                    rowMaxAge.setVisibility(View.GONE);
                    barMaxAge.setProgress(NewEventRequirements.MaxAge);
                    barMinAge.setMax(NewEventRequirements.MaxAge);
                }
            }
        });
        barMinAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textMinAge.setText(Integer.toString(progress));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    barMaxAge.setMin(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        barMaxAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textMaxAge.setText(Integer.toString(progress));
                barMinAge.setMax(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        checkBoxGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    groupGender.setVisibility(View.VISIBLE);
                else
                    groupGender.setVisibility(View.GONE);
            }
        });
        checkBoxReputation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ratingReputation.setEnabled(true);
                    textReputation.setText(Float.toString(ratingReputation.getRating()));
                }
                else {
                    ratingReputation.setEnabled(false);
                    textReputation.setText("");
                }
            }
        });
        ratingReputation.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textReputation.setText(Float.toString(ratingBar.getRating()));
            }
        });

        cargarDatosEvento();
    }

    private void mostrarHoraSeleccionada() {
        String cadHora = Integer.toString(hora);
        String cadMin = Integer.toString(minutos);
        if (cadHora.length()==1)
            cadHora="0"+cadHora;
        if (cadMin.length()==1)
            cadMin="0"+cadMin;
        editHora.setText(cadHora+":"+cadMin);
    }

    private boolean fechaCorrecta(Date fecha) {
        return fecha.after(new Date());
    }

    //Devuelve la fecha seleccionada, siempre que ésta sea una fecha futura.
    public Date getFechaEvento() {
        if (date!=null) {
            newCalendar.set(anio,mes,dia,0,0,0);
            if (newTime!=null) {
                newCalendar.set(anio,mes,dia,hora,minutos);
            }
            date = newCalendar.getTime();
            if (date.after(new Date()))
                return date;
        }
        return null;
    }

    public String getHoraEvento() {
        return editHora.getText().toString().toUpperCase();
    }

    public int getNumParticipantes() {
        if (participantes.length()>0)
            return Integer.parseInt(participantes.getText().toString());
        return -1;
    }

    public String getDireccion() {
        return direccion.getText().toString().toUpperCase();
    }

    public boolean getResevaRealizada() {
        return reserva.isChecked();
    }

    public float getCosteReserva() {
        if (reserva.isChecked()) {
            if (coste.length()>0)
                return Float.parseFloat(coste.getText().toString());
        }
        return -1;
    }

    public float getPrecioIndividual() {
        if (precio.length()>0)
            return Float.parseFloat(precio.getText().toString());
        return -1;
    }

    public String getComentarios() {
        return comentarios.getText().toString().toUpperCase();
    }

    public boolean getElOrganizadorEsParticipante() {
        return (!notParticipant.isChecked());
    }

    public int getEdadMinima() {
        if (checkBoxMinAge.isChecked())
            return barMinAge.getProgress();
        return -1;
    }

    public int getEdadMaxima() {
        if (checkBoxMaxAge.isChecked())
            return barMaxAge.getProgress();
        return -1;
    }

    public String getGenero() {
        if (checkBoxGender.isChecked()) {
            if (radioMale.isChecked())
                return ("Male").toUpperCase();
            if (radioFemale.isChecked())
                return ("Female").toUpperCase();
        }
        return null;
    }

    public float getReputacion() {
        if (checkBoxReputation.isChecked())
            return ratingReputation.getRating();
        return -1;
    }

    private void cargarDatosEvento() {
        if (!Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getEmailUsuario().equals(LoginActivity.usuario.getEmailUsuario())) {
            participantes.setFocusable(false);
            direccion.setFocusable(false);
            reserva.setClickable(false);
            coste.setFocusable(false);
            precio.setFocusable(false);
            notParticipant.setClickable(false);
            comentarios.setFocusable(false);
            checkBoxMinAge.setClickable(false);
            checkBoxMaxAge.setClickable(false);
            checkBoxGender.setClickable(false);
            checkBoxReputation.setClickable(false);
            barMinAge.setEnabled(false);
            barMaxAge.setEnabled(false);
            radioMale.setEnabled(false);
            radioFemale.setEnabled(false);
            ratingReputation.setIsIndicator(true);
            ratingBarOrganizer.setRating(Funcionalidades.eventoSeleccionado.getOrganizadorEvento().getReputacionOrganizadorUsuario());
        }else
            rowOrganizerReputation.setVisibility(View.GONE);

        if (Funcionalidades.eventoSeleccionado.getFechaEvento() != null)
            editFecha.setText(Funcionalidades.dateToString(Funcionalidades.eventoSeleccionado.getFechaEvento()).toUpperCase());
        editHora.setText(Funcionalidades.eventoSeleccionado.getHoraEvento());
        if (Funcionalidades.eventoSeleccionado.getMaximoParticipantes() > -1)
            participantes.setText(Integer.toString(Funcionalidades.eventoSeleccionado.getMaximoParticipantes()));
        direccion.setText(Funcionalidades.eventoSeleccionado.getDireccion());
        reserva.setChecked(Funcionalidades.eventoSeleccionado.isInstalacionesReservadas());
        if (Funcionalidades.eventoSeleccionado.getCosteEvento() > -1)
            coste.setText(Float.toString(Funcionalidades.eventoSeleccionado.getCosteEvento()));
        if (Funcionalidades.eventoSeleccionado.getPrecioPorParticipante() > -1)
            precio.setText(Float.toString(Funcionalidades.eventoSeleccionado.getPrecioPorParticipante()));
        notParticipant.setChecked(!Funcionalidades.eventoSeleccionado.getListaParticipantes().contains(Funcionalidades.eventoSeleccionado.getOrganizadorEvento()));
        comentarios.setText(Funcionalidades.eventoSeleccionado.getComentarios());
        if (Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMinima() > NewEventRequirements.MinAge) {
            checkBoxMinAge.setChecked(true);
            barMinAge.setProgress(Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMinima());
        }
        if (Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMaxima() != -1 && Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMaxima() < NewEventRequirements.MaxAge) {
            checkBoxMaxAge.setChecked(true);
            barMaxAge.setProgress(Funcionalidades.eventoSeleccionado.getRequisitos().getEdadMaxima());
        }
        if (Funcionalidades.eventoSeleccionado.getRequisitos().getRequisitoDeGenero() != null
                && !Funcionalidades.eventoSeleccionado.getRequisitos().getRequisitoDeGenero().isEmpty()) {
            checkBoxGender.setChecked(true);
            if (Funcionalidades.eventoSeleccionado.getRequisitos().getRequisitoDeGenero().toUpperCase().equals("MALE"))
                radioMale.setChecked(true);
            else if (Funcionalidades.eventoSeleccionado.getRequisitos().getRequisitoDeGenero().toUpperCase().equals("FEMALE"))
                radioFemale.setChecked(true);
        }
        if (Funcionalidades.eventoSeleccionado.getRequisitos().getReputacionNecesaria() > 0) {
            checkBoxReputation.setChecked(true);
            ratingReputation.setRating(Funcionalidades.eventoSeleccionado.getRequisitos().getReputacionNecesaria());
        }
    }
}
