package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Tag;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	protected ProjectRepository projectRepository;
	
	@Transactional
	public Project getProject(Long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		for(Project p : this.projectRepository.findAll())
			projects.add(p);
		return projects;
	}
	
	@Transactional
	public Project saveProject(Project p) {
		return this.projectRepository.save(p);
	}
	
	@Transactional
	public void deleteProject(Project p) {
		this.projectRepository.delete(p);
	}
	
	@Transactional
	public void deleteProjectById(Long id) {
		this.projectRepository.deleteById(id);
	}
	
	@Transactional
	public Project shareProjectWithUser(Project p, User u) {
		p.addMember(u);
		return this.projectRepository.save(p);
	}
	
	@Transactional
	public List<Project> getShareProjects(User u) {
		return this.projectRepository.findByMembers(u);
	}
	
	@Transactional
	public List<Project> getMyProjects(User u) {
		return this.projectRepository.findByOwner(u);
	}
	
	@Transactional
	public void addTag(Project p, Tag tag) {
		p.addTag(tag);
		this.projectRepository.save(p);
	}
	
}
