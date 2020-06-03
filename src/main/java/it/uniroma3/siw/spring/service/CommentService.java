package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Comment;
import it.uniroma3.siw.spring.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	protected CommentRepository commentRepository;
	
	@Transactional
	public Comment saveComment(Comment c) {
		return this.commentRepository.save(c);
	}
	
	@Transactional
	public void deleteComment(Comment c) {
		this.commentRepository.delete(c);
	}
}
