package com.advocacia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.advocacia.dto.AcessDTO;
import com.advocacia.dto.AuthenticationDTO;
import com.advocacia.exceptions.LoginErrorException;
import com.advocacia.security.jwt.JwtUtils;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	public AcessDTO login(AuthenticationDTO authDTO) {
		
		try {
		
			//Cria mecanismode credencial para o spring 
			UsernamePasswordAuthenticationToken userAuth = 
					new UsernamePasswordAuthenticationToken(authDTO.getUserName(), authDTO.getPassword());
			
			//Prepara mecanismo para autenticação
			Authentication authentication = authenticationManager.authenticate(userAuth);
			
			//Busca usuario logado
			UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
		
			String token = jwtUtils.generateTokenFromUserDetailsImplementation(userAuthenticate);
			
			AcessDTO acessDto = new AcessDTO(token);
			
			return acessDto;
			
		} catch(BadCredentialsException e) {
			//TODO Implements error
			throw new LoginErrorException("Credenciais inválidas");
		}
	
	}
}
