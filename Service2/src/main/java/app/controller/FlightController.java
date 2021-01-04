package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;

import java.util.Optional;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Flight;
import app.entities.Plane;
import app.forms.FlightForm;
import app.repository.FlightRepository;
import app.repository.PlaneRepository;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("/flight")
public class FlightController {
	
	
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue ticketQueue;

	@Autowired
	Queue userQueue;

	private FlightRepository flightRepo;
	private PlaneRepository planeRepo;

	@Autowired
	public FlightController(FlightRepository flightRepo, PlaneRepository planeRepo) {
		this.flightRepo = flightRepo;
		this.planeRepo = planeRepo;
	}

	@GetMapping("/get/{flightId}")
	public ResponseEntity<Flight> getById(@PathVariable Long flightId, @RequestHeader(value = HEADER_STRING) String token){
		try {
			ResponseEntity<Integer> response = UtilsMethods.sendGet("http://localhost:8080/user/isAdmin/", token);
			Flight flight = flightRepo.findById(flightId).get();
			return new ResponseEntity<Flight>(flight, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addNew")
	public ResponseEntity<String> addNewFlight(@RequestBody FlightForm flightForm,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			ResponseEntity<Integer> response = UtilsMethods.sendGet("http://localhost:8080/user/isAdmin/", token);
			if (response.getBody() == 1) {
				Flight flight = new Flight(flightForm.getPlaneID(), flightForm.getStartingDestination(),
						flightForm.getEndingDestination(), flightForm.getDuration(), flightForm.getPrice());

				flightRepo.saveAndFlush(flight);

				return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{x}")
	public ResponseEntity<Long> deleteFlight(@PathVariable Long x, @RequestHeader(value = HEADER_STRING) String token) {

		try {

			ResponseEntity<Integer> response = UtilsMethods.sendGet("http://localhost:8080/user/isAdmin/", token);
			if (response.getBody() == 1) {
				flightRepo.deleteById(x);
				
				jmsTemplate.convertAndSend(ticketQueue, x);
				jmsTemplate.convertAndSend(userQueue, x);
				return new ResponseEntity<Long>(x, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<Long>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/show/{page}/{size}")
	public ResponseEntity<Page<Flight>> show(@PathVariable int page, @PathVariable int size) {

		try {

			Pageable pageable = PageRequest.of(page, size);

			Page<Flight> flightPage = flightRepo.findAll(pageable);
			return new ResponseEntity<Page<Flight>>(flightPage, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Page<Flight>>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/flightFull/{flightId}/{count}")
	public ResponseEntity<Boolean> isFlightFull(@PathVariable Long flightId, @PathVariable Long count, @RequestHeader(value = HEADER_STRING) String token){
		try {
			Flight flight = flightRepo.findById(flightId).get();
			Long planeId = flight.getPlaneID();
			Plane plane = planeRepo.findById(planeId).get();
			Boolean result = count >= plane.getCapacity();
			return new ResponseEntity<Boolean>(result, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/search/{page}/{size}")
	public ResponseEntity<Page<Flight>> sort(@PathVariable int page, @PathVariable int size,
			@RequestBody FlightForm flightForm) {

		try {
			Pageable pageable = PageRequest.of(page, size);

			ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
					.withMatcher("planeID", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("startingDestination", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("endingDestination", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("duration", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("price", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

			Example<Flight> example = Example.of(
					Flight.from(flightForm.getPlaneID(), flightForm.getStartingDestination(),
							flightForm.getEndingDestination(), flightForm.getDuration(), flightForm.getPrice()),
					customExampleMatcher);

			Page<Flight> flightPage = flightRepo.findAll(example, pageable);
			System.out.println(flightPage.toString());

			return new ResponseEntity<Page<Flight>>(flightPage, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Page<Flight>>(HttpStatus.BAD_REQUEST);
		}

	}
	
	
}
