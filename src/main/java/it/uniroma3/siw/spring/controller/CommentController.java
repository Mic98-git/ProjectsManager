package it.uniroma3.siw.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Comment;
import it.uniroma3.siw.spring.model.Tag;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.service.CommentService;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TaskService;
import it.uniroma3.siw.spring.validator.CommentValidator;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	private CommentValidator commentValidator;
	
	@PostMapping("/projects/{projectId}/task/{taskId}/addcomment")
	public String addComment(@Valid @ModelAttribute("commentForm") Comment comment,
			@PathVariable Long taskId,
			@PathVariable Long projectId,
			BindingResult commentBindingResult,
			Model model
			) {
		comment.setUser(sessionData.getLoggedUser());
		
		commentValidator.validate(comment, commentBindingResult);		
		if(commentBindingResult.hasErrors()) {
			Task task = this.taskService.getTask(taskId);
			model.addAttribute("task",task);
			model.addAttribute("projectId", projectId);
			model.addAttribute("loggedUser",sessionData.getLoggedUser());
			/* questa riga non va messa, altrimenti gli errori non vengono segnalati */
			//model.addAttribute("commentForm",comment);
			
			model.addAttribute("allTaskTags",this.taskService.getTask(taskId).getTags());
			
			List<Tag> tags = this.projectService.getProject(projectId).getTags();
			tags.removeAll(task.getTags());
			model.addAttribute("addableTags",tags);
			
			model.addAttribute("tagForm",new Tag());
			
			return "task";
		}
				
		Task task = taskService.getTask(taskId);
		task.getComments().add(comment);
		taskService.saveTask(task);
		
		//System.out.println();
		
		return "redirect:/projects/" + projectId + "/task/"+taskId;
	}

}
