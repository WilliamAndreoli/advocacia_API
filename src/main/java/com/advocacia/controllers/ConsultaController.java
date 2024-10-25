package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.entities.Consulta;
import com.advocacia.exceptions.ConsultaErrorException;
import com.advocacia.services.ConsultaService;

@RestController
@RequestMapping("/consultas")
@CrossOrigin
public class ConsultaController {

	@Autowired
	private ConsultaService consultaService;
	
	@GetMapping
	public List<Consulta> findAll() {
		return consultaService.findAll();
	}
	
	@PostMapping
	public Consulta createConsulta(@RequestBody Consulta consulta) {
		System.out.println(consulta);
		System.out.println(consulta.getPagamento());
		if (consulta.getValor() != null) {
			return consultaService.save(consulta);
		} else {
			throw new ConsultaErrorException("Valor não pode ser nulo");
		}
		
        
    }
	
}
