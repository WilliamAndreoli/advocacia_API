package com.advocacia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advocacia.entities.Advogado;
import com.advocacia.entities.Cliente;
import com.advocacia.entities.Processo;
import com.advocacia.entities.StatusProcesso;
import com.advocacia.exceptions.ProcessoErrorException;
import com.advocacia.services.AdvogadoService;
import com.advocacia.services.ClienteService;
import com.advocacia.services.ProcessoService;

@RestController
@RequestMapping("/processos")
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
