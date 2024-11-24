package com.advocacia.dto;

import java.util.Date;

import com.advocacia.entities.StatusConsulta;

public class ConsultaDTO {
	private Integer id;
	private Double valor;
	private Date dataMarcada;
	private Date dataRealizada;
	private String pagamento;
	private Date dataPagamento;
	private String meioPagamento;
	private String resumo;
	private StatusConsulta status;
	private ClienteDTO cliente;

	public ConsultaDTO(Integer id, Double valor, Date dataMarcada, Date dataRealizada, String pagamento,
			Date dataPagamento, String meioPagamento, String resumo, StatusConsulta status, ClienteDTO cliente) {
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

	public Date getDataMarcada() {
		return dataMarcada;
	}

	public void setDataMarcada(Date dataMarcada) {
		this.dataMarcada = dataMarcada;
	}

	public Date getDataRealizada() {
		return dataRealizada;
	}

	public void setDataRealizada(Date dataRealizada) {
		this.dataRealizada = dataRealizada;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
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
