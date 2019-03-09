package edu.mta.groupa.planner.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
import edu.mta.groupa.planner.EmailExistsException;
import edu.mta.groupa.planner.UserDTO;

import edu.mta.groupa.planner.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService service;
	
	@GetMapping("/signup")
	public String showSignupForm(WebRequest request, Model model) {
		model.addAttribute("user", new UserDTO());
		return "signup";
	}
	
	
	@PostMapping("/signup")
	  public String signup(@ModelAttribute("user") @Valid UserDTO userDto, BindingResult result, Model model, Errors errors) {
		User newUser = new User();
		if (!result.hasErrors()) {
			newUser = createNewUser(userDto, result);
		}    	
	  	if (newUser == null) {
	  		result.rejectValue("email", "message.regError");
	  	}
	  	if (result.hasErrors()) {
	  		model.addAttribute("user", userDto);
	  		return "signup";
	  	}
	  	else {
	  		model.addAttribute("user", newUser);
	  		model.addAttribute("message", "Registered successfully.");
	  		return "redirect:/";
	  	}
	  }
	  
	  private User createNewUser(UserDTO dto, BindingResult result) {
		  User newUser = null;
		  try {
			  newUser = service.registerNewUserAccount(dto);
		  } catch (EmailExistsException e) {
			  return null;
		  }
		  return newUser;
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
	    	User updatedUser = null;
	        if (!result.hasErrors()) {
	        	updatedUser = service.updateUserAccount(user);
	        }
	        if (updatedUser == null) {
		  		result.rejectValue("email", "message.regError");
		  	}
	        if (result.hasErrors()) {
	        	model.addAttribute("user", user); 
	            return "edit-account";
	        }

	        model.addAttribute("user", updatedUser); 

	        return "redirect:/";
	    }
	 
	 
}
