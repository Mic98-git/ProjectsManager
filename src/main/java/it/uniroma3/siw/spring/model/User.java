package it.uniroma3.siw.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false,length=100)
	private String name;
	
	@Column(nullable=false,length=100)
	private String lastname;
	
	@Column(updatable = false,nullable=false)
	private LocalDateTime creationTimestamp;
	
	@Column(nullable=false)
	private LocalDateTime lastUpdateTimestamp;
	
	@Column(unique=true,nullable=false,length=100)
	private String username;
	
	@Column(nullable=false,length=100)
	private String password;
	
//	@ManyToOne
//	private Role role;
	
	@ManyToMany(mappedBy="members")
	private List<Project> visibleProjects;
	
	@OneToMany(cascade=CascadeType.REMOVE,mappedBy = "owner")
	private List<Project> ownedProjects;

	@OneToMany
	@JoinColumn(name = "task_id")
	private List<Comment> comments;
	
	public User() {
		this.ownedProjects = new ArrayList<>();
		this.visibleProjects = new ArrayList<>();
		this.comments = new ArrayList<>();
	}
	
	public User(String name, String lastname, String username, String password) {
		this();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
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

	// GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}

	public List<Project> getVisibleProjects() {
		return visibleProjects;
	}

	public void setVisibleProjects(List<Project> visibleProjects) {
		this.visibleProjects = visibleProjects;
	}

	public List<Project> getOwnedProjects() {
		return ownedProjects;
	}

	public void setOwnedProjects(List<Project> ownedProjects) {
		this.ownedProjects = ownedProjects;
	}

	public LocalDateTime getCreationTimeStamp() {
		return creationTimestamp;
	}

	public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
		this.creationTimestamp = creationTimeStamp;
	}

	public LocalDateTime getLastUpdateTimeStamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimeStamp(LocalDateTime lastUpdateTimeStamp) {
		this.lastUpdateTimestamp = lastUpdateTimeStamp;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
