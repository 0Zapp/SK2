package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	private String surrname;
	private String email;
	private String password;
	private long passportNumber;
	
	private int miles;
	private String userRank;

	public User() {

	}

	public User(String name, String surrname, String email, long passportNumber, String password) {
		super();
		this.name = name;
		this.surrname = surrname;
		this.email = email;
		this.password = password;
		this.passportNumber = passportNumber;
		
		this.miles = 0;
		this.userRank = "Bronze";
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}

	public String getRank() {
		return userRank;
	}

	public void setRank(String rank) {
		this.userRank = rank;
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

	public String getSurrname() {
		return surrname;
	}

	public void setSurrname(String surrname) {
		this.surrname = surrname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(long passportNumber) {
		this.passportNumber = passportNumber;
	}

}
