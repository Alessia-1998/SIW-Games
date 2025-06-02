package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.service.PiattaformaService;

@Controller
public class PiattaformaController {
	
	@Autowired PiattaformaService piattaformaService;
	
	//LISTA DELLE PIATTAFORME
	@GetMapping("/piattaforme")
	public String listaPiattaforme(Model model) {
		model.addAttribute("piattaforme", this.piattaformaService.getAllPiattaforme()); //passa tutte le piattaforme alla lista
		model.addAttribute("totalPiattaforme", this.piattaformaService.countPiattaforme()); //passa il totale alla lista
		return "listaPiattaforme.html";
	}
	
	
	//MOSTRA DETTAGLI DELLA SPECIFICA PIATTAFORMA
	@GetMapping("/piattaforme/{id}")
	public String dettagliPiattaforma(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piattaforma", this.piattaformaService.getPiattaformaById(id)); // ottengo la piattaforma con lo specifico id
		return "dettagliPiattaforma.html";
	}

}
