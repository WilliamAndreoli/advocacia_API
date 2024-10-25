package com.advocacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
}
