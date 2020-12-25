package app.forms;

public class FlightForm {

	private long planeID;
	private String startingDestination;
	private String endingDestination;
	private long duration;
	private int price;

	public FlightForm(long planeID, String startingDestination, String endingDestination, long duration, int price) {

		this.planeID = planeID;
		this.startingDestination = startingDestination;
		this.endingDestination = endingDestination;
		this.duration = duration;
		this.price = price;
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
