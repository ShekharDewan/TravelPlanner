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

@ControllerAdvice
public class TripsControllerAdvice {
	
	 @Autowired
	 private TripRepository tripRepository;
	    
	 @Autowired
	 private UserRepository userRepository;

	@ModelAttribute("allTrips")
    public List<Trip> getTrips() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User currentUser = userRepository.findByEmail(email);
		
		if (currentUser == null) return null;

		return tripRepository.findAllByUserIDOrderByStartAsc(currentUser.getId());
    }

}
