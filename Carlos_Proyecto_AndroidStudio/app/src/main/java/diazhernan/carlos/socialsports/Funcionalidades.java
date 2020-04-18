package diazhernan.carlos.socialsports;

import android.app.Application;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Funcionalidades extends AppCompatActivity {

    public static void cambiarColoresTexto(EditText et, Application application){
        if (et.isFocused()) {
            et.setTextColor(application.getResources().getColor(R.color.colorAccent));
            et.getBackground().setTint(application.getResources().getColor(R.color.colorAccent));
        }
        else {
            et.setTextColor(application.getResources().getColor(R.color.colorElements));
            et.getBackground().setTint(application.getResources().getColor(R.color.colorElements));
        }
    }

    public static void cambiarColoresBoton(Button button, Application application){
        if (button.isFocused()) {
            button.setTextColor(application.getResources().getColor(R.color.colorAccent));
            button.setBackground(application.getResources().getDrawable(R.drawable.boton1_selected));
        }
        else {
            button.setTextColor(application.getResources().getColor(R.color.colorElements));
            button.setBackground(application.getResources().getDrawable(R.drawable.boton1));
        }
    }

    public static void cambiarColoresBotonSimple(Button button, Application application){
        if (button.isFocused())
            button.setTextColor(application.getResources().getColor(R.color.colorAccent));
        else
            button.setTextColor(application.getResources().getColor(R.color.colorElements));
    }

    public static void mostrarMensaje(String mensaje, Context context){
        Toast toast = Toast.makeText(context,mensaje,Toast.LENGTH_LONG);
        toast.show();
    }

    //Mostrar un fragment determinado en un elemento de tipo "R.id.container"(int) determindo.
    public static void showSelectedFragment(int container, FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
