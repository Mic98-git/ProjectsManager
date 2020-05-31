package it.uniroma3.siw.sping.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	private String content;
	
	@ManyToOne
	private User user;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
