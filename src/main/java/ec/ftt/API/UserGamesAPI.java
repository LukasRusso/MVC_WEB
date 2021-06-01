package ec.ftt.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import ec.ftt.DAO.UserGameDao;
import ec.ftt.Model.Game;


@WebServlet("/UserGamesAPI")
public class UserGamesAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserGamesAPI() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = Long.parseLong(request.getParameter("user_id"));	
		List<Game> games = new ArrayList<Game>();
		UserGameDao dao = new UserGameDao();
		Gson gson =  new Gson();
		
		try {	
			String resp = "";
			response.setStatus(200);
			response.setContentType("application/json");	
			
			
			games = dao.getAllGameFromUser(userId);				
			
			resp +=  "{\"Status\": 200, \"Games\":[ ";
			while(!games.isEmpty()) {								
				resp += gson.toJson(games.remove(0)) + ",";
			}	
			resp = resp.substring(0, resp.length() - 1);
			resp += "]}";
			
			response.getWriter().append(resp);
		}
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500 }");
			e.printStackTrace();			
		}	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setStatus(200);			
			Long userId = Long.parseLong(request.getParameter("user-id"));
			Long gameId = Long.parseLong(request.getParameter("game-id"));
			
			UserGameDao dao = new UserGameDao();

			dao.addGameUser(userId,gameId);
		}
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500 }");
			e.printStackTrace();			
		}	
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setStatus(200);
			Long userId = Long.parseLong(request.getParameter("user-id"));
			Long gameId = Long.parseLong(request.getParameter("game-id"));
			
			UserGameDao dao = new UserGameDao();

			dao.deleteGame(userId, gameId);
		}
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500 }");
			e.printStackTrace();			
		}	
	}

}
