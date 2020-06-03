package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;

public class AuthenticationController {
	
	@Autowired
	CredentialsService credentialsService;

	@RequestMapping(value= {"/users/register"},method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm",new User());
		model.addAttribute("credentialsForm",new Credentials());
		
		return "registerUser";
	}
	
	// FIXME Aggiungere Valid
	@RequestMapping(value= {"/users/register"},method=RequestMethod.POST)
	public String registerUser( @ModelAttribute("userForm") User user,
			BindingResult userBindingResult,
			@ModelAttribute("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		
		// FIXME Metti il codice dei validatori qui
		
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSuccessful";
		}
		
		return "registerUser";
	}
	
}
