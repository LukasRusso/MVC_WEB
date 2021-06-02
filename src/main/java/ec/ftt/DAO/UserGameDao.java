package ec.ftt.DAO;

import java.sql.*;
import java.util.*;

import ec.ftt.Model.Game;

public class UserGameDao {

	private Connection connection;
	
	public UserGameDao() {
		connection = new ConnectionSQL().getConnection();
	}

	public void addGameUser(Long userId,Long gameId) {

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Web_API.UserGame (userId, gamesId) "
							+ "VALUES (?, ?)");


			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, gameId);

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
			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //deleteUser
		
	public List<Game> getAllGameFromUser(Long userId) {
		List<Game> gameList = new ArrayList<Game>();

		try {
			String sql = "SELECT * FROM Web_API.UserGame \r\n"
					+ "inner join  Web_API.GAME\r\n"
					+ "on gamesId = Web_API.GAME.id\r\n"
					+ "WHERE userId = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, userId);
			ResultSet rs = statement.executeQuery();			
			
			while (rs.next()) {

				Game game = new Game();

				game.setId(rs.getLong("gamesId"));
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

}
