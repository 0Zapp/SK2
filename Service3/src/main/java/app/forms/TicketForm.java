package app.forms;


public class TicketForm {


	private Long flightId;
	private Long userId;
	private String status;
	private String date;
	private Long cardId;
	private Integer price;


	public TicketForm(Long flightId, Long userId, String status, String date, Long cardId, Integer price) {
		super();
		this.flightId = flightId;
		this.userId = userId;
		this.status = status;
		this.date = date;
		this.cardId = cardId;
		this.price = price;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public Long getCardId() {
		return cardId;
	}


	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}


	public Long getFlightId() {
		return flightId;
	}


	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}

}
