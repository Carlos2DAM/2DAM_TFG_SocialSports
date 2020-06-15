package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private Connection connection;
	private final String NAME = "dam2";
	private final String PASSWORD = "contraseña";
	
	public Conexion() throws ClassNotFoundException, SQLException {
		setConnection();
	}

	public void setConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		this.connection = (Connection) DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:system", NAME, PASSWORD);
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void hacerCommit() {
		try {
			this.connection.setAutoCommit(false);
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public void closeConnection() {
		if(connection != null){
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
