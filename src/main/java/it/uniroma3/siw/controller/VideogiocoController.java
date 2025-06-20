package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

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
import it.uniroma3.siw.controller.validator.VideogiocoValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Piattaforma;
import it.uniroma3.siw.model.Sviluppatore;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PiattaformaService;
import it.uniroma3.siw.service.SviluppatoreService;
import it.uniroma3.siw.service.VideogiocoService;
import jakarta.validation.Valid;

@Controller
public class VideogiocoController {
	
	@Autowired private VideogiocoService videogiocoService;
	@Autowired private CredentialsService credentialsService;
	@Autowired private UserRepository userRepository;
	@Autowired private PiattaformaService piattaformaService;
	@Autowired private SviluppatoreService sviluppatoreService;
	@Autowired private VideogiocoValidator videogiocoValidator;
	
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
    
    
    /* ADMIN */
    
    // MOSTRA LA LISTA DEI VIDEOGIOCHI CON I PULSANTI "MODIFICA" E "ELIMINA"
    @GetMapping("/admin/gestisciVideogiochi")
    public String mostraIndexLibri(Model model) {
    	List<Videogioco> videogiochi = (List<Videogioco>) videogiocoService.getAllVideogiochi(); // Prendo tutti i videogiochi
    	model.addAttribute("videogiochi", videogiochi );
    	model.addAttribute("totalVideogiochi", videogiocoService.countVideogiochi()); // Prendo il totale
    	return "admin/indexVideogiochi"; //  Ritorno la lista dei videogiochi da modificare
    }
    
    // ELIMINA VIDEOGIOCO
    @PostMapping("/admin/eliminaVideogioco/{id}") 
    public String eliminaVideogioco(@PathVariable("id") Long id, Principal principal) {
    	Videogioco videogioco = videogiocoService.getVideogiocoById(id);
    	if (videogioco != null) {
    		// Recupera tutti gli utenti, in modo da eliminare il videogioco da OGNI lista dei preferiti
    		List<User> utenti = (List<User>) userRepository.findAll();
    		for (User u : utenti) {
    			if (u.getPreferiti().contains(videogioco)) {
    				u.getPreferiti().remove(videogioco);
    				userRepository.save(u); // Aggiorna l'utente
    			}
    		}
    		videogiocoService.delete(videogioco); // Ora posso eliminare il videogioco
    	}
    	return "redirect:/admin/gestisciVideogiochi"; // Torna alla lista dopo l'eliminazione
    }
    
    // MODIFICA GIOCO PER AGGIUNGERE/ELIMINARE PIATTAFORME E UN SOLO SVILUPPATORE
    @GetMapping("/admin/modificaVideogioco/{id}")
    public String modificaVideogioco(@PathVariable("id") Long id,  Model model) {
	    Videogioco videogioco = videogiocoService.getVideogiocoById(id);
    	if (videogioco == null) return "redirect:/videogiochi";

    	List<Piattaforma> piattaformeDisponibili =(List<Piattaforma>) piattaformaService.getAllPiattaforme();
    	piattaformeDisponibili.removeAll(videogioco.getPiattaforme()); // Rimuovo le piattaforme già associate al videogioco
    	List<Sviluppatore> sviluppatoriDisponibili =(List<Sviluppatore>) sviluppatoreService.getAllSviluppatori();
    	
    	model.addAttribute("videogioco", videogioco);  // Passo il videogioco alla vista
    	model.addAttribute("piattaformeDisponibili", piattaformeDisponibili); // Passo le piattaforme disponibili alla vista
    	model.addAttribute("sviluppatoriDisponibili", sviluppatoriDisponibili); // Passo gli sviluppatori disponibili alla vista
    	return "admin/formModificaVideogioco.html";
    }
    
 
    // ASSOCIO UNA PIATTAFORMA AL VIDEOGIOCO
    @PostMapping("/admin/{videogiocoId}/aggiungiPiattaforma/{piattaformaId}")
	public String aggiungiPiattaforma(@PathVariable("videogiocoId") Long videogiocoId, @PathVariable("piattaformaId") Long piattaformaId) {
		Videogioco videogioco = videogiocoService.getVideogiocoById(videogiocoId);
		Piattaforma piattaforma = piattaformaService.getPiattaformaById(piattaformaId);
		if (!videogioco.getPiattaforme().contains(piattaforma)) {
		    videogioco.getPiattaforme().add(piattaforma);
		    this.videogiocoService.save(videogioco);
		}
		return "redirect:/admin/modificaVideogioco/" + videogiocoId;
	}
	

