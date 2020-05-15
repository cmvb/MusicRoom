package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información de los Consecutivo de Todas las Tablas del Modelo")
@Entity
@Table(name = "MRA_CONSECUTIVO_TB")
public class ConsecutivoTB implements Serializable {

	private static final long serialVersionUID = 401714452766591895L;

	@Id
	@NotNull
	@Column(name = "tabla", nullable = false, length = 100)
	private String tabla;

	@NotNull
	@Column(name = "valor", nullable = false, length = 10)
	private long valor;

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}

}