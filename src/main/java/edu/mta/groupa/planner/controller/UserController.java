package edu.mta.groupa.planner.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
import edu.mta.groupa.planner.UserDTO;

import edu.mta.groupa.planner.service.UserService;
import edu.mta.groupa.planner.validator.UserValidator;


@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("/signup")
	public String showSignupForm(WebRequest request, Model model) {
		model.addAttribute("user", new UserDTO());
		return "signup";
	}
	
	
	@PostMapping("/signup")
	  public String signup(@ModelAttribute("user") @Valid UserDTO userDto, 
			  BindingResult result, Model model) {
		User newUser = new User(userDto.getFirstName(), userDto.getLastName(), 
				userDto.getPassword(), userDto.getEmail());
		
		userValidator.validate(newUser, result);
		
	  	if (result.hasErrors()) {
	  		model.addAttribute("user", userDto);
	  		return "signup";
	  	}
	  	service.registerNewUserAccount(userDto);
	  	model.addAttribute("message", "Registered successfully.");
	  	
	  	return "login";
	  }
	
	 @GetMapping("/login")
	 public String login(Model model, String error, String logout) {

	     if (error != null)
	         model.addAttribute("error", "Invalid credentials.");

	     if (logout != null)
	         model.addAttribute("message", "Logged out successfully.");

	     return "login";
     }
	 
	 @GetMapping("/account")
	 public String showAccount(Model model) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String email = auth.getName();
	     model.addAttribute("user", userRepository.findByEmail(email));
	     return "edit-account";
     }
	    
	    @PostMapping("/account/{id}/update-success")
	    public String updateAccount(@PathVariable("id") long id, @Valid User user,
	      BindingResult result, Model model) {
    	
	    	userValidator.validate(user, result);

	        if (result.hasErrors()) {
	        	model.addAttribute("user", user); 
	            return "edit-account";
	        }        
	        service.updateUserAccount(user);

	        return "redirect:/";
	    }	 
}
