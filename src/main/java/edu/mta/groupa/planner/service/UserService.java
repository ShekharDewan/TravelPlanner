package edu.mta.groupa.planner.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mta.groupa.planner.model.Role;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
import edu.mta.groupa.planner.UserDTO;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
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
	
	public User updateUserAccount(User user) {
		User oldUser = userRepository.findById(user.getId()).get();

        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(oldUser);
        
        return oldUser;
	}
}
