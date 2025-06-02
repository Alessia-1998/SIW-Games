package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Videogioco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	private String titolo;
	
	@NotNull  // Campo obbligatorio, e compreso tra 1900 e 2025
	@Min(1900)
	@Max(2025)
	private Integer anno;  // anno di uscita
	
	private String descrizione;
	
	private String image; // percorso immagine
	
	private String genere;
	
	// Se elimino un videgioco, tolgo SOLO l'associazione con le piattaforme
	@ManyToMany (mappedBy = "videogiochi", cascade = CascadeType.REMOVE)
	private List<Piattaforma> piattaforme;
	
	@ManyToOne
	private Sviluppatore sviluppatore;
	
	
	// Getter e Setter

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public List<Piattaforma> getPiattaforme() {
		return piattaforme;
	}

	public void setPiattaforme(List<Piattaforma> piattaforme) {
		this.piattaforme = piattaforme;
	}

	public Sviluppatore getSviluppatore() {
		return sviluppatore;
	}

	public void setSviluppatore(Sviluppatore sviluppatore) {
		this.sviluppatore = sviluppatore;
	}

	
	// Equals e hashCode (un videogioco può avere un remake con lo stesso titolo, quindi verifico anche l'anno)

	@Override
	public int hashCode() {
		return Objects.hash(anno, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Videogioco other = (Videogioco) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(titolo, other.titolo);
	}	
	

}
