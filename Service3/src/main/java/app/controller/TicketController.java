package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Ticket;
import app.forms.TicketForm;
import app.repository.TicketRepository;
import app.utils.Flight;
import app.utils.User;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	private TicketRepository ticketRepo;

	@Autowired
	public TicketController(TicketRepository ticketRepo) {
		this.ticketRepo = ticketRepo;
	}

	@PostMapping("/addNew")
	public ResponseEntity<String> addNewTicket(@RequestBody TicketForm ticketForm,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {
			int count = ticketRepo.countByFlightId(ticketForm.getFlightId());
			String url = "http://localhost:8081/flight/flightFull/" + ticketForm.getFlightId() + "/" + count;
			boolean isFull = UtilsMethods.sendGet(url, token, Boolean.class).getBody();
			if (isFull) {
				return new ResponseEntity<String>(HttpStatus.CONFLICT);
			}
			User user = UtilsMethods.sendGet("http://localhost:8080/user/get/", token, User.class).getBody();
			Flight flight = UtilsMethods.sendGet("http://localhost:8081/flight/get/" + ticketForm.getFlightId(), token, Flight.class).getBody();
			Integer price = flight.getPrice();
			String rank = user.getRank();
			if (rank.equals("Gold"))
				price = (int) (price * 0.8);
			else if (rank.equals("Silver"))
				price = (int) (price * 0.8);
			
			Date date = new Date(System.currentTimeMillis());
			Ticket ticket = new Ticket(ticketForm.getFlightId(), user.getId(), date, ticketForm.getCardId(), price);

			ticketRepo.saveAndFlush(ticket);
			String status = UtilsMethods.sendGet("http://localhost:8080/user/addMiles/" + flight.getDuration().toString(), token, String.class).getBody();
			return new ResponseEntity<String>(status, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Ticket>> getTicketsForUser(@RequestHeader(value = HEADER_STRING) String token) {
		try {
			User user = UtilsMethods.sendGet("http://localhost:8080/user/get/", token, User.class).getBody();
			Long userId = user.getId();
			List<Ticket> tickets = ticketRepo.findByUserIdOrderByDateDesc(userId);

			return new ResponseEntity<>(tickets, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/cancel/{flightId}")
	public ResponseEntity<String> cancelTicket(@PathVariable Long flightId, @RequestHeader(value = HEADER_STRING) String token) {
		try {
			ArrayList<Ticket> tickets = (ArrayList<Ticket>) ticketRepo.findByFlightId(flightId);
			for (Ticket ticket : tickets) {
				ticket.setStatus("canceled");
				ticketRepo.saveAndFlush(ticket);
				Flight flight = UtilsMethods.sendGet("http://localhost:8081/flight/get/" + ticket.getFlightId(), token, Flight.class).getBody();
				String status = UtilsMethods.sendGet("http://localhost:8080/user/removeMiles/" + ticket.getUserId() + "/" + flight.getDuration().toString(), token, String.class).getBody();
			}
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

	}

}
