package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.entities.Advogado;
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Processo;
import com.advocacia.exceptions.ProcessoErrorException;
import com.advocacia.services.AdvogadoService;
import com.advocacia.services.ClienteService;
import com.advocacia.services.ProcessoService;

@RestController
@RequestMapping("/processos")
@CrossOrigin("http://localhost:4200")
public class ProcessoController {

	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private AdvogadoService advogadoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public List<Processo> findAll() {
		return processoService.findAll();
	}
	
	@GetMapping("/numeroProcesso/{numeroProcesso}")
	public Processo findByNumeroProcesso(@PathVariable String numeroProcesso) {
		return processoService.findByNumeroProcesso(numeroProcesso);
	}
	
	@GetMapping("/cliente/{cpf}")
	public ResponseEntity<List<Processo>> findByClienteCpf(@PathVariable String cpf) {
		List<Processo> processos = processoService.findByClienteCpf(cpf);
		return ResponseEntity.ok(processos);
	}
	
	@GetMapping("/advogado/{numeroOrdem}")
	public Page<Processo> findByAdvogadoPageable(@PathVariable String numeroOrdem, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page,  size);
		
		Page<Processo> processos = processoService.findByAdvogadoNumeroOrdem(numeroOrdem, pageable);
		
		return processos;
	}
	
	@PostMapping
	public Processo createProcesso(@RequestBody Processo processoDetails) {
		
		Advogado advogado = advogadoService.findByNumeroOrdem(processoDetails.getAdvogado().getNumeroOrdem());
		
		if (advogado == null) {
			throw new ProcessoErrorException("Nenhum Advogado encontrado com esse Número da Ordem: " + processoDetails.getAdvogado().getNumeroOrdem());
		}
		
		Cliente cliente = clienteService.findByCpf(processoDetails.getCliente().getCpf());
		
		if (cliente == null) {
			throw new ProcessoErrorException("Nenhum Cliente encontrado com esse CPF: " + processoDetails.getCliente().getCpf());
		}
		
		//Fazer docs depois
		
		processoDetails.setAdvogado(advogado);
		processoDetails.setCliente(cliente);
		
		return processoService.save(processoDetails);
	}
	
	@PutMapping("/numeroProcesso/{numeroProcesso}")
	public Processo updateProcesso(@PathVariable String numeroProcesso, @RequestBody Processo processoDetails) {
		Processo processo = processoService.findByNumeroProcesso(numeroProcesso);
		
		if(processo == null) {
			throw new ProcessoErrorException("Processo não encontrado com o numero: " + numeroProcesso);
		}
		
		Processo updateProcesso = new Processo();
		
		updateProcesso.setId(processo.getId());
		
		if(processoDetails.getNumeroProcesso() == null) {
			updateProcesso.setNumero_processo(processo.getNumeroProcesso());
		} else {
			updateProcesso.setNumero_processo(processoDetails.getNumeroProcesso());
		}
		
		if(processoDetails.getJuiz() == null) {
			updateProcesso.setJuiz(processo.getJuiz());
		} else {
			updateProcesso.setJuiz(processoDetails.getJuiz());
		}
		
		if(processoDetails.getArea() == null) {
			updateProcesso.setArea(processo.getArea());
		} else {
			updateProcesso.setArea(processoDetails.getArea());
		}
		
		if(processoDetails.getComarca() == null) {
			updateProcesso.setComarca(processo.getArea());
		} else {
			updateProcesso.setComarca(processoDetails.getArea());
		}
		
		if(processoDetails.getStatus() == null) {
			updateProcesso.setStatus(processo.getStatus());
		} else {
			updateProcesso.setStatus(processoDetails.getStatus());
		}
		
		if(processoDetails.getValor_processo() == null) {
			updateProcesso.setValor_processo(processo.getValor_processo());
		} else {
			updateProcesso.setValor_processo(processoDetails.getValor_processo());
		}
		
		if(processoDetails.getAdvogado() == null) {
			updateProcesso.setAdvogado(processo.getAdvogado());
		} else {
			updateProcesso.setAdvogado(processoDetails.getAdvogado());
		}
		
		if(processoDetails.getCliente() == null) {
			updateProcesso.setCliente(processo.getCliente());
		} else {
			updateProcesso.setCliente(processoDetails.getCliente());
		}
		
		if(processoDetails.getDocumentosProcesso() == null) {
			updateProcesso.setDocumentosProcesso(processo.getDocumentosProcesso());
		} else {
			updateProcesso.setDocumentosProcesso(processoDetails.getDocumentosProcesso());
		}
		
		return processoService.save(updateProcesso);
		
	}
	
	@PutMapping("/status/{numeroProcesso}")
	public Processo alteraStatus(@PathVariable String numeroProcesso, @RequestBody Processo processoDetails) {

	    // Buscar o processo com o número fornecido
	    Processo updateProcesso = processoService.findByNumeroProcesso(numeroProcesso);
	    
	    // Verificar se o status fornecido é nulo
	    if (processoDetails.getStatus() == null) {
	        throw new ProcessoErrorException("Status não pode ser nulo!");
	    }

	    // Atualizar o status do processo e salvar
	    updateProcesso.setStatus(processoDetails.getStatus());
	    return processoService.save(updateProcesso);
	}

	
}
