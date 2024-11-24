package com.advocacia.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.advocacia.entities.Cliente;
import com.advocacia.entities.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Query("SELECT c FROM Cliente c WHERE c.status = 'ATIVO'")
    Page<Cliente> findAllAtivos(Pageable pageable);
	
	Optional<Cliente> findById(Integer id);

	Optional<Cliente> findByNome(String nome);

	Cliente findByCpf(String cpf);

	Cliente findByRg(String rg);

	Cliente findByEmail(String email);
	
}
