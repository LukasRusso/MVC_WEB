package ec.ftt.API;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ec.ftt.DAO.UserDAO;
import ec.ftt.Model.User;
import ec.ftt.Util.ValidaUser;

@WebServlet("/UserAPI")
public class UserAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	
    public UserAPI() {
        super();        
    }
	
	public void init(ServletConfig config) throws ServletException {}
	
	public void destroy() {}
	
	//Method GET(OK)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users = new ArrayList<User>();
		UserDAO dao = new UserDAO();	
		User user = new User();
		
		try {	
			String resp = "";
			response.setStatus(200);
			response.setContentType("application/json");			
			boolean list = Boolean.parseBoolean(request.getParameter("list_users"));
			boolean cpf = (request.getParameter("user_cpf") != null);	
			
			String email = request.getParameter("user_email");		
			String pass = request.getParameter("user_pass");		
			
			if(list) {
				users = dao.listUser();				
				
				resp +=  "{\"Status\": 200, \"Users\":[ ";
				while(!users.isEmpty()) {
					User u = users.remove(0);
					u.setPass("*****Nothing here to see*****");					
					resp += gson.toJson(u) + ",";
				}	
				resp = resp.substring(0, resp.length() - 1);
				resp += "]}";
				
				response.setStatus(200);
				response.getWriter().append(resp);
				return;
			}	
			
			if(cpf) {
				user = dao.getUserCPF(request.getParameter("user_cpf"));
				if(user.getCpf() == null) {
					response.setStatus(404);
					response.getWriter().append("{\"Status\": 404 }");
				}
				else {
					response.setStatus(200);				
					user.setPass("*****Nothing here to see*****");	
					response.getWriter().append("{\"Status\": 200,\"User\": " + gson.toJson(user) + "}");
				}				
						
				return;
			}	
			
			System.out.println(email);
			user = dao.getUser(email);
			
			//CPF is a required field			
			if(user.getCpf() != null) {
				if(!user.getPass().equals(pass)) {
					response.setStatus(403);				
					response.getWriter().append("{\"Status\": 403,\"Error\": \"Forbidden\"}");					
				} else {
					response.setStatus(200);
					response.getWriter().append("{\"Status\": 200,\"User\": " + gson.toJson(user) + "}");
				}
			}
			else {
				response.setStatus(404);
				response.getWriter().append("{\"Status\": 404 }");
			}
		}
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500 }");
			e.printStackTrace();			
		}	
	}
	
	//Method Post(OK)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = createUser(request);
			UserDAO dao =  new UserDAO();
			ValidaUser valUser =  new ValidaUser();	
			int resp = -1;
			String error = valUser.checkUser(user);			
			response.setContentType("application/json");
			
			if(error.length() != 0) {
				resp = 0;				
			}
			else {
				resp = dao.saveUser(user);
			}
			
			switch(resp) {				
				case 0:{
					response.setStatus(400);
					response.getWriter().append("{\"Status\": 400" + ","
							+ " \"Error\": " + gson.toJson(error.substring(0, error.length()-1).split("#")) + "}");
					break;
				}
				case 1:{
					response.setStatus(201);
					user.setPass("*****Nothing here to see*****");
					response.getWriter().append("{\"Status\": 201, \"User\": " + 
							gson.toJson(user) + "}");
					break;
				}
				case 2:{
					response.setStatus(409);
					response.getWriter().append("{\"Status\": 409, \"Error\": \"Email Already Register\"}");
					break;
				}
				//CPF already register
				case 3:{
					response.setStatus(409);
					response.getWriter().append("{\"Status\": 409, \"Error\": \"CPF Already Register\"}");
					break;
				}
				case 500:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\": " + resp + "}");
					break;
				}
				default:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\":" + resp + "}");
					break;
				}
			}
		}		
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500}");
		}		
	}
	
	//Method Put(OK)
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {			
			User user = createUser(postRequestBody(request));
			UserDAO dao =  new UserDAO();
			ValidaUser valUser =  new ValidaUser();	
			String error = valUser.checkUser(user);	
			
			int resp = -1;
			
			if(error.length() != 0) {
				resp = 0;				
			}
			else {
				resp = dao.updateUser(user);
			}			
			
			response.setContentType("application/json");
			
			switch(resp) {
				case 1:{
					response.setStatus(200);
					user.setPass("*****Nothing here to see*****");
					response.getWriter().append("{\"Status\": 202, \"User\": " + gson.toJson(user) + "}");					
					break;
				}
				case 0:{
					response.setStatus(400);
					response.getWriter().append("{\"Status\": 400" + 
							" \"Error\": " + gson.toJson(error.substring(0, error.length()-1).split("#")) + "}");				
					break;
				}
				case 500:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\": 500");
					break;
				}
				default:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\": 500");
					break;
				}
			}
		}		
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500}");
		}		
	}
	
	//Method Delete(OK)
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = createUser(postRequestBody(request));
			UserDAO dao = new UserDAO();
			response.setContentType("application/json");
			int resp = dao.deleteUser(user.getCpf());
					
			switch(resp) {
				case 0:{
					response.setStatus(404);
					response.getWriter().append("{\"Status\": 404, \"Error\": \"CPF - " +
							request.getParameter("user_cpf") + " not found\"}");
					break;
				}
				case 1:{
					response.setStatus(204);
					response.getWriter().append("{\"Status\": 204}");
					break;
				}
				case 409:{
					response.setStatus(409);
					response.getWriter().append("{\"Status\": 409, \"Error\": \"First delete User\'s address\"}");
					break;
				}
				case 500:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\": 500}");
					break;
				}
				default:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\": 500}");
					break;
				}
			}
		}		
		catch(Exception e) {
			response.setStatus(500);
			response.getWriter().append("{\"Status\": 500}");
		}		
	}
	
	//Create object User from request	
	private User createUser(HttpServletRequest request) {
		User user = new User();	
		
		user.setName(request.getParameter("user_name"));
		user.setBirthday(request.getParameter("user_birthday"));
		user.setEmail(request.getParameter("user_email"));
		user.setPass(request.getParameter("user_pass"));
		user.setColor(request.getParameter("user_color"));
		user.setCpf(request.getParameter("user_cpf"));
		
		return user;
	}
		
	//Create object User from text
	private User createUser(String text) {
		User user = new User();	
		text = text.replace("+", " ").replace("%23", "#").replace("%40", "@");
		String[] campos = text.split("&");;
		
		for(int i = 0 ; i < campos.length; i++) {
			String[] chaveValor = campos[i].split("=");			
			
			if(chaveValor[0].contains("user_name")) {				
				user.setName(chaveValor[1]);
			}
				
			else if(chaveValor[0].contains("user_birthday")) {
				user.setBirthday(chaveValor[1]);
			}
				
			else if(chaveValor[0].contains("user_email")) {
				user.setEmail(chaveValor[1]);
			}
			
			else if(chaveValor[0].contains("user_pass")) {
				user.setEmail(chaveValor[1]);
			}
				
			else if(chaveValor[0].contains("user_color")) {
				user.setColor(chaveValor[1]);
			}
				
			else if(chaveValor[0].contains("user_cpf")) {
				user.setCpf(chaveValor[1]);
			}				
		}	
		
		return user;
	}
	
	//Get body from request
	private String postRequestBody(HttpServletRequest request) {		
		Scanner s = null;
		String value = "";
		try {
			s = new Scanner(request.getInputStream(), "UTF-8");
			
			while(s.hasNext()) {
				value += s.next();
			}
	
			System.out.println(value);
			return value;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(s != null)
				s.close();
		}
		return "";
	}
}
