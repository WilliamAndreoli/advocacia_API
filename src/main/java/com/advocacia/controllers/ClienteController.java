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
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Status;
import com.advocacia.entities.Tipo_Usuario;
import com.advocacia.entities.Usuario;
import com.advocacia.exceptions.ClienteErrorException;
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
        
		Cliente verificaCpfCliente = clienteService.findByCpf(cliente.getCpf());
		
		Cliente verificaRgCliente = clienteService.findByRg(cliente.getRg());
		
		Cliente verificaEmailCliente = clienteService.findByEmail(cliente.getEmail());
		
		if(verificaCpfCliente != null) {
			throw new ClienteErrorException("Já existe um Cliente cadastrado com esse CPF " + verificaCpfCliente.getCpf());
		}
		
		if(verificaRgCliente != null) {
			throw new ClienteErrorException("Já existe um Cliente cadastrado com esse RG " + verificaRgCliente.getRg());
		}
		
		if(verificaEmailCliente != null) {
			throw new ClienteErrorException("Já existe um Cliente cadastrado com esse E-mail " + verificaEmailCliente.getEmail());
		}
		
		return clienteService.save(cliente);
    }
	
	@PutMapping("/cpf/{cpf}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable String cpf, @RequestBody Cliente clienteDetails) {
		
		Cliente cliente = clienteService.findByCpf(cpf);
		
		if (cliente == null) {
			throw new ClienteErrorException("Cliente não encontrado com esse CPF: " + cpf);
		}
		
		Cliente updateCliente = new Cliente();
		
		updateCliente.setId(cliente.getId());
		updateCliente.setNome(clienteDetails.getNome());
		updateCliente.setEmail(clienteDetails.getEmail());
		updateCliente.setCpf(clienteDetails.getCpf());
		updateCliente.setRg(clienteDetails.getRg());
		updateCliente.setTelefone(clienteDetails.getTelefone());
		updateCliente.setEndereco(clienteDetails.getEndereco());
		updateCliente.setNome_pai(clienteDetails.getNome_pai());
		updateCliente.setNome_mae(clienteDetails.getNome_mae());
		updateCliente.setCtps(clienteDetails.getCtps());
		updateCliente.setCnh(clienteDetails.getCnh());
		updateCliente.setData_nascimento(clienteDetails.getData_nascimento());
		
		clienteService.updateCliente(updateCliente);
		
		return ResponseEntity.ok(updateCliente);
		
	}
	
	@PutMapping("status/{cpf}")
    public ResponseEntity<Cliente> alteraStatus(@PathVariable String cpf, @RequestBody Cliente clienteDetails) {
    	Cliente cliente = clienteService.findByCpf(cpf);
    	if (cliente == null) {
            throw new ClienteErrorException("Cliente não encontrado com o CPF: " + cpf);
        }
    	
    	cliente.setStatus(clienteDetails.getStatus());
    	
        Cliente updatedCliente = clienteService.alteraStatus(cliente.getStatus(), cpf);
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
