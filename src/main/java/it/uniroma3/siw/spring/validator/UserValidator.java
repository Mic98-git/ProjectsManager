package it.uniroma3.siw.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.User;

@Component
public class UserValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		String firstName = user.getName().trim();
		String lastName = user.getLastname().trim();
		
		if(firstName.isBlank())
			errors.rejectValue("firstName", "required"); // FIXME Va bene firstName o devo mettere name e basta??
		else if(firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_NAME_LENGTH)
			errors.rejectValue("firstName", "size");
		
		if(lastName.isBlank())
			errors.rejectValue("lastName", "required"); // FIXME Va bene lastName o devo mettere lastname e basta??
		else if(lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_NAME_LENGTH)
			errors.rejectValue("lastName", "size");
	}

}
