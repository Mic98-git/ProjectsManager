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
public class User {
	
	@Id
	private Long id;

	private String name;
	private String lastname;
	
	private LocalDateTime creationDate = LocalDateTime.now();
	
	private String username;
	private String password;
	
	@ManyToOne
	private Role role;
	
	@ManyToMany
	private List<Project> visibleProjects = new ArrayList<>();
	
	@OneToMany
	@JoinColumn(name = "owner_id")
	private List<Project> projects = new ArrayList<>();
	
	
	
}
