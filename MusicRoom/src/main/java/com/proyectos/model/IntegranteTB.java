package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Integrante de la Banda")
@Entity
@Table(name = "MRA_INTEGRANTE_TB")
public class IntegranteTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 8800887290413060967L;

	@Id
	@Column(name = "int_id_integrante", nullable = false, length = 10)
	private long idIntegrante;

	@NotNull
	@Column(name = "int_nombre_integrante", nullable = false, length = 50)
	private String nombreIntegrante;

	@NotNull
	@Column(name = "int_edad_integrante", nullable = false, length = 10)
	private long edadIntegrante;

	@NotNull
	@Column(name = "int_instrumento_accesorio", nullable = false, length = 10)
	private short instrumentoAccesorio;

	public long getIdIntegrante() {
		return idIntegrante;
	}

	public void setIdIntegrante(long idIntegrante) {
		this.idIntegrante = idIntegrante;
	}

	public String getNombreIntegrante() {
		return nombreIntegrante;
	}

	public void setNombreIntegrante(String nombreIntegrante) {
		this.nombreIntegrante = nombreIntegrante;
	}

	public long getEdadIntegrante() {
		return edadIntegrante;
	}

	public void setEdadIntegrante(long edadIntegrante) {
		this.edadIntegrante = edadIntegrante;
	}

	public short getInstrumentoAccesorio() {
		return instrumentoAccesorio;
	}

	public void setInstrumentoAccesorio(short instrumentoAccesorio) {
		this.instrumentoAccesorio = instrumentoAccesorio;
	}

}
