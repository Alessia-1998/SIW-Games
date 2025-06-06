package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.VideogiocoRepository;

@Service
public class VideogiocoService {
	
	@Autowired VideogiocoRepository videogiocoRepository;
	
	public Videogioco getVideogiocoById(Long id) {
		return videogiocoRepository.findById(id).get();
	}
	
	public Iterable<Videogioco> getAllVideogiochi() {
		return videogiocoRepository.findAll();
	}
	
	@Transactional
	public void save(Videogioco videogioco) {
		this.videogiocoRepository.save(videogioco);
	}

	public long countVideogiochi() {
		return this.videogiocoRepository.count();
	}

	public List<Videogioco> cercaPerTitolo(String titolo) {
		return videogiocoRepository.findByTitoloContainingIgnoreCase(titolo);
	}

	public List<Videogioco> cercaPerAnno(Integer anno) {
		return videogiocoRepository.findByAnno(anno);
	}

	public void delete(Videogioco videogioco) {
		this.videogiocoRepository.delete(videogioco);
	}
}
