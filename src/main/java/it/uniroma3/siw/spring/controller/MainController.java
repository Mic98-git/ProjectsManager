package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.session.SessionData;

public class MainController {
	
	public MainController() {
		
	}
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}

}
