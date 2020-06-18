package it.uniroma3.siw.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Tag;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TagService;
import it.uniroma3.siw.spring.validator.TagValidator;

@Controller
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TagValidator tagValidator;
	
	@RequestMapping(value = {"/projects/{projectId}/addtag"}, method = RequestMethod.GET)
	public String newTagForm(@PathVariable Long projectId, Model model) {
		model.addAttribute("tagForm", new Tag());
		model.addAttribute("projectId", projectId);
		
		return "newTag";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/addtag"}, method = RequestMethod.POST)
	public String setProjectTags(@PathVariable Long projectId, 
			@Valid @ModelAttribute("tagForm") Tag tag,
			BindingResult tagBindingResult, Model model) {
		this.tagValidator.validate(tag, tagBindingResult);
		if(tagBindingResult.hasErrors()) {
			model.addAttribute("projectId", projectId);
			return "newTag";
		}
		
		Project p = this.projectService.getProject(projectId);
		p.addTag(tag);
		this.projectService.saveProject(p);
		
		return "redirect:/projects/" + projectId;
	}
	
	@RequestMapping(value = {"projects/{projectId}/tags/{tagId}/remove"}, method = RequestMethod.POST)
	public String removeTag(@PathVariable Long tagId, @PathVariable Long projectId) {
		this.tagService.deleteTagById(tagId);
		
		return "redirect:/projects/" + projectId;
	}
	
	@GetMapping("/projects/{projectId}/tags/{tagId}/edit")
	public String viewEditTagForm(@PathVariable Long projectId,
			@PathVariable Long tagId, Model model) {
		Tag tag = this.tagService.getTagById(tagId);
				
		// per il form del task da editare	
		model.addAttribute("tagForm", tag);
		model.addAttribute("projectId", projectId);
		
		return "editTag";
	}
	
	@PostMapping("/projects/{projectId}/tags/{tagId}/edit")
	public String updateTag(@PathVariable Long projectId,
			@PathVariable Long tagId,
			Model model,
			@Valid @ModelAttribute("tagForm") Tag tag,
			BindingResult tagBindingResult) {
		tag.setId(tagId);
		this.tagValidator.validate(tag, tagBindingResult);
		if(tagBindingResult.hasErrors()) {
			model.addAttribute("projectId", projectId);
			return "editTag";
		}
		
		Tag t = this.tagService.getTagById(tagId);
		t.setName(tag.getName());
		t.setColor(tag.getColor());
		t.setDescription(tag.getDescription());
		
		this.tagService.saveTag(t);
		
		return "redirect:/projects/"+ projectId;
	}
	
}
