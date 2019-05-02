package com.proyectos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.proyectos.enums.EEstado;

@MappedSuperclass
public class BaseEntidadTB {

	@NotNull
	@Column(name = "estado", nullable = false, length = 10)
	private short estado = (short) EEstado.ACTIVO.ordinal();

	@NotNull
	@Column(name = "fecha_creacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion = new Date();

	@NotNull
	@Column(name = "usuario_creacion", nullable = false, length = 10)
	private String usuarioCreacion = "SYSTEM";

	@NotNull
	@Column(name = "fecha_actualiza", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualiza = new Date();

	@NotNull
	@Column(name = "usuario_actualiza", nullable = false, length = 10)
	private String usuarioActualiza = "SYSTEM";

	public short getEstado() {
		return estado;
	}

	public void setEstado(short estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaActualiza() {
		return fechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}

	public String getUsuarioActualiza() {
		return usuarioActualiza;
	}

	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
	}

}
