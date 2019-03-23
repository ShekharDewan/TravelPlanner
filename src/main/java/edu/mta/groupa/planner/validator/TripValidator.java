package edu.mta.groupa.planner.validator;

import java.util.List;
import java.util.Optional;

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
		
		ValidationUtils.rejectIfEmpty(errors, "start", "message.noStart");
		ValidationUtils.rejectIfEmpty(errors, "end", "message.noEnd");
		ValidationUtils.rejectIfEmpty(errors, "title", "message.noTitle");
		ValidationUtils.rejectIfEmpty(errors, "destinations", "message.destEmpty");
		
		if (trip.getEnd().before(trip.getStart())) {
            errors.rejectValue("end", "message.badEnd");
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
