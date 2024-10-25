package com.advocacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

	Processo findByNumeroProcesso(String numeroProcesso);

}
