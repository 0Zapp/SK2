package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long FlightId;

	private Long planeID;
	private String startingDestination;
	private String endingDestination;
	private Long duration;
	private Integer price;

	public Flight() {

	}

	public Flight(Long planeID, String startingDestination, String endingDestination, Long duration, Integer price) {
		super();
		this.planeID = planeID;
		this.startingDestination = startingDestination;
		this.endingDestination = endingDestination;
		this.duration = duration;
		this.price = price;
	}

	public static Flight from(Long planeID, String startingDestination, String endingDestination, Long duration, Integer price) {
		return new Flight(planeID, startingDestination, endingDestination, duration, price);
	}

	public long getFlightId() {
		return FlightId;
	}

	public void setFlightId(long flightId) {
		FlightId = flightId;
	}

	public Long getPlaneID() {
		return planeID;
	}

	public void setPlaneID(Long planeID) {
		this.planeID = planeID;
	}

	public String getStartingDestination() {
		return startingDestination;
	}

	public void setStartingDestination(String startingDestination) {
		this.startingDestination = startingDestination;
	}

	public String getEndingDestination() {
		return endingDestination;
	}

	public void setEndingDestination(String endingDestination) {
		this.endingDestination = endingDestination;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}


}
