package com.advocacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Advogado;

public interface AdvogadoRepository extends JpaRepository<Advogado, Integer>  {

	void deleteByNumeroOrdem(String numeroOrdem);

	Advogado findByNumeroOrdem(String numeroOrdem);

	
	
}
