package it.uniroma3.siw.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Tag;

@Component
public class TagValidator implements Validator {
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIPTION_LENGTH = 1000;
	final Integer MAX_COLOR_LENGTH = 100;
	final Integer MIN_COLOR_LENGTH = 3;

	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Tag t =(Tag)o;
		String name = t.getName();
		String description = t.getDescription();
		String color = t.getColor();
		
		if(name.trim().isEmpty())
			errors.rejectValue("name", "required");
		else if(name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
			errors.rejectValue("name", "size");
		
		if(description.length() > MAX_DESCRIPTION_LENGTH)
			errors.rejectValue("description", "size");
		
		if(color.trim().isEmpty())
			errors.rejectValue("color", "required");
		else if(name.length() < MIN_COLOR_LENGTH || name.length() > MAX_COLOR_LENGTH)
			errors.rejectValue("color", "size");
	}
}
