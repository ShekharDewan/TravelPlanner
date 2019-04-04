package edu.mta.groupa.planner.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mta.groupa.planner.model.Role;
/**
 * The Role Repository extends the Spring Crud Repository object,
 * supplying create, update, and delete functionality for the Role
 * object.
 * 
 * @author Jennifer
 *
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

}
