package ec.ftt.DAO;

import java.sql.*;
import java.util.*;

import ec.ftt.Model.Game;
import ec.ftt.Model.Movie;

public class UserMovieDao {

	private Connection connection;
	
	public UserMovieDao() {
		connection = new ConnectionSQL().getConnection();
	}

	public void addMovieUser(Long userId,Long movieId) {

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Web_API.UserMovie (userId, moviesId) "
							+ "VALUES (?, ?)");


			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, movieId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	public void deleteGame(Long userId,Long gameId) {
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM Web_API.UserGame WHERE userId=? and gamesId=?");

			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, gameId);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //deleteUser

	public void updateGame(Game game) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE Web_API.GAME SET NAME=?, " 
							+ "PRODUCER=?, " 
							+ "GENRE=?, " 
							+ "RELEASE_DATE=? " 
							+ "WHERE ID=?");

			preparedStatement.setString(1, game.getName());
			preparedStatement.setString(2, game.getProducer());
			preparedStatement.setString(3, game.getGenre());
			preparedStatement.setString(4, game.getReleaseDate());

			preparedStatement.setLong(5, game.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Game> getAllGame() {

		List<Game> gameList = new ArrayList<Game>();

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Web_API.GAME");
			while (rs.next()) {

				Game game = new Game();

				game.setId(rs.getLong("ID"));
				game.setName(rs.getString("NAME"));
				game.setProducer(rs.getString("PRODUCER"));
				game.setGenre(rs.getString("GENRE"));
				game.setReleaseDate(rs.getString("RELEASE_DATE"));

				gameList.add(game);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gameList;
	} 
	
	public List<Movie> getAllMoviesFromUser(Long userId) {
		List<Movie> movieList = new ArrayList<Movie>();

		try {
			String sql = "SELECT * FROM Web_API.UserMovie \r\n"
					+ "inner join  Web_API.MOVIE\r\n"
					+ "on moviesId = Web_API.MOVIE.id\r\n"
					+ "WHERE userId = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, userId);
			ResultSet rs = statement.executeQuery();			
			
			while (rs.next()) {

				Movie movie = new Movie();

				movie.setId(rs.getLong("moviesId"));
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

	public Game getGameById(Long id) {

		Game gameOutput = new Game();

		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("SELECT * from Web_API.GAME WHERE ID=?");

			preparedStatement.setLong(1,id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				gameOutput.setId(rs.getLong("ID"));
				gameOutput.setName(rs.getString("NAME"));
				gameOutput.setProducer(rs.getString("PRODUCER"));
				gameOutput.setGenre(rs.getString("GENRE"));
				gameOutput.setReleaseDate(rs.getString("RELEASE_DATE"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gameOutput;
	} //getUserById

	public String getDbDate() {
		String output="";

		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("SELECT now() NOW");

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				output = rs.getString("NOW");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;
	} //getDbDate

}
