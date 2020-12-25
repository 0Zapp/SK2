package app.forms;

public class CardForm {

	String UserName;
	String UserSurname;
	long cardNumber;
	int securityNumber;

	public CardForm(String userName, String userSurname, long cardNumber, int securityNumber) {
		super();
		UserName = userName;
		UserSurname = userSurname;
		this.cardNumber = cardNumber;
		this.securityNumber = securityNumber;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserSurname() {
		return UserSurname;
	}

	public void setUserSurname(String userSurname) {
		UserSurname = userSurname;
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
