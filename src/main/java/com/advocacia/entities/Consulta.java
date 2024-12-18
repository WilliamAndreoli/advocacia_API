package com.advocacia.entities;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Double valor;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date data_marcada;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date data_realizada;
	
	private String pagamento;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date data_pagamento;
	
	private String meio_pagamento;
	
	private String resumo;
	
	@OneToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	public Consulta() {}

	public Consulta(Integer id, Double valor, Date data_marcada, Date data_realizada, String pagamento,
			Date data_pagamento, String meio_pagamento, String resumo, Cliente cliente) {
		super();
		this.id = id;
		this.valor = valor;
		this.data_marcada = data_marcada;
		this.data_realizada = data_realizada;
		this.pagamento = pagamento;
		this.data_pagamento = data_pagamento;
		this.meio_pagamento = meio_pagamento;
		this.resumo = resumo;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData_marcada() {
		return data_marcada;
	}

	public void setData_marcada(Date data_marcada) {
		this.data_marcada = data_marcada;
	}

	public Date getData_realizada() {
		return data_realizada;
	}

	public void setData_realizada(Date data_realizada) {
		this.data_realizada = data_realizada;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public Date getData_pagamento() {
		return data_pagamento;
	}

	public void setData_pagamento(Date data_pagamento) {
		this.data_pagamento = data_pagamento;
	}

	public String getMeio_pagamento() {
		return meio_pagamento;
	}

	public void setMeio_pagamento(String meio_pagamento) {
		this.meio_pagamento = meio_pagamento;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consulta other = (Consulta) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
