package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Trip;
/**
 * This component Validator class is used to validate Itinerary 
 * objects.
 * Invalid data is flagged as rejected, and error messages
 * are provided for the user.
 * 
 * @author Jennifer
 *
 */
@Component
public class ItineraryValidator implements Validator {
	/**
	 * Determines whether a class is supported by this
	 * Validator. Must be an Itinerary class.
	 * 
	 * @param clazz		the class being tested.
	 * @return 			true if supported class; 
	 * 					false otherwise.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Itinerary.class.equals(clazz);
	}
	/**
	 * Validates an Itinerary object.
	 * Date cannot be empty, must be within Trip start and
	 * end date, and must be unique.
	 * 
	 * @param target	the object being validated. 
	 * @param errors	the errors with the given object.
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Itinerary itinerary = (Itinerary) target;
		
		Trip trip = itinerary.getTrip();
		
		ValidationUtils.rejectIfEmpty(errors, "date", "message.noDate");
		
		if (itinerary.getDate() != null) {
			if(itinerary.getDate().after(trip.getEnd())) {
				errors.rejectValue("date", "message.tripEnd");
			}
			
			if(itinerary.getDate().before(trip.getStart())) {
				errors.rejectValue("date", "message.tripStart");
			}
			
			for(Itinerary i : trip.getItineraries()){
    			if(itinerary.getDate().equals(i.getDate())
    				&& i.getId() != itinerary.getId()) {
    				errors.rejectValue("date", "message.itinDupl");
    			}
    		}
		}
   	}
}
