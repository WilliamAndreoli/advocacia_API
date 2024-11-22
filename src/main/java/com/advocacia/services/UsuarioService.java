package com.advocacia.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.advocacia.dto.UsuarioDTO;
import com.advocacia.dto.UsuarioNoPassDTO;
import com.advocacia.entities.Status;
import com.advocacia.entities.Tipo_Usuario;
import com.advocacia.entities.Usuario;
import com.advocacia.exceptions.UsuarioErrorException;
import com.advocacia.repositories.Tipo_UsuarioRepository;
import com.advocacia.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Tipo_UsuarioRepository tipoUsuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UsuarioNoPassDTO> findAll() {
		return usuarioRepository.findAllAtivos().stream().map(this::convertToNoPassDto).collect(Collectors.toList());
	}

	public UsuarioNoPassDTO save(UsuarioDTO usuarioDto) {
		Usuario usuario = convertToEntity(usuarioDto);

		Usuario existingUsuario = usuarioRepository.findByUsername(usuario.getUsername());

		if (existingUsuario != null) {
			throw new UsuarioErrorException("Já existe um Usuário cadastrado com esse e-mail");
		}

		// Verificando se o Tipo_Usuario já existe
		Tipo_Usuario tipoUsuario = usuario.getTipo_Usuario();
		if (tipoUsuario != null) {
			if (tipoUsuario.getDescricao() != null) {
				// Buscar o Tipo_usuario existente
				Tipo_Usuario existingTipoUsuario = tipoUsuarioRepository.findByDescricao(tipoUsuario.getDescricao());
				if (existingTipoUsuario != null) {
					// Se o Tipo_Usuario existe, associe-o ao Usuario
					usuario.setTipo_Usuario(existingTipoUsuario);
				} else {
					// Se o Tipo_Usuario não existe, salve o novo Tipo_Usuario
					tipoUsuario = tipoUsuarioRepository.save(tipoUsuario);
					usuario.setTipo_Usuario(tipoUsuario);
				}
			} else {
				throw new RuntimeException("Tipo Usuário nulo ou inexistente: " + tipoUsuario.getDescricao());
			}
		}
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		Usuario savedUsuario = usuarioRepository.save(usuario);
		return convertToNoPassDto(savedUsuario);
	}

	public Usuario save(Usuario usuario) {

		Usuario existingUsuario = usuarioRepository.findByUsername(usuario.getUsername());

		if (existingUsuario != null) {
			throw new UsuarioErrorException("Já existe um Usuário cadastrado com esse e-mail");
		}

		// Verificando se o Tipo_Usuario já existe
		Tipo_Usuario tipoUsuario = usuario.getTipo_Usuario();
		if (tipoUsuario != null) {
			if (tipoUsuario.getDescricao() != null) {
				// Buscar o Tipo_usuario existente
				Tipo_Usuario existingTipoUsuario = tipoUsuarioRepository.findByDescricao(tipoUsuario.getDescricao());
				if (existingTipoUsuario != null) {
					// Se o Tipo_Usuario existe, associe-o ao Usuario
					usuario.setTipo_Usuario(existingTipoUsuario);
				} else {
					// Se o Tipo_Usuario não existe, salve o novo Tipo_Usuario
					tipoUsuario = tipoUsuarioRepository.save(tipoUsuario);
					usuario.setTipo_Usuario(tipoUsuario);
				}
			} else {
				throw new RuntimeException("Tipo Usuário nulo ou inexistente: " + tipoUsuario.getDescricao());
			}
		}

		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		Usuario savedUsuario = usuarioRepository.save(usuario);
		return savedUsuario;
	}

	public UsuarioNoPassDTO update(UsuarioDTO usuarioDto) {
		Usuario usuario = convertToEntity(usuarioDto);

		Usuario existingUsuario = usuarioRepository.findByUsername(usuario.getUsername());

//		if (existingUsuario != null) {
//			throw new UsuarioErrorException("Já existe um Usuário cadastrado com esse e-mail");
//		}

		// Verificando se o Tipo_Usuario já existe
		Tipo_Usuario tipoUsuario = usuario.getTipo_Usuario();
		if (tipoUsuario != null) {
			if (tipoUsuario.getDescricao() != null) {
				// Buscar o Tipo_usuario existente
				Tipo_Usuario existingTipoUsuario = tipoUsuarioRepository.findByDescricao(tipoUsuario.getDescricao());
				if (existingTipoUsuario != null) {
					// Se o Tipo_Usuario existe, associe-o ao Usuario
					usuario.setTipo_Usuario(existingTipoUsuario);
				} else {
					// Se o Tipo_Usuario não existe, salve o novo Tipo_Usuario
					tipoUsuario = tipoUsuarioRepository.save(tipoUsuario);
					usuario.setTipo_Usuario(tipoUsuario);
				}
			} else {
				throw new RuntimeException("Tipo Usuário nulo ou inexistente: " + tipoUsuario.getDescricao());
			}
		}

		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		Usuario savedUsuario = usuarioRepository.save(usuario);
		return convertToNoPassDto(savedUsuario);
	}

	@Transactional
	public void deleteById(Integer id) {
		usuarioRepository.deleteById(id);
	}

	@Transactional
	public void deleteByUsername(String username) {
		usuarioRepository.deleteByUsername(username);
	}

	public List<UsuarioDTO> findByAllUsername(String username) {
		return usuarioRepository.findAllByUsername(username).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public UsuarioDTO findByUsername(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario != null) {
			return convertToDto(usuario);
		}
		// Lançar uma exceção ou retornar null se o usuário não for encontrado
		return null;
	}

	public UsuarioNoPassDTO findByUsernameNoPass(String username) {
		System.out.println(username);
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario != null) {
			return convertToNoPassDto(usuario);
		}
		// Lançar uma exceção ou retornar null se o usuário não for encontrado
		return null;
	}

	public UsuarioNoPassDTO findById(int id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		return usuario != null ? convertToNoPassDto(usuario) : null;
	}

	public UsuarioNoPassDTO alteraStatus(Status status, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);

		usuario.setStatus(status);

		Usuario savedUsuario = usuarioRepository.save(usuario);
		return convertToNoPassDto(savedUsuario);

	}

	// Métodos auxiliares para conversão
	private UsuarioDTO convertToDto(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getUsername(), usuario.getName(), usuario.getPassword(), usuario.getStatus(),
				usuario.getTipo_Usuario());
	}

	private UsuarioNoPassDTO convertToNoPassDto(Usuario usuario) {
		return new UsuarioNoPassDTO(usuario.getId(), usuario.getUsername(), usuario.getName(), usuario.getStatus(),
				usuario.getTipo_Usuario());
	}

	private Usuario convertToEntity(UsuarioDTO usuarioDto) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioDto.getId());
		usuario.setUsername(usuarioDto.getUsername());
		usuario.setName(usuarioDto.getName());
		usuario.setPassword(usuarioDto.getPassword());
		usuario.setStatus(usuarioDto.getStatus());
		usuario.setTipo_Usuario(tipoUsuarioRepository.findByDescricao(usuarioDto.getTipoUsuario().getDescricao()));
		return usuario;
	}

}