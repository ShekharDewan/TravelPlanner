package edu.mta.groupa.planner.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@Service
public class ItineraryService implements IService<Itinerary, Trip> {

	@PersistenceContext
    private EntityManager eManager;
	
	@Autowired
    private TripRepository tripRepository;
	
	
	@Override
	public Itinerary add(Trip trip, Itinerary itinerary) {
		trip.getItineraries().add(itinerary);
		tripRepository.save(trip); 
		
		return itinerary;
	}

	@Override
	public Itinerary update(Trip trip, long oldItinId, Itinerary itinerary) {
		Itinerary oldItinerary = eManager.find(Itinerary.class, oldItinId); 
        oldItinerary.setNotes(itinerary.getNotes());
        oldItinerary.setDate(itinerary.getDate());
        
        tripRepository.save(trip);
        
        return oldItinerary;
	}

	@Override
	public void delete(long parentId, long id) {
		Trip trip = tripRepository.findById(parentId).get();
    	Itinerary itinerary = eManager.find(Itinerary.class, id); 
    	trip.getItineraries().remove(itinerary);
    	tripRepository.save(trip);
	}

}
