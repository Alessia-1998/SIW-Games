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

import it.uniroma3.siw.controller.validator.PiattaformaValidator;
import it.uniroma3.siw.model.Piattaforma;
import it.uniroma3.siw.service.PiattaformaService;
import jakarta.validation.Valid;

@Controller
public class PiattaformaController {
	
	@Autowired private PiattaformaService piattaformaService;
	@Autowired private PiattaformaValidator piattaformaValidator;
	
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
	
	
	 /* ADMIN */
	
	// VISUALIZZA LA FORM PER L'AGGIUNTA DELLE PIATTAFORME
    @GetMapping("/admin/aggiungiPiattaforma")
    public String aggiungiPiattaforma(Model model) {
    	model.addAttribute("piattaforma", new Piattaforma());
        return "admin/aggiungiPiattaforma.html";
    }
    
    //AGGIUNGI NUOVA PIATTAFORMA
    @PostMapping("/admin/aggiungiPiattaforma")
    public String newPiattaforma(@Valid @ModelAttribute("piattaforma") Piattaforma piattaforma, BindingResult bindingResult,
    		@RequestParam(value="immagineFile" ,required = false) MultipartFile image, Model model) throws IOException {
    	this.piattaformaValidator.validate(piattaforma, bindingResult); // Validazione

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
    		    piattaforma.setImage("/uploads/" + image.getOriginalFilename());
    		} else {
    		    piattaforma.setImage("/defaultcover.png");
    		}
    		//////////////////////////////////////////////

    		this.piattaformaService.save(piattaforma);       
    		model.addAttribute("piattaforma", piattaforma); //  Ricarica la piattaforma inserita
    		return "redirect:/piattaforme/" + piattaforma.getId();     
    	} else {       
    		return "admin/aggiungiPiattaforma";     
    	}
    }

}
