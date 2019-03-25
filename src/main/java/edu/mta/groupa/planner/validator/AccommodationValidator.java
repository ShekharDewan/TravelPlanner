package edu.mta.groupa.planner.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Trip;

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
		
		ValidationUtils.rejectIfEmpty(errors, "checkOut", "message.noCheckOut");
		ValidationUtils.rejectIfEmpty(errors, "checkIn", "message.noCheckIn");
		ValidationUtils.rejectIfEmpty(errors, "title", "message.noTitle");
		
		if (accommodation.getCheckIn() != null && accommodation.getCheckOut() != null) {
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
		
		if (accommodation.getAddress() != null) {
			Address address = accommodation.getAddress();
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
	}
}
