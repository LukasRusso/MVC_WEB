package ec.ftt.DAO;

import java.sql.*;
import java.util.*;

import ec.ftt.Model.Movie;

public class UserMovieDao {
	private Connection connection;
	
	public UserMovieDao() {
		connection = new ConnectionSQL().getConnection();
	} 

	public void addMovieUser(Long userId,Long movieId) {

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Web_API.UserMovies (userId, moviesId) "
							+ "VALUES (?, ?)");
			
			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, movieId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	public void deleteMovieUser(Long userId, Long moviesId) {
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM Web_API.UserMovies WHERE userId=? and moviesId=?");

			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, moviesId);
			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	public List<Movie> getAllMovieUser(Long userId) {
		List<Movie> movieList = new ArrayList<Movie>();

		try {
			String sql = "SELECT * FROM Web_API.UserMovies \r\n"
					+ "inner join  Web_API.MOVIE\r\n"
					+ "on moviesId = Web_API.MOVIE.id\r\n"
					+ "WHERE userId = ?";			
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, userId);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();

				movie.setId(rs.getLong("ID"));
				movie.setName(rs.getString("NAME"));
				movie.setProducer(rs.getString("PRODUCER"));
				movie.setGenre(rs.getString("GENRE"));
				movie.setReleaseDate(rs.getString("RELEASE_DATE"));

				movieList.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movieList;
	} 
}
