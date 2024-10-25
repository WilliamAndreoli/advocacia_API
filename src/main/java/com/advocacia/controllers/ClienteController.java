package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.entities.Cliente;
import com.advocacia.services.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public List<Cliente> findAll() {
		return clienteService.findAll();
	}

	//Resolver retorno de cliente com todos os atributos
	@PostMapping
	public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }
	
}
