package seguridad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
	public boolean validarCorreo(String correo) {
		/*Pattern p = Pattern.compile("[\\w-.]+[\\w]*@[\\w]*[.]{1}[a-z]*$"); 
		Matcher m = p.matcher(correo);
		boolean valido = m.matches();
		return valido;*/
		return true;
	}
	
	public boolean validarContrasena(String contrasena) {
		return true;
	}
}
