package edu.mta.groupa.planner.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@Component
public class TripValidator implements Validator {
	
	@Autowired
	TripRepository tripRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Trip.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Trip trip = (Trip) target;
		
		if (trip.getEnd().before(trip.getStart())) {
            errors.rejectValue("end", "message.badEnd");
        }
		
		List<Trip> trips = tripRepository.findByUserIDAndTitle(trip.getUserID(), trip.getTitle());
		
		if (tripRepository.findByUserIDAndTitle(trip.getUserID(), trip.getTitle()) != null
				&& trips.size() > 1) {
			errors.rejectValue("title", "message.titleExists");
		}
		
		if (trip.getTitle().length() > 25) {
			errors.rejectValue("title", "message.titleLong");
		}
		
		ValidationUtils.rejectIfEmpty(errors, "destinations", "message.destEmpty");
	}
	
	

}
