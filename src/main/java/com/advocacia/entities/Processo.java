package com.advocacia.entities;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Processo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "numero_processo")
	private String numeroProcesso;
	
	private String juiz;
	
	private String area;
	
	private String comarca;
	
	private StatusProcesso status;
	
	private Double valor_processo;
	
	@OneToOne
	@JoinColumn(name = "advogado_id")
	private Advogado advogado;
	
	@OneToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Documentos_Processo> documentosProcesso;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumero_processo(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getJuiz() {
		return juiz;
	}

	public void setJuiz(String juiz) {
		this.juiz = juiz;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	public StatusProcesso getStatus() {
		return status;
	}

	public void setStatus(StatusProcesso statusProcesso) {
		this.status = statusProcesso;
	}

	public Double getValor_processo() {
		return valor_processo;
	}

	public void setValor_processo(Double valor_processo) {
		this.valor_processo = valor_processo;
	}

	public Advogado getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Advogado advogado) {
		this.advogado = advogado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Documentos_Processo> getDocumentosProcesso() {
		return documentosProcesso;
	}

	public void setDocumentosProcesso(List<Documentos_Processo> documentosProcesso) {
		this.documentosProcesso = documentosProcesso;
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
		Processo other = (Processo) obj;
		return Objects.equals(id, other.id);
	}
	
}
