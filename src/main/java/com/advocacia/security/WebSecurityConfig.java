package com.advocacia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.advocacia.security.jwt.AuthEntryPointJwt;
import com.advocacia.security.jwt.AuthFilterToken;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthFilterToken authFilterToken() {
		return new AuthFilterToken();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.cors(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable())
			.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
							.requestMatchers(HttpMethod.POST,"/contato/**").permitAll()
							.requestMatchers(HttpMethod.GET,"/usuarios/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll()
							.requestMatchers(HttpMethod.PUT,"/usuarios/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE,"/usuarios/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.GET,"/consultas/**").hasAnyRole("ADVOGADO", "ADMIN", "CLIENTE")
							.requestMatchers(HttpMethod.POST,"/consultas/**").hasAnyRole("ADVOGADO", "ADMIN")
							.requestMatchers(HttpMethod.PUT,"/consultas/**").hasAnyRole("ADVOGADO", "ADMIN")
							.requestMatchers(HttpMethod.GET,"/clientes/**").hasAnyRole("ADVOGADO", "ADMIN", "CLIENTE")
							.requestMatchers(HttpMethod.POST,"/clientes/**").hasAnyRole("ADVOGADO", "ADMIN")
							.requestMatchers(HttpMethod.PUT,"/clientes/**").hasAnyRole("ADVOGADO", "ADMIN")
							.requestMatchers(HttpMethod.GET,"/advogados/**").hasAnyRole("ADVOGADO", "ADMIN")
							.requestMatchers(HttpMethod.POST,"/advogados/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.PUT,"/advogados/**").hasRole("ADMIN")	
							.requestMatchers(HttpMethod.GET,"/processos/**").hasAnyRole("ADVOGADO", "ADMIN", "CLIENTE")
							.requestMatchers(HttpMethod.POST,"/processos/**").hasAnyRole("ADMIN", "ADVOGADO")
							.requestMatchers(HttpMethod.PUT,"/processos/**").hasAnyRole("ADMIN", "ADVOGADO")	
							.requestMatchers(HttpMethod.GET,"/documentosProcesso/**").hasAnyRole("ADVOGADO", "ADMIN", "CLIENTE")
							.requestMatchers(HttpMethod.POST,"/documentosProcesso/**").hasAnyRole("ADVOGADO" ,"ADMIN")
							.requestMatchers(HttpMethod.PUT,"/documentosProcesso/**").hasAnyRole("ADVOGADO","ADMIN")	
							.anyRequest().authenticated());
		
		http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
}
