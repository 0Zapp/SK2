package app.forms;

public class CardForm {

	long cardNumber;
	int securityNumber;

	public CardForm(String userName, String userSurname, long cardNumber, int securityNumber) {
		super();
		this.cardNumber = cardNumber;
		this.securityNumber = securityNumber;
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
