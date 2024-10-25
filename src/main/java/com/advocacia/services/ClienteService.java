package com.advocacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Cliente;
import com.advocacia.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Optional<Cliente> findById(Integer id) {
		return clienteRepository.findById(id);
	}
	
	public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
	
}
