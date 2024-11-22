package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.converters.UsuarioDTOConverter;
import com.advocacia.dto.UsuarioDTO;
import com.advocacia.dto.UsuarioNoPassDTO;
import com.advocacia.entities.Status;
import com.advocacia.exceptions.UsuarioErrorException;
import com.advocacia.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDTOConverter usuarioDTOConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public List<UsuarioNoPassDTO> getAllUsuarios() {
        List<UsuarioNoPassDTO> usuariosDto = usuarioService.findAll();
        return usuariosDto;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioNoPassDTO> findById(@PathVariable Integer id) {
        UsuarioNoPassDTO usuarioDto = usuarioService.findById(id);
        if (usuarioDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDto);
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioNoPassDTO> findByUsername(@PathVariable String username) {
        UsuarioNoPassDTO usuarioDto = usuarioService.findByUsernameNoPass(username);
        if (usuarioDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDto);
    }

    @PostMapping
    public ResponseEntity<UsuarioNoPassDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        if(usuarioDTO.getStatus() == null) {
        	usuarioDTO.setStatus(Status.ATIVO);
        }
        
        UsuarioNoPassDTO existingUsuario = usuarioService.findByUsernameNoPass(usuarioDTO.getUsername());
        
        if(existingUsuario != null && existingUsuario.getUsername() != null) {
        	throw new UsuarioErrorException("Já existe um usuário com esse e-mail");
        }
    	
    	UsuarioNoPassDTO savedUsuario = usuarioService.save(usuarioDTO);
        return ResponseEntity.ok(savedUsuario);
    }

    @PutMapping("username/{username}")
    public ResponseEntity<UsuarioNoPassDTO> updateUsuario(@PathVariable String username, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioService.findByUsername(username);
 
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        if (usuarioDTO.getUsername() == null) {
        	usuario.setUsername(username);
        } else {
        	usuario.setUsername(usuarioDTO.getUsername());	
        }
        
        if (usuarioDTO.getName() == null) {
        	usuario.setName(usuario.getName());
        } else {
        	usuario.setName(usuarioDTO.getName());
        }
        
       if (usuarioDTO.getPassword() == null) {
    		usuario.setPassword(usuario.getPassword());   
       } else {
    	   usuario.setPassword(usuarioDTO.getPassword());   
       }
       
        usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        UsuarioNoPassDTO updatedUsuario = usuarioService.update(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }
    
    @PutMapping("status/{username}")
    public ResponseEntity<UsuarioNoPassDTO> alteraStatus(@PathVariable String username, @RequestBody UsuarioDTO usuarioDTO) {
    	UsuarioDTO usuario = usuarioService.findByUsername(username);
    	if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
    	
    	usuario.setStatus(usuarioDTO.getStatus());
    	
        UsuarioNoPassDTO updatedUsuario = usuarioService.alteraStatus(usuario.getStatus(), username);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> deleteUsuarioByUsername(@PathVariable String username) {
        UsuarioDTO usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
