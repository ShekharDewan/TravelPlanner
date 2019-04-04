package edu.mta.groupa.planner.service;

import edu.mta.groupa.planner.UserDTO;
import edu.mta.groupa.planner.model.User;
/**
 * An interface which defines operations for a User object.
 * Currently supports registration/creation and updating.
 * 
 * @author Jennifer
 *
 */
public interface IUserService {
	User registerNewUserAccount(UserDTO accountDto);
	User updateUserAccount(User user);
}
