package com.advocacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

}
