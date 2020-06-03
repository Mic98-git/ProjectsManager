package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/** Servirà per visualizzazione del profilo
	 * 
	 * */
	@Transactional
	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}
		
	/** Servirà per aggiornare il profilo  
	 * 
	 * */
	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public List<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(user -> users.add(user));
		return users;
	}
}
