package com.advocacia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Advogado;
import com.advocacia.entities.Status;
import com.advocacia.repositories.AdvogadoRepository;

import jakarta.transaction.Transactional;

@Service
public class AdvogadoService {

	@Autowired
	private AdvogadoRepository advogadoRepository;
	
	public List<Advogado> findAll() {
		return advogadoRepository.findAll();
	}
	
	public Advogado save(Advogado advogado) {
		return advogadoRepository.save(advogado);
	}
	
	public Advogado alteraStatus(Status status, String numeroOrdem) {
		Advogado advogado = advogadoRepository.findByNumeroOrdem(numeroOrdem);
	
		advogado.setStatus(status);
		
		Advogado savedAdvogado = advogadoRepository.save(advogado);
		return savedAdvogado;
	}
	
	@Transactional
	public void deleteByNumeroOrdem(String numeroOrdem) {
		advogadoRepository.deleteByNumeroOrdem(numeroOrdem);
	}

	public Advogado findByNumeroOrdem(String numeroOrdem) {
		return advogadoRepository.findByNumeroOrdem(numeroOrdem);
	}
	
}
