package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	int countByFlightId(Long flightId);
	
	List<Ticket> findByUserIdOrderByDateDesc(Long userId);
	
	List<Ticket> findByFlightId(Long flightId);
	
}
