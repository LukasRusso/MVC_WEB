package ec.ftt.Util;

import java.util.*;
import ec.ftt.Model.User;

public class ValidaUser {
	public ValidaUser() {}
	
	public String checkUser(User user) {
		String error = "";
		
		if(!checkName(user)) error += "Nome Invalido#";
		if(!checkCPF(user)) error += "CPF Invalido#";
		if(!checkBirthday(user)) error += "Data Invalida#";
		
		return error;
	}
	private boolean checkBirthday(User user) {
		Date now = new Date();
		
		if((now.getTime() - 86400000) <= user.getBirthday().getTime())
			return false;	
		
		return true;
	}
	
	private boolean checkName(User user) {
		String name = user.getName();
				
		if(name.length() <= 3) return false;
		
		return true;
	}
	
	private boolean checkCPF(User user) {
		String cpf = user.getCpf().replace(".", "").replace("-", "");			
		int soma = 0;
		int resto = 0;
		
		if(cpf == "00000000000" || cpf.length() == 0) 
			return false;
	
		for(int i = 1; i <= 9; i++) {
			soma = soma + Integer.parseInt(cpf.substring(i-1, i)) * (11-i);
		}
		
		resto = (soma*10) % 11;		
		if((resto == 10) || (resto == 11)) resto = 0;
		if(resto != Integer.parseInt(cpf.substring(9, 10))) return false;
						
		soma = 0;
		for(int i = 1; i <= 10; i++) {
			soma = soma + Integer.parseInt(cpf.substring(i-1, i)) * (12-i);
		}
		
		resto = (soma*10) % 11;
		
		if((resto == 10) || (resto == 11)) resto = 0;		
		if(resto != Integer.parseInt(cpf.substring(10, 11))) return false;	
		
		return true;
	}
}
