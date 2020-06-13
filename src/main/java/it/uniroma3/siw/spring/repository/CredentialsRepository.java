package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.spring.model.Credentials;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	public Optional<Credentials> findByUserName(String userName);
}
