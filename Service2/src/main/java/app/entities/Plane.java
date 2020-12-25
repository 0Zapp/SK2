package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Plane {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PlaneId;

	private String name;
	private int capacity;

	public Plane() {

	}

	public Plane(String name, int capacity) {
		super();

		this.name = name;
		this.capacity = capacity;
	}

	public long getPlaneId() {
		return PlaneId;
	}

	public void setPlaneId(long planeId) {
		PlaneId = planeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
