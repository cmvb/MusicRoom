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

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Código de Verificación de Usuario")
@Entity
@Table(name = "MRA_CODIGO_VERIFICACION_TB")
public class CodigoVerificacionTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -2399765333846057062L;

	@Id
	@Column(name = "vco_id_codigo_verificacion", nullable = false, length = 10)
	private long idCodigoVerificacion;

	@Column(name = "vco_token", nullable = false, length = 500)
	private String token;

	@Column(name = "vco_email", nullable = false, length = 50)
	private String email;

	@Column(name = "voc_expiracion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiracion;

	public long getIdCodigoVerificacion() {
		return idCodigoVerificacion;
	}

	public void setIdCodigoVerificacion(Long idCodigoVerificacion) {
		this.idCodigoVerificacion = idCodigoVerificacion;
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
