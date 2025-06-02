package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.VideogiocoService;

@Controller
public class VideogiocoController {
	
	@Autowired VideogiocoService videogiocoService;
	
	// LISTA DEI VIDEOGIOCHI
	@GetMapping("/videogiochi")
	public String listaVideogiochi(Model model) {
		model.addAttribute("videogiochi", this.videogiocoService.getAllVideogiochi()); // passa tutti i giochi alla lista
		model.addAttribute("totalVideogiochi", this.videogiocoService.countVideogiochi()); // passa il totale alla vista
		return "listaVideogiochi.html";
	}
	
	// MOSTRA DETTAGLI DEL VIDEOGIOCO SPECIFICO
	@GetMapping("/videogiochi/{id}")
	public String dettagliVideogioco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("videogioco", this.videogiocoService.getVideogiocoById(id)); // Ottengo il gioco con specifico id
		return "dettagliVideogioco.html";
	}

}
