package it.uniroma3.siw.sping.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Task {
	
	@Id
	private Long id;
	private String name;
	private String description;
	private LocalDateTime creationDate = LocalDateTime.now();

	@ManyToOne
	private Project project;
	@ManyToMany
	private List<Tag> tags = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "task_id")
	private List<Comment> comments = new ArrayList<>();
	
	

}
