package it.uniroma3.siw.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	/** Aggiungi nuovo task a un progetto. L'oggetto progetto è già memorizzato nel task */
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}
	
	/**  */
	public Task updateTask(Task task) { // non richiamare delete e save, devo avere un UPDATE
		return null;
	}
	
	/* Cancellare un task */
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}	
}
