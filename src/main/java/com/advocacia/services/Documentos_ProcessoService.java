package com.advocacia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Documentos_Processo;
import com.advocacia.repositories.Documentos_ProcessoRepository;

@Service
public class Documentos_ProcessoService {

	@Autowired
	private Documentos_ProcessoRepository doc_ProcessoRepository;
	
	public List<Documentos_Processo> findAll() {
		return doc_ProcessoRepository.findAll();
	}
	
	public Documentos_Processo findByNomeArquivo(String nomeArquivo) {
		return doc_ProcessoRepository.findByNomeArquivo(nomeArquivo);
	};
	
}
