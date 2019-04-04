package edu.mta.groupa.planner.service;

import edu.mta.groupa.planner.model.Trip;
/**
 * An interface which defines operations for a Trip object.
 * Currently supports creation, deletion, and updating.
 * 
 * @author Jennifer
 *
 */
public interface ITripService{
	public Trip add(Trip trip);
	public Trip update(Trip trip);
	public void delete(long id);
}
