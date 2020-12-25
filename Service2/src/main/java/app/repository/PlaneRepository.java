package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {

}
