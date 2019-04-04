package edu.mta.groupa.planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.repository.UserRepository;
/**
 * A controller advice class. Methods are executed for each
 * page rendered. 
 * Currently, used to display trip list on each page,
 * and to always allow access to current user.
 * 
 * @author Jennifer
 *
 */
@ControllerAdvice
public class TripsControllerAdvice {
	/**
	 * The Trip repository which holds all Trips.
	 */
	 @Autowired
	 private TripRepository tripRepository;
	 /**
	  * The User repository which holds all Users.
	  */
	 @Autowired
	 private UserRepository userRepository;
	/**
	 * Retrieves all of the current User's trips.
	 * 
	 * @return	the list of trips.
	 */
	@ModelAttribute("trips")
    public List<Trip> getTrips() {
		User currentUser = getCurrentUser();
		if (currentUser == null) return null;

		return tripRepository.findAllByUserIDOrderByStartAsc(currentUser.getId());
    }
	/**
	 * Retrieves the current User's first name.
	 * 
	 * @return	the User's first name.
	 */
	 @ModelAttribute("userFirstName")
	 public String getUserFirstName() {
		 User currentUser = getCurrentUser();
		 if (currentUser == null) return null;
		 
		 return currentUser.getFirstName();
	 }
	/**
	 * Provides access to the current User object.
	 * 
	 * @return	the current User.
	 */
	 @ModelAttribute("currUser")
	 public User getUser() {
		 User currentUser = getCurrentUser();
		 if (currentUser == null) return null;
		 
		 return currentUser;
	 }
	/**
	 * Retrieves the currently logged in User
	 * from the SecurityContextHolder.
	 * 
	 * @return	the current User.
	 */
	private User getCurrentUser() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String email =  auth.getName();
		 User currentUser = userRepository.findByEmail(email);
		 
		 return currentUser;
	}
}
