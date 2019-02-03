package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findByTitle(String title);
}