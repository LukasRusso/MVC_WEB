package ec.ftt.Util;

import ec.ftt.Model.Address;

public class ValidaAddress {

	public ValidaAddress() {}
	
	public String checkAddress(Address addr) {
		String error = "";
		
		if(!checkStreet(addr.getAddr_street())) error += "The Street field must be informed!#";
		if(!checkNumber(addr.getAddr_number())) error += "The Number field must be greater than zero!#";
		if(!checkCity(addr.getAddr_city())) error += "The City field must be informed!#";
		if(!checkState(addr.getAddr_state())) error += "The State field must be informed!#";
		if(!checkCountry(addr.getAddr_country())) error += "The Country field must be informed!#";
		if(!checkCep(addr.getAddr_cep())) error += "The CEP field must be informed!#";
		
		return error;
	}
	
	private boolean checkStreet(String street) {
		if(street.isEmpty() || street.length() == 0) return false;		
		return true;
	}
	private boolean checkNumber(int number) {
		if(number <= 0) return false;		
		return true;
	}
	private boolean checkCity(String city) {
		if(city.isEmpty() || city.length() == 0) return false;		
		return true;
	}
	private boolean checkState(String state) {
		if(state.isEmpty() || state.length() == 0) return false;		
		return true;
	}
	private boolean checkCountry(String country) {
		if(country.isEmpty() || country.length() == 0) return false;		
		return true;
	}
	private boolean checkCep(String cep) {
		if(cep.isEmpty() || cep.length() == 0) return false;		
		return true;
	}
}
