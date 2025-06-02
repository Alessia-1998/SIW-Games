package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
    protected CredentialsRepository credentialsRepository;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Transactional
    public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }
	
	@Transactional
    public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);

		System.out.println("🔹 Password originale: " + credentials.getPassword());

		// Se la password non è già in formato BCrypt, la codifichiamo
		if (!credentials.getPassword().startsWith("$2a$")) {
			credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		}

		System.out.println("🔹 Password finale da salvare: " + credentials.getPassword());

		return this.credentialsRepository.save(credentials);
		

	}

}
