package com.advocacia.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.dto.ClienteDTO;
import com.advocacia.dto.ConsultaDTO;
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Consulta;
import com.advocacia.exceptions.ConsultaErrorException;
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
	
	@GetMapping("/cliente/{nomecliente}")
	public ResponseEntity<List<ConsultaDTO>> findAllConsultaCliente(@PathVariable String nomecliente) {
		Optional<Cliente> verificaCliente = clienteService.findByNome(nomecliente);
		
		if (verificaCliente.get() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente existingCliente = verificaCliente.get();
		
		List<Consulta> consultas = consultaService.findAllConsultaCliente(existingCliente.getId());
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
	
	@PutMapping("/id/{id}")
	public ResponseEntity<Consulta> alteraConsulta(@PathVariable Integer id,@RequestBody Consulta consultaDetails) {
		
		Optional<Consulta> consulta = consultaService.findById(id);
		
		if (consulta.get() == null) {
			return ResponseEntity.notFound().build();
		} 
		
		Consulta existingConsulta = consulta.get();
		
		existingConsulta.setValor(consultaDetails.getValor());
		existingConsulta.setData_marcada(consultaDetails.getData_marcada());
		existingConsulta.setData_realizada(consultaDetails.getData_realizada());
		existingConsulta.setData_pagamento(consultaDetails.getData_pagamento());
		existingConsulta.setMeio_pagamento(consultaDetails.getMeio_pagamento());
		existingConsulta.setResumo(consultaDetails.getResumo());
		
		Optional<Cliente> cliente = clienteService.findById(consultaDetails.getCliente().getId());
		
		if (cliente.get() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente existingCliente = cliente.get(); 
	
		existingConsulta.setCliente(existingCliente);
		
		return ResponseEntity.ok(consultaService.save(existingConsulta));
	}
	
	@DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteConsultaById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        
        Cliente existingCliente = cliente.get();
        
        if (existingCliente == null) {
            return ResponseEntity.notFound().build();
        } else { 
        	clienteService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
	
}
