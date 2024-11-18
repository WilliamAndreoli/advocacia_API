package com.advocacia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advocacia.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

	@Query("SELECT c FROM Consulta c WHERE c.cliente.id = :clienteId")
    List<Consulta> findAllConsultaCliente(@Param("clienteId") Integer clienteId);

}
