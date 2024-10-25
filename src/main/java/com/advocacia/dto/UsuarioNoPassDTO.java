package com.advocacia.dto;

import com.advocacia.entities.Status;
import com.advocacia.entities.Tipo_Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioNoPassDTO {

	private Integer id;
    private String username;
    @JsonFormat
    private Status status = Status.ATIVO;
    private Tipo_Usuario tipoUsuario;

    // Construtores
    public UsuarioNoPassDTO() {}

    public UsuarioNoPassDTO(Integer id, String username, Status status, Tipo_Usuario tipoUsuario) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Tipo_Usuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Tipo_Usuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
	
}
