package servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UsuarioDAO;
import modelo.Usuario;
import seguridad.JwtProvider;
import seguridad.Validaciones;

@Path("/registro")
public class Registro {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON})
	public Response registro(@FormParam("emailUsuario") String emailUsuario, @FormParam("passwordUsuario") String passwordUsuario) {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Validaciones validaciones = new Validaciones();

		if(validaciones.validarCorreo(emailUsuario) && validaciones.validarContrasena(passwordUsuario)) {
			
			if(usuarioDAO.registroUsuario(emailUsuario, passwordUsuario)) {
				JwtProvider jwt = new JwtProvider();
				String token = jwt.generarToken(emailUsuario);
				
				Usuario user = usuarioDAO.cogerUsuario(emailUsuario);
				
				return Response
						.status(Status.CREATED)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.entity(user)
						.build();
				
			}else if(usuarioDAO.existeCorreo(emailUsuario)){
				return Response
						.status(Status.CONFLICT)
						.entity("Este email ya está registrado.")
						.build();
			}
		}
		
		return Response.status(Status.OK).build();
	}
}


