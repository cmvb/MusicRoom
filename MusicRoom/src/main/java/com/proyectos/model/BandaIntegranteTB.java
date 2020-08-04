package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del la relación entre un Integrante y las Bandas")
@Entity
@Table(name = "MRA_BANDA_INTEGRANTE_TB")
public class BandaIntegranteTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -2675281644418243289L;

	@Id
	@Column(name = "bdi_id_banda_integrante", nullable = false, length = 10)
	private long idBandaIntegrante;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bdi_integrante")
	private IntegranteTB integranteTb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bdi_banda")
	private BandaTB bandaTb;

	public long getIdBandaIntegrante() {
		return idBandaIntegrante;
	}

	public void setIdBandaIntegrante(long idBandaIntegrante) {
		this.idBandaIntegrante = idBandaIntegrante;
	}

	public IntegranteTB getIntegranteTb() {
		return integranteTb;
	}

	public void setIntegranteTb(IntegranteTB integranteTb) {
		this.integranteTb = integranteTb;
	}

	public BandaTB getBandaTb() {
		return bandaTb;
	}

	public void setBandaTb(BandaTB bandaTb) {
		this.bandaTb = bandaTb;
	}
}
