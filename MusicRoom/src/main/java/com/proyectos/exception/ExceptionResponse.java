package com.proyectos.exception;

import java.util.Date;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Información del las Excepciones")
public class ExceptionResponse {

	private Date fecha;
	private String mensaje;
	private String detalles;

	public ExceptionResponse(Date fecha, String mensaje, String detalles) {
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.detalles = detalles;

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

}
