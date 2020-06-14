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
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value= {"/user/profile"},method=RequestMethod.GET)
	public String showProfile(Model model) {
		model.addAttribute("user",sessionData.getLoggedUser());
		
		return "profile";
	}

	@RequestMapping(value= {"/user/profile/edit"},method=RequestMethod.GET)
	public String showEditUserForm(Model model) {
		model.addAttribute("userForm",sessionData.getLoggedUser());
		
		return "userForm";
	}
	
	@RequestMapping(value= {"/user/profile/edit"},method=RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("userForm") User user) {
		User loggedUser = this.sessionData.getLoggedUser();
		loggedUser.setName(user.getName());
		loggedUser.setLastname(user.getLastname());		
		this.userService.saveUser(loggedUser);
		
		System.out.println("");
		
		return "redirect:/user/profile/edit";
	}
}
