package com.proyectos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Bloque de Ensayo")
@Entity
@Table(name = "MRA_BLOQUE_TB")
public class BloqueTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -3031529458931882541L;

	@Id
	@Column(name = "blo_id_bloque", nullable = false, length = 10)
	private long idBloque;

	@NotNull
	@Column(name = "blo_fecha_inicio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@NotNull
	@Column(name = "blo_fecha_fin", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "blo_ensayo", nullable = false, insertable = false)
	private EnsayoTB ensayoTb;

	@NotNull
	@OneToMany(mappedBy = "bloqueTb", fetch = FetchType.LAZY)
	private List<PrestamoTB> listaPrestamoTb;

	public long getIdBloque() {
		return idBloque;
	}

	public void setIdBloque(long idBloque) {
		this.idBloque = idBloque;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EnsayoTB getEnsayoTb() {
		return ensayoTb;
	}

	public void setEnsayoTb(EnsayoTB ensayoTb) {
		this.ensayoTb = ensayoTb;
	}

	public List<PrestamoTB> getListaPrestamoTb() {
		return listaPrestamoTb;
	}

	public void setListaPrestamoTb(List<PrestamoTB> listaPrestamoTb) {
		this.listaPrestamoTb = listaPrestamoTb;
	}

}
