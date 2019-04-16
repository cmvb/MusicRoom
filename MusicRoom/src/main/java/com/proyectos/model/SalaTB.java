package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información de la Sala de Ensayo")
@Entity
@Table(name = "MRA_SALA_TB")
public class SalaTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 3287497366438529836L;

	@Id
	@Column(name = "sal_id_sala", nullable = false, length = 10)
	private long idSala;

	@NotNull
	@Column(name = "sal_nombre_sala", nullable = false, length = 50)
	private String nombreSala;

	@NotNull
	@Column(name = "sal_info_adicional", nullable = false, length = 100)
	private String infoAdicional;

	public long getIdSala() {
		return idSala;
	}

	public void setIdSala(long idSala) {
		this.idSala = idSala;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

}
