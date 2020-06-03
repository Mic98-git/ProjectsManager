package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.uniroma3.siw.spring.model.Comment;
import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TaskService;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProjectService projectService;
	
	/* projectId non servirebbe, ma lo metto per convenienza */
	@GetMapping("/{projectId}/task/{taskId}")
	public String viewTaskDetails(Long projectId,Long taskId,Model model) {
		Task task = this.taskService.getTask(taskId);
		model.addAttribute(task);
		
		return "task.html";
	}
	
	@GetMapping("/{projectId}/task")
	public String viewNewTaskForm(Long projectId,Model model) {
		// trovo il progetto a partire dall'id
		Project project = this.projectService.getProject(projectId);
		// serve per la pagina, voglio informazioni sul progetto
		model.addAttribute("project",project);
		// per il form del task nuovo
		model.addAttribute("task",new Task());
		
		return "taskform.html";
	}
	
	@PostMapping("/{projectId}/task")
	public String addTask(@RequestBody Task task,Long projectId) {
		Long taskId = task.getId();
		this.taskService.saveTask(task);
		
		return "redirect:/" + projectId + "/task/" + taskId;
	}
	
	@PutMapping("/{projectId}/task")
	public String updateTask(@RequestBody Task task,Long projectId) {
		Long taskId = task.getId();
		this.taskService.saveTask(task);
		
		return "redirect:/" + projectId + "/task/" + taskId;
	}
	
	@DeleteMapping("/{projectId}/task")
	public String deleteTask(@RequestBody Task task,Long projectId) {
		this.taskService.deleteTask(task);
		
		// reindirizza ai miei progetti
		return "redirect:/projects/my";
	}
	
	@PostMapping("/{projectId}/task/{taskId}/addcomment")
	public String addComment(@RequestBody Comment comment,Long projectId,Long taskId) {
		Task task = taskService.getTask(taskId);
		task.getComments().add(comment);
		taskService.saveTask(task);
		
		return "TODO"; // TODO
	}
}
