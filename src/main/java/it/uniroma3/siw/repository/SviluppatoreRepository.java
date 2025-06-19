package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Sviluppatore;

public interface SviluppatoreRepository extends CrudRepository<Sviluppatore, Long> {

	boolean existsByNome(String nome);

}
