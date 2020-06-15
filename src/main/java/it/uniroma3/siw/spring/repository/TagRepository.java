package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag,Long> {
	
}
