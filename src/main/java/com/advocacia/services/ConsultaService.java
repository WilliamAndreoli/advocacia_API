package com.advocacia.services;

import java.util.List;

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
	
	//Implementar findByData
	
}
