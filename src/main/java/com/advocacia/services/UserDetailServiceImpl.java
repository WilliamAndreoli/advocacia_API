package com.advocacia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Usuario;
import com.advocacia.exceptions.LoginErrorException;
import com.advocacia.repositories.UsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);
		
		if (usuario != null) {
			return UserDetailsImpl.build(usuario);
		} else {
			throw new LoginErrorException("Credências Inválidas, tente novamente");
		}
		
		
	}

}
