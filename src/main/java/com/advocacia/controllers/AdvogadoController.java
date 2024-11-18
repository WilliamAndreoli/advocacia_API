package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.dto.UsuarioDTO;
import com.advocacia.entities.Advogado;
import com.advocacia.entities.Status;
import com.advocacia.entities.Tipo_Usuario;
import com.advocacia.entities.Usuario;
import com.advocacia.exceptions.AdvogadoErrorException;
import com.advocacia.exceptions.UsuarioErrorException;
import com.advocacia.services.AdvogadoService;
import com.advocacia.services.Tipo_UsuarioService;
import com.advocacia.services.UsuarioService;

@RestController
@RequestMapping("/advogados")
public class AdvogadoController {

	@Autowired
	private AdvogadoService advogadoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Tipo_UsuarioService tipo_UsuarioService;
	
	@GetMapping
	public List<Advogado> findAll() {
		return advogadoService.findAll();
	}
	
	@PostMapping
	public Advogado save(@RequestBody Advogado advogadoDetails) {
		return advogadoService.save(advogadoDetails);
	}
	
	@PutMapping("/status/{numeroOrdem}")
	public Advogado alteraStatus(@PathVariable String numeroOrdem, @RequestBody Advogado advogadoDetails) {
		return advogadoService.alteraStatus(advogadoDetails.getStatus(), numeroOrdem);
	}
	
	@PutMapping("/numeroOrdem/{numeroOrdem}")
	public Advogado updateAdvogado(@PathVariable String numeroOrdem, @RequestBody Advogado advogadoDetails) {
		Advogado verificaAdvogado = advogadoService.findByNumeroOrdem(numeroOrdem);
		
		if (verificaAdvogado == null) {
			throw new AdvogadoErrorException("Nenhum Advogado encontrado com o Número da Ordem: " + numeroOrdem);
		}
		
		Advogado updateAdvogado = new Advogado();
		updateAdvogado.setId(advogadoDetails.getId());
		updateAdvogado.setNome(advogadoDetails.getNome());
		updateAdvogado.setEmail(advogadoDetails.getEmail());
		updateAdvogado.setNumeroOrdem(advogadoDetails.getNumeroOrdem());
		updateAdvogado.setTelefone(advogadoDetails.getTelefone());
		updateAdvogado.setStatus(advogadoDetails.getStatus());
		
		return advogadoService.save(updateAdvogado);
	}
	
	@PostMapping("/numeroOrdem/{numeroOrdem}")
	public ResponseEntity<Usuario> createUsuarioCliente(@PathVariable String numeroOrdem) {
		
		Advogado advogado = advogadoService.findByNumeroOrdem(numeroOrdem);
		
		if (advogado == null) {
			throw new AdvogadoErrorException("Nenhum Advogado encontrado com o Número da Ordem: " + numeroOrdem);
		}
		
		String username = advogado.getEmail();
		
		String password = "advogadoAdvocacia" + advogado.getNumeroOrdem();
		
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(password);
		
		Tipo_Usuario tipo = tipo_UsuarioService.findByDescricao("ADVOGADO");
		
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
