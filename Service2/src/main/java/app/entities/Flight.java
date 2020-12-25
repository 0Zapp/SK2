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

	private long planeID;
	private String startingDestination;
	private String endingDestination;
	private long duration;
	private int price;

	public Flight() {

	}

	public Flight(long planeID, String startingDestination, String endingDestination, long duration, int price) {
		super();
		this.planeID = planeID;
		this.startingDestination = startingDestination;
		this.endingDestination = endingDestination;
		this.duration = duration;
		this.price = price;
	}

	public long getFlightId() {
		return FlightId;
	}

	public void setFlightId(long flightId) {
		FlightId = flightId;
	}

	public long getPlaneID() {
		return planeID;
	}

	public void setPlaneID(long planeID) {
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

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
