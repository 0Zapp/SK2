package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	List<Card> findByUserId(Long userId);
}
