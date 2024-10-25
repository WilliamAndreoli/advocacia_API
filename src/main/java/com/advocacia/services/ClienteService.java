package com.advocacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.dto.UsuarioNoPassDTO;
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Status;
import com.advocacia.entities.Usuario;
import com.advocacia.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

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
	
	@Transactional
    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

	public Optional<Cliente> findByNome(String nome) {
		return clienteRepository.findByNome(nome);
	}

	public Cliente alteraStatus(Status status, String cpf) {
		Cliente cliente = clienteRepository.findByCpf(cpf);
		
		cliente.setStatus(status);
		
		Cliente savedCliente = clienteRepository.save(cliente);
		return savedCliente;
	}

	public Cliente findByCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	public Cliente findByRg(String rg) {
		return clienteRepository.findByRg(rg);
	}

	public Cliente findByEmail(String email) {
		return clienteRepository.findByEmail(email);
	}

	public Cliente updateCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
}
