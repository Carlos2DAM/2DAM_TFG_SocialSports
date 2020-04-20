package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Usuario;

@Path("/prueba")
public class RestWebService {
	
	//registro incompleto
	@POST
	@Path("/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Usuario usuario) {
		Response.Status responseStatus = Response.Status.OK;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection ("jdbc:mysql://localhost/socialsport","root","password");
			PreparedStatement sentencia = connection.prepareStatement("SELECT * FROM USUARIOS WHERE CORREO = ?");
			sentencia.setString(1, usuario.getCorreo());
			
			ResultSet resultset = sentencia.executeQuery();
			
			if(resultset.next()) {
				//ese correo ya esta registrado
				responseStatus = Response.Status.UNAUTHORIZED; 
			}else {
				String sql = "INSERT INTO USUARIOS(NOMBRE, CONTRASENA, CORREO, REPUTACION) VALUES(?,?,?,?)";
				PreparedStatement insert = connection.prepareStatement(sql);
				insert.setString(1, usuario.getNombre());
				insert.setString(2, usuario.getContrasena()); //cifrar contrasena
				insert.setString(3, usuario.getCorreo());
				insert.setInt(4, 0);
				int affectedRows = insert.executeUpdate();
				
				if(affectedRows == 0) {
					//no se ha añadido
				}else {
					
				}
			}
			
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		return Response.status(responseStatus).build();
	}
	
	//prueba para recibir parámetros y enviar una respuesta con datos
	@GET
	@Path("/{idUsuario}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUsuario(@PathParam("idUsuario") int idUsuario) {
		
		Response.Status responseStatus;
		modelo.Usuario usuario = new modelo.Usuario();
	
		if (idUsuario == 1) {
			responseStatus = Response.Status.OK;
			usuario.setNombre("Usuario1");
			usuario.setCorreo("correo1@email.com");
		} else
			responseStatus = Response.Status.NOT_FOUND;

		if(responseStatus == Response.Status.OK) return Response.ok(usuario).build();
		else return Response.status(responseStatus).build();
	}
}


