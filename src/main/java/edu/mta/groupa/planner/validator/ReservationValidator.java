package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;
/**
 * This component Validator class is used to validate Reservation 
 * objects.
 * Invalid data is flagged as rejected, and error messages
 * are provided for the user.
 * 
 * @author Jennifer
 *
 */
@Component
public class ReservationValidator implements Validator {
	/**
	 * Determines whether a class is supported by this
	 * Validator. Must be a Reservation class.
	 * 
	 * @param clazz		the class being tested.
	 * @return 			true if supported class; 
	 * 					false otherwise.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Reservation.class.equals(clazz);
	}
	/**
	 * Validates a Reservation object.
	 * Title and date cannot be missing, and date must be within
	 * the Trip's start and end date.
	 * Title must be less than 26 characters. 
	 * Address latitude and longitude must be valid or both empty.
	 * 
	 * @param target	the object being validated. 
	 * @param errors	the errors with the given object.
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Reservation reservation = (Reservation) target;

		Trip trip = reservation.getTrip();
		
		ValidationUtils.rejectIfEmpty(errors, "date", "message.noDate");
		ValidationUtils.rejectIfEmpty(errors, "title", "message.noTitle");
		
		if (reservation.getDate() != null) {
			if(reservation.getDate().after(trip.getEnd())) {
				errors.rejectValue("date", "message.tripEnd");
			}
			if(reservation.getDate().before(trip.getStart())) {
				errors.rejectValue("date", "message.tripStart");
			}
		}
		
		if (reservation.getAddress() != null) {
			Address address = reservation.getAddress();
			Double longitude = address.getLongitude();
			Double latitude = address.getLatitude();
			
			if (longitude != null && latitude != null) {
				if ((longitude.doubleValue() > 180.0) 
						|| (longitude.doubleValue() < -180.0)) {
					errors.rejectValue("address.longitude", "message.badLong");
				}
				
				if ((latitude.doubleValue() > 90.0) 
						|| (latitude.doubleValue() < -90.0)) {
					errors.rejectValue("address.latitude", "message.badLat");
				}
			}
			
			if (longitude != null && latitude == null) {
				errors.rejectValue("address.latitude", "message.noLat");
			}
			if (longitude == null && latitude != null) {
				errors.rejectValue("address.longitude", "message.noLong");
			}
		}
		
		if (reservation.getTitle().length() > 25) {
			errors.rejectValue("title", "message.titleLong");
		}
	}
}
