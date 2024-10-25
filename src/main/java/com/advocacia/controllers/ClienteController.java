package com.advocacia.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.dto.UsuarioDTO;
import com.advocacia.dto.UsuarioNoPassDTO;
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Status;
import com.advocacia.entities.Tipo_Usuario;
import com.advocacia.entities.Usuario;
import com.advocacia.exceptions.UsuarioErrorException;
import com.advocacia.services.ClienteService;
import com.advocacia.services.Tipo_UsuarioService;
import com.advocacia.services.UsuarioService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Tipo_UsuarioService tipo_UsuarioService;
	
	@GetMapping
	public List<Cliente> findAll() {
		return clienteService.findAll();
	}

	//Resolver retorno de cliente com todos os atributos
	@PostMapping
	public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }
	
	@PutMapping("/nome/{nome}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable String nome, @RequestBody Cliente clienteDetails) {
		
		Optional<Cliente> cliente = clienteService.findByNome(nome);
		
		if (cliente.get() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente existingCliente = cliente.get();
		
		existingCliente.setNome(clienteDetails.getNome());
		existingCliente.setEmail(clienteDetails.getEmail());
		existingCliente.setCpf(clienteDetails.getCpf());
		existingCliente.setRg(clienteDetails.getRg());
		existingCliente.setTelefone(clienteDetails.getTelefone());
		existingCliente.setEndereco(clienteDetails.getEndereco());
		existingCliente.setNome_pai(clienteDetails.getNome_pai());
		existingCliente.setNome_mae(clienteDetails.getNome_mae());
		existingCliente.setCtps(clienteDetails.getCtps());
		existingCliente.setCnh(clienteDetails.getCnh());
		existingCliente.setData_nascimento(clienteDetails.getData_nascimento());
		
		return ResponseEntity.ok(existingCliente);
		
	}
	
	@PutMapping("status/{nome}")
    public ResponseEntity<Cliente> alteraStatus(@PathVariable String nome, @RequestBody Cliente clienteDetails) {
    	Optional<Cliente> cliente = clienteService.findByNome(nome);
    	if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
    	
    	Cliente existingCliente = cliente.get();
    	
    	existingCliente.setStatus(clienteDetails.getStatus());
    	
        Cliente updatedCliente = clienteService.alteraStatus(existingCliente.getStatus(), nome);
        return ResponseEntity.ok(updatedCliente);
    }
	
	@PostMapping("/nome/{nome}")
	public ResponseEntity<Usuario> createUsuarioCliente(@PathVariable String nome) {
		
		Optional<Cliente> cliente = clienteService.findByNome(nome);
		
		if (cliente.get() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente existingCliente = cliente.get();
		
		String username = existingCliente.getEmail();
		
		String password = "clienteAdvocacia" + existingCliente.getCpf();
		
		System.out.println(password);
		
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(password);
		
		Tipo_Usuario tipo = tipo_UsuarioService.findByDescricao("CLIENTE");
		
		usuario.setTipo_Usuario(tipo);
		usuario.setStatus(Status.ATIVO);
		
		UsuarioDTO verificaUsuario = usuarioService.findByUsername(usuario.getUsername());
		
		if (verificaUsuario != null) {
			throw new UsuarioErrorException("Já existe um Usuário com esse Username " + usuario.getUsername());
		}
		
		Usuario savedUsuario = usuarioService.save(usuario);
		
		savedUsuario.setPassword(password);
		
		return ResponseEntity.ok(savedUsuario);
		
	}
	
}
