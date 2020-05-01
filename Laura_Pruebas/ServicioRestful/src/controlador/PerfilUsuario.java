package controlador;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/perfil")
public class PerfilUsuario {

	@GET
	@Path("/eventospendientes")
	public Response eventosPendientes() {
		return null;
	}
	
	@GET
	@Path("/suscripcionespendientes")
	public Response suscripcionesPendientes() {
		return null;
	}
	
	@GET 
	@Path("/eventosrealizados")
	public Response eventosRealizados() {
		return null;
	}
	
	@GET 
	@Path("/datosusuario")
	@Produces({MediaType.APPLICATION_JSON})
	public Response datosUsuario() {
		return null;
	}

}
