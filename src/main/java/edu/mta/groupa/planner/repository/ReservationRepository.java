package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Reservation;

/**
 * The Reservation Repository extends the spring Crud Repository object,
 * supplying create, update, and delete functionality for the Reservation
 * object.
 * 
 * @author Maryse
 *
 */
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findByTitle(String title);
}