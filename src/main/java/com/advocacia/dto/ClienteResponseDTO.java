package com.advocacia.dto;

import java.util.Date;

import com.advocacia.entities.Status;

public class ClienteResponseDTO {
	 private Integer id;
	    private String nome;
	    private String email;
	    private String cpf;
	    private String rg;
	    private String telefone;
	    private String endereco;
	    private String nome_pai;
	    private String nome_mae;
	    private String ctps;
	    private String cnh;
	    private Date data_nascimento;
	    private Status status;
	    private UsuarioNoPassDTO usuario;
	    
		public ClienteResponseDTO(Integer id, String nome, String email, String cpf, String rg, String telefone,
				String endereco, String nome_pai, String nome_mae, String ctps, String cnh,
				Date data_nascimento, Status status, UsuarioNoPassDTO usuario) {
			super();
			this.id = id;
			this.nome = nome;
			this.email = email;
			this.cpf = cpf;
			this.rg = rg;
			this.telefone = telefone;
			this.endereco = endereco;
			this.nome_pai = nome_pai;
			this.nome_mae = nome_mae;
			this.ctps = ctps;
			this.cnh = cnh;
			this.data_nascimento = data_nascimento;
			this.status = status;
			this.usuario = usuario;
		}
		
		public ClienteResponseDTO() {
			 
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
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public String getRg() {
			return rg;
		}
		public void setRg(String rg) {
			this.rg = rg;
		}
		public String getTelefone() {
			return telefone;
		}
		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}
		public String getEndereco() {
			return endereco;
		}
		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}
		public String getNome_pai() {
			return nome_pai;
		}
		public void setNome_pai(String nome_pai) {
			this.nome_pai = nome_pai;
		}
		public String getNome_mae() {
			return nome_mae;
		}
		public void setNome_mae(String nome_mae) {
			this.nome_mae = nome_mae;
		}
		public String getCtps() {
			return ctps;
		}
		public void setCtps(String ctps) {
			this.ctps = ctps;
		}
		public String getCnh() {
			return cnh;
		}
		public void setCnh(String cnh) {
			this.cnh = cnh;
		}
		public Date getData_nascimento() {
			return data_nascimento;
		}
		public void setData_nascimento(Date data_nascimento) {
			this.data_nascimento = data_nascimento;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		public UsuarioNoPassDTO getUsuario() {
			return usuario;
		}
		public void setUsuario(UsuarioNoPassDTO usuario) {
			this.usuario = usuario;
		}

	    
}
