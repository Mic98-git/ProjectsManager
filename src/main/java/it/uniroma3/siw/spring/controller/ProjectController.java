package it.uniroma3.siw.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.UserService;
import it.uniroma3.siw.spring.validator.ProjectValidator;

@Controller
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	@RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.getMyProjects(loggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		
		return "myOwnedProjects";
	}
	
	@RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
	public String shareProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.getShareProjects(loggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		
		return "sharedProjects";
	}
	
	@RequestMapping(value = {"/projects/{projectId}"}, method = RequestMethod.GET)
	public String project(Model model, @PathVariable Long projectId ) {
		Project project = projectService.getProject(projectId);
		if(project == null)
			return "redirect:/projects";
		User loggedUser = sessionData.getLoggedUser();
		List<User> members = userService.getMembers(project);
		if(!project.getOwner().equals(loggedUser) && !members.contains(loggedUser))
			return "redirect:/projects";
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		
		return "project";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/edit"}, method = RequestMethod.GET)
	public String createEditProjectForm(Model model, @PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		model.addAttribute("projectForm", project);
		
		return "projectForm";
	}
	
	@RequestMapping(value= {"/projects/{projectId}/edit"}, method=RequestMethod.POST)
	public String editProject(@Valid @ModelAttribute("projectForm") Project project, @PathVariable Long projectId, 
								BindingResult projectBindingResult, Model model) {
		Project p = this.projectService.getProject(projectId);
		p.setName(project.getName());
		p.setDescription(project.getDescription());
		this.projectService.saveProject(p);
		
		return "redirect:/projects/{projectId}";
	}
	
	@RequestMapping(value = {"/project/add"}, method = RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		
		return "addProject";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectsForm") Project project,
								BindingResult projectBindingResult,
								Model model) {
		User loggedUser = sessionData.getLoggedUser();
		projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		model.addAttribute("loggedUser", loggedUser);
		
		return "addProject";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/delete"}, method = RequestMethod.POST)
	public String deleteProject(@PathVariable Long projectId, Model model) {
		this.projectService.deleteProjectById(projectId);
		
		return "redirect:/projects";		
	}
}
