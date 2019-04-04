package edu.mta.groupa.planner.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
/**
 * A Service class which handles operations for Accommodations.
 * Current operations include creation, updating, 
 * and deletion.
 * 
 * @author Jennifer
 *
 */
@Service
public class AccommodationService implements IService<Accommodation, Trip> {
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
	 * Adds a new Accommodation to the Trip using validated
	 * information.
	 * Saves the updated Trip to the repository.
	 * 
	 * @param trip			the Trip which to add the new Accommodation.
	 * @param accommodation	the new Accommodation.
	 * @return				the new Accommodation.
	 */
	@Override
	public Accommodation add(Trip trip, Accommodation accommodation) {
		trip.getAccommodations().add(accommodation);
    	tripRepository.save(trip);
    	
    	return accommodation;
	}
	/**
	 * Updates an Accommodation within a Trip using validated 
	 * information.
	 * Saves the updated Trip to the repository.
	 * 
	 * @param trip			the Trip containing the Accommodation.
	 * @param oldAccomId	the Accommodation's id.
	 * @param accommodation	the updated Accommodation.
	 * @return				the updated Accommodation.
	 */
	@Override
	public Accommodation update(Trip trip, long oldAccommId, Accommodation accommodation) {
		Accommodation oldAccommodation = eManager.find(Accommodation.class, oldAccommId); 
        
        oldAccommodation.setNotes(accommodation.getNotes());
        oldAccommodation.setAddress(accommodation.getAddress());
        oldAccommodation.setCheckIn(accommodation.getCheckIn());
        oldAccommodation.setCheckOut(accommodation.getCheckOut());
        oldAccommodation.setPrice(accommodation.getPrice());
        oldAccommodation.setTitle(accommodation.getTitle());
        
        tripRepository.save(trip);
        
        return oldAccommodation;
	}
	/**
	 * Deletes an Accommodation from a Trip.
	 * 
	 * @param tripId		  the Trip's id.
	 * @param accommodationId the Accommodation's id.
	 */
	@Override
	public void delete(long tripId, long accommodationId) {
		Trip trip = tripRepository.findById(tripId).get();
    	Accommodation accommodation = eManager.find(Accommodation.class, accommodationId); 
    	trip.getAccommodations().remove(accommodation);
    	tripRepository.save(trip);
	}	
}
