package edu.mta.groupa.planner.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Itinerary;

/**
 * The Itinerary Repository extends the spring Crud Repository object,
 * supplying create, update, and delete functionality for the Itinerary
 * object.
 * 
 * @author Maryse
 *
 */
public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

}