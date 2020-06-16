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
	private String lastName;
	
	@Column(updatable = false,nullable=false)
	private LocalDateTime creationTimestamp;
	
	@Column(nullable=false)
	private LocalDateTime lastUpdateTimestamp;
	
	/* Importantissimo il cascade remove FIXME */
	@ManyToMany(mappedBy="members",fetch=FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Project> visibleProjects;
	
	@OneToMany(cascade=CascadeType.REMOVE,mappedBy = "owner",fetch=FetchType.LAZY)
	private List<Project> ownedProjects;

	public User() {
		this.ownedProjects = new ArrayList<>();
		this.visibleProjects = new ArrayList<>();
	}
	
	public User(String name, String lastName) {
		this();
		this.name = name;
		this.lastName = lastName;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public LocalDateTime getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((lastUpdateTimestamp == null) ? 0 : lastUpdateTimestamp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (creationTimestamp == null) {
			if (other.creationTimestamp != null)
				return false;
		} else if (!creationTimestamp.equals(other.creationTimestamp))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (lastUpdateTimestamp == null) {
			if (other.lastUpdateTimestamp != null)
				return false;
		} else if (!lastUpdateTimestamp.equals(other.lastUpdateTimestamp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastName=" + lastName + ", creationTimestamp="
				+ creationTimestamp + ", lastUpdateTimestamp=" + lastUpdateTimestamp + "]";
	}	
	
}
