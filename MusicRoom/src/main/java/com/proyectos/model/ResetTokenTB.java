package com.proyectos.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Null;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Información del Código de Verificación de Usuario")
@Entity
@Table(name = "MRA_RESET_TOKEN_TB")
public class ResetTokenTB extends BaseEntidadTB implements Serializable {
	private static final long serialVersionUID = -1152750201354814302L;

	@Id
	@Column(name = "tok_id_token", nullable = false, length = 10)
	private Long idToken;

	@Null
	@Column(name = "tok_token", nullable = false, unique = true, length = 1000)
	private String token;

	@Column(name = "tok_email", nullable = false, unique = true, length = 50)
	private String email;

	@Column(name = "tok_expiracion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiracion;

	public Long getIdToken() {
		return idToken;
	}

	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpiracion() {
		return expiracion;
	}

	public void setExpiracion(Date expiracion) {
		this.expiracion = expiracion;
	}

	public void setExpiracion(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expiracion = now.getTime();
	}

	public boolean isExpirado() {
		return new Date().after(this.expiracion);
	}
}