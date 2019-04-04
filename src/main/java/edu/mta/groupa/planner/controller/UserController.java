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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
import edu.mta.groupa.planner.UserDTO;

import edu.mta.groupa.planner.service.UserService;
import edu.mta.groupa.planner.validator.UserValidator;
/**
 * A controller class for handling User requests and operations.
 * Currently, functionality includes logging in and out, 
 * registration, and account updating.
 * 
 * @author Jennifer
 *
 */
@Controller
public class UserController {
	/**
	 * The User repository which holds all users.
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * The Service class which handles User operations.
	 */
	@Autowired
	private UserService service;
	/**
	 * The Validator class which validates User information.
	 */
	@Autowired
	private UserValidator userValidator;
	/**
	 * GET operation which displays the registration page.
	 * Creates User data transfer object for user to submit data.
	 * 
	 * @param request	the WebRequest.
	 * @param model		the model which to add attributes.
	 * @return			the HTML page to be rendered.
	 */
	@GetMapping("/signup")
	public String showSignupForm(WebRequest request, Model model) {
		model.addAttribute("user", new UserDTO());
		return "signup";
	}
	/**
	 * POST operation for registration. The information received
	 * is used to create a new User account. If any information is invalid,
	 * the form is returned with error messages.
	 * If successful, user is redirected to login page.
	 * 
	 * 
	 * @param userDto	the object that holds transfered User information.
	 * @param result	determines whether the information is valid.
	 * @param model		the model which to add attributes.
	 * @param redit		a tool to allow attributes to survive over redirects.
	 * @return 			the HTML page to render.
	 */
	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") @Valid UserDTO userDto, 
			  BindingResult result, Model model, RedirectAttributes redit) {
		User newUser = new User(userDto.getFirstName(), userDto.getLastName(), 
				userDto.getPassword(), userDto.getEmail());
		
		userValidator.validate(newUser, result);
		
	  	if (result.hasErrors()) {
	  		model.addAttribute("user", userDto);
	  		return "signup";
	  	}
	  	service.registerNewUserAccount(userDto);
	  	redit.addFlashAttribute("message", "Registered successfully.");
	  	
	  	return "redirect:/login";
	  }
	/**
	 * GET operation for displaying the login page.
	 * Note that Spring handles login POST operation.
	 * 
	 * @param model		the model which to add attributes.
	 * @param error		an error flag set upon failed login.
	 * @param logout	a logout flag set upon logout.
	 * @param message	a flag set upon account registration.
	 * @return			the HTML page to be rendered.
	 */
	 @GetMapping("/login")
	 public String login(Model model, String error, String logout,
			 @ModelAttribute("message") String message) {

	     if (error != null)
	         model.addAttribute("error", "Invalid credentials.");

	     if (logout != null)
	         model.addAttribute("message", "Logged out successfully.");

	     if (!message.isEmpty())
	         model.addAttribute("message", "Registered successfully.");
	     
	     return "login";
     }
	 /**
	  * GET operation for displaying edit account page.
	  * 
	  * @param model	the model which to add attributes.
	  * @return			the HTML page to be rendered.
	  */
	 @GetMapping("/account")
	 public String showAccount(Model model) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String email = auth.getName();
	     model.addAttribute("user", userRepository.findByEmail(email));
	     return "edit-account";
     }
	 /**
	  * POST operation for updating account information.
	  * If information is not valid, returns edit account form
	  * with error messages.
	  * Upon success, redirects to home page.
	  * 
	  * @param id		the User's account id.
	  * @param user		the User object containing updated information.
	  * @param result	the BindingResult.
	  * @param model	the model which to add attributes.
	  * @return			the HTML page to be rendered.
	  */
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
