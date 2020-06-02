package diazhernan.carlos.socialsports.fragments.userconfig;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import diazhernan.carlos.socialsports.Funcionalidades;
import diazhernan.carlos.socialsports.LoginActivity;
import diazhernan.carlos.socialsports.R;

public class UserConfigSettings extends Fragment {

    private TextView textEmail;
    private RatingBar ratingBarUser;
    private RatingBar ratingBarOrganizer;
    private EditText editNombre;
    private EditText editApellido;
    private EditText editDireccion;
    private EditText editNacimiento;
    private EditText editNewpass;
    private EditText editRepeatpass;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private Date birthdate;
    private DatePickerDialog dialogoCalendario;
    private Calendar newCalendar;

    public UserConfigSettings() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_config_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textEmail = getActivity().findViewById(R.id.textUserConfigEmail);
        ratingBarUser = getActivity().findViewById(R.id.ratingUserConfigReputation);
        ratingBarOrganizer = getActivity().findViewById(R.id.ratingUserConfigOrganizer);
        editNombre = getActivity().findViewById(R.id.editUserConfigNombre);
        editApellido = getActivity().findViewById(R.id.editUserConfigApellidos);
        editDireccion = getActivity().findViewById(R.id.editUserConfigDireccion);
        editNacimiento = getActivity().findViewById(R.id.editUserBirthday);
        editNewpass = getActivity().findViewById(R.id.editUserConfigNewpass);
        editRepeatpass = getActivity().findViewById(R.id.editUserConfigRepeatpass);
        radioMale = getActivity().findViewById(R.id.radioUserConfigMale);
        radioFemale = getActivity().findViewById(R.id.radioUserConfigFemale);
        //TODO permitir cargar una imagen de perfil.

        newCalendar = Calendar.getInstance();
        dialogoCalendario = new DatePickerDialog(getContext(), R.style.calenderDialogCustom, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newCalendar.set(year, monthOfYear, dayOfMonth);
                birthdate = newCalendar.getTime();
                editNacimiento.setText(Funcionalidades.dateToString(birthdate));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        editNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoCalendario.show();
                Funcionalidades.esconderTeclado(getActivity(),getContext(),v);
            }
        });

        textEmail.setText(LoginActivity.usuario.getEmailUsuario());
        ratingBarUser.setRating(LoginActivity.usuario.getReputacionParticipanteUsuario());
        ratingBarOrganizer.setRating(LoginActivity.usuario.getReputacionOrganizadorUsuario());
        editNombre.setText(LoginActivity.usuario.getNombreUsuario());
        editApellido.setText(LoginActivity.usuario.getApellidosUsuario());
        editDireccion.setText(LoginActivity.usuario.getDireccionUsuario());
        editNacimiento.setText(Funcionalidades.dateToString(LoginActivity.usuario.getFechaNacimientoUsuario()));
        if (LoginActivity.usuario.getGeneroUsuario().toUpperCase().equals("MALE"))
            radioMale.setChecked(true);
        else if (LoginActivity.usuario.getGeneroUsuario().toUpperCase().equals("FEMALE"))
            radioFemale.setChecked(true);
        birthdate = LoginActivity.usuario.getFechaNacimientoUsuario();
    }

    public String getNombre() {
        return editNombre.getText().toString();
    }

    public String getApellido() {
        return editApellido.getText().toString();
    }

    public String getDireccion() {
        return editDireccion.getText().toString();
    }

    public String getNewpass() {
        return editNewpass.getText().toString();
    }

    public String getRepeatpass() {
        return editRepeatpass.getText().toString();
    }

    public String getGenero() {
        if (radioMale.isChecked())
            return "MALE";
        else if (radioFemale.isChecked())
            return "FEMALE";
        return "";
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
