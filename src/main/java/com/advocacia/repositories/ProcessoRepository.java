package com.advocacia.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.entities.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

	Processo findByNumeroProcesso(String numeroProcesso);

	
	@Query("SELECT p FROM Processo p INNER JOIN p.cliente c WHERE c.cpf = :cpf")
	List<Processo> findByClienteCpf(@Param("cpf") String cpf);
	
	@Query("SELECT p FROM Processo p INNER JOIN p.advogado a WHERE a.numeroOrdem = :numeroOrdem")
	List<Processo> findByAdvogadoNumeroOrdem(@Param("numeroOrdem") String numeroOrdem);
	
	@Query("SELECT p FROM Processo p INNER JOIN p.advogado a WHERE a.numeroOrdem = :numeroOrdem")
    Page<Processo> findByAdvogadoNumeroOrdemPageable(@Param("numeroOrdem") String numeroOrdem, Pageable pageable);
	
	Page<Processo> findAll(Pageable pageable);
	
}
