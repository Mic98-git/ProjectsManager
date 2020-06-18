package it.uniroma3.siw.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Comment;
import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Tag;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TagService;
import it.uniroma3.siw.spring.service.TaskService;
import it.uniroma3.siw.spring.validator.TaskValidator;
import net.bytebuddy.asm.Advice.This;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	private TaskValidator taskValidator;
	
	/* projectId non servirebbe, ma lo metto per convenienza */
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}"}, method = RequestMethod.GET)
	public String viewTaskDetails(@PathVariable Long projectId,
			@PathVariable Long taskId,			
			Model model) {
		Project project = this.projectService.getProject(projectId);
		boolean currentUserIsOwner = project.getOwner().equals(sessionData.getLoggedUser());
		
		Task task = this.taskService.getTask(taskId);
		model.addAttribute("task",task);
		model.addAttribute("projectId", projectId);
		model.addAttribute("loggedUser",sessionData.getLoggedUser());
		model.addAttribute("commentForm",new Comment());
		model.addAttribute("currentUserIsOwner", currentUserIsOwner);
		
		model.addAttribute("allTaskTags",this.taskService.getTask(taskId).getTags());
		
		List<Tag> tags = this.projectService.getProject(projectId).getTags();
		tags.removeAll(task.getTags());
		model.addAttribute("addableTags",tags);
		
		model.addAttribute("tagForm",new Tag());
		
		System.out.print("");
		return "task";
	}
	
	@PostMapping("/projects/{projectId}/task/{taskId}/addtag")
	public String addTag(@PathVariable Long projectId,
			@PathVariable Long taskId,
			@ModelAttribute("tagForm") Tag tag,
			BindingResult tagBindingResult) {
		Task task = taskService.getTask(taskId);
		tag = this.tagService.getTagById(tag.getId());
		tag.getTasks().add(task);
		this.tagService.saveTag(tag);
		
		return "redirect:/projects/"+projectId+"/task/"+taskId;
	}
	
	@PostMapping("/projects/{projectId}/task/{taskId}/deletetag/{tagId}")
	public String removeTag(@PathVariable Long projectId,
			@PathVariable Long taskId,
			@PathVariable Long tagId) {
		Tag tag = this.tagService.getTagById(tagId);
		Task task = this.taskService.getTask(taskId);
		task.getTags().remove(tag);
		this.taskService.saveTask(task);
		
		return "redirect:/projects/"+projectId+"/task/"+taskId;
	}
	
	@GetMapping("/projects/{projectId}/task/new")
	public String viewNewTaskForm(@PathVariable Long projectId,Model model) {
		// trovo il progetto a partire dall'id
		Project project = this.projectService.getProject(projectId);
		
		if(!project.getMembers().contains(sessionData.getLoggedUser())) {
			return "noAuth";
		}
		
		// serve per la pagina, voglio informazioni sul progetto
		model.addAttribute("project",project);
		// per il form del task nuovo
		model.addAttribute("taskForm",new Task());
		System.out.print("");
		
		return "newTask";
	}
	
	@PostMapping("/projects/{projectId}/task/new")
	public String addNewTask(@Valid @ModelAttribute("taskForm") Task task,
			BindingResult taskBindingResult,
			@PathVariable Long projectId,
			Model model) {
		Project project = projectService.getProject(projectId);
		if(!project.getMembers().contains(sessionData.getLoggedUser())) {
			return "noAuth";
		}
		
		taskValidator.validate(task, taskBindingResult);
		if(taskBindingResult.hasErrors()) {// serve per la pagina, voglio informazioni sul progetto
			model.addAttribute("project",project);
			return "newTask";
		}
		
		System.out.println();
		project.getProjectTasks().add(task);
		projectService.saveProject(project);
		
		return "redirect:/projects/" + projectId;
	}
	
	@GetMapping("/projects/{projectId}/task/{taskId}/edit")
	public String viewEditTaskForm(@PathVariable Long projectId,
			@PathVariable Long taskId,Model model) {
		Task task = taskService.getTask(taskId);
				
		// per il form del task da editare	
		model.addAttribute("taskForm",task);
		model.addAttribute("projectId", projectId);
		
		return "editTask";
	}
	
	@PostMapping("/projects/{projectId}/task/{taskId}/edit")
	public String updateTask(@PathVariable Long projectId,
			@PathVariable Long taskId,
			@Valid @ModelAttribute("taskForm") Task task,
			BindingResult taskBindingResult,
			Model model) {
		this.taskValidator.validate(task, taskBindingResult);
		if(taskBindingResult.hasErrors()) {
			model.addAttribute("projectId", projectId);	
			
			return "editTask";
		}
		
		Task t = taskService.getTask(taskId);
		t.setName(task.getName());
		t.setDescription(task.getDescription());
		t.setCompleted(task.isCompleted());
		
		this.taskService.saveTask(task);
		
		return "redirect:/projects/"+ projectId +"/task/" + taskId;
	}
	
	@PostMapping("/projects/{projectId}/task/{taskId}/completed")
	public String markCompletedTask(@PathVariable Long projectId, 
			@PathVariable Long taskId) {
		Task task = taskService.getTask(taskId);
		task.setCompleted(true);
		taskService.saveTask(task);
		System.out.println("");
		
		return "redirect:/projects/"+projectId+"/task/"+taskId;
	}
	
	@PostMapping("/projects/{projectId}/task/{taskId}/delete")
	public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
		this.taskService.deleteTask(taskId);
		
		// reindirizza ai miei progetti
		return "redirect:/projects/"+projectId;
	}
		
}
