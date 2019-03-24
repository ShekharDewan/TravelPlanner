package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;

@Component
public class ReservationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Reservation.class.equals(clazz);
	}

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
	}
}
