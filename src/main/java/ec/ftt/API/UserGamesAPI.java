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

import ec.ftt.DAO.GameDao;
import ec.ftt.DAO.UserDAO;
import ec.ftt.Model.Game;
import ec.ftt.Model.User;

@WebServlet("/UserGamesAPI")
public class UserGamesAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson =  new Gson();	
    
    public UserGamesAPI() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("user_id"));	
		List<Game> games = new ArrayList<Game>();
		GameDao dao = new GameDao();
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
		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
