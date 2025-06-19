package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Piattaforma;
import it.uniroma3.siw.repository.PiattaformaRepository;

@Component
public class PiattaformaValidator implements Validator {
	
	@Autowired PiattaformaRepository piattaformaRepository;
	
	@Override
	public void validate(Object p, Errors errors) {
		Piattaforma piattaforma = (Piattaforma) p;
		if (piattaforma.getNome() != null && piattaforma.getDataDiRilascio() != null
			&& piattaformaRepository.existsByNomeAndDataDiRilascio(piattaforma.getNome(), piattaforma.getDataDiRilascio())) {
			errors.reject("piattaforma.duplicate"); // specifica che c'è stato un errore nella validazione e registra un codice di errore
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Piattaforma.class.equals(aClass);
	}

}
