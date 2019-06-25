package com.proyectos.model.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Información DTO del Usuario")
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 693796673608256644L;

	private String usuario;

	private String numeroDocumento;

	private short tipoDocumento;

	private String email;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public short getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(short tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
