package edu.mta.groupa.planner.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
}