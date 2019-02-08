package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Accomodation;

/**
 * The Accomodation Repository extends the spring Crud Repository object,
 * supplying create, update, and delete functionality for the Accomodation
 * object.
 * 
 * @author Maryse
 *
 */
public interface AccomodationRepository extends CrudRepository<Accomodation, Long> {
	List<Accomodation> findByTitle(String title);
}