package ec.ftt.API;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ec.ftt.DAO.GameDao;
import ec.ftt.Model.Game;

@WebServlet("/GameAPI")
public class GameAPI extends HttpServlet  {
	private static final long serialVersionUID = 2L;

	public GameAPI() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
	}

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(418);

		String gameId = request.getParameter("game-id");

		if(gameId != null) {
			long id = Long.valueOf(gameId);

			GameDao gameDao = new GameDao();

			Game game = gameDao.getGameById(id);
			Gson gson = new Gson();
			response.getWriter().append(gson.toJson(game));

		} else {

			GameDao gameDao = new GameDao();

			List<Game> games = gameDao.getAllGame();

			Gson gson = new Gson();

			response.getWriter().append(gson.toJson(games));
		} 


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Game u = new Game(
				request.getParameter("game-name"),
				request.getParameter("game-producer"),
				request.getParameter("game-genre"),
				request.getParameter("game-releaseDate")
				);

		GameDao gameDao = new GameDao();

		gameDao.addGame(u);

		response.sendRedirect("/MVC_WEB/Game/allGames.html");
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		Game u = new Game(
				Long.parseLong(request.getParameter("game-id")),
				request.getParameter("game-name"),
				request.getParameter("game-producer"),
				request.getParameter("game-genre"),
				request.getParameter("game-releaseDate")
				);
		GameDao gameDao = new GameDao();

		gameDao.updateGame(u);

		response.getWriter().append(u.toString());
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("game-id") == null)
			response.sendError(407, "Informe o ID do usuário a ser retornado!!!" );
		else {
			Long gameId = Long.valueOf(request.getParameter("game-id"));

			GameDao ud = new GameDao();

			ud.deleteGame(gameId);
			response.setStatus(200);
			response.getWriter().append(request.getParameter("game-id") + " Game removido");
		}
	}
}
