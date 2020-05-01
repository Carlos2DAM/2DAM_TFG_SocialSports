package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private Connection connection;
	private final String NAME = "root";
	private final String PASSWORD = "password";
	
	public Conexion() throws ClassNotFoundException, SQLException {
		setConnection();
	}

	public void setConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = (Connection) DriverManager.getConnection ("jdbc:mysql://localhost/socialsport", NAME, PASSWORD);
	}
	
	public Connection getConnection() {
		return this.connection;
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
