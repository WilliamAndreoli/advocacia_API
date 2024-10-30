package com.advocacia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.entities.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

	Processo findByNumeroProcesso(String numeroProcesso);

	
	@Query("SELECT p FROM Processo p INNER JOIN p.cliente c WHERE c.cpf = :cpf")
	List<Processo> findByClienteCpf(@Param("cpf") String cpf);
	
}
