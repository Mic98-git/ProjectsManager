package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.TagService;

@Controller
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value = {"/{projectId}/addTag"}, method = RequestMethod.POST)
	public String setProjectTags() {
		
		return "";
	}
	
	@RequestMapping(value = {"/tags/{tagId}/remove"}, method = RequestMethod.POST)
	public String removeTag(@PathVariable Long tagId) {
		tagService.deleteTagById(tagId);
		return "";
	}
	
}
