package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.entities.Tipo_Usuario;
import com.advocacia.services.Tipo_UsuarioService;

@RestController
@RequestMapping("/tipo_usuarios")
@CrossOrigin
public class Tipo_UsuarioController {

	@Autowired
	private Tipo_UsuarioService tipo_usuarioService;

	@GetMapping
	public List<Tipo_Usuario> getAllTipo_Usuarioes() {
		return tipo_usuarioService.findAll();
	}
	
	// Buscar por descricao
    @GetMapping("/descricao/{descricao}")
    public Tipo_Usuario findByDescricao(@PathVariable String descricao) {
    	return tipo_usuarioService.findByDescricao(descricao);
    }
	
    @PostMapping
    public Tipo_Usuario createTipo_Usuario(@RequestBody Tipo_Usuario tipo_usuario) {
        return tipo_usuarioService.save(tipo_usuario);
    }
	
	@PutMapping("/{descricao}")
    public ResponseEntity<Tipo_Usuario> updateTipo_Usuario(@PathVariable String descricao, @RequestBody Tipo_Usuario tipo_usuarioDetails) {
        Tipo_Usuario tipo_usuario = tipo_usuarioService.findByDescricao(descricao);
        if (tipo_usuario == null) {
            return ResponseEntity.notFound().build();
        }
        tipo_usuario.setDescricao(tipo_usuarioDetails.getDescricao());
        return ResponseEntity.ok(tipo_usuarioService.save(tipo_usuario));
    }
	
	@DeleteMapping("/descricao/{descricao}")
    public ResponseEntity<Void> deleteTipo_UsuarioByDescricao(@PathVariable String descricao) {
        Tipo_Usuario tipo_usuario = tipo_usuarioService.findByDescricao(descricao);
        if (tipo_usuario == null) {
            return ResponseEntity.notFound().build();
        }
        tipo_usuarioService.deleteByDescricao(descricao);
        return ResponseEntity.noContent().build();
    }
    
}
