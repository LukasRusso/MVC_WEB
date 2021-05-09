package ec.ftt.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import ec.ftt.DAO.AddressDAO;
import ec.ftt.Model.Address;
import ec.ftt.Util.ValidaAddress;


@WebServlet("/AddressAPI")
public class AddressAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
    
    public AddressAPI() {
        super();        
    }
    
	public void init(ServletConfig config) throws ServletException {}
	
	public void destroy() {}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		List<Address> addrs = new ArrayList<Address>();
		AddressDAO dao = new AddressDAO();	
		
		try {
			String resp = "";
			response.setContentType("application/json");
			boolean list =  Boolean.parseBoolean(request.getParameter("list_Address"));			
			if(list) {
				addrs = dao.listAddress();				
				
				resp +=  "{\"Status\":200, \"Address\":[";
				while(!addrs.isEmpty()) {					
					resp += gson.toJson(addrs.remove(0)) + ",";
				}	
				resp = resp.substring(0, resp.length() - 1);
				resp += "]}";
				
				response.setStatus(200);
				response.getWriter().append(resp);
				return;
			}			
			
			String parameter = request.getParameter("addr_userId");
			if(parameter == null || parameter.isEmpty()) {
				response.setStatus(400);
				response.getWriter().append("{\"Status\": 400,\"Error\": \"Bad Request\"}");
				return;
			}
			
			addrs.add(new AddressDAO().getAddress(Long.parseLong(parameter)));
			
			//userID is a required field			
			if(addrs.get(0).getAddr_userId() != 0) {
				response.setStatus(200);
				response.getWriter().append("{\"Status\": 200 ,\"Address\": " + gson.toJson(addrs.remove(0)) + "}");
			}
			else {
				response.setStatus(404);
				response.getWriter().append("{\"Status\": 404,\"Error\": \"Not Found\"}");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			response.getWriter().append("{\"Status\": 500}");			
			response.setStatus(500);
		}	
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {			
			Address addr = createAddress(postRequestBody(request));
			AddressDAO dao =  new AddressDAO();
			ValidaAddress valAddress =  new ValidaAddress();	
			String error = valAddress.checkAddress(addr);	
			
			int resp = -1;
			
			if(error.length() != 0) {
				resp = 0;				
			}
			else {
				resp = dao.updateAddress(addr);
			}			
			
			response.setStatus(200);
			response.setContentType("application/json");
			
			switch(resp) {
				case 1:{
					response.setStatus(200);
					response.getWriter().append("{\"Status\": 202, \"Address\": " + gson.toJson(addr) + "}");					
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
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Address addr = createAddress(request);
			AddressDAO dao =  new AddressDAO();	
			ValidaAddress valAdd =  new ValidaAddress();
			
			int resp = -1;
			String error = valAdd.checkAddress(addr);			
			response.setContentType("application/json");
			
			if(error.length() != 0) {
				resp = 0;				
			}
			else {
				resp = dao.saveAddress(addr);
			}	
			
			switch(resp) {
				case 1:{
					response.setStatus(201);
					response.getWriter().append("{\"Status\": 201, \"Address\": " + gson.toJson(addr) + "}");
					break;
				}
				case 0:{
					response.setStatus(400);
					response.getWriter().append("{\"Status\": 400" + ","
							+ " \"Error\": " + gson.toJson(error.substring(0, error.length()-1).split("#")) + "}");
					break;
				}
				//Address already register
				case 409:{
					response.setStatus(409);
					response.getWriter().append("{\"Status\": " + 
							resp + ", \"Error\": \"User with Address Already Register\"}");
					break;
				}
				case 500:{
					response.setStatus(500);
					response.getWriter().append("{\"Status\": 500}");
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
			e.printStackTrace();
			response.setStatus(500);
		}	
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			Address addr = createAddress(postRequestBody(request));
			AddressDAO dao = new AddressDAO();
			
			response.setStatus(204);
			response.setContentType("application/json");
			int resp = dao.deleteAddres(addr.getAddr_userId());
					
			switch(resp) {
				case 0:{
					response.setStatus(404);
					response.getWriter().append("{\"Status\": 404, \"Error\": \"Address not found\"}");
					break;
				}
				case 1:{
					response.setStatus(204);
					response.getWriter().append("{\"Status\": 204}");
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
	
	//Create object Address from request
	private Address createAddress(HttpServletRequest request) {
		Address addr = new Address();	
		
		addr.setAddr_userId(Integer.parseInt(request.getParameter("addr_userId")));
		addr.setAddr_street(request.getParameter("addr_street"));
		addr.setAddr_number(Integer.parseInt(request.getParameter("addr_number")));
		addr.setAddr_city(request.getParameter("addr_city"));
		addr.setAddr_state(request.getParameter("addr_state"));
		addr.setAddr_country(request.getParameter("addr_country"));
		addr.setAddr_cep(request.getParameter("addr_cep"));
		
		return addr;
	}
	
	//Create object Address from text
	private Address createAddress(String text) {
		Address addr = new Address();	
		text = text.replace("+", " ").replace("%23", "#").replace("%40", "@");
		String[] campos = text.split("&");;
		
		for(int i = 0 ; i < campos.length; i++) {
			String[] chaveValor = campos[i].split("=");			
			
			if(chaveValor[0].contains("addr_userId")) {				
				addr.setAddr_userId(Long.parseLong(chaveValor[1]));
			}
				
			else if(chaveValor[0].contains("addr_street")) {
				addr.setAddr_street(chaveValor[1]);
			}
				
			else if(chaveValor[0].contains("addr_number")) {
				addr.setAddr_number(Integer.parseInt(chaveValor[1]));
			}
				
			else if(chaveValor[0].contains("addr_city")) {
				addr.setAddr_city(chaveValor[1]);
			}
				
			else if(chaveValor[0].contains("addr_state")) {
				addr.setAddr_state(chaveValor[1]);
			}	
			
			else if(chaveValor[0].contains("addr_country")) {
				addr.setAddr_country(chaveValor[1]);
			}
			
			else if(chaveValor[0].contains("addr_cep")) {
				addr.setAddr_cep(chaveValor[1]);
			}
		}	
		
		return addr;
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
