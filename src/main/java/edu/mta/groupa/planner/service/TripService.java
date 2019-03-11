package edu.mta.groupa.planner.service;

import org.springframework.beans.factory.annotation.Autowired;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

public class TripService implements IService {

	@Autowired
	TripRepository tripRepository;

	@Override
	public void save(Trip trip) {
		tripRepository.save(trip);

	}

}
