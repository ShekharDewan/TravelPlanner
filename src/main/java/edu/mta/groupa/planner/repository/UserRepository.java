package edu.mta.groupa.planner.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.User;
/**
 * The User Repository extends the Spring Crud Repository object,
 * supplying create, update, and delete functionality for the User
 * object.
 * 
 * @author Jennifer
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
	public User findByEmail(String email);
}