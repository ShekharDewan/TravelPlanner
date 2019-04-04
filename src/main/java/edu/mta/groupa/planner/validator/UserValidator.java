package edu.mta.groupa.planner.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;
/**
 * This component Validator class is used to validate User 
 * objects.
 * Invalid data is flagged as rejected, and error messages
 * are provided for the user.
 * 
 * @author Jennifer
 *
 */
@Component
public class UserValidator implements Validator {
	/**
	 * The User repository which holds all Users.
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Determines whether a class is supported by this
	 * Validator. Must be a User class.
	 * 
	 * @param clazz		the class being tested.
	 * @return 			true if supported class; 
	 * 					false otherwise.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	/**
	 * Validates a User object.
	 * Email and password cannot be empty or contain any whitespace.
	 * First and last name cannot be empty.
	 * Email must be unique.
	 * Password must be greater than 7 characters.
	 * 
	 * @param target	the object being validated. 
	 * @param errors	the errors with the given object.
	 */
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "message.badEmail");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.noSpacePass");
		ValidationUtils.rejectIfEmpty(errors, "firstName", "message.firstName");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "message.lastName");
		
		if (emailExists(user)) {  
			errors.rejectValue("email", "message.regError");
        }
		
		if (user.getPassword().length() < 8) {
			errors.rejectValue("password", "message.shortPass");
		}
	}
	/**
	 * Determines whether an email is currently being used
	 * by another User.
	 * 
	 * @param user	the User wanting the email.
	 * @return		true if the email is in use;
	 * 				false otherwise.
	 */
	private boolean emailExists(User user) {
		if (userRepository.existsById(user.getId())) {
			User userById = userRepository.findById(user.getId()).get();
			User userByEmail = userRepository.findByEmail(user.getEmail());

			return (userByEmail != null) && !userByEmail.equals(userById);
		}

        return userRepository.findByEmail(user.getEmail()) != null;
    }
}
