package com.proyectos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class BaseEntidadTB {

	@NotNull
	@Column(name = "estado", nullable = false, length = 10)
	private short estado;

	@NotNull
	@Column(name = "fecha_creacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@NotNull
	@Column(name = "usuario_creacion", nullable = false, length = 10)
	private String usuarioCreacion;

	@NotNull
	@Column(name = "fecha_actualiza", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualiza;

	@NotNull
	@Column(name = "usuario_actualiza", nullable = false, length = 10)
	private String usuarioActualiza;

}
