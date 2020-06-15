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

@Path("/login")
public class Login { 

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON})
	public Response login(@FormParam("emailUsuario") String emailUsuario, @FormParam("passwordUsuario") String passwordUsuario) {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		if(usuarioDAO.loginUsuario(emailUsuario, passwordUsuario)) {
			JwtProvider jwt = new JwtProvider();
			String token = jwt.generarToken(emailUsuario);
			
			Usuario user = usuarioDAO.cogerUsuario(emailUsuario);
			user.setListaAmigos(usuarioDAO.listaAmigos(emailUsuario));
			user.setListaBloqueados(usuarioDAO.listaBloqueados(emailUsuario));
			
			return Response
					.status(Status.OK)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.entity(user)
					.build();
		}
		
		return Response
				.status(Status.UNAUTHORIZED)
				.build();
	}
}
