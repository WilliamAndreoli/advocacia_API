package com.advocacia.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.dto.ClienteDTO;
import com.advocacia.dto.ConsultaDTO;
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Consulta;
import com.advocacia.services.ClienteService;
import com.advocacia.services.ConsultaService;

@RestController
@RequestMapping("/consultas")
@CrossOrigin
public class ConsultaController {

	@Autowired
	private ConsultaService consultaService;
	
	@Autowired
	private ClienteService clienteService;
	
	//Transformar em ClienteDTO
	@GetMapping
	public ResponseEntity<List<ConsultaDTO>> findAll() {
		List<Consulta> consultas = consultaService.findAll();
	    List<ConsultaDTO> consultaDTOs = consultas.stream().map(consulta -> {
	        Cliente cliente = consulta.getCliente(); // Supondo que você tenha um método getCliente()
	        ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome());
	        return new ConsultaDTO(consulta.getId(), consulta.getValor(), consulta.getData_marcada(),
	                consulta.getData_realizada(), consulta.getPagamento(), consulta.getData_pagamento(),
	                consulta.getMeio_pagamento(), consulta.getResumo(), clienteDTO);
	    }).collect(Collectors.toList());
	    
	    return ResponseEntity.ok(consultaDTOs);
	}
	
	@PostMapping
	public ResponseEntity<ConsultaDTO> createConsulta(@RequestBody Consulta consulta) {
		Consulta novaConsulta = consultaService.save(consulta);

		Optional<Cliente> cliente = clienteService.findById(novaConsulta.getCliente().getId());
		Cliente existingCliente = cliente.get();
		existingCliente.setNome(existingCliente.getNome());
		ClienteDTO clienteDTO = new ClienteDTO(existingCliente.getId(), existingCliente.getNome());
		
        ConsultaDTO consultaDto = new ConsultaDTO(novaConsulta.getId(), novaConsulta.getValor(), novaConsulta.getData_marcada(), novaConsulta.getData_realizada(), novaConsulta.getPagamento(), novaConsulta.getData_pagamento(), 
				novaConsulta.getMeio_pagamento(), novaConsulta.getResumo(), clienteDTO);
        
		return ResponseEntity.ok(consultaDto);
    }
	
}
