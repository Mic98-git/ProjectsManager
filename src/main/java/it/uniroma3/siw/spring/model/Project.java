package it.uniroma3.siw.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private LocalDateTime dateOfStart;
	
	@OneToMany
	@JoinColumn(name = "project_id")
	private List<Tag> tags = new ArrayList<>();
	
	@OneToMany(mappedBy = "project")
	private List<Task> projectTasks = new ArrayList<>();
	
	@ManyToMany(mappedBy = "visibleProjects")
	private List<User> visibleUsers = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDateOfStart() {
		return dateOfStart;
	}

	public void setDateOfStart(LocalDateTime dateOfStart) {
		this.dateOfStart = dateOfStart;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Task> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<Task> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public List<User> getVisibleUsers() {
		return visibleUsers;
	}

	public void setVisibleUsers(List<User> visibleUsers) {
		this.visibleUsers = visibleUsers;
	}
}
