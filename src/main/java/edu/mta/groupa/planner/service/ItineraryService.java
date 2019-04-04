package edu.mta.groupa.planner.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
/**
 * A Service class which handles operations for Itineraries.
 * Current operations include creation, updating, 
 * and deletion.
 * 
 * @author Jennifer
 *
 */
@Service
public class ItineraryService implements IService<Itinerary, Trip> {
	/**
	 * The EntityManager which manages all registered entities.
	 */
	@PersistenceContext
    private EntityManager eManager;
	/**
	 * The Trip repository which holds all Trips.
	 */
	@Autowired
    private TripRepository tripRepository;
	/**
	 * Adds a new Itinerary to the Trip using validated
	 * information.
	 * Saves the updated Trip to the repository.
	 * 
	 * @param trip		the Trip which to add the new Itinerary.
	 * @param itinerary the new Itinerary.
	 * @return			the new Itinerary.
	 */
	@Override
	public Itinerary add(Trip trip, Itinerary itinerary) {
		trip.getItineraries().add(itinerary);
		tripRepository.save(trip); 
		
		return itinerary;
	}
	/**
	 * Updates an Itinerary within a Trip using validated 
	 * information.
	 * Saves the updated Trip to the repository.
	 * 
	 * @param trip		the Trip containing the Itinerary.
	 * @param oldItinId the Itinerary's id.
	 * @param itinerary the updated Itinerary.
	 * @return 			the updated Itinerary.
	 */
	@Override
	public Itinerary update(Trip trip, long oldItinId, Itinerary itinerary) {
		Itinerary oldItinerary = eManager.find(Itinerary.class, oldItinId); 
        oldItinerary.setNotes(itinerary.getNotes());
        oldItinerary.setDate(itinerary.getDate());
        
        tripRepository.save(trip);
        
        return oldItinerary;
	}
	/**
	 * Deletes an Itinerary from a Trip.
	 * 
	 * @param tripId	the Trip's id.
	 * @param id		the Itinerary's id.
	 */
	@Override
	public void delete(long tripId, long id) {
		Trip trip = tripRepository.findById(tripId).get();
    	Itinerary itinerary = eManager.find(Itinerary.class, id); 
    	trip.getItineraries().remove(itinerary);
    	tripRepository.save(trip);
	}
}
