package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
