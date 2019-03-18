package edu.mta.groupa.planner.service;


import edu.mta.groupa.planner.UserDTO;
import edu.mta.groupa.planner.model.User;

public interface IUserService {
	User registerNewUserAccount(UserDTO accountDto);
	User updateUserAccount(User user);
}
