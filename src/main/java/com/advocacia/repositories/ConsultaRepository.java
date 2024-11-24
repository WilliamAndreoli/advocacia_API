package com.advocacia.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.entities.Consulta;
import com.advocacia.entities.Usuario;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

	@Query("SELECT c FROM Consulta c WHERE c.cliente.id = :clienteId")
    List<Consulta> findAllConsultaCliente(@Param("clienteId") Integer clienteId);
	
	Page<Consulta> findAll(Pageable pageable);
	
}
