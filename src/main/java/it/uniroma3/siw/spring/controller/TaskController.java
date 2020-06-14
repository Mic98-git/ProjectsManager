package it.uniroma3.siw.spring.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.session.SessionData;
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
	
	@Autowired
	SessionData sessionData;
	
	/* projectId non servirebbe, ma lo metto per convenienza */
	@GetMapping("/task/{taskId}")
	public String viewTaskDetails(@PathVariable Long taskId,Model model) {
		Task task = this.taskService.getTask(taskId);
		model.addAttribute("task",task);
		model.addAttribute("loggedUser",sessionData.getLoggedUser());
		model.addAttribute("commentForm",new Comment());
		
		//FIXME Mettere possibilità di aggiungere tag, ma solo a utente proprietario delprogetto
		
		//System.out.print("");
		return "task";
	}
	
	@GetMapping("/{projectId}/task/new")
	public String viewNewTaskForm(@PathVariable Long projectId,Model model) {
		// trovo il progetto a partire dall'id
		Project project = this.projectService.getProject(projectId);
		
		if(!project.getMembers().contains(sessionData.getLoggedUser())) {
			return "noAuth";
		}
		
		// serve per la pagina, voglio informazioni sul progetto
		model.addAttribute("project",project);
		// per il form del task nuovo
		model.addAttribute("task",new Task());
		//System.out.print("");
		
		return "newTask";
	}
	
	@PostMapping("/{projectId}/task/new")
	public String addNewTask(@ModelAttribute("task") Task task,
			BindingResult taskBindingResult,
			@PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		//System.out.println(project.getName());
		project.getProjectTasks().add(task);
		projectService.saveProject(project);
		
		return "redirect:/project/" + projectId;
	}
	
	@GetMapping("/task/{taskId}/edit")
	public String viewEditTaskForm(@PathVariable Long taskId,Model model) {
		Task task = taskService.getTask(taskId);
				
		// per il form del task da editare	
		model.addAttribute("taskForm",task);
		
		return "editTask";
	}
	
	@PostMapping("/task/edit")
	public String updateTask(@ModelAttribute Task task) {
		Long taskId = task.getId();
		this.taskService.saveTask(task);
		
		return "redirect:/task/" + taskId;
	}
	
	@DeleteMapping("/task/{taskId}")
	public String deleteTask(@PathVariable Long taskId) {
		this.taskService.deleteTask(taskId);
		
		// reindirizza ai miei progetti
		return "redirect:/projects/my";
	}
		
}
