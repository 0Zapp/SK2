package app.listener;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import app.entities.Ticket;
import app.repository.TicketRepository;

@Component
public class Consumer {

	private TicketRepository ticketRepo;

	@Autowired
	public Consumer(TicketRepository ticketRepo) {
		this.ticketRepo = ticketRepo;
	}
	
	@JmsListener(destination = "ticket.queue")
	public void consume(Long flightID) {

		ArrayList<Ticket> tickets = (ArrayList<Ticket>) ticketRepo.findByFlightId(flightID);
		for (Ticket ticket : tickets) {
			ticket.setStatus("canceled");
			ticketRepo.saveAndFlush(ticket);

		}

	}

}
