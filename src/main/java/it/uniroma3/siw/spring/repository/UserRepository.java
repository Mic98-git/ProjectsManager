package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findByVisibleProjects(Project project);
	
	public List<User> findByUserName(String username);
}
