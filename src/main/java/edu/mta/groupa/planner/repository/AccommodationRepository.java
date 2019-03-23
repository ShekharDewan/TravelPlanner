package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Accommodation;

/**
 * The Accommodation Repository extends the spring Crud Repository object,
 * supplying create, update, and delete functionality for the Accomodation
 * object.
 * 
 * @author Maryse
 *
 */
public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {
	List<Accommodation> findByTitle(String title);	
}

