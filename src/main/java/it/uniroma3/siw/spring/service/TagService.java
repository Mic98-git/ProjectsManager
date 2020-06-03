package it.uniroma3.siw.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	protected TagRepository tagRepository;
}
