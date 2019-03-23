package edu.mta.groupa.planner.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

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
	
	private boolean emailExists(User user) {
		if (userRepository.existsById(user.getId())) {
			User userById = userRepository.findById(user.getId()).get();
			User userByEmail = userRepository.findByEmail(user.getEmail());

			return (userByEmail != null) && !userByEmail.equals(userById);
		}

        return userRepository.findByEmail(user.getEmail()) != null;
    }
}
