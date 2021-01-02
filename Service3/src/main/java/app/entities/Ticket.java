package app.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long TicketId;

	private Long flightId;
	private Long userId;
	private String status;
	private Date date;
	private Long cardId;
	private Integer price;

	public Ticket() {

	}


	public Ticket(Long flightId, Long userId, Date date, Long cardId, Integer price) {
		super();
		this.flightId = flightId;
		this.userId = userId;
		this.status = "active";
		this.date = date;
		this.cardId = cardId;
		this.price = price;
	}


	public static Ticket from(Long flightId, Long userId, Date date, Long cardId, Integer price) {
		return new Ticket(flightId, userId, date, cardId, price);
	}


	public long getTicketId() {
		return TicketId;
	}


	public Long getCardId() {
		return cardId;
	}


	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public void setTicketId(long ticketId) {
		TicketId = ticketId;
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	
}
