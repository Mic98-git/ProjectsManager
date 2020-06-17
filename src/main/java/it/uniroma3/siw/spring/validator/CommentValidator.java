package it.uniroma3.siw.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Comment;

@Component
public class CommentValidator implements Validator {
	
	private final Integer MIN_COMMENT_LENGTH = 2;
	private final Integer MAX_COMMENT_LENGTH = 1000;

	@Override
	public boolean supports(Class<?> clazz) {
		return Comment.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		Comment comment = (Comment) o;
		
		if(comment.getContent().trim().isEmpty())
			errors.rejectValue("content", "required");
		else if(comment.getContent().length() < MIN_COMMENT_LENGTH || 
				comment.getContent().length() > MAX_COMMENT_LENGTH)
			errors.rejectValue("content", "size");
	}

}
