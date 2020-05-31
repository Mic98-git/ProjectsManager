package it.uniroma3.siw.sping.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private LocalDateTime dateOfStart;
	
	@OneToMany
	private List<Tag> tags;
	
	@OneToMany
	private List<Task> projectTasks;
	
	@ManyToMany
	private List<User> visibleUsers;

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
