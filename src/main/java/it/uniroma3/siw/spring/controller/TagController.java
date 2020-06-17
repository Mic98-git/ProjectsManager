package it.uniroma3.siw.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Tag;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TagService;

@Controller
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = {"/projects/{projectId}/addtag"}, method = RequestMethod.GET)
	public String newTagForm(@PathVariable Long projectId,Model model) {
		model.addAttribute("tagForm", new Tag());
		model.addAttribute("projectId", projectId);
		String b;
		
		return "newTag";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/addtag"}, method = RequestMethod.POST)
	public String setProjectTags(@PathVariable Long projectId, 
			@Valid @ModelAttribute("tagForm") Tag tag,
			BindingResult projectBindingResult, Model model) {
		Project p = this.projectService.getProject(projectId);
		p.addTag(tag);
		this.projectService.saveProject(p);
		
		
		return "redirect:/projects/" + projectId;
	}
	
	@RequestMapping(value = {"project/{projectId}/tags/{tagId}/remove"}, method = RequestMethod.POST)
	public String removeTag(@PathVariable Long tagId, @PathVariable Long projectId) {
		tagService.deleteTagById(tagId);
		return "redirect:/projects/" + projectId;
	}
	
}
