package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.SviluppatoreValidator;
import it.uniroma3.siw.model.Sviluppatore;
import it.uniroma3.siw.service.SviluppatoreService;
import jakarta.validation.Valid;

@Controller
public class SviluppatoreController {
	
	@Autowired private SviluppatoreService sviluppatoreService;
	@Autowired private SviluppatoreValidator sviluppatoreValidator;
	
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
	
	
	 /* ADMIN */
	
	// VISUALIZZA LA FORM PER L'AGGIUNTA DEGLI SVILUPPATORI 
    @GetMapping("/admin/aggiungiSviluppatore")
    public String aggiungiSviluppatore(Model model) {
    	model.addAttribute("sviluppatore", new Sviluppatore());
        return "admin/aggiungiSviluppatore.html";
    }
    
    //AGGIUNGI NUOVO SVILUPPATORE
    @PostMapping("/admin/aggiungiSviluppatore")
    public String newSviluppatore(@Valid @ModelAttribute("sviluppatore") Sviluppatore sviluppatore, BindingResult bindingResult,
    		@RequestParam(value="immagineFile" ,required = false) MultipartFile image, Model model) throws IOException {
    	this.sviluppatoreValidator.validate(sviluppatore, bindingResult); // Validazione

    	if (!bindingResult.hasErrors()) {

    		// PER GESTIRE L'UPLOAD DELLE IMMAGINI //
    		if (image != null && !image.isEmpty()) {
    			String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
    			System.out.println("Tentativo creazione cartella in: " + uploadDir);
    			File dir = new File(uploadDir);

    			if (!dir.exists()) {
    			    boolean created = dir.mkdirs();
    			    System.out.println("Cartella creata? " + created);
    			} else {
    			    System.out.println("Cartella già esistente");
    			}

    			File file = new File(dir, image.getOriginalFilename());
    		    image.transferTo(file);
    		    sviluppatore.setImage("/uploads/" + image.getOriginalFilename());
    		} else {
    		    sviluppatore.setImage("/defaultcover.png");
    		}
    		//////////////////////////////////////////////
    		
    		this.sviluppatoreService.save(sviluppatore);       
    		model.addAttribute("sviluppatore", sviluppatore); //  Ricarica il videogioco inserito 
    		return "redirect:/sviluppatori/" + sviluppatore.getId();     
    	} else {       
    		return "admin/aggiungiSviluppatori";     
    	}
    }
	

}
