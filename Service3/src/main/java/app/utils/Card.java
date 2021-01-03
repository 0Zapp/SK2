package app.utils;

public class Card {

	private long cardId;

	String UserName;
	String UserSurname;
	long cardNumber;
	int securityNumber;

	long UserId;

	public Card() {

	}

	public Card(String userName, String userSurname, long cardNumber, int securityNumber, long USerId) {
		super();
		UserName = userName;
		UserSurname = userSurname;
		this.cardNumber = cardNumber;
		this.securityNumber = securityNumber;
		this.UserId = USerId;
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

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
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
