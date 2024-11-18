package com.advocacia.dto;

import com.advocacia.entities.Cliente;

public class ClienteDTO {
	
	private Integer id;
	
	private String nome;

	public ClienteDTO(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cliente convertToEntity() {
		Cliente cliente = new Cliente();
        cliente.setId(this.id);      // Supondo que sua entidade Cliente tenha um método setId
        cliente.setNome(this.nome);  // Supondo que sua entidade Cliente tenha um método setNome
        return cliente;
	}
	
	

}
