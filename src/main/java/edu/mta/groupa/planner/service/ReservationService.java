package edu.mta.groupa.planner.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@Service
public class ReservationService implements IService<Reservation, Trip> {

	@PersistenceContext
    private EntityManager eManager;
	
	@Autowired
    private TripRepository tripRepository;
	
	@Override
	public Reservation add(Trip trip, Reservation reservation) {
		trip.getReservations().add(reservation);
    	tripRepository.save(trip);
    	
    	return reservation;
	}

	@Override
	public Reservation update(Trip trip, long oldResID, Reservation reservation) {
		Reservation oldReservation = eManager.find(Reservation.class, oldResID); 

        oldReservation.setTitle(reservation.getTitle());
        oldReservation.setAddress(reservation.getAddress());
        oldReservation.setPrice(reservation.getPrice());
        oldReservation.setConfirmation(reservation.getConfirmation());
        oldReservation.setNotes(reservation.getNotes());
        oldReservation.setDate(reservation.getDate());
        oldReservation.setReserveTime(reservation.getReserveTime());
        oldReservation.setType(reservation.getType());
        
        tripRepository.save(trip);
        
        return oldReservation;
	}

	@Override
	public void delete(long tripId, long reservationId) {
		Trip trip = tripRepository.findById(tripId).get();
    	Reservation reservation = eManager.find(Reservation.class, reservationId); 
    	trip.getReservations().remove(reservation);
    	tripRepository.save(trip);
	}

	
}
