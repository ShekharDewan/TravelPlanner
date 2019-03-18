package edu.mta.groupa.planner.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@Service
public class AccommodationService implements IService<Accommodation, Trip> {

	@PersistenceContext
    private EntityManager eManager;
	
	@Autowired
    private TripRepository tripRepository;

	@Override
	public Accommodation add(Trip trip, Accommodation accommodation) {
		trip.getAccommodations().add(accommodation);
    	tripRepository.save(trip);
    	
    	return accommodation;
	}

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

	@Override
	public void delete(long tripId, long accommodationId) {
		Trip trip = tripRepository.findById(tripId).get();
    	Accommodation accommodation = eManager.find(Accommodation.class, accommodationId); 
    	trip.getAccommodations().remove(accommodation);
    	tripRepository.save(trip);
	}	
}
