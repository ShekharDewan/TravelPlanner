package edu.mta.groupa.planner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.repository.UserRepository;

@Service
public class TripService implements ITripService {

	@Autowired
	private TripRepository tripRepository;
	
	@Override
	public Trip add(Trip trip) {
    	tripRepository.save(trip);
    	
    	return trip;
	}

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

	@Override
	public void delete(long id) {
		tripRepository.deleteById(id);
	}
}
