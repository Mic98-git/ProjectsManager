package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.session.SessionData;

@Controller
public class MainController {
	
	@Autowired
	SessionData sessionData;
	
	public MainController() {
		
	}
	
	@RequestMapping(value = {"/","/index","/home"}, method = RequestMethod.GET)
	public String index(Model model) {
		//System.out.println("");
		model.addAttribute("loggedUser",sessionData.getLoggedUser());
		model.addAttribute("loggedCredentials",sessionData.getLoggedCredentials());
		
		return "index";
	}

}
