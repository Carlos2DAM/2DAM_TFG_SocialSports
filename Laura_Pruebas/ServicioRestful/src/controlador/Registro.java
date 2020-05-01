package controlador;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Hex;

import modelo.Usuario;
import dao.Conexion;
import security.PasswordHash;
import security.Validaciones;

@Path("/usuario")
public class Registro {
	
	@POST
	@Path("/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Usuario usuario) {
		
		Validaciones validaciones = new Validaciones();
		String correo = usuario.getCorreo();
		String contrasena = usuario.getContrasena();
		Response.Status responseStatus = null;

		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
		
			Conexion connection;
			
			try {
				connection = new Conexion();
				PreparedStatement sentencia = connection.getConnection().prepareStatement("SELECT * FROM USUARIOS WHERE CORREO = ?");
				sentencia.setString(1, correo);
				
				ResultSet resultset = sentencia.executeQuery();
					
				if(resultset.next()) {
					responseStatus = Response.Status.UNAUTHORIZED; 
				}else {
					
					PasswordHash hash = new PasswordHash();
			        String hashedString = hash.getHash(contrasena);
			        
			        System.out.println(hashedString);
					
					String sql = "INSERT INTO USUARIOS(ID, CORREO, CONTRASENA, REPUTACIONORGANIZADOR, REPUTACIONPARTICIPANTE, ALTA) VALUES(?,?,?,?,?,?)";
					PreparedStatement insert = connection.getConnection().prepareStatement(sql);
					insert.setLong(1, generarId());
					insert.setString(2, correo); 
					insert.setString(3, hashedString);
					insert.setInt(4, 0);
					insert.setInt(5, 0);
					insert.setDate(6, new Date(new java.util.Date().getTime()));
					
					int affectedRows = insert.executeUpdate();
					
					if(affectedRows==1) {
						responseStatus = Response.Status.CREATED;
					}
				}
				
				resultset.close();
				sentencia.close();
				connection.closeConnection();
				
			} catch (ClassNotFoundException | SQLException e) {
				responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
				e.printStackTrace();
			}
	
		}else {
			responseStatus = Response.Status.UNAUTHORIZED;
		}
		
		return Response.status(responseStatus).build();
	}
	
	private long generarId() {
		
		long id=1;
		
		try {
			Conexion conexion = new Conexion();
			PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT ID FROM USUARIOS ORDER BY 1 DESC");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getLong(1)+1;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

}


