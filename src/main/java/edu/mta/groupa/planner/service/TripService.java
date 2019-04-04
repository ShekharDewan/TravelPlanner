package edu.mta.groupa.planner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
/**
 * A Service class which handles operations for Trips.
 * Current operations include creation, updating, 
 * and deletion.
 * 
 * @author Jennifer
 *
 */
@Service
public class TripService implements ITripService {
	/**
	 * The Trip repository which holds all Trips.
	 */
	@Autowired
	private TripRepository tripRepository;
	/**
	 * Saves a new Trip to the repository with validated
	 * information.
	 * 
	 * @param trip	the new Trip.
	 * @return		the new Trip.
	 */
	@Override
	public Trip add(Trip trip) {
    	tripRepository.save(trip);
    	
    	return trip;
	}
	/**
	 * Updates a Trip with the given validated information.
	 * 
	 * @param trip	the updated Trip.
	 * @return		the updated Trip.
	 */
	@Override
	public Trip update(Trip trip) {
		Trip oldTrip = tripRepository.findById(trip.getId()).get();

        oldTrip.setNotes(trip.getNotes());
        oldTrip.setStart(trip.getStart());
        oldTrip.setEnd(trip.getEnd());
        oldTrip.setTitle(trip.getTitle());
        oldTrip.setDescription(trip.getDescription());
        oldTrip.setDestinations(trip.getDestinations());
        
        tripRepository.save(oldTrip);
        
        return oldTrip;
	}
	/**
	 * Deletes a Trip given its id.
	 * 
	 * @param id	the Trip's id.
	 */
	@Override
	public void delete(long id) {
		tripRepository.deleteById(id);
	}
}
