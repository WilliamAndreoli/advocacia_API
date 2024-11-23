package com.advocacia.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.advocacia.exceptions.JWTTokenException;
import com.advocacia.services.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilterToken extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtil;
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			System.out.println("AuthFilterToken invoked for request: " + request.getRequestURI());
		try {
			
			String jwt = getToken(request);
			System.out.println("Token recebido: " + jwt);
			if(jwt != null && jwtUtil.validateJwtToken(jwt)) {
				
				System.out.println("Token JWT validado com sucesso: " + jwt);
				
				String username = jwtUtil.getUserNameToken(jwt);
				
				UserDetails userDetails = userDetailService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				 System.out.println("Usuário identificado no token: " + username);
				
			} else {
				throw new JWTTokenException("Token JWT inválido ou ausente");
			}
		} catch(JWTTokenException e) {
			System.out.println("Ocorreu um erro ao processar o token: " + e.getMessage());
		} finally {
			filterChain.doFilter(request, response);
		}
		
	}
	
	private String getToken(HttpServletRequest request) {
		String headerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
			return headerToken.replace("Bearer ", "");
		}
		return null;
	}

	
	
}
