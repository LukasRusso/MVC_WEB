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

import ec.ftt.DAO.MovieDao;
import ec.ftt.Model.Movie;

@WebServlet("/MovieAPI")
public class MovieAPI extends HttpServlet  {
	private static final long serialVersionUID = 3L;

	public MovieAPI() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {}

	public void destroy() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String movieId = request.getParameter("movie-id");

		if(movieId != null) {
			long id = Long.valueOf(movieId);

			MovieDao movieDao = new MovieDao();

			Movie movie = movieDao.getMovieById(id);
			Gson gson = new Gson();
			response.getWriter().append(gson.toJson(movie));

		} else {

			MovieDao movieDao = new MovieDao();

			List<Movie> movies = movieDao.getAllMovie();

			Gson gson = new Gson();

			response.getWriter().append(gson.toJson(movies));
		} 

		response.setStatus(200);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Movie u = new Movie(
				request.getParameter("movie-name"),
				request.getParameter("movie-producer"),
				request.getParameter("movie-genre"),
				request.getParameter("movie-releaseDate")
				);
		MovieDao movieDao = new MovieDao();
		System.out.println(request.getParameter("movie-name"));
		System.out.println(u.getName());

		movieDao.addMovie(u);

		response.sendRedirect("/MVC_WEB/Movie/allMovies.html");

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		Movie u = new Movie(
				Long.parseLong(request.getParameter("movie-id")),
				request.getParameter("movie-name"),
				request.getParameter("movie-producer"),
				request.getParameter("movie-genre"),
				request.getParameter("movie-releaseDate")
				);
		MovieDao movieDao = new MovieDao();

		movieDao.updateMovie(u);

		response.getWriter().append(u.toString());


	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("movie-id") == null)
			response.sendError(407, "Informe o ID do usuário a ser retornado!!!" );
		else {
			Long movieId = Long.valueOf(request.getParameter("movie-id"));
			System.out.println(movieId);



			MovieDao ud = new MovieDao();

			ud.deleteMovie(movieId);
			response.setStatus(200);
			response.getWriter().append(request.getParameter("movie-id") + " Movie removido");
		}
	}

}
