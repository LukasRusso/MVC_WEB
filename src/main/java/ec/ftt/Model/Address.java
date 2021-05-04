package ec.ftt.Model;

public class Address {
	private long addr_id;
	private long addr_userId;
	private String addr_street;
	private int addr_number;
	private String addr_city;
	private String addr_state;
	private String addr_country;
	private String addr_cep;
	
	public Address() {}
	
	public Address(long addr_id, long addr_userId, String addr_street, int addr_number,
			String addr_city, String addr_state, String addr_cep) {
		
		this.setAddr_id(addr_id);
		this.setAddr_userId(addr_userId);
		this.setAddr_street(addr_street);
		this.setAddr_number(addr_number);
		this.setAddr_city(addr_city);
		this.setAddr_state(addr_state);
		this.setAddr_cep(addr_cep);
	}
	
	public long getAddr_id() {
		return addr_id;
	}
	public void setAddr_id(long addr_id) {
		this.addr_id = addr_id;
	}
	public long getAddr_userId() {
		return addr_userId;
	}
	public void setAddr_userId(long addr_userId) {
		this.addr_userId = addr_userId;
	}
	public String getAddr_street() {
		return addr_street;
	}
	public void setAddr_street(String addr_street) {
		this.addr_street = addr_street;
	}
	public int getAddr_number() {
		return addr_number;
	}
	public void setAddr_number(int addr_number) {
		this.addr_number = addr_number;
	}
	public String getAddr_city() {
		return addr_city;
	}
	public void setAddr_city(String addr_city) {
		this.addr_city = addr_city;
	}
	public String getAddr_state() {
		return addr_state;
	}
	public void setAddr_state(String addr_state) {
		this.addr_state = addr_state;
	}
	public String getAddr_country() {
		return addr_country;
	}
	public void setAddr_country(String addr_country) {
		this.addr_country = addr_country;
	}
	public String getAddr_cep() {
		return addr_cep;
	}
	public void setAddr_cep(String addr_cep) {
		this.addr_cep = addr_cep;
	}
	@Override
	public String toString() {
		return 	"{\n\tID: " + String.valueOf(this.getAddr_id()) + ",\n" +
				"\tUserID: " + String.valueOf(this.getAddr_userId()) + ",\n" +
				"\tStreet: " + this.getAddr_street() +  ",\n" +
				"\tNumber: " + String.valueOf(this.getAddr_number()) + ",\n" +
				"\tCity:" + this.getAddr_city() + ",\n" +
				"\tState:" + this.getAddr_state() + ",\n" +
				"\tCEP: " + this.getAddr_cep() + "\n}";
	}	
}
