package dao;

import java.util.Date;

import org.apache.commons.io.FileUtils;

import java.util.Base64;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import modelo.Evento;
import modelo.PuntuacionParticipante;
import modelo.Usuario;
import seguridad.PasswordHash;
import seguridad.Validaciones;

public class UsuarioDAO {
	
public boolean registroUsuario(String correo, String contrasena) {
		
		Conexion conn = null;
		boolean registrado = false;
		
		try {
			conn = new Conexion();
			
			if(!existeCorreo(correo)) {
				PasswordHash hash = new PasswordHash();
				String salt = hash.generateSalt();
				hash.generatePassword(contrasena, salt);
		        String hashedString = hash.getHash();
				PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO TABLAUSUARIOS (EMAILUSUARIO, PASSWORDUSUARIO, USUARIOSALT, FECHAALTAUSUARIO, LISTAAMIGOS, LISTABLOQUEADOS) values(?,?,?,?, TLISTAPERSONAS(), TLISTAPERSONAS())");
				insert.setString(1, correo);
				insert.setString(2, hashedString);
				insert.setString(3, salt);
				insert.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				
				int affectedRows = insert.executeUpdate();
				
				if(affectedRows==1) {
					registrado = true;
				}
				
				insert.close();
			}
			
			conn.closeConnection();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return registrado;
	}

	public boolean loginUsuario(String correo, String contrasena) {
		
		Conexion conn = null;
		boolean valido = false;
		Validaciones validaciones = new Validaciones();
		
		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
		
			try {
				conn = new Conexion();
				PreparedStatement ps = conn.getConnection().prepareStatement("SELECT EMAILUSUARIO, PASSWORDUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
				ps.setString(1, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
				
					PasswordHash hash = new PasswordHash();
					String salt = getSalt(correo);
					hash.generatePassword(contrasena, salt);
			        String hashedString = hash.getHash();
			        
					if(hashedString.equals(rs.getObject(2))) {
						valido = true;
					}
				}
				
				rs.close();
				ps.close();
				conn.closeConnection();
			
			
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
			
		return valido;
	}
	
	public boolean existeCorreo(String correo) {
		
		boolean existe = false;
		Conexion conn = null;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT EMAILUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) existe = true;
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return existe;
	}
	
	public boolean actualizarNombre(String correo, String nombre) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET NOMBREUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, nombre);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarApellidos(String correo, String apellidos) {
		
		Conexion conn = null;
		boolean actualizado = false;
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET APELLIDOSUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, apellidos);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarDireccion(String correo, String direccion) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET DIRECCIONUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, direccion);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return actualizado;
	}
	
	public boolean actualizarGenero(String correo, String genero) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET GENEROUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, genero);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarFechaNacimiento(String correo, Date fecha) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET FECHANACIMIENTOUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setDate(1, new java.sql.Date(fecha.getTime()));
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarPassword(String correo, String password) {
		
		Conexion conn = null;
		boolean actualizado = false;
		Validaciones validaciones = new Validaciones();
		
		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(password)) {
			try {
				PasswordHash hash = new PasswordHash();
				String salt = getSalt(correo);
				hash.generatePassword(password, salt);
		        String hashedString = hash.getHash();
				
				conn = new Conexion();
				PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET PASSWORDUSUARIO = ? WHERE EMAILUSUARIO = ?");
				ps.setString(1, hashedString);
				ps.setString(2, correo);
				int n = ps.executeUpdate();
				
				if(n > 0) actualizado = true;
				
				ps.close();
				conn.closeConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return actualizado;
	}
	
	public boolean borrarUsuario(String correo) {
		
		Conexion conn = null;
		boolean borrado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("DELETE FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) borrado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return borrado;
	}
	
	public boolean agregarAmigo(String correo, String correoAmigo) {
		
		boolean agregado = false;
		
		if(!yaEsAmigo(correo, correoAmigo)) {
			try {
				Conexion conn = new Conexion();
				String SQL = "insert into "
						+ "table(select listaamigos from tablausuarios where emailusuario = ?)"
						+ "(select ref(u) from tablausuarios u where u.emailusuario = ?)";
				PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
				ps.setString(1, correo);
				ps.setString(2, correoAmigo);
				
				int n = ps.executeUpdate();
				
				if(n > 0) {
					agregado = true;
					quitarBloqueo(correo, correoAmigo);
				}
				
				ps.close();
				conn.closeConnection();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return agregado;
	}
	
	public boolean borrarAmigo(String correo, String correoAmigo) {
		
		boolean borrado = false;
		
		try {
			Conexion conn = new Conexion();
			String SQL = "delete from table(select listaamigos from tablausuarios where emailusuario = ?) a where deref(a.COLUMN_VALUE).emailusuario = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, correo);
			ps.setString(2, correoAmigo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) borrado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return borrado;
	}
	
	public boolean bloquearUsuario(String correo, String correoBloqueado) {
		boolean bloqueado = false;
		
		if(!yaEstaBloqueado(correo, correoBloqueado)) {
			try {
				Conexion conn = new Conexion();
				String SQL = "insert into "
						+ "table(select listabloqueados from tablausuarios where emailusuario = ?)"
						+ "(select ref(u) from tablausuarios u where u.emailusuario = ?)";
				PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
				ps.setString(1, correo);
				ps.setString(2, correoBloqueado);
				
				int n = ps.executeUpdate();
				
				if(n > 0) {
					bloqueado = true;
					borrarAmigo(correo, correoBloqueado);
				}
				
				ps.close();
				conn.closeConnection();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bloqueado;
	}
	
	public boolean quitarBloqueo(String correo, String correoBloqueado) {
		boolean desbloqueado = false;
		
		try {
			Conexion conn = new Conexion();
			String SQL = "delete from table(select listabloqueados from tablausuarios where emailusuario = ?) a "
					+ "where deref(a.COLUMN_VALUE).emailusuario = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, correo);
			ps.setString(2, correoBloqueado);
			
			int n = ps.executeUpdate();
			
			if(n > 0) desbloqueado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return desbloqueado;
	}
	
	public boolean yaEsAmigo(String correo, String correoAmigo) {
		
		boolean yaEsAmigo = false;
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select * from table(select listaamigos from tablausuarios where emailusuario = ?) a "
									+ "where deref(a.column_value).emailusuario = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, correo);
			ps.setString(2, correoAmigo);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) yaEsAmigo = true;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return yaEsAmigo;
	}
	
	public boolean yaEstaBloqueado(String correo, String correoBloqueado) {
		boolean yaEstaBloqueado = false;
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select * from table(select listabloqueados from tablausuarios where emailusuario = ?) a "
									+ "where deref(a.column_value).emailusuario = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, correo);
			ps.setString(2, correoBloqueado);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) yaEstaBloqueado = true;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return yaEstaBloqueado;
	}
	
	public Usuario cogerUsuario(String correo){
		
		Conexion conn = null;
		Usuario usuario = new Usuario();
		
		try {
			conn = new Conexion();
			String sql = "SELECT * FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				usuario.setEmailUsuario(rs.getString("EMAILUSUARIO"));
				usuario.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
				usuario.setApellidosUsuario(rs.getString("APELLIDOSUSUARIO"));
				usuario.setGeneroUsuario(rs.getString("GENEROUSUARIO"));
				usuario.setDireccionUsuario(rs.getString("DIRECCIONUSUARIO"));
				java.util.Date fechaNacimiento = rs.getDate("FECHANACIMIENTOUSUARIO");
				if(fechaNacimiento != null) usuario.setFechaNacimientoUsuario(fechaNacimiento.toString());
				java.util.Date fechaAlta = rs.getDate("FECHAALTAUSUARIO");
				if(fechaAlta != null) usuario.setFechaAltaUsuario(fechaAlta.toString());
				usuario.setReputacionOrganizadorUsuario(rs.getFloat("REPUTACIONORGANIZADORUSUARIO"));
				usuario.setReputacionParticipanteUsuario(rs.getFloat("REPUTACIONPARTICIPANTEUSUARIO"));
				usuario.setFotoPerfilUsuario(getFotoEnBase64(usuario.getEmailUsuario()));
				
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	public String getFotoEnBase64(String name) {
		String encoded = "";
		try {
			File fileJPG = new File("C:\\Users\\0xNea\\Pictures\\prueba\\" + name.replace(".", "") + ".jpg");
			File filePNG = new File("C:\\Users\\0xNea\\Pictures\\prueba\\" + name.replace(".", "") + ".png");
			
			if(fileJPG.exists()) {
				System.out.println(name + "jpg");
				byte[] fileContent = FileUtils.readFileToByteArray(fileJPG);
				encoded = Base64.getEncoder().encodeToString(fileContent);
			}else if (filePNG.exists()) {
				System.out.println("png");
				byte[] fileContent = FileUtils.readFileToByteArray(filePNG);
				encoded = Base64.getEncoder().encodeToString(fileContent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encoded;
	}
	
	public ArrayList<Usuario> listaAmigos(String correo) {
		
		Conexion conn = null;
		ArrayList<Usuario> listaAmigos = new ArrayList<>();
		
		try {
			conn = new Conexion();
			String sql = "select deref(a.COLUMN_VALUE).EMAILUSUARIO,"
					+ "deref(a.COLUMN_VALUE).NOMBREUSUARIO,"
					+ "deref(a.COLUMN_VALUE).APELLIDOSUSUARIO,"
					+ "deref(a.COLUMN_VALUE).GENEROUSUARIO,"
					+ "deref(a.COLUMN_VALUE).FECHANACIMIENTOUSUARIO, "
					+ "deref(a.COLUMN_VALUE).REPUTACIONORGANIZADORUSUARIO, "
					+ "deref(a.COLUMN_VALUE).REPUTACIONPARTICIPANTEUSUARIO "
					+ " from table(select u.listaamigos from tablausuarios u where u.emailusuario = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			Usuario amigo;
			
			while(rs.next()) {
				
				amigo = new Usuario();
				
				String email = rs.getString(1);
				String nombre = rs.getString(2);
				String apellidos = rs.getString(3);
				String genero = rs.getString(4);
				Date date = rs.getDate(5);
				float reputacionOrganizador = rs.getFloat(6);
				float reputacionParticipante = rs.getFloat(7);
				
				amigo.setEmailUsuario(email);
				amigo.setNombreUsuario(nombre);
				amigo.setApellidosUsuario(apellidos);
				amigo.setGeneroUsuario(genero);
				amigo.setReputacionOrganizadorUsuario(reputacionOrganizador);
				amigo.setReputacionParticipanteUsuario(reputacionParticipante);
				amigo.setFotoPerfilUsuario(getFotoEnBase64(amigo.getEmailUsuario()));
				if(date != null) amigo.setFechaNacimientoUsuario(date.toString());
				
				if(email != null) {
					listaAmigos.add(amigo);
				}
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaAmigos;
	}
	
	public ArrayList<Usuario> listaBloqueados(String correo) {
		Conexion conn = null;
		ArrayList<Usuario> listaBloqueados = new ArrayList<>();
		
		try {
			conn = new Conexion();
			String sql = "select deref(a.COLUMN_VALUE).EMAILUSUARIO,"
					+ "deref(a.COLUMN_VALUE).NOMBREUSUARIO,"
					+ "deref(a.COLUMN_VALUE).APELLIDOSUSUARIO,"
					+ "deref(a.COLUMN_VALUE).GENEROUSUARIO,"
					+ "deref(a.COLUMN_VALUE).FECHANACIMIENTOUSUARIO"
					+ " from table(select u.listabloqueados from tablausuarios u where u.emailusuario = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			Usuario bloqueado;
			
			while(rs.next()) {
				
				bloqueado = new Usuario();
				
				String email = rs.getString(1);
				String nombre = rs.getString(2);
				String apellidos = rs.getString(3);
				String genero = rs.getString(4);
				Date date = rs.getDate(5);
				
				bloqueado.setEmailUsuario(email);
				bloqueado.setNombreUsuario(nombre);
				bloqueado.setApellidosUsuario(apellidos);
				bloqueado.setGeneroUsuario(genero);
				if(date != null) bloqueado.setFechaNacimientoUsuario(date.toString());
				
				if(email != null) {
					listaBloqueados.add(bloqueado);
				}
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaBloqueados;
	}
	
	public boolean actualizarPuntuacionParticipante(Conexion conn, String correo, Float puntuacion) {
		boolean actualizado = false;
		
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET REPUTACIONPARTICIPANTEUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setFloat(1, puntuacion);
			ps.setString(2, correo);
			int numFilas = ps.executeUpdate();
			
			if (numFilas > 0)
				actualizado = true;
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	
	public boolean actualizarPuntuacionOrganizador(Conexion conn, String correo, Float puntuacion) {
		boolean actualizado = false;
		
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET REPUTACIONORGANIZADORUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setFloat(1, puntuacion);
			ps.setString(2, correo);
			int numFilas = ps.executeUpdate();
			
			if (numFilas > 0)
				actualizado = true;
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	private String getSalt(String correo) {
		
		Conexion conn = null;
		String salt = "";
	
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT USUARIOSALT FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				salt = rs.getString(1);
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return salt;
	}
	
	public float calcularPuntuacionParticipante(String correo) {
		
		Conexion conn = null;
		int vecesPuntuado = 0;
		float sumaPuntuaciones = 0, puntuacionFinal = 0;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT COUNT(CALIFICACION), SUM(CALIFICACION) FROM TABLAPUNTUACIONESPARTICIPANTES WHERE EMAILUSUARIOPUNTUADO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				vecesPuntuado = rs.getInt(1);
				sumaPuntuaciones = rs.getFloat(2);
			}
			
			if (vecesPuntuado == 0)
				puntuacionFinal = 4f;
			else
				puntuacionFinal = sumaPuntuaciones / vecesPuntuado;
			
			ps.close();
			rs.close();
		
			System.out.println(correo+"-> Puntuación como Participante >>>>> "+puntuacionFinal);
			actualizarPuntuacionParticipante(conn,correo,puntuacionFinal);
			
			conn.hacerCommit();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return puntuacionFinal;
	}
	
	
	public float calcularPuntuacionOrganizador(String correo) {
		
		EventoDAO eventoDAO = new EventoDAO();
		ArrayList<Evento> listaEventosTerminados = eventoDAO.obtenerEventosFinalizados(correo);
		Conexion conn = null;
		float sumaPuntuaciones = 0, puntuacionFinal = 0;
		int vecesPuntuado = 0, sumaVecesPuntuado = 0;
		
		try {
			conn = new Conexion();
			for (Evento evento: listaEventosTerminados) {
				System.out.println("Evento "+evento.getIdEvento());
				PreparedStatement ps = conn.getConnection().prepareStatement("SELECT COUNT(CALIFICACION), SUM(CALIFICACION) FROM TABLAPUNTUACIONESEVENTOS WHERE IDEVENTOFINALIZADO = ?");
				ps.setString(1, evento.getIdEvento());
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					vecesPuntuado = rs.getInt(1);
					sumaVecesPuntuado = sumaVecesPuntuado + vecesPuntuado;
				}
				
				if (vecesPuntuado > 0)
					sumaPuntuaciones = sumaPuntuaciones + rs.getFloat(2);
				
				ps.close();
				rs.close();
				System.out.println("Evento "+evento.getIdEvento()+" puntuado "+vecesPuntuado+" veces. Suma acumulada de Puntuaciones en tus eventos: "+sumaPuntuaciones);
			}
			
			if (sumaVecesPuntuado == 0)
				puntuacionFinal = 4f;
			else
				puntuacionFinal = sumaPuntuaciones / sumaVecesPuntuado;
		
			System.out.println(correo+"-> Puntuación como Organizador >>>>> "+puntuacionFinal);
			actualizarPuntuacionOrganizador(conn,correo,puntuacionFinal);
			
			conn.hacerCommit();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return puntuacionFinal;
	}
	
	
	public boolean actualizarPuntuacionParticipante(Conexion conn, String correo, float puntuacion) {
		boolean actualizado = false;
		
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET REPUTACIONPARTICIPANTEUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setFloat(1, puntuacion);
			ps.setString(2, correo);
			int numFilas = ps.executeUpdate();
			
			if (numFilas > 0)
				actualizado = true;
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	
	public boolean actualizarPuntuacionOrganizador(Conexion conn, String correo, float puntuacion) {
		boolean actualizado = false;
		
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET REPUTACIONORGANIZADORUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setFloat(1, puntuacion);
			ps.setString(2, correo);
			int numFilas = ps.executeUpdate();
			
			if (numFilas > 0)
				actualizado = true;
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	
	public boolean insertarPuntuacionParticipante(PuntuacionParticipante puntuacion) {
		
		boolean insertado = false;
		Conexion conn = null;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("INSERT INTO TABLAPUNTUACIONESPARTICIPANTES VALUES(?,?,?,?)");
			ps.setString(1, puntuacion.getEmailUsuarioEmisor());
			ps.setString(2, puntuacion.getEmailUsuarioPuntuado());
			ps.setString(3, puntuacion.getIdEventoFinalizado());
			ps.setFloat(4, puntuacion.getCalificacion());
			int filasAfectadas = ps.executeUpdate();
			
			if (filasAfectadas > 0)
				insertado = true;
			
			ps.close();
			
			conn.hacerCommit();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		calcularPuntuacionParticipante(puntuacion.getEmailUsuarioPuntuado());
		
		return insertado;
	}
	
	public Date StringToDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fecha!=null && !fecha.isEmpty()) {
            try {
            	Date date = formato.parse(fecha);
            	System.out.println(fecha+" --- "+date.toString());
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
