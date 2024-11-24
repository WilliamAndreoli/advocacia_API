package com.advocacia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Processo> findAllPageable(Pageable pageable) {
        return processoRepository.findAll(pageable);
    }
	
	public Processo findByNumeroProcesso(String numeroProcesso) {
		return processoRepository.findByNumeroProcesso(numeroProcesso);
	}
	
	public List<Processo> findByClienteCpf(String cpf) {
		return processoRepository.findByClienteCpf(cpf);
	}
	
	public Page<Processo> findByAdvogadoNumeroOrdem(String numeroOrdem, Pageable pageable) {
		return processoRepository.findByAdvogadoNumeroOrdemPageable(numeroOrdem, pageable);
	}
	
	public Processo save(Processo processo) {
		return processoRepository.save(processo);
	}
	
}
