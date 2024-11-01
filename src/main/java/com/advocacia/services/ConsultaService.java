package com.advocacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Consulta;
import com.advocacia.repositories.ConsultaRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	public List<Consulta> findAll() {
		return consultaRepository.findAll();
	}
	
	public Consulta save(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

	public Optional<Consulta> findById(Integer id) {
		return consultaRepository.findById(id);
	}

	public List<Consulta> findAllConsultaCliente(Integer idCliente) {
		return consultaRepository.findAllConsultaCliente(idCliente);
	}

	public void deleteById(Integer id) {
		consultaRepository.deleteById(id);
	}
	
	//Implementar findByData
	
}
