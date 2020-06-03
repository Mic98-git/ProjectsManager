package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Comment;

public interface CommentRepository extends CrudRepository<Comment,Long> {

}
