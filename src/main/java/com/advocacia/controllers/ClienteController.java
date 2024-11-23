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

import com.advocacia.dto.ClienteResponseDTO;
import com.advocacia.dto.UsuarioDTO;
import com.advocacia.dto.UsuarioNoPassDTO;
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
@CrossOrigin("http://localhost:4200")
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
	
	@GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> findByCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.findByCpf(cpf);
        ClienteResponseDTO clienteDTO = convertToDTO(cliente);
        return ResponseEntity.ok(clienteDTO);
    }
	
	@GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> findByEmail(@PathVariable String email) {
        Cliente cliente = clienteService.findByEmail(email);
        
        if(cliente == null) {
        	return ResponseEntity.notFound().build();
        }
        
        System.out.println(cliente);
        
        ClienteResponseDTO clienteDTO = convertToDTO(cliente);
        return ResponseEntity.ok(clienteDTO);
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
		
		System.out.println("nome:" + nome);
		
		Optional<Cliente> cliente = clienteService.findByNome(nome);
		
		if (cliente.get() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente existingCliente = cliente.get();
		
		String name = existingCliente.getNome();
		
		String username = existingCliente.getEmail();
		
		String password = "clienteAdvocacia" + existingCliente.getCpf();
		
		//System.out.println(password);
		
		Usuario usuario = new Usuario();
		usuario.setName(name);
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
		
		existingCliente.setUsuario(savedUsuario);
		
		System.out.println(existingCliente.getUsuario());
		
		Cliente updatedCliente = clienteService.updateCliente(existingCliente);
		
		return ResponseEntity.ok(savedUsuario);
		
	}
	
	private ClienteResponseDTO convertToDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        // Copiar propriedades básicas
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setCpf(cliente.getCpf());
        dto.setRg(cliente.getRg());
        dto.setTelefone(cliente.getTelefone());
        dto.setEndereco(cliente.getEndereco());
        dto.setNome_pai(cliente.getNome_pai());
        dto.setNome_mae(cliente.getNome_mae());
        dto.setCtps(cliente.getCtps());
        dto.setCnh(cliente.getCnh());
        dto.setData_nascimento(cliente.getData_nascimento());
        dto.setStatus(cliente.getStatus());

        // Converter Usuario para UsuarioNoPassDTO
        if (cliente.getUsuario() != null) {
            UsuarioNoPassDTO usuarioDTO = new UsuarioNoPassDTO();
            Usuario usuario = cliente.getUsuario();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setUsername(usuario.getUsername());
            usuarioDTO.setName(usuario.getName());
            usuarioDTO.setStatus(usuario.getStatus());
            usuarioDTO.setTipoUsuario(usuario.getTipo_Usuario());
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }
	
}
