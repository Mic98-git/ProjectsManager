package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Tag;
import it.uniroma3.siw.spring.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	protected TagRepository tagRepository;
	
	@Transactional
	public Tag saveTag(Tag tag) {
		return this.tagRepository.save(tag);
	}
	
	@Transactional
	public void deleteTag(Tag tag) {
		this.tagRepository.delete(tag);
	}	
	
	@Transactional
	public void deleteTagById(Long id) {
		this.tagRepository.deleteById(id);
	}
}
