package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Accommodation;

public interface AccomodationRepository extends CrudRepository<Accommodation, Long> {
	List<Accommodation> findByTitle(String title);
}