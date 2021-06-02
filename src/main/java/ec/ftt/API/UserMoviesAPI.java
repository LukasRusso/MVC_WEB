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
import ec.ftt.DAO.UserMovieDao;
import ec.ftt.Model.Movie;

/**
 * Servlet implementation class userMoviesAPI
 */
@WebServlet("/UserMoviesAPI")
public class UserMoviesAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UserMoviesAPI() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = Long.parseLong(request.getParameter("user_id"));	
		List<Movie> movie = new ArrayList<Movie>();
		UserMovieDao dao = new UserMovieDao();
		Gson gson =  new Gson();
		
		try {	
			String resp = "";
			response.setStatus(200);
			response.setContentType("application/json");
			
			movie = dao.getAllMovieUser(userId);				
			
			resp +=  "{\"Status\": 200, \"Movies\":[ ";
			while(!movie.isEmpty()) {								
				resp += gson.toJson(movie.remove(0)) + ",";
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
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setStatus(200);			
			Long userId = Long.parseLong(request.getParameter("user-id"));
			Long movieId = Long.parseLong(request.getParameter("movie-id"));
			
			UserMovieDao dao = new UserMovieDao();

			dao.addMovieUser(userId, movieId);
		}
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500 }");
			e.printStackTrace();			
		}	
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setStatus(204);	
			Long userId = Long.parseLong(request.getParameter("user-id"));
			Long movieId = Long.parseLong(request.getParameter("movie-id"));
			
			UserMovieDao dao = new UserMovieDao();

			dao.deleteMovieUser(userId, movieId);
		}
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500 }");
			e.printStackTrace();			
		}	
}
