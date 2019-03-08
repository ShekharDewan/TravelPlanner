package edu.mta.groupa.planner.service;


import edu.mta.groupa.planner.EmailExistsException;
import edu.mta.groupa.planner.UserDTO;
import edu.mta.groupa.planner.model.User;

public interface IUserService {
	User registerNewUserAccount(UserDTO accountDto) throws EmailExistsException;
	User updateUserAccount(User user) throws EmailExistsException; //*
}
