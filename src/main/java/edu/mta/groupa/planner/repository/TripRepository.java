package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Trip;

public interface TripRepository extends CrudRepository<Trip, Long> {
	List<Trip> findByTitle(String title);
}