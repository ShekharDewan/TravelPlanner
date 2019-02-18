package edu.mta.groupa.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Accomodation;

public interface AccomodationRepository extends CrudRepository<Accomodation, Long> {
	List<Accomodation> findByTitle(String title);
}