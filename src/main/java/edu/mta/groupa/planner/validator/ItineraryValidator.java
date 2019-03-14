package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Trip;

@Component
public class ItineraryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Itinerary.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Itinerary itinerary = (Itinerary) target;
		
		Trip trip = itinerary.getTrip();
		
		if(itinerary.getDate().after(trip.getEnd())) {
			errors.rejectValue("date", "message.tripEnd");
		}
		
		if(itinerary.getDate().before(trip.getStart())) {
			errors.rejectValue("date", "message.tripStart");
		}
		
		for(Itinerary i : trip.getItineraries()){
    		if(itinerary.getDate().equals(i.getDate())
    				&& !trip.getItineraries().contains(itinerary)) {
    			errors.rejectValue("date", "message.itinDupl");
    		}
    	}
	}

}
