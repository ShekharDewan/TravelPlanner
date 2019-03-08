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

@Service
public class NewUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

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
	          user.getPassword(), enabled, accountNonExpired, //get pass to lowercase removed
	          credentialsNonExpired, accountNonLocked, 
	          getAuthorities(user.getRoles()));
	    }
	
	private static List<GrantedAuthority> getAuthorities (Set<Role> set) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : set) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}
