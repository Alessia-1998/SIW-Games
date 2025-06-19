package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Sviluppatore;
import it.uniroma3.siw.repository.SviluppatoreRepository;

@Component
public class SviluppatoreValidator implements Validator{
	
	@Autowired private SviluppatoreRepository sviluppatoreRepository;
	
	@Override
	public void validate(Object s, Errors errors) {
		Sviluppatore sviluppatore = (Sviluppatore) s;
		if (sviluppatore.getNome() != null
			&& sviluppatoreRepository.existsByNome(sviluppatore.getNome()) ) {
			errors.reject("sviluppatore.duplicate"); // specifica che c'è stato un errore nella validazione e registra un codice di errore
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Sviluppatore.class.equals(aClass);
	}

}
