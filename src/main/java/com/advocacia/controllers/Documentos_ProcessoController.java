package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.advocacia.entities.Documentos_Processo;
import com.advocacia.entities.Processo;
import com.advocacia.exceptions.Documentos_ProcessoErrorException;
import com.advocacia.services.Documentos_ProcessoService;
import com.advocacia.services.ProcessoService;

@RestController
@RequestMapping("/documentosProcesso")
public class Documentos_ProcessoController {

	@Autowired
	private Documentos_ProcessoService doc_ProcessoService;
	
	@Autowired
	private ProcessoService processoService;
	
	@GetMapping
	public List<Documentos_Processo> findAll() {
		return doc_ProcessoService.findAll();
	}
	
	@GetMapping("/nomeArquivo/{nomeArquivo}")
	public Documentos_Processo findByNomeArquivo(String nomeArquivo) {
		return doc_ProcessoService.findByNomeArquivo(nomeArquivo);
	};
	
	@PostMapping("/processo/{numeroProcesso}/documento/upload")
	public ResponseEntity<Documentos_Processo> uploadDocumento(
	        @PathVariable String numeroProcesso,
	        @RequestParam("file") MultipartFile file) {
		Documentos_Processo novoDoc = doc_ProcessoService.uploadArquivo(file, numeroProcesso);
		
		return ResponseEntity.ok(novoDoc);
	}
}
