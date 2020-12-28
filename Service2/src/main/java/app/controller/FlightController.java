package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Flight;
import app.forms.FlightForm;
import app.repository.FlightRepository;

@RestController
@RequestMapping("/flight")
public class FlightController {

	private FlightRepository flightRepo;

	@Autowired
	public FlightController(FlightRepository flightRepo) {
		this.flightRepo = flightRepo;
	}

	@PostMapping("/addNew")
	public ResponseEntity<String> addNewFlight(@RequestBody FlightForm flightForm) {
		try {

			Flight flight = new Flight(flightForm.getPlaneID(), flightForm.getStartingDestination(),
					flightForm.getEndingDestination(), flightForm.getDuration(), flightForm.getPrice());

			flightRepo.saveAndFlush(flight);

			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{x}")
	public ResponseEntity<Long> deleteFlight(@PathVariable Long x) {

		try {
			flightRepo.deleteById(x);
			return new ResponseEntity<Long>(x, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}
	
	//search po svakom parametru
	//spisak letova
	

}
