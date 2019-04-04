package edu.mta.groupa.planner.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
/**
 * A Service class which handles operations for Reservations.
 * Current operations include creation, updating, 
 * and deletion.
 * 
 * @author Jennifer
 *
 */
@Service
public class ReservationService implements IService<Reservation, Trip> {
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
	 * Adds a new Reservation to the Trip using validated
	 * information.
	 * Saves the updated Trip to the repository.
	 * 
	 * @param trip		  the Trip which to add the new Reservation.
	 * @param reservation the new Reservation.
	 * @return			  the new Reservation.
	 */
	@Override
	public Reservation add(Trip trip, Reservation reservation) {
		trip.getReservations().add(reservation);
    	tripRepository.save(trip);
    	
    	return reservation;
	}
	/**
	 * Updates a Reservation within a Trip using validated 
	 * information.
	 * Saves the updated Trip to the repository.
	 * 
	 * @param trip		  the Trip containing the Reservation.
	 * @param oldResId 	  the Reservation's id.
	 * @param reservation the updated Reservation.
	 * @return 			  the updated Reservation.
	 */
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
	/**
	 * Deletes a Reservation from a Trip.
	 * 
	 * @param tripId		the Trip's id.
	 * @param reservationId	the Reservation's id.
	 */
	@Override
	public void delete(long tripId, long reservationId) {
		Trip trip = tripRepository.findById(tripId).get();
    	Reservation reservation = eManager.find(Reservation.class, reservationId); 
    	trip.getReservations().remove(reservation);
    	tripRepository.save(trip);
	}	
}
