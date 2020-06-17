package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.ProjectService;

@Controller
public class MainController {
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	ProjectService projectService;
	
	public MainController() {
		
	}
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public String index(Model model) {
		//System.out.println("");
		User currentUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser",currentUser);
		model.addAttribute("loggedCredentials",sessionData.getLoggedCredentials());
		model.addAttribute("projectsList",projectService.getShareProjects(currentUser));
		
		return "home";
	}

}
