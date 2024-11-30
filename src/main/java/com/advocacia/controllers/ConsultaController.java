package com.advocacia.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	        Cliente cliente = consulta.getCliente(); 
	        ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome());
	        return new ConsultaDTO(consulta.getId(), consulta.getValor(), consulta.getData_marcada(),
	                consulta.getData_realizada(), consulta.getPagamento(), consulta.getData_pagamento(),
	                consulta.getMeio_pagamento(), consulta.getResumo(), consulta.getStatus(), clienteDTO);
	    }).collect(Collectors.toList());
	    
	    return ResponseEntity.ok(consultaDTOs);
	}
	
	 @GetMapping("/pageable")
	    public Page<ConsultaDTO> getAllConsultasAtivos(Pageable pageable) {
	        Page<Consulta> consultas = consultaService.findAllPageable(pageable);
	        
	        List<ConsultaDTO> consultaDTOList = consultas.getContent().stream()
	            .map(consulta -> {
	                Cliente cliente = consulta.getCliente();
	                ClienteDTO clienteDTO = new ClienteDTO(
	                    cliente.getId(), 
	                    cliente.getNome()
	                );
	                
	                return new ConsultaDTO(
	                    consulta.getId(),
	                    consulta.getValor(),
	                    consulta.getData_marcada(),
	                    consulta.getData_realizada(),
	                    consulta.getPagamento(),
	                    consulta.getData_pagamento(),
	                    consulta.getMeio_pagamento(),
	                    consulta.getResumo(),
	                    consulta.getStatus(),
	                    clienteDTO
	                );
	            })
	            .collect(Collectors.toList());
	            
	        return new PageImpl<>(
	            consultaDTOList,
	            pageable,
	            consultas.getTotalElements()
	        );
	    }
	
	
	@GetMapping("/cliente/{nomecliente}")
	public ResponseEntity<List<ConsultaDTO>> findAllConsultaCliente(@PathVariable String nomecliente) {
		Optional<Cliente> verificaCliente = clienteService.findByNome(nomecliente);
		
		if (!verificaCliente.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
		
		Cliente existingCliente = verificaCliente.get();
		
		List<Consulta> consultas = consultaService.findAllConsultaCliente(existingCliente.getId());
	    List<ConsultaDTO> consultaDTOs = consultas.stream().map(consulta -> {
	        Cliente cliente = consulta.getCliente(); // Supondo que você tenha um método getCliente()
	        ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome());
	        return new ConsultaDTO(consulta.getId(), consulta.getValor(), consulta.getData_marcada(),
	                consulta.getData_realizada(), consulta.getPagamento(), consulta.getData_pagamento(),
	                consulta.getMeio_pagamento(), consulta.getResumo(), consulta.getStatus(), clienteDTO);
	    }).collect(Collectors.toList());
	    
	    return ResponseEntity.ok(consultaDTOs);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ConsultaDTO> getConsultaById(@PathVariable Integer id) {
	    Optional<Consulta> consultaOpt = consultaService.findById(id);

	    if (consultaOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    Consulta consulta = consultaOpt.get();
	    ClienteDTO clienteDTO = new ClienteDTO(consulta.getCliente()); // Conversão do Cliente para ClienteDTO

	    ConsultaDTO consultaDTO = new ConsultaDTO(
	        consulta.getId(),
	        consulta.getValor(),
	        consulta.getData_marcada(),
	        consulta.getData_realizada(),
	        consulta.getPagamento(),
	        consulta.getData_pagamento(),
	        consulta.getMeio_pagamento(),
	        consulta.getResumo(),
	        consulta.getStatus(),
	        clienteDTO // Passa o ClienteDTO no lugar do Cliente
	    );

	    return ResponseEntity.ok(consultaDTO);
	}
	
	@PostMapping
	public ResponseEntity<ConsultaDTO> createConsulta(@RequestBody Consulta consulta) {
		
		Cliente cliente = clienteService.findByCpf(consulta.getCliente().getCpf());

		if (cliente == null) {
			throw new ConsultaErrorException("Cliente não encontrado com o CPF: " + consulta.getCliente().getCpf());
		}
		
		consulta.setCliente(cliente);
		
		Consulta novaConsulta = consultaService.save(consulta);

		ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome());
		
        ConsultaDTO consultaDto = new ConsultaDTO(novaConsulta.getId(), novaConsulta.getValor(), novaConsulta.getData_marcada(), novaConsulta.getData_realizada(), novaConsulta.getPagamento(), novaConsulta.getData_pagamento(), 
				novaConsulta.getMeio_pagamento(), novaConsulta.getResumo(), novaConsulta.getStatus(), clienteDTO);
        
		return ResponseEntity.ok(consultaDto);
    }
	
	@PutMapping("/id/{id}")
	public ResponseEntity<ConsultaDTO> alteraConsulta(@PathVariable Integer id,@RequestBody Consulta consultaDetails) {
		
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
		
		Cliente cliente = clienteService.findByCpf(consultaDetails.getCliente().getCpf());
		
		if (cliente == null) {
			return ResponseEntity.notFound().build();
		} 
	
		existingConsulta.setCliente(cliente);
		
		Consulta updatedConsulta = consultaService.save(existingConsulta);
		
		ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome());
		
		ConsultaDTO consultaDTO = new ConsultaDTO(updatedConsulta.getId(), updatedConsulta.getValor(), updatedConsulta.getData_marcada(), updatedConsulta.getData_realizada(), updatedConsulta.getPagamento(), updatedConsulta.getData_pagamento(), 
				updatedConsulta.getMeio_pagamento(), updatedConsulta.getResumo(), updatedConsulta.getStatus(), clienteDTO);
		
		return ResponseEntity.ok(consultaDTO);
	}
	
	@DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteConsultaById(@PathVariable Integer id) {
        Optional<Consulta> consulta = consultaService.findById(id);
        
        Consulta existingConsulta = consulta.get();
        
        if (existingConsulta == null) {
            return ResponseEntity.notFound().build();
        } else { 
        	consultaService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
	
}
