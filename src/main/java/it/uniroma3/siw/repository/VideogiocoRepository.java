package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Videogioco;

public interface VideogiocoRepository extends CrudRepository<Videogioco, Long> {

  public List<Videogioco> findByTitoloContainingIgnoreCase(String titolo);
  
  public List<Videogioco> findByAnno(Integer anno);

}
