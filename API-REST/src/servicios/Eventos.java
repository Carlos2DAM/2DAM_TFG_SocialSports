package servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.EventoDAO;
import modelo.Evento;
import modelo.PuntuacionEvento;
import seguridad.Secured;

@Path("/eventos")
public class Eventos {
	
	private EventoDAO eventoDAO;

	@Secured
	@POST
	@Path("/crear")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearEvento(Evento evento) {
		eventoDAO = new EventoDAO();
		String id = eventoDAO.crearEvento(evento);
		if(!id.equals("-1")) {
			evento.setIdEvento(id);
			eventoDAO.enviarInvitaciones(evento); 
			return Response.status(Status.CREATED).entity(evento).build();
		}
	
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@Secured
	@GET
	@Path("/pendientes/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eventosPendientes(@PathParam("correo") String correo) {
		eventoDAO = new EventoDAO();
		ArrayList<Evento> listaEventos = new ArrayList<>();
		listaEventos = eventoDAO.obtenerEventosPendientes(correo);
		return Response.status(Status.OK).entity(listaEventos).build();
	}
	
	@Secured
	@GET
	@Path("/finalizados/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eventosFinalizados(@PathParam("correo") String correo) {
		eventoDAO = new EventoDAO();
		ArrayList<Evento> listaEventos = new ArrayList<>();
		listaEventos = eventoDAO.obtenerEventosFinalizados(correo);
		return Response.status(Status.OK).entity(listaEventos).build();
	}
	
	@Secured
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEventos(@QueryParam("deporte") String deporte, @QueryParam("localidad") String localidad, @QueryParam("fecha") String fecha,
			@QueryParam("hora") String hora, @QueryParam("reservado") boolean reservado, @QueryParam("reputacion") float reputacion) {
		
		if(deporte == null && localidad == null && fecha == null && hora == null && reservado == false && reputacion == -1.0) {
			return Response.status(Status.BAD_REQUEST).build();
		}else {
			eventoDAO = new EventoDAO();
			ArrayList<Evento> listaEventos = eventoDAO.buscarEventoFiltrado(deporte, localidad, eventoDAO.StringToDate(fecha), hora, reservado, reputacion);
			return Response.status(Status.OK).entity(listaEventos).build();
		}
		
	}
	
	/******DATOS EVENTO******/
	
	@Secured
	@PUT
	@Path("/actualizar/fecha")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarFecha(@FormParam("idEvento") String idEvento, @FormParam("fecha") String fecha) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarFecha(idEvento, eventoDAO.StringToDate(fecha));
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/actualizar/hora")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarHora(@FormParam("idEvento") String idEvento, @FormParam("hora") String hora) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarHora(idEvento, hora);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/actualizar/direccion")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarDireccion(@FormParam("idEvento") String idEvento, @FormParam("direccion") String direccion) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarDireccion(idEvento, direccion);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/actualizar/maximoparticipantes")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarMaxParticipantes(@FormParam("idEvento") String idEvento, @FormParam("maxParticipantes") int maxParticipantes) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarMaxParticipantes(idEvento, maxParticipantes);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/actualizar/terminado")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarTerminarEvento(@FormParam("idEvento") String idEvento, @FormParam("terminado") boolean terminado) {
		eventoDAO = new EventoDAO();
		int n = 0;
		if(terminado) n = 1;
		
		boolean actualizado = eventoDAO.actualizarTerminarEvento(idEvento, n);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/reserva")
	public Response actualizarReserva(@FormParam("idEvento") String idEvento, @FormParam("reserva") boolean reserva) {
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarReserva(idEvento, reserva);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/coste")
	public Response actualizarCoste(@FormParam("idEvento") String idEvento, @FormParam("coste") float coste) {
		System.out.println(idEvento + " " + coste);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarCoste(idEvento, coste);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/precio")
	public Response actualizarPrecio(@FormParam("idEvento") String idEvento, @FormParam("precio") float precio) {
		System.out.println(idEvento + " " + precio);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarPrecio(idEvento, precio);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/comentarios")
	public Response actualizarComentarios(@FormParam("idEvento") String idEvento, @FormParam("comentarios") String comentarios) {
		System.out.println(idEvento + " " + comentarios);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarComentarios(idEvento, comentarios);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/edadminima")
	public Response actualizarEdadMinima(@FormParam("idEvento") String idEvento, @FormParam("edad") int edad) {
		System.out.println(idEvento + " " + edad);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarEdadMinima(idEvento, edad);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/edadmaxima")
	public Response actualizarEdadMaxima(@FormParam("idEvento") String idEvento, @FormParam("edad") int edad) {
		System.out.println(idEvento + " " + edad);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarEdadMaxima(idEvento, edad);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/genero")
	public Response actualizarGenero(@FormParam("idEvento") String idEvento, @FormParam("genero") String genero) {
		System.out.println(idEvento + " " + genero);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarGenero(idEvento, genero);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/actualizar/reputacion")
	public Response actualizarReputacion(@FormParam("idEvento") String idEvento, @FormParam("reputacion") float reputacion) {
		System.out.println(idEvento + " " + reputacion);
		eventoDAO = new EventoDAO();
		boolean actualizado = eventoDAO.actualizarReputacion(idEvento, reputacion);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/eliminar/{idEvento}")
	public Response eliminarEvento(@PathParam("idEvento") String idEvento) {
		System.out.println(idEvento);
		eventoDAO = new EventoDAO();
		eventoDAO.eliminarEvento(idEvento);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	/******USUARIOS EVENTO******/
	
	@DELETE
	@Path("/eliminarparticipante/{idEvento}/{correo}")
	public Response eliminarParticipante(@PathParam("idEvento") String idEvento, @PathParam("correo") String correo) {
		System.out.println(idEvento + " " + correo);
		eventoDAO = new EventoDAO();
		eventoDAO.eliminarParticipante(idEvento, correo);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/insertarparticipante")
	public Response insertarParticipante(@FormParam("idEvento") String idEvento, @FormParam("correo") String correo) {
		System.out.println(idEvento + " " + correo);
		eventoDAO = new EventoDAO();
		eventoDAO.meterParticipantes(idEvento, correo);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/insertarsolicitante")
	public Response insertarSolicitante(@FormParam("idEvento") String idEvento, @FormParam("correo") String correo) {
		System.out.println(idEvento + " " + correo);
		eventoDAO = new EventoDAO();
		eventoDAO.meterSolicitantes(idEvento, correo);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@DELETE
	@Path("/eliminarsolicitante/{idEvento}/{correo}")
	public Response bloquearSolicitud(@PathParam("idEvento") String idEvento, @PathParam("correo") String correo) {
		System.out.println(idEvento + " " + correo);
		eventoDAO = new EventoDAO();
		eventoDAO.eliminarSolicitante(idEvento, correo);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@DELETE
	@Path("/bloquearsolicitud/{idEvento}/{correo}")
	public Response eliminarSolicitante(@PathParam("idEvento") String idEvento, @PathParam("correo") String correo) {
		System.out.println(idEvento + " " + correo);
		eventoDAO = new EventoDAO();
		eventoDAO.bloquearSolicitud(idEvento, correo);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@Secured
	@GET
	@Path("/hasidopuntuado/{idevento}/{email}")
	@Produces({MediaType.TEXT_PLAIN})
	public Response getHaSidoPuntuado(@PathParam("idevento") String idevento, @PathParam("email") String email) {
		
		eventoDAO = new EventoDAO();
		boolean b = eventoDAO.haSidoPuntuado(idevento,email);
		return Response.status(Status.OK).entity(b).build();
		
	}
	
	@Secured
	@POST
	@Path("/insertarpuntuacion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.TEXT_PLAIN})
	public Response insertarPuntuacionEvento(PuntuacionEvento puntuacion) {
		
		eventoDAO = new EventoDAO();
		if (eventoDAO.insertarPuntuacionEvento(puntuacion))
			return Response.status(Status.CREATED).entity("Puntuacion insertada").build();
		else
			return Response.status(Status.BAD_REQUEST).build();
	}

}
