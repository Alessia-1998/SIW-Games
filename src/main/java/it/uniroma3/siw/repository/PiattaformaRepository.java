package it.uniroma3.siw.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Piattaforma;

public interface PiattaformaRepository extends CrudRepository<Piattaforma, Long> {

	boolean existsByNomeAndDataDiRilascio(String nome, LocalDate dataDiRilascio);

}
