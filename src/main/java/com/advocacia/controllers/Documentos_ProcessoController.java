package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.entities.Documentos_Processo;
import com.advocacia.services.Documentos_ProcessoService;

@RestController
@RequestMapping("/documentosProcesso")
public class Documentos_ProcessoController {

	@Autowired
	private Documentos_ProcessoService doc_ProcessoService;
	
	@GetMapping
	public List<Documentos_Processo> findAll() {
		return doc_ProcessoService.findAll();
	}
	
	@GetMapping("/nomeArquivo/{nomeArquivo}")
	public Documentos_Processo findByNomeArquivo(String nomeArquivo) {
		return doc_ProcessoService.findByNomeArquivo(nomeArquivo);
	};
	
}
