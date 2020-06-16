package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Comment;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.service.CommentService;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TaskService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	SessionData sessionData;
	
	@PostMapping("/projects/{projectId}/task/{taskId}/addcomment")
	public String addComment(@ModelAttribute("commentForm") Comment comment,
			@PathVariable Long taskId,
			@PathVariable Long projectId) {
		comment.setUser(sessionData.getLoggedUser());
		
		Task task = taskService.getTask(taskId);
		task.getComments().add(comment);
		taskService.saveTask(task);
		
		//System.out.println();
		
		return "redirect:/projects/" + projectId + "/task/"+taskId;
	}

}
