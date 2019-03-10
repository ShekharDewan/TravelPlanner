package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Trip;

@Component
public class TripValidator implements Validator {



	@Override
	public boolean supports(Class<?> clazz) {
		Trip.class.equals(clazz);
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Trip trip = (Trip) target;
		
		if (trip.getEnd().before(trip.getStart())) {
            errors.rejectValue("end", "message.badEnd");
        }
	}
}
