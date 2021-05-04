package ec.ftt.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {	
	private static Connection connection = null;
	
	public Connection getConnection() {
		if(connection == null) {
			String sqlDriver = "com.mysql.cj.jdbc.Driver";
			String sqlUrl = "jdbc:mysql://127.0.0.1:3306";
			String sqlUser = "root";
			String sqlPass = "@FzsGzd03";		
			
			try {				
				Class.forName(sqlDriver);				
				connection = DriverManager.getConnection(sqlUrl, sqlUser, sqlPass);	
			}
			catch (ClassNotFoundException e) {				
				e.printStackTrace();
			} 
			catch (SQLException e) {				
				e.printStackTrace();
			}
		}		
		
		return connection;
	}
}