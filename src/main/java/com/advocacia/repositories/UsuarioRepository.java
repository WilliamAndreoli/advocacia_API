package com.advocacia.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.advocacia.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query("SELECT u FROM Usuario u WHERE u.status = 'ATIVO'")
	List<Usuario> findAll();
	
	List<Usuario> findAllByUsername(String username);

	@Query("SELECT u FROM Usuario u WHERE u.status = 'ATIVO'")
    Page<Usuario> findAllAtivos(Pageable pageable);
	
	void deleteByUsername(String username);

	Usuario findByUsername(String username);
	
}
