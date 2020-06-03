package it.uniroma3.siw.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(updatable = false,nullable=false)
	private LocalDateTime creationTimestamp;
	
	@Column(nullable=false)
	private LocalDateTime lastUpdateTimestamp;
	
	@OneToMany (cascade = CascadeType.REMOVE)
	@JoinColumn(name = "project_id")
	private List<Tag> tags;
	
	@OneToMany (cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private List<Task> projectTasks;
	
	@ManyToMany (fetch=FetchType.LAZY)
	private List<User> members;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User owner;
	
	public Project() {
		this.tags = new ArrayList<>();
		this.projectTasks = new ArrayList<>();
		this.members = new ArrayList<>();
	}
	
	public Project(String name) {
		this();
		this.name = name;
	}
	
	@PrePersist
	protected void onPersist() {
		this.creationTimestamp = LocalDateTime.now();
		this.lastUpdateTimestamp = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateTimestamp = LocalDateTime.now();
	}

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

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}
	
	public void addMember(User u) {
		this.members.add(u);
	}
	
	public void addTag(Tag t) {
		this.tags.add(t);
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public LocalDateTime getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	
}
