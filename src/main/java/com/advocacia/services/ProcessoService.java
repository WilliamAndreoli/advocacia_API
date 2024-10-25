package com.advocacia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Processo;
import com.advocacia.repositories.ProcessoRepository;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;
	
	public List<Processo> findAll() {
		return processoRepository.findAll();
	}
	
	public Processo findByNumeroProcesso(String numeroProcesso) {
		return processoRepository.findByNumeroProcesso(numeroProcesso);
	}
	
	public Processo save(Processo processo) {
		return processoRepository.save(processo);
	}
	
}