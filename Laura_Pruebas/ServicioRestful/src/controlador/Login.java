package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Hex;

import security.PasswordHash;
import security.Validaciones;
import modelo.Usuario;
import dao.Conexion;

@Path("/login")
public class Login {
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response login(Usuario usuario) {
		
		Validaciones validaciones = new Validaciones();
		String correo = usuario.getCorreo();
		String contrasena = usuario.getContrasena();
		
		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
			try {
				Conexion conexion = new Conexion();
				PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT CORREO, CONTRASENA FROM USUARIOS WHERE CORREO = ?");
				ps.setString(1, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
				
					PasswordHash hash = new PasswordHash();
			        String hashedString = hash.getHash(contrasena);
			        
			        System.out.println(hashedString);
			        
					if(hashedString.equals(rs.getObject(2))) {
						System.out.println("si");
					}else {
						System.out.println("no");
					}
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return Response.status(Response.Status.OK).build();
	}
}
