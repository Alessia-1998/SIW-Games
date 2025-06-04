package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Videogioco;

public interface VideogiocoRepository extends CrudRepository<Videogioco, Long> {

  List<Videogioco> findByTitoloContainingIgnoreCase(String titolo);
  List<Videogioco> findByAnno(int anno);

}
