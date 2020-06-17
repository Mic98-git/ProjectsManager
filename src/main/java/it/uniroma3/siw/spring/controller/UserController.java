package it.uniroma3.siw.spring.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.UserService;
import it.uniroma3.siw.spring.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;
	
	@RequestMapping(value= {"/admin"},method=RequestMethod.GET)
	public String adminPageIndex(Model model) {
		return "admin";
	}
	
	@RequestMapping(value="/admin/users/{username}/delete", method=RequestMethod.POST)
	public String removeUser(Model model,@PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		
		return "redirect:/admin/users";
	}
	
	@RequestMapping(value= {"/user/profile"},method=RequestMethod.GET)
	public String showProfile(Model model) {
		model.addAttribute("user", sessionData.getLoggedUser());
		model.addAttribute("credentials", sessionData.getLoggedCredentials());

		//System.out.println("");
		return "profile";
	}

	@RequestMapping(value= {"/user/profile/edit"},method=RequestMethod.GET)
	public String showEditUserForm(Model model) {
		model.addAttribute("userForm",sessionData.getLoggedUser());
		
		return "editUser";
	}
	
	@RequestMapping(value= {"/user/profile/edit"},method=RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("userForm") User user,
			Model model,
			BindingResult userBindingResult) {
		this.userValidator.validate(user, userBindingResult);
		if(userBindingResult.hasErrors()) {
			return "editUser";
		}
		
		User loggedUser = this.sessionData.getLoggedUser();
		loggedUser.setName(user.getName());
		loggedUser.setLastName(user.getLastName());
		this.userService.saveUser(loggedUser);
		//System.out.println();
		return "redirect:/user/profile/edit";
	}
	
	@RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
	public String userList(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		//System.out.println();
		
		return "allUsers";
	}
}
