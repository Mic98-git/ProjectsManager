package it.uniroma3.siw.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.validator.CredentialsValidator;
import it.uniroma3.siw.spring.validator.UserValidator;

@Controller
public class AuthenticationController {
	
	@Autowired
	CredentialsService credentialsService;

	@Autowired
	UserValidator userValidator;
	
	@Autowired
	CredentialsValidator credentialsValidator;
	
	@RequestMapping(value= {"/users/register"},method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm",new User());
		model.addAttribute("credentialsForm",new Credentials());
		//System.out.print("");
		
		return "registerUser";
	}
	
	@RequestMapping(value= {"/users/register"},method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("userForm") User user,
			BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);		
				
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "redirect:/";
		}
		
		return "registerUser";
	}
	
	@RequestMapping(value= {"/deniedAccess"},method=RequestMethod.GET)
	public String deniedAccess() {
		return "noAuth";
	}	
}
