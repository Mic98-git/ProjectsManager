package it.uniroma3.siw.spring.service;

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
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
		
	/** Servirà per aggiornare il profilo  
	 * 
	 * */
	@Transactional
	public User updateUserProfile(User user) {
		return userRepository.save(user);
	}
	
}
