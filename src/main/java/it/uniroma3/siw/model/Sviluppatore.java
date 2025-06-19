package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Sviluppatore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
    private String nome;
	
	@NotBlank
	private String sede;
	
	private String image; // percorso immagine
	
	@OneToMany (mappedBy = "sviluppatore")
	private List<Videogioco> videogiochi;
	
	
	// Getter e Setter

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Videogioco> getVideogiochi() {
		return videogiochi;
	}

	public void setVideogiochi(List<Videogioco> videogiochi) {
		this.videogiochi = videogiochi;
	}

	
	// Equals e hashCode
	
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sviluppatore other = (Sviluppatore) obj;
		return Objects.equals(nome, other.nome);
	}
	
	
	
}
