package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Comment;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.service.CommentService;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TaskService;

public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	SessionData sessionData;
	
	@PostMapping("/{projectId}/task/{taskId}/addcomment")
	public String addComment(@RequestBody Comment comment,Long projectId,Long taskId) {
		comment.setUser(sessionData.getLoggedUser());
		
		Task task = taskService.getTask(taskId);
		task.getComments().add(comment);
		taskService.saveTask(task);
		commentService.saveComment(comment);
		
		return "redirect:/";
	}

}
