package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.VideogiocoRepository;

@Component
public class VideogiocoValidator implements Validator{
	
	@Autowired private VideogiocoRepository videogiocoRepository;
	
	@Override
	public void validate(Object v, Errors errors) {
		Videogioco videogioco = (Videogioco) v;
		if (videogioco.getTitolo() != null && videogioco.getAnno() != null
			&& videogiocoRepository.existsByTitoloAndAnno(videogioco.getTitolo(), videogioco.getAnno()) ) {
			errors.reject("videogioco.duplicate"); // specifica che c'è stato un errore nella validazione e registra un codice di errore
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Videogioco.class.equals(aClass);
	}

}
