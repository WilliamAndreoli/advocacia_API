package com.advocacia.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.advocacia.exceptions.AdvogadoErrorException;
import com.advocacia.exceptions.ClienteErrorException;
import com.advocacia.exceptions.ConsultaErrorException;
import com.advocacia.exceptions.Documentos_ProcessoErrorException;
import com.advocacia.exceptions.JWTTokenException;
import com.advocacia.exceptions.LoginErrorException;
import com.advocacia.exceptions.ProcessoErrorException;
import com.advocacia.exceptions.UsuarioErrorException;
import com.advocacia.exceptions.UsuarioNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNotFound(UsuarioNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Usuário não encontrado");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(LoginErrorException.class)
    public ResponseEntity<Map<String, String>> handleLoginError(LoginErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Credências inválidas");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(JWTTokenException.class)
    public ResponseEntity<Map<String, String>> handleLoginError(JWTTokenException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro no token");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UsuarioErrorException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioError(UsuarioErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro no Usuário");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ConsultaErrorException.class)
    public ResponseEntity<Map<String, String>> handleConsultaError(ConsultaErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro na Consulta");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ClienteErrorException.class)
    public ResponseEntity<Map<String, String>> handleConsultaError(ClienteErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro no Cliente");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AdvogadoErrorException.class)
    public ResponseEntity<Map<String, String>> handleConsultaError(AdvogadoErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro no Advogado");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ProcessoErrorException.class)
    public ResponseEntity<Map<String, String>> handleConsultaError(ProcessoErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro no Processo");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Documentos_ProcessoErrorException.class)
    public ResponseEntity<Map<String, String>> handleConsultaError(Documentos_ProcessoErrorException ex) {
		Map<String, String> response = new HashMap<>();
	    response.put("error", "Erro no Documento");
	    response.put("message", ex.getMessage());

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
