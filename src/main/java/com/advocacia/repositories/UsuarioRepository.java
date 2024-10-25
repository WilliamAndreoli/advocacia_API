package com.advocacia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	List<Usuario> findAllByUsername(String username);

	void deleteByUsername(String username);

	Usuario findByUsername(String username);
	
}
