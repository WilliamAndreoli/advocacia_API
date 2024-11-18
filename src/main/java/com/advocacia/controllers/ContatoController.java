package com.advocacia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contato")
@CrossOrigin("http://localhost:4200")
public class ContatoController {

	@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping
	public ResponseEntity<String> enviarEmail(@RequestBody Contato contato) {
		try {
			SimpleMailMessage mensagem = new SimpleMailMessage();
			mensagem.setTo(contato.getEmail());
			mensagem.setSubject("Confirmação de recebimento - Advocacia Andreoli");
			mensagem.setText("Olá " + contato.getNome() + ",\n\nRecebemos sua mensagem com o assunto: "
						+ contato.getAssunto() + ".\nNossa equipe entrará em contato em breve.\n\nAtenciosamente,\nAndreoli Advocacia.");
			mailSender.send(mensagem);
			
			System.out.println("Email enviado para: " + contato.getEmail());
			
			return ResponseEntity.ok("Email enviado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar mensagem.");
		}
	}
	
}

class Contato {
    private String nome;
    private String email;
    private String assunto;
    private String conteudo;
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

    
}