package edu.mta.groupa.planner.validator;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
/**
 * This component Validator class is used to validate Trip objects.
 * Invalid data is flagged as rejected, and errors messages
 * are provided for the user.
 * 
 * @author Jennifer
 *
 */
@Component
public class TripValidator implements Validator {
	/**
	 * The Trip repository which holds all Trips.
	 */
	@Autowired
	private TripRepository tripRepository;
	/**
	 * Determines whether a class is supported by this
	 * Validator. Must be a Trip class.
	 * 
	 * @param clazz		the class being tested.
	 * @return 			true if supported class; 
	 * 					false otherwise.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Trip.class.equals(clazz);
	}
	/**
	 * Validates a Trip object.
	 * Start and end dates cannot be missing, and the end date cannot
	 * be before the start date.
	 * Title and destinations cannot be empty.
	 * Title must be unique and less than 26 characters. 
	 * 
	 * @param target	the object being validated. 
	 * @param errors	the errors with the given object.
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Trip trip = (Trip) target;
		
		ValidationUtils.rejectIfEmpty(errors, "start", "message.noStart");
		ValidationUtils.rejectIfEmpty(errors, "end", "message.noEnd");
		ValidationUtils.rejectIfEmpty(errors, "title", "message.noTitle");
		ValidationUtils.rejectIfEmpty(errors, "destinations", "message.destEmpty");
		
		if (trip.getStart() != null && trip.getEnd() != null) {	
			if (trip.getEnd().before(trip.getStart())) {
	            errors.rejectValue("end", "message.badEnd");
	        }
		}

		List<Trip> trips = tripRepository.findAllByUserID(trip.getUserID());

		for (Trip t : trips) {
			if (t.getTitle().equals(trip.getTitle()) && t.getId() != trip.getId()) {
				errors.rejectValue("title", "message.titleExists");
			}
		}

		if (trip.getTitle().length() > 25) {
			errors.rejectValue("title", "message.titleLong");
		}
	}
}
