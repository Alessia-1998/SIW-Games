package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Sviluppatore;
import it.uniroma3.siw.repository.SviluppatoreRepository;

@Service
public class SviluppatoreService {
	
	@Autowired private SviluppatoreRepository sviluppatoreRepository;
	
	public Sviluppatore getSviluppatoreById(Long id) {
		return sviluppatoreRepository.findById(id).get();
	}
	
	
	public Iterable<Sviluppatore> getAllSviluppatori(){
		return sviluppatoreRepository.findAll();
	}
	
	
	public void save (Sviluppatore sviluppatore) {
		sviluppatoreRepository.save(sviluppatore);
	}
	
	
	public long countSviluppatori() {
		return this.sviluppatoreRepository.count();
	}

}
