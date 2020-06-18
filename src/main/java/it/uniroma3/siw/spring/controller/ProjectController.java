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
import it.uniroma3.siw.spring.model.Tag;
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
	CredentialsService credentialsService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	@RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.getMyProjects(loggedUser);
		model.addAttribute("projectsList", projectsList);
		
		return "myOwnedProjects";
	}
	
	@RequestMapping(value = {"/projects/shared"}, method = RequestMethod.GET)
	public String sharedProjectsWithMe(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.getShareProjects(loggedUser);
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
		if(!(project.getOwner().equals(loggedUser) || members.contains(loggedUser)))
			return "redirect:/projects";
		
		List<Credentials> credentials = credentialsService.getAllCredentials();
		credentials.forEach(credential -> credential.setPassword("[PROTECTED]"));
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		model.addAttribute("credentialForm", new Credentials());
		
		// SPIEGARE Java 8
		// comando che rimuove dalle credentials tutti gli utenti già facenti
		// parte dei membri del project
		credentials.removeIf(credential -> members.contains(credential.getUser()));
		model.addAttribute("allCredentials", credentials);
		model.addAttribute("tagForm", new Tag());
		
		//System.out.println();
		
		return "project";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/edit"}, method = RequestMethod.GET)
	public String createEditProjectForm(Model model, @PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		model.addAttribute("projectForm", project);
		
		return "editProject";
	}
	
	@RequestMapping(value= {"/projects/{projectId}/edit"}, method=RequestMethod.POST)
	public String editProject(@Valid @ModelAttribute("projectForm") Project project, @PathVariable Long projectId, 
								BindingResult projectBindingResult, Model model) {
		this.projectValidator.validate(project, projectBindingResult);
		if(projectBindingResult.hasErrors()) {
			return "editProject";
		}
		Project p = this.projectService.getProject(projectId);
		p.setName(project.getName());
		p.setDescription(project.getDescription());
		this.projectService.saveProject(p);
		
		return "redirect:/projects/{projectId}";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		//System.out.println("");
		return "registerProject";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectForm") Project project,
								BindingResult projectBindingResult,
								Model model) {
		User loggedUser = sessionData.getLoggedUser();
		
		projectValidator.validate(project, projectBindingResult);
		
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			project.addMember(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		
		model.addAttribute("loggedUser", loggedUser);
		//model.addAttribute("projectForm", project);
		return "registerProject";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/delete"}, method = RequestMethod.POST)
	public String deleteProject(@PathVariable Long projectId, Model model) {
		this.projectService.deleteProjectById(projectId);
		
		return "redirect:/projects";		
	}
	
	@RequestMapping(value = {"/projects/{projectId}/members/add"}, method = RequestMethod.POST)
	public String addMemberToProject(@PathVariable Long projectId,
			@ModelAttribute Credentials credentials) {
		Credentials c = credentialsService.getCredentials(credentials.getUserName());
		Project project = projectService.getProject(projectId);
		
		if(!project.getMembers().contains(c.getUser()))
			project.addMember(c.getUser());
		projectService.saveProject(project);
		
		//System.out.println();
		
		
		return "redirect:/projects/"+projectId;
	}
}
