package ec.ftt.DAO;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import ec.ftt.Model.User;

public class UserDAO {
	private Connection connection;
	
	public UserDAO() {
		connection = new ConnectionSQL().getConnection();
	}
	
	public int saveUser(User user) {
		String sqlStatement = "INSERT INTO Web_API.User (user_name, user_birthday, user_email, "
				+ "user_color, user_cpf) VALUES(?, ?, ?, ?, ?)";	
		
		try {
			if(connection == null) {
				return 500;
			}
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			
			statement.setString(1, user.getName());		
			statement.setDate(2, new Date(user.getBirthday().getTime()));
			statement.setString(3, user.getEmail());	
			statement.setString(4, user.getColor());	
			statement.setString(5, user.getCpf());	
			
			return statement.executeUpdate();
		} catch (SQLException e) {		
			e.printStackTrace();
			
			if(e.getMessage().contains("Duplicate entry")) 
				return 409;					
			
			return 500;
		}
	}
	
	public User getUser(String cpf) {
		User user = new User();
		
		try {
			String sqlStatement = "SELECT * FROM Web_API.User WHERE user_cpf = ?";
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);			
			statement.setString(1, cpf);								
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {				
				user.setId(rs.getLong("user_id"));				
				user.setName(rs.getString("user_name"));				
				user.setBirthday(rs.getDate("user_birthday").toString());				
				user.setEmail(rs.getString("user_email"));				
				user.setColor(rs.getString("user_color"));				
				user.setCpf(rs.getString("user_cpf"));
			}			
			
		} catch (SQLException e) {			
			e.printStackTrace();			
			return null;
		}
		
		return user;
	}
	
	public int updateUser(User user) {
		String sqlStatement = "UPDATE Web_API.User SET "
				+ "user_name = ?,"
				+ "user_birthday = ?,"
				+ "user_email = ?,"
				+ "user_color = ? "				
				+ "WHERE user_cpf = ?";	
		
		try {
			if(connection == null) {
				return 500;
			}			
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			
			statement.setString(1, user.getName());		
			statement.setDate(2, new Date(user.getBirthday().getTime()));
			statement.setString(3, user.getEmail());	
			statement.setString(4, user.getColor());	
			statement.setString(5, user.getCpf());	
			
			return statement.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();			
			return 500;
		}
	}
	
	public List<User> listUser(){
		List<User> users =  new ArrayList<User>();
		
		try {
			String sqlStatement = "SELECT * FROM Web_API.User;";
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			ResultSet rs = statement.executeQuery();
			
			User user;			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getLong("user_id"));				
				user.setName(rs.getString("user_name"));				
				user.setBirthday(rs.getDate("user_birthday").toString());				
				user.setEmail(rs.getString("user_email"));				
				user.setColor(rs.getString("user_color"));				
				user.setCpf(rs.getString("user_cpf"));
				
				users.add(user);
			}		
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return users;		
	}

	public int deleteUser(String cpf) {	
		try {
			if(connection == null) {
				return 500;
			}
						
			String sqlStatement = "Delete FROM Web_API.User WHERE user_cpf = ?";			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);			
			statement.setString(1, cpf);
			
			return statement.executeUpdate();			
		} catch (SQLException e) {			
			e.printStackTrace();	
			
			if(e.getMessage().contains("foreign key constraint fails"))
				return 409;			
			
			return 500;
		}
	}

}
