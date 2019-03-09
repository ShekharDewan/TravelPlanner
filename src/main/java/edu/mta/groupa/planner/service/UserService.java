package edu.mta.groupa.planner.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mta.groupa.planner.model.Role;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
import edu.mta.groupa.planner.EmailExistsException;
import edu.mta.groupa.planner.UserDTO;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	@Override
	public User registerNewUserAccount(UserDTO dto) throws EmailExistsException {
		if (emailExists(dto.getEmail())) {  
            throw new EmailExistsException(
              "There is an account with that email adress: "
              +  dto.getEmail());
        }
		User newUser = new User(dto.getFirstName(), dto.getLastName(), encoder.encode(dto.getPassword()), dto.getEmail());
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(new Role("ROLE_USER")); 
		newUser.setRoles(roles);
		userRepository.save(newUser);

		return newUser;
	}
	
	private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
	
	public User updateUserAccount(User user) throws EmailExistsException { //*
		User oldUser = userRepository.findById(user.getId()).get();
		if (emailExists(user.getEmail()) && !(user.getEmail().equals(oldUser.getEmail()))) {  
            throw new EmailExistsException(
              "There is an account with that email adress: "
              +  user.getEmail());
        }
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        
        userRepository.save(oldUser);
        
        return oldUser;
	}
}
