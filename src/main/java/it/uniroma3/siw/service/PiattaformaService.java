package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Piattaforma;
import it.uniroma3.siw.repository.PiattaformaRepository;

@Service
public class PiattaformaService {
	
	@Autowired private PiattaformaRepository piattaformaRepository;
	
	public Piattaforma getPiattaformaById(Long id) {
		return piattaformaRepository.findById(id).get();
	}
	
	public Iterable<Piattaforma> getAllPiattaforme() {
		return piattaformaRepository.findAll();
	}
	
	public void save(Piattaforma piattaforma) {
		piattaformaRepository.save(piattaforma);
	}
	
	public long countPiattaforme() {
		return this.piattaformaRepository.count();
	}

}
