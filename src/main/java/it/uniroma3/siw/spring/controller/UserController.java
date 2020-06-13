package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value= {"/user/profile/edit"},method=RequestMethod.PUT)
	public String editUser(@RequestBody User user) {
		this.userService.saveUser(user);
		
		return "redirect:/";
	}
}
