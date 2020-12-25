package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cardId;

	String UserName;
	String UserSurname;
	long cardNumber;
	int securityNumber;

	public Card(String userName, String userSurname, long cardNumber, int securityNumber) {
		super();
		UserName = userName;
		UserSurname = userSurname;
		this.cardNumber = cardNumber;
		this.securityNumber = securityNumber;
	}
	
	public Card() {

	}

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getSecurityNumber() {
		return securityNumber;
	}

	public void setSecurityNumber(int securityNumber) {
		this.securityNumber = securityNumber;
	}

}
