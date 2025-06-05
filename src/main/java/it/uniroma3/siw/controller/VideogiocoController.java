package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.VideogiocoService;

@Controller
public class VideogiocoController {
	
	@Autowired private VideogiocoService videogiocoService;
	@Autowired private CredentialsService credentialsService;
	@Autowired private UserRepository userRepository;
	
	// LISTA DEI VIDEOGIOCHI
	@GetMapping("/videogiochi")
	public String listaVideogiochi(Model model) {
		model.addAttribute("videogiochi", this.videogiocoService.getAllVideogiochi()); // passa tutti i giochi alla lista
		model.addAttribute("totalVideogiochi", this.videogiocoService.countVideogiochi()); // passa il totale alla vista
		return "listaVideogiochi.html";
	}
	
	// MOSTRA DETTAGLI DEL VIDEOGIOCO SPECIFICO
	@GetMapping("/videogiochi/{id}")
	public String dettagliVideogioco(@PathVariable("id") Long id, Model model, Principal principal) {
		Videogioco videogioco = this.videogiocoService.getVideogiocoById(id); // Ottengo il gioco con specifico id
		model.addAttribute("videogioco", videogioco); 

		// Se l'utente NON è autenticato, saltiamo la parte di "credentials"
		Credentials credentials = (principal != null) ? credentialsService.findByUsername(principal.getName()).orElse(null) : null ;
		// variabile = (condizione) ? valore_se_vero : valore_se_falso 
		User user = (credentials != null) ? credentials.getUser() : null;
		boolean videogiocoPreferito = user!= null && user.getPreferiti().contains(videogioco); // Controlla se il libro è nei preferiti
		
		model.addAttribute("videogiocoPreferito", videogiocoPreferito); // Passo il booleano alla lista

		return "dettagliVideogioco.html";
	}
	
	// PER AGGIUNGERE VIDEOGIOCO A LISTA PREFERITI
	@PostMapping("/videogiochi/{id}/preferiti")
	public String gestisciPreferiti(@PathVariable("id") Long id, Principal principal, Model model) {

		// Se l'utente NON è autenticato, saltiamo la parte di "credentials"
		Credentials credentials = (principal != null) ? credentialsService.findByUsername(principal.getName()).orElse(null) : null ;
		// variabile = (condizione) ? valore_se_vero : valore_se_falso 
		User user = (credentials != null) ? credentials.getUser() : null;

		Videogioco videogioco = this.videogiocoService.getVideogiocoById(id); // Ottengo il gioco con specifico id

		if(user.getPreferiti().contains(videogioco)) {
			user.getPreferiti().remove(videogioco); // Rimuove dai preferiti
		} else {
			user.getPreferiti().add(videogioco); // Aggiunge ai preferiti
		}

		userRepository.save(user);
		return "redirect:/videogiochi/" + id;
	}

	// LISTA DEI PREFERITI
	@GetMapping("videogiochi/preferiti")
	public String mostraListaPreferiti(Model model, Principal principal) {
		// Se l'utente NON è autenticato, saltiamo la parte di "credentials"
		Credentials credentials = (principal != null) ? credentialsService.findByUsername(principal.getName()).orElse(null) : null ;
		// variabile = (condizione) ? valore_se_vero : valore_se_falso 
		User user = (credentials != null) ? credentials.getUser() : null;

		if(user==null) return "redirect:/videogiochi"; 
		model.addAttribute("preferiti", user.getPreferiti()); // Passo la lista dei preferiti alla vista
		return "preferiti";
	}

    // RICERCA DEI VIDEOGIOCHI IN BASE AL TITOLO O ALL'ANNO DI USCITA
    @PostMapping("videogiochi/search")
    public String cercaVideogiochi(@RequestParam(required = false) String titolo, @RequestParam(required = false) Integer anno, Model model) {
        List<Videogioco> videogiochi = Collections.emptyList() ;
        if (!titolo.isBlank()) {  // cioè se non è nullo o non è esattamente vuoto (cioè se non ha solo spazi bianchi) 
            videogiochi = videogiocoService.cercaPerTitolo(titolo);
        } else if (anno != null) {
            videogiochi = videogiocoService.cercaPerAnno(anno);
        }
        model.addAttribute("videogiochi", videogiochi);
        return "giochiTrovati";
    }

    // FORM PER LA RICERCA DEI VIDEOGIOCHI 
    @GetMapping("videogiochi/formSearchGiochi")
    public String formSearchGiochi() {
        return "formSearchGiochi.html";
    }


}
