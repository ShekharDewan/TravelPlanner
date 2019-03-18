package edu.mta.groupa.planner.service;

import edu.mta.groupa.planner.model.Trip;

public interface ITripService{
	public Trip add(Trip trip);
	public Trip update(Trip trip);
	public void delete(long id);
}
