package it.uniroma3.siw.spring.controller.session;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.CredentialsRepository;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {
	
	private User user;
	
	private Credentials credentials;
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	public Credentials getLoggedCredentials() {
		if(this.credentials==null)
			this.update();
		return this.credentials;
	}
	
	public User getLoggedUser() {
		if(this.user==null)
			this.update();
		return this.user;
	}
	
	private void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
		// come sgonfiare la fisarmonica?
		if(obj instanceof UserDetails) { // login normale
			UserDetails userDetails = (UserDetails)obj;
			this.credentials = this.credentialsRepository.findByUserName(userDetails.getUsername()).get();
			//this.credentials.setPassword("[PROTECTED]");
			this.user = this.credentials.getUser();
		} else if(obj instanceof DefaultOidcUser) { // login oauth google
			DefaultOidcUser oidcUser = (DefaultOidcUser)obj;
			if(this.credentialsRepository.findByUserName(oidcUser.getEmail()).isPresent()) {
				this.credentials = this.credentialsRepository.findByUserName(oidcUser.getEmail()).get();
				this.user = this.credentials.getUser();
			} else {
				// devo creare le nuove credenziali con password fittizia e poi l'utente associato
				Credentials credentials = new Credentials();
				credentials.setUserName(oidcUser.getEmail());
				credentials.setPassword("[PROTECTED]");
				credentials.setRole(Credentials.DEFAULT_ROLE);
				credentials.setUser(new User(oidcUser.getGivenName(),oidcUser.getFamilyName()));
				this.credentialsRepository.save(credentials);
				// codice duplicato
				this.credentials = this.credentialsRepository.findByUserName(oidcUser.getEmail()).get();
				this.user = this.credentials.getUser();
			}
		}
		
		
	}
}
