package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUserName(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE); //di base quando salvo delle credenziali nuove, l'utente ha pochi provilegi
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}
	
	@Transactional
	public void deleteCredentials(String userName) {
		this.credentialsRepository.deleteByUserName(userName);
	}
	
	@Transactional
	public List<Credentials> getAllCredentials() {
		List<Credentials> allCredentials = new ArrayList<>();
		this.credentialsRepository.findAll().forEach(credential -> allCredentials.add(credential));
		return allCredentials;
	}
}
