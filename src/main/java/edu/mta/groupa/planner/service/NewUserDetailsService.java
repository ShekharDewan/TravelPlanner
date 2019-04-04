package edu.mta.groupa.planner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mta.groupa.planner.model.Role;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
/**
 * This class overrides Spring's UserDetailsService in order to
 * inject a custom User object into Spring's security system.
 * Data from a User is used to create Spring User objects (UserDetails).
 * Spring UserDetails are used to authenticate and de-authenticate 
 * Users when logging in and out. 
 * 
 * @author Jennifer
 *
 */
@Service
public class NewUserDetailsService implements UserDetailsService {
	/**
	 * The User repository which holds all Users.
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Loads a User from the User Repository using its email.
	 * Creates a native Spring security User object (UserDetails), 
	 * which will be authenticated upon login.
	 * 
	 * @param email	the email by which to load the User.
	 * @return 		the UserDetails created from the User.
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		 if (user == null) {
	            throw new UsernameNotFoundException("No user found with username: "+ email);
	        }
		 boolean enabled = true;
	     boolean accountNonExpired = true;
	     boolean credentialsNonExpired = true;
	     boolean accountNonLocked = true;
	     return  new org.springframework.security.core.userdetails.User
	          (user.getEmail(), 
	          user.getPassword(), enabled, accountNonExpired,
	          credentialsNonExpired, accountNonLocked, 
	          getAuthorities(user.getRoles()));
	    }
	/**
	 * Creates a list of granted authorities given a set of roles.
	 * 
	 * @param set	the set of roles.
	 * @return		the list of granted authorities.
	 */
	private static List<GrantedAuthority> getAuthorities (Set<Role> set) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : set) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
