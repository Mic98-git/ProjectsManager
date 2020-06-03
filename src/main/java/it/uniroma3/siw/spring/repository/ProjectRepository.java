package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Project;

public interface ProjectRepository extends CrudRepository<Project,Long> {

}
