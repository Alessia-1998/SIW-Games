package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.SviluppatoreService;

@Controller
public class SviluppatoreController {
	
	@Autowired SviluppatoreService sviluppatoreService;
	
	// LISTA DEGLI SVILUPPATORI
	@GetMapping("/sviluppatori")
	public String listaSviluppatori(Model model) {
		model.addAttribute("sviluppatori", this.sviluppatoreService.getAllSviluppatori()); //passa tutti gli sviluppatori alla lista
		model.addAttribute("totalSviluppatori", this.sviluppatoreService.countSviluppatori()); //passa il totale alla lista
		return "listaSviluppatori.html";
	}
	
	
	// MOSTRA I DETTAGLI DELLO SVILUPPATORE SPECIFICO
	@GetMapping("/sviluppatori/{id}")
	public String dettagliSviluppatore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sviluppatore", this.sviluppatoreService.getSviluppatoreById(id)); //ottengo lo sviluppatore con l'id specificato
		return "dettagliSviluppatore.html";
	}

}
