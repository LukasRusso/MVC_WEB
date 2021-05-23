package ec.ftt.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
	
	private long id;
	private String name;
	private Date birthday;
	private String email;
	private String pass;
	private String color;
	private String cpf;
	
	public User() {}
	
	public User(long id, String name, Date birthday, String email, String color, String cpf) {
		super();
		this.setId(id);
		this.setName(name);
		this.setBirthday(birthday);
		this.setEmail(email);
		this.setColor(color);
		this.setCpf(cpf);
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public void setBirthday(String birthday) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);			
			this.setBirthday(date);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Override
	public String toString() {
		return 	"ID: " + String.valueOf(this.getId()) + ", " +
				"Name: " + this.getName() + ", " +
				"Birthday: " + this.getBirthday() +  ", " +
				"Email: " + this.getEmail() + ", " +
				"Pass: *********, " +
				"Color (Hex): #" + this.getColor() + ", " +
				"CPF: " + this.getCpf();
	}
}
