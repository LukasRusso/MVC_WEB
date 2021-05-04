package ec.ftt.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ec.ftt.Model.Address;

public class AddressDAO {
	private Connection connection;
	
	public AddressDAO() {
		connection = new ConnectionSQL().getConnection();
	}
	
	public int saveAddres(Address addr) {
		String sqlStatement = "INSERT INTO Web_API.Address ("
				+ "addr_userId, "
				+ "addr_street, "
				+ "addr_number, "
				+ "addr_city, "
				+ "addr_state, "
				+ "addr_country, "
				+ "addr_cep) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";	
		
		try {
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			
			statement.setLong(1, addr.getAddr_userId());		
			statement.setString(2, addr.getAddr_street());
			statement.setInt(3, addr.getAddr_number());	
			statement.setString(4, addr.getAddr_city());	
			statement.setString(5, addr.getAddr_state());	
			statement.setString(6, addr.getAddr_country());	
			statement.setString(7, addr.getAddr_cep());	
			
			statement.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();			
			return 404;
		}	
		
		System.out.println("(AddresDAO) address - Saved");
		return 200;
	}
	
	public Address getAddress(Long idUser) {
		Address addr = new Address();
		
		try {
			String sqlStatement = "SELECT * FROM Web_API.Address WHERE addr_userId = ?";
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);			
			statement.setLong(1, idUser);								
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {				
				addr.setAddr_id(rs.getLong("addr_id"));				
				addr.setAddr_userId(rs.getLong("addr_userId"));				
				addr.setAddr_street(rs.getDate("addr_street").toString());				
				addr.setAddr_number(rs.getInt("addr_number"));				
				addr.setAddr_city(rs.getString("addr_city"));				
				addr.setAddr_state(rs.getString("addr_state"));
				addr.setAddr_country(rs.getString("addr_country"));				
				addr.setAddr_cep(rs.getString("addr_cep"));
			}			
			
		} catch (SQLException e) {			
			e.printStackTrace();			
			return null;
		}
		
		return addr;
	}
	
	public int updateAddress(Address addr) {
		String sqlStatement = "UPDATE Web_API.Address SET "
				+ "addr_street = ?,"
				+ "addr_number = ?,"
				+ "addr_city = ?,"
				+ "addr_state = ? "	
				+ "addr_country = ?,"
				+ "addr_cep = ? "	
				+ "WHERE addr_userId = ?";	
		
		try {
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			
			statement.setString(1, addr.getAddr_street());		
			statement.setInt(2, addr.getAddr_number());
			statement.setString(3, addr.getAddr_city());	
			statement.setString(4, addr.getAddr_state());	
			statement.setString(5, addr.getAddr_country());	
			statement.setString(6, addr.getAddr_cep());	
			statement.setLong(7, addr.getAddr_userId());
			
			statement.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();			
			return 404;
		}	
		
		System.out.println("(AddressDAO) Address - Updated");
		return 200;
	}
	
	public List<Address> listAddress(){
		List<Address> addrs =  new ArrayList<Address>();
		
		try {
			String sqlStatement = "SELECT * FROM Web_API.Address;";
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			ResultSet rs = statement.executeQuery();
			
			Address addr;			
			while(rs.next()) {
				addr = new Address();
				addr.setAddr_id(rs.getLong("addr_id"));				
				addr.setAddr_userId(rs.getLong("addr_userId"));				
				addr.setAddr_street(rs.getString("addr_street"));				
				addr.setAddr_number(rs.getInt("addr_number"));				
				addr.setAddr_city(rs.getString("addr_city"));				
				addr.setAddr_state(rs.getString("addr_state"));
				addr.setAddr_country(rs.getString("addr_country"));				
				addr.setAddr_cep(rs.getString("addr_cep"));
				
				addrs.add(addr);
			}		
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return addrs;		
	}

	public int deleteUser(Long idUser) {
		try {
			String sqlStatement = "Delete FROM Web_API.Address WHERE addr_userId = ?";
			
			PreparedStatement statement = connection.prepareStatement(sqlStatement);			
			statement.setLong(1, idUser);	
			
			statement.executeQuery();			
		} catch (SQLException e) {			
			e.printStackTrace();			
			return 404;
		}
		
		return 200;
	}
}
