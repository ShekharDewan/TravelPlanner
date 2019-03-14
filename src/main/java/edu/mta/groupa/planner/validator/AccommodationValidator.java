package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@Component
public class AccommodationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Accommodation.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Accommodation accommodation = (Accommodation) target;
		
		Trip trip = accommodation.getTrip();
		
		if(accommodation.getCheckOut().after(trip.getEnd())) {
			errors.rejectValue("checkOut", "message.tripEnd");
		}
		if(accommodation.getCheckIn().before(trip.getStart())) {
			errors.rejectValue("checkIn", "message.tripStart");
		}
		if(accommodation.getCheckIn().after(trip.getEnd())) {
			errors.rejectValue("checkIn", "message.tripEnd");
		}
		if(accommodation.getCheckOut().before(trip.getStart())) {
			errors.rejectValue("checkOut", "message.tripStart");
		}
		if(accommodation.getCheckIn().after(accommodation.getCheckOut())){
			errors.rejectValue("checkOut", "message.checkIn");
		}

	}

}
