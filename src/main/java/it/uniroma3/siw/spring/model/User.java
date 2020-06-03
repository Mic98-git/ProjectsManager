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
import javax.persistence.ManyToMany;
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
			
	@ManyToMany(mappedBy="members")
	private List<Project> visibleProjects;
	
	@OneToMany(cascade=CascadeType.REMOVE,mappedBy = "owner")
	private List<Project> ownedProjects;

	public User() {
		this.ownedProjects = new ArrayList<>();
		this.visibleProjects = new ArrayList<>();
	}
	
	public User(String name, String lastname) {
		this();
		this.name = name;
		this.lastname = lastname;
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
}
