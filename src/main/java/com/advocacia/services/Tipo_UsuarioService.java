package com.advocacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Tipo_Usuario;
import com.advocacia.repositories.Tipo_UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class Tipo_UsuarioService {

	@Autowired
	private Tipo_UsuarioRepository tipo_usuarioRepository;
	
	public List<Tipo_Usuario> findAll() {
		return tipo_usuarioRepository.findAll();
	}
	
	public Tipo_Usuario save(Tipo_Usuario tipo_usuario) {
        return tipo_usuarioRepository.save(tipo_usuario);
    }

	//Deleta por nome
	@Transactional
    public void deleteByDescricao(String descricao) {
        tipo_usuarioRepository.deleteByDescricao(descricao);
    }

	//Busca por nome
	public Tipo_Usuario findByDescricao(String descricao) {
        return tipo_usuarioRepository.findByDescricao(descricao);
    }
	
	public Optional<Tipo_Usuario> findById(Integer id) {
		return tipo_usuarioRepository.findById(id);
	}
	
}
