package com.advocacia.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.advocacia.entities.StatusConsulta;

public class ConsultaDTO {
	private Integer id;
	private Double valor;
	private LocalDateTime dataMarcada;
	private LocalDateTime dataRealizada;
	private String pagamento;
	private LocalDateTime dataPagamento;
	private String meioPagamento;
	private String resumo;
	private StatusConsulta status;
	private ClienteDTO cliente;

	public ConsultaDTO(Integer id, Double valor, LocalDateTime dataMarcada, LocalDateTime dataRealizada, String pagamento,
			LocalDateTime dataPagamento, String meioPagamento, String resumo, StatusConsulta status, ClienteDTO cliente) {
		this.id = id;
		this.valor = valor;
		this.dataMarcada = dataMarcada;
		this.dataRealizada = dataRealizada;
		this.pagamento = pagamento;
		this.dataPagamento = dataPagamento;
		this.meioPagamento = meioPagamento;
		this.resumo = resumo;
		this.status = status;
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

	public LocalDateTime getDataMarcada() {
		return dataMarcada;
	}

	public void setDataMarcada(LocalDateTime dataMarcada) {
		this.dataMarcada = dataMarcada;
	}

	public LocalDateTime getDataRealizada() {
		return dataRealizada;
	}

	public void setDataRealizada(LocalDateTime dataRealizada) {
		this.dataRealizada = dataRealizada;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(String meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	
	public StatusConsulta getStatus() {
		return status;
	}


	public void setStatus(StatusConsulta status) {
		this.status = status;
	}


	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	
	
}
