package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información de la Sesión")
@Entity
@Table(name = "MRA_SESION_TB")
public class SesionTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 151116432510599047L;

	@Id
	@Column(name = "ses_id_sesion", nullable = false, length = 10)
	private long idSesion;

	@NotNull
	@Column(name = "ses_token_sesion", nullable = false, length = 50)
	private String tokenSesion;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ses_usuario")
	private UsuarioTB usuarioTb;

	public long getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(long idSesion) {
		this.idSesion = idSesion;
	}

	public String getTokenSesion() {
		return tokenSesion;
	}

	public void setTokenSesion(String tokenSesion) {
		this.tokenSesion = tokenSesion;
	}

	public UsuarioTB getUsuarioTb() {
		return usuarioTb;
	}

	public void setUsuarioTb(UsuarioTB usuarioTb) {
		this.usuarioTb = usuarioTb;
	}

}