    // DISSOCIO UNA PIATTAFORMA DAL VIDEOGIOCO
	@PostMapping("/admin/{videogiocoId}/rimuoviPiattaforma/{piattaformaId}")
	public String rimuoviPiattaforma(@PathVariable("videogiocoId") Long videogiocoId, @PathVariable("piattaformaId") Long piattaformaId) {
		Videogioco videogioco = videogiocoService.getVideogiocoById(videogiocoId);
		Piattaforma piattaforma = piattaformaService.getPiattaformaById(piattaformaId);
		if (videogioco.getPiattaforme().contains(piattaforma)) {
		    videogioco.getPiattaforme().remove(piattaforma);
		   this.videogiocoService.save(videogioco);
		}
		return "redirect:/admin/modificaVideogioco/" + videogiocoId;
	}
	
	// POSSO IMPOSTARE SOLO UNO SVILUPPATORE ALLA VOLTA 
	@PostMapping("/admin/{videogiocoId}/modificaSviluppatore")
	public String modificaSviluppatore(@PathVariable("videogiocoId") Long videogiocoId, @RequestParam Long sviluppatoreId) {
		Videogioco videogioco = videogiocoService.getVideogiocoById(videogiocoId);
		/* Se seleziono come sviluppatore "nessuno" con id fittizio -1 */
		if (sviluppatoreId == -1) { 
			videogioco.setSviluppatore(null);
			videogiocoService.save(videogioco);
		}
		/* Se seleziono uno sviluppatore già presente nel database */
		else if (videogioco != null) {
			Sviluppatore sviluppatore = sviluppatoreService.getSviluppatoreById(sviluppatoreId);
			videogioco.setSviluppatore(sviluppatore); 
			videogiocoService.save(videogioco);
		}
		return "redirect:/admin/modificaVideogioco/" + videogiocoId;
	}

    // MODIFICA ANNO, GENERE E DESCRIZIONE ESISTENTI
    @PostMapping("/admin/{videogiocoId}/modifica")
    public String modificaVideogioco(@PathVariable Long videogiocoId, @ModelAttribute("videogioco") Videogioco videogioco) {
               Videogioco videogiocoEsistente = videogiocoService.getVideogiocoById(videogiocoId);

        // Aggiorna i dati
        videogiocoEsistente.setAnno(videogioco.getAnno());
        videogiocoEsistente.setGenere(videogioco.getGenere());
        videogiocoEsistente.setDescrizione(videogioco.getDescrizione());

        // Salva le modifiche nel database
        videogiocoService.save(videogiocoEsistente);

        return "redirect:/videogiochi/" + videogiocoId;
    }
	
	
	// VISUALIZZA LA FORM PER L'AGGIUNTA DEI VIDEOGIOCHI 
    @GetMapping("/admin/aggiungiVideogioco")
    public String aggiungiVideogioco(Model model) {
    	model.addAttribute("videogioco", new Videogioco());
        return "admin/aggiungiVideogioco.html";
    }
    
    //AGGIUNGI NUOVO VIDEOGIOCO
    @PostMapping("/admin/aggiungiVideogioco")
    public String newVideogioco(@Valid @ModelAttribute("videogioco") Videogioco videogioco, BindingResult bindingResult,
    		@RequestParam(value="immagineFile" ,required = false) MultipartFile image, Model model) throws IOException {
    	this.videogiocoValidator.validate(videogioco, bindingResult); // Validazione

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
    		    videogioco.setImage("/uploads/" + image.getOriginalFilename());
    		} else {
    		    videogioco.setImage("/defaultcover.png");
    		}
    		//////////////////////////////////////////////


    		this.videogiocoService.save(videogioco);       
    		model.addAttribute("videogioco", videogioco); //  Ricarica il videogioco inserito 
    		return "redirect:/videogiochi/" + videogioco.getId();     
    	} else {       
    		return "admin/aggiungiVideogioco";     
    	}
    }
	


}
