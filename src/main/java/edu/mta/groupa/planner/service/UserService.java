package edu.mta.groupa.planner.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mta.groupa.planner.model.Role;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
import edu.mta.groupa.planner.UserDTO;
/**
 * A Service class which handles operations for Users.
 * Current operations include creation/registration 
 * and deletion.
 * 
 * @author Jennifer
 *
 */
@Service
public class UserService implements IUserService {
	/**
	 * The User repository which holds all Users.
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * The registered password encoder.
	 */
	@Autowired
	private BCryptPasswordEncoder encoder;
	/**
	 * Creates a new User object with the given validated information.
	 * Currently all users are given a regular "USER" role.
	 * 
	 * @param dto	the data transfer object holding the new User data.
	 * @return		the new User object.
	 */
	@Transactional
	@Override
	public User registerNewUserAccount(UserDTO dto) {
		User newUser = new User(dto.getFirstName(), dto.getLastName(), 
				encoder.encode(dto.getPassword()), dto.getEmail());
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(new Role("ROLE_USER")); 
		newUser.setRoles(roles);
		userRepository.save(newUser);

		return newUser;
	}
	/**
	 * Updates a User's information given an updated and validated
	 * object.
	 * If email is changed, forcibly logs out the User.
	 * 
	 * @param user	the User object holding the updated information.
	 * @return		the updated User.
	 */
	public User updateUserAccount(User user) {
		User oldUser = userRepository.findById(user.getId()).get();
		String oldEmail = oldUser.getEmail();
		
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(oldUser);
        
        if (!oldEmail.equals(oldUser.getEmail())) {  	
        	SecurityContext sc = SecurityContextHolder.getContext();
        	Authentication auth = sc.getAuthentication();
        	auth.setAuthenticated(false);
        }
        
        return oldUser;
	}
}
