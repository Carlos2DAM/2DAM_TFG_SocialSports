package dao;

import java.util.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import modelo.Evento;
import modelo.PuntuacionEvento;
import modelo.Requisitos;
import modelo.Usuario;

public class EventoDAO {
	
	public String crearEvento(Evento evento) {
		String id = generarId();
		System.out.println(id);
		Conexion conn;
		
		try {
			conn = new Conexion();
			
			String refSql = "select ref(u) from tablausuarios u where emailusuario = ?";
			PreparedStatement refPs = conn.getConnection().prepareStatement(refSql);
			refPs.setString(1, evento.getOrganizadorEvento().getEmailUsuario());
			ResultSet refRs = refPs.executeQuery();
			java.sql.Ref ref = null;
			
			if(refRs.next()) {
				ref = refRs.getRef(1);
			}
			
			String sql = "insert into tablaeventos(idEvento, organizadorEvento, deporte, localidad, direccion, fechaEvento, "
					+ "horaEvento, fechaCreacionEvento, maximoParticipantes, instalacionesReservadas, costeEvento, precioPorParticipante,"
					+ "comentarios, terminado, requisitos, listaSolicitantes, listaDescartados, listaParticipantes) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?, TRequisito(?,?,?,?), TLISTAPERSONAS(), TLISTAPERSONAS(), TLISTAPERSONAS())";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, generarId());
			ps.setRef(2, ref);
			ps.setString(3, evento.getDeporte());
			ps.setString(4, evento.getLocalidad());
			ps.setString(5, evento.getDireccion());
			if(evento.getFechaEvento() != null) {
				ps.setDate(6, new java.sql.Date(StringToDate(evento.getFechaEvento()).getTime()));  
			}else {
				ps.setDate(6, null);
			}
			ps.setString(7, evento.getHoraEvento());
			ps.setDate(8, new java.sql.Date(StringToDate(evento.getFechaCreacionEvento()).getTime()));
			ps.setInt(9, evento.getMaximoParticipantes());
			ps.setBoolean(10, evento.isInstalacionesReservadas());
			ps.setFloat(11, evento.getCosteEvento());
			ps.setFloat(12, evento.getPrecioPorParticipante());
			ps.setString(13, evento.getComentarios());
			ps.setBoolean(14, evento.isTerminado());
			ps.setInt(15, evento.getRequisitos().getEdadMinima());
			ps.setInt(16, evento.getRequisitos().getEdadMaxima());
			ps.setString(17, evento.getRequisitos().getRequisitoDeGenero());
			ps.setFloat(18, evento.getRequisitos().getReputacionNecesaria());
			
			int n = ps.executeUpdate();
			
			if(n == 0) id = "-1";

			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public ArrayList<Evento> obtenerEventosPendientes(String correo) {
		
		ArrayList<Evento> listaEventos = new ArrayList<>();
		
		try {
			Conexion conn = new Conexion();
			
			String sqlEventos = "SELECT IDEVENTO FROM TABLAEVENTOS WHERE TERMINADO = 0";
			
			String sql = "select deref(a.COLUMN_VALUE).emailusuario "
					+ "from table(select listaParticipantes from tablaeventos where idevento = ?) a "
					+ "where ? in deref(a.COLUMN_VALUE).emailusuario";
			
			PreparedStatement psEventos = conn.getConnection().prepareStatement(sqlEventos);
			ResultSet rsEventos = psEventos.executeQuery();
			
			while(rsEventos.next()) {
				String id = rsEventos.getString(1);
				
				PreparedStatement ps = conn.getConnection().prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					listaEventos.add(getEvento(id));
				}
				
				rs.close();
				ps.close();
				
			}
			
			rsEventos.close();
			psEventos.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaEventos;
	}
	
	public ArrayList<Evento> obtenerEventosFinalizados(String correo){
		ArrayList<Evento> listaEventos = new ArrayList<>();
		
		try {
			Conexion conn = new Conexion();
			
			String sqlEventos = "SELECT IDEVENTO FROM TABLAEVENTOS WHERE TERMINADO = 1";
			
			String sql = "select deref(a.COLUMN_VALUE).emailusuario "
					+ "from table(select listaParticipantes from tablaeventos where idevento = ?) a "
					+ "where ? in deref(a.COLUMN_VALUE).emailusuario";
			
			PreparedStatement psEventos = conn.getConnection().prepareStatement(sqlEventos);
			ResultSet rsEventos = psEventos.executeQuery();
			
			while(rsEventos.next()) {
				String id = rsEventos.getString(1);
				
				PreparedStatement ps = conn.getConnection().prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					listaEventos.add(getEvento(id));
				}
				
				rs.close();
				ps.close();
				
			}
			
			rsEventos.close();
			psEventos.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaEventos;
	}
	
	public Evento getEvento(String id) {
		
		Conexion conn;
		Evento evento = new Evento();
		
		try {
			conn = new Conexion();	
			PreparedStatement psEventoPendiente = conn.getConnection().prepareStatement("SELECT * FROM TABLAEVENTOS WHERE IDEVENTO = ?");
			psEventoPendiente.setString(1, id);
			ResultSet rsEventoPendiente = psEventoPendiente.executeQuery();
			
			if(rsEventoPendiente.next()) {
				evento.setIdEvento(id);
				evento.setDeporte(rsEventoPendiente.getString("DEPORTE"));
				evento.setDireccion(rsEventoPendiente.getString("DIRECCION"));
				Date fechaCreacion = rsEventoPendiente.getDate("FECHACREACIONEVENTO");
				if(fechaCreacion != null) evento.setFechaCreacionEvento(fechaCreacion.toString());
				Date fechaEvento = rsEventoPendiente.getDate("FECHAEVENTO");
				if(fechaEvento != null) evento.setFechaEvento(fechaEvento.toString());
				evento.setHoraEvento(rsEventoPendiente.getString("HORAEVENTO"));
				evento.setInstalacionesReservadas(rsEventoPendiente.getBoolean("INSTALACIONESRESERVADAS"));
				evento.setLocalidad(rsEventoPendiente.getString("LOCALIDAD"));
				evento.setTerminado(rsEventoPendiente.getBoolean("TERMINADO"));
				evento.setPrecioPorParticipante(rsEventoPendiente.getFloat("PRECIOPORPARTICIPANTE"));
				evento.setMaximoParticipantes(rsEventoPendiente.getInt("MAXIMOPARTICIPANTES"));
				evento.setCosteEvento(rsEventoPendiente.getFloat("COSTEEVENTO"));
				evento.setComentarios(rsEventoPendiente.getString("COMENTARIOS"));
				
				evento.setOrganizadorEvento(getOrganizador(id));
				setRequisitos(evento, id);
				evento.setListaParticipantes(getParticipantesEvento(id));
				evento.setListaDescartados(getDescartadosEvento(id));
				evento.setListaSolicitantes(getSolicitantesEvento(id));
			}
			
			rsEventoPendiente.close();
			psEventoPendiente.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return evento;
	}
	
	public ArrayList<Usuario> getParticipantesEvento(String idEvento) {
		
		ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select deref(a.COLUMN_VALUE).emailusuario from table(select listaparticipantes from tablaeventos where idevento = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, idEvento);
			ResultSet rs = ps.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while(rs.next()) {
				Usuario usuario = usuarioDAO.cogerUsuario(rs.getString(1));
				listaParticipantes.add(usuario);
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaParticipantes;
	}
	
	public ArrayList<Usuario> getSolicitantesEvento(String idEvento) {
ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select deref(a.COLUMN_VALUE).emailusuario from table(select listasolicitantes from tablaeventos where idevento = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, idEvento);
			ResultSet rs = ps.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while(rs.next()) {
				Usuario usuario = usuarioDAO.cogerUsuario(rs.getString(1));
				listaParticipantes.add(usuario);
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaParticipantes;
	}
	
	public ArrayList<Usuario> getDescartadosEvento(String idEvento) {
		ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select deref(a.COLUMN_VALUE).emailusuario from table(select listadescartados from tablaeventos where idevento = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, idEvento);
			ResultSet rs = ps.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while(rs.next()) {
				Usuario usuario = new Usuario();
				String email = rs.getString(1);
				if(email != null) {
					usuario = usuarioDAO.cogerUsuario(email);
					listaParticipantes.add(usuario);
				}
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaParticipantes;
	}
	
	public void setRequisitos(Evento evento, String idEvento) {
		
		Conexion conn;
		
		try {
			conn = new Conexion();
			String req = "select r.requisitos.edadminima, r.requisitos.edadmaxima, r.requisitos.requisitodegenero, r.requisitos.reputacionnecesaria from tablaeventos r where idevento = ?";
			PreparedStatement psReq = conn.getConnection().prepareStatement(req);
			psReq.setString(1, idEvento);
			ResultSet rsReq = psReq.executeQuery();
			
			if(rsReq.next()) {
				Requisitos requisitos = new Requisitos();
				requisitos.setEdadMinima(rsReq.getInt(1));
				requisitos.setEdadMaxima(rsReq.getInt(2));
				requisitos.setRequisitoDeGenero(rsReq.getString(3));
				requisitos.setReputacionNecesaria(rsReq.getFloat(4));
				evento.setRequisitos(requisitos);
			}
			
			rsReq.close();
			psReq.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario getOrganizador(String idEvento) {
		
		Usuario organizador = new Usuario();
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement psOrganizador = conn.getConnection().prepareStatement("SELECT deref(organizadorevento).emailusuario FROM TABLAEVENTOS WHERE IDEVENTO = ?");
			psOrganizador.setString(1, idEvento);
			ResultSet rsOrganizador = psOrganizador.executeQuery();
			
			if(rsOrganizador.next()) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				organizador = usuarioDAO.cogerUsuario(rsOrganizador.getString(1));
			}
			
			rsOrganizador.close();
			psOrganizador.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return organizador;
	}
	
	public boolean actualizarFecha(String idEvento, Date fecha) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET FECHAEVENTO = ? WHERE IDEVENTO = ?");
			ps.setDate(1, new java.sql.Date(fecha.getTime()));
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarHora(String idEvento, String hora) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET HORAEVENTO = ? WHERE IDEVENTO = ?");
			ps.setString(1,  hora);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarDireccion(String idEvento, String direccion) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET DIRECCION = ? WHERE IDEVENTO = ?");
			ps.setString(1,  direccion);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarMaxParticipantes(String idEvento, int maxParticipantes) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET MAXIMOPARTICIPANTES = ? WHERE IDEVENTO = ?");
			ps.setInt(1,  maxParticipantes);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarTerminarEvento(String idEvento, int terminado) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET TERMINADO = ? WHERE IDEVENTO = ?");
			ps.setInt(1,  terminado);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarReserva(String idEvento, boolean reserva) {
		int n = 0;
		if(reserva) n = 1;
		
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET INSTALACIONESRESERVADAS = ? WHERE IDEVENTO = ?");
			ps.setInt(1,  n);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarCoste(String idEvento, float coste) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET COSTEEVENTO = ? WHERE IDEVENTO = ?");
			ps.setFloat(1,  coste);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarPrecio(String idEvento, float precio) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET PRECIOPORPARTICIPANTE = ? WHERE IDEVENTO = ?");
			ps.setFloat(1,  precio);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarComentarios(String idEvento, String comentarios) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET COMENTARIOS = ? WHERE IDEVENTO = ?");
			ps.setString(1,  comentarios);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarEdadMinima(String idEvento, int edad) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS E SET E.REQUISITOS.EDADMINIMA = ? WHERE IDEVENTO = ?");
			ps.setInt(1, edad);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarEdadMaxima(String idEvento, int edad) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS E SET E.REQUISITOS.EDADMAXIMA = ? WHERE IDEVENTO = ?");
			ps.setInt(1, edad);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarGenero(String idEvento, String genero) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS E SET E.REQUISITOS.REQUISITODEGENERO = ? WHERE IDEVENTO = ?");
			ps.setString(1, genero);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarReputacion(String idEvento, float reputacion) {
		boolean actualizado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS E SET E.REQUISITOS.REPUTACIONNECESARIA = ? WHERE IDEVENTO = ?");
			ps.setFloat(1, reputacion);
			ps.setString(2, idEvento);
			
			int x = ps.executeUpdate();
			
			if(x > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean eliminarEvento(String idEvento) {
		boolean eliminado = false;
		try {
			Conexion conn = new Conexion();
			String sql = "DELETE FROM TABLAEVENTOS WHERE IDEVENTO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) eliminado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return eliminado;
	}
	
	public boolean eliminarParticipante(String idEvento, String correo) {
		boolean eliminado = false;
		try {
			Conexion conn = new Conexion();
			String sql = "DELETE FROM TABLE(SELECT LISTAPARTICIPANTES FROM TABLAEVENTOS WHERE IDEVENTO = ?) A WHERE DEREF(A.COLUMN_VALUE).EMAILUSUARIO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			ps.setString(2, correo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) eliminado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return eliminado;
	}
	
	public boolean eliminarSolicitante(String idEvento, String correo) {
		boolean eliminado = false;
		try {
			Conexion conn = new Conexion();
			String sql = "DELETE FROM TABLE(SELECT LISTASOLICITANTES FROM TABLAEVENTOS WHERE IDEVENTO = ?) A WHERE DEREF(A.COLUMN_VALUE).EMAILUSUARIO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			ps.setString(2, correo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) eliminado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return eliminado;
	}
	
	public void bloquearSolicitud(String idEvento, String correo) {
		try {
			Conexion conn = new Conexion();
			String sql = "DELETE FROM TABLE(SELECT LISTADESCARTADOS FROM TABLAEVENTOS WHERE IDEVENTO = ?) A WHERE DEREF(A.COLUMN_VALUE).EMAILUSUARIO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			ps.setString(2, correo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) {
				eliminarParticipante(idEvento, correo);
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void meterParticipantes(String idEvento, String correo) {
		try {
			Conexion conn = new Conexion();
			String sql = "insert into table(select listaParticipantes from tablaeventos where idevento = ?)(select ref(u) from tablausuarios u where u.emailusuario = ?)";
			PreparedStatement ps = null;
			
			ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			ps.setString(2, correo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) {
				eliminarSolicitante(idEvento, correo);
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void meterSolicitantes(String idEvento, String correo) {
		try {
			Conexion conn = new Conexion();
			String sql = "insert into table(select listasolicitantes from tablaeventos where idevento = ?)(select ref(u) from tablausuarios u where u.emailusuario = ?)";
			PreparedStatement ps = null;
			
			ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			ps.setString(2, correo);
			
			ps.executeUpdate();
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void enviarInvitaciones(Evento evento) {
		
		if(evento.getListaParticipantes() != null) {
			for(int i = 0; i < evento.getListaParticipantes().size(); i++) {
				meterParticipantes(evento.getIdEvento(), evento.getListaParticipantes().get(i).getEmailUsuario());
			}
		}
		
	}
	
	public ArrayList<Evento> buscarEventoFiltrado(String deporte, String localidad, Date fecha, String hora, boolean reservado, float reputacion){
		
		ArrayList<Evento> listaEventos = new ArrayList<>();
		
		String SQL = "select idevento from tablaeventos where terminado = 0";
		
		if(deporte != null && !deporte.equals("")) {
			SQL += " and deporte = '" + deporte + "'";
		}
		if(localidad != null && !localidad.equals("")) {
			SQL += " and localidad = '" + localidad + "'";
		}
		if(fecha != null) {
			SQL += " and fechaevento = '" + fecha + "'";
		}
		if(hora != null && !hora.equals("")) {
			SQL += " and horaevento = '" + hora + "'"; 
		}
		if(reservado) {
			SQL += " and instalacionesReservadas = 1";
		}
		if(reputacion != -1.0) {
			SQL += " and deref(organizadorevento).reputacionorganizadorusuario >= '" + reputacion + "'";
		}
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String idEvento = rs.getString(1);
				Evento evento = getEvento(idEvento);
				if(evento.getOrganizadorEvento() != null) {
					listaEventos.add(evento);
				}
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaEventos;
	}
	
public boolean haSidoPuntuado(String idEvento, String email) {
		
		boolean rated = false;
		Conexion conn = null;
		int vecesPuntuadoEvento = 0;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT COUNT(CALIFICACION) FROM TABLAPUNTUACIONESPARTICIPANTES WHERE IDEVENTOFINALIZADO = ? AND EMAILUSUARIOEMISOR = ?");
			ps.setString(1, idEvento);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				vecesPuntuadoEvento = rs.getInt(1);
			
			if (vecesPuntuadoEvento > 0)
				rated = true;
			
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Veces puntuado >>> "+vecesPuntuadoEvento);
		
		return rated;
	}
	
	
	public boolean insertarPuntuacionEvento(PuntuacionEvento puntuacion) {

		boolean insertado = false;
		Conexion conn = null;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("INSERT INTO TABLAPUNTUACIONESEVENTOS VALUES(?,?,?)");
			ps.setString(1, puntuacion.getEmailUsuarioEmisor());
			ps.setString(2, puntuacion.getIdEventoFinalizado());
			ps.setFloat(3, puntuacion.getCalificacion());
			int filasAfectadas = ps.executeUpdate();
			
			if (filasAfectadas > 0)
				insertado = true;				
			
			ps.close();
			
			conn.hacerCommit();
			
			PreparedStatement ps2 = conn.getConnection().prepareStatement("SELECT DEREF(ORGANIZADOREVENTO).EMAILUSUARIO FROM TABLAEVENTOS WHERE IDEVENTO = ?");
			ps2.setString(1, puntuacion.getIdEventoFinalizado());
			ResultSet rs = ps2.executeQuery();
			
			if (rs.next()) {
				UsuarioDAO user = new UsuarioDAO();
				user.calcularPuntuacionOrganizador(rs.getString(1));
			}

			ps2.close();
			conn.hacerCommit();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return insertado;
	}
	
	
	public String generarId() {
		int id = 1;
		String cadenaBase = "000000000", idStr;
		int longitudBase = cadenaBase.length(), longitudId;
		
		try {
			Conexion conexion = new Conexion();
			PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT IDEVENTO FROM TABLAEVENTOS ORDER BY 1 DESC");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = Integer.parseInt(rs.getString(1)) + 1;
			}
			rs.close();
			ps.close();
			conexion.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		idStr = Integer.toString(id);
		longitudId = idStr.length();
		
		idStr = cadenaBase.substring(0,(longitudBase-longitudId))+idStr;
		
		return idStr;

	}
	
	public Date StringToDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fecha!=null && !fecha.isEmpty()) {
            try {
            	Date date = (Date) formato.parse(fecha);
            	System.out.println(fecha+" --- "+date.toString());
                return date;
            } catch (ParseException e) {
            	e.printStackTrace();
            }
        }
       return null;
	}
}
