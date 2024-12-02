package com.advocacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.advocacia.entities.Cliente;
import com.advocacia.entities.Consulta;
import com.advocacia.entities.Status;
import com.advocacia.entities.StatusConsulta;
import com.advocacia.exceptions.ConsultaErrorException;
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

	public Page<Consulta> findAllPageable(Pageable pageable) {
		return consultaRepository.findAll(pageable);
	}

	public Consulta concluirConsulta(Integer id) {

		Optional<Consulta> consulta = consultaRepository.findById(id);

		Consulta existingConsulta = consulta.get();

		if (existingConsulta == null) {
			throw new ConsultaErrorException("Consulta não encontrada com esse Id " + id);
		}

		existingConsulta.setStatus(StatusConsulta.REALIZADA);

		Consulta savedConsulta = consultaRepository.save(existingConsulta);
		return savedConsulta;
	}

	public Consulta pagarConsulta(Consulta consultaDetails) {

		Optional<Consulta> consulta = consultaRepository.findById(consultaDetails.getId());

		Consulta consultaPaga = consultaDetails;

		Consulta savedConsulta = consultaRepository.save(consultaPaga);
		return savedConsulta;
	}

	// Implementar findByData

}
