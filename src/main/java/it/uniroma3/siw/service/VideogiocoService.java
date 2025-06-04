package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public void save(Videogioco videogioco) {
		videogiocoRepository.save(videogioco);
	}

	public long countVideogiochi() {
		return this.videogiocoRepository.count();
	}

	public List<Videogioco> cercaPerTitolo(String titolo) {
                return videogiocoRepository.findByTitoloContainingIgnoreCase(titolo);
        }

        public List<Videogioco> cercaPerAnno(int anno) {
                return videogiocoRepository.findByAnno(anno);
        }
}
