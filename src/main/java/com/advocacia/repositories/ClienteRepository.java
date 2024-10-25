package com.advocacia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Optional<Cliente> findById(Integer id);

	Optional<Cliente> findByNome(String nome);
	
}
