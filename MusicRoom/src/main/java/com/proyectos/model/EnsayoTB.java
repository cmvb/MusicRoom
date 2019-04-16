package com.proyectos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Ensayo")
@Entity
@Table(name = "MRA_ENSAYO_TB")
public class EnsayoTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -8871048911113859887L;

	@Id
	@Column(name = "ens_id_ensayo", nullable = false, length = 10)
	private long idEnsayo;

	@NotNull
	@Column(name = "ens_nombre_encargado", nullable = false, length = 50)
	private String nombreEncargado;

	@NotNull
	@Column(name = "ens_documento_encargado", nullable = false, length = 20)
	private String documentoEncargado;

	@NotNull
	@Column(name = "ens_fecha_inicio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@NotNull
	@Column(name = "ens_fecha_fin", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	@NotNull
	@Column(name = "ens_intervalo_minutos", nullable = false, length = 10)
	private int intervaloMinutos;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ens_banda", nullable = false, insertable = false)
	private BandaTB bandaTb;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ens_sala", nullable = false, insertable = false)
	private SalaTB salaTb;

	@NotNull
	@Column(name = "ens_observaciones", nullable = false, length = 100)
	private String observaciones;

	@NotNull
	@OneToMany(mappedBy = "ensayoTb")
	private List<BloqueTB> listaBloqueTb;

	public long getIdEnsayo() {
		return idEnsayo;
	}

	public void setIdEnsayo(long idEnsayo) {
		this.idEnsayo = idEnsayo;
	}

	public String getNombreEncargado() {
		return nombreEncargado;
	}

	public void setNombreEncargado(String nombreEncargado) {
		this.nombreEncargado = nombreEncargado;
	}

	public String getDocumentoEncargado() {
		return documentoEncargado;
	}

	public void setDocumentoEncargado(String documentoEncargado) {
		this.documentoEncargado = documentoEncargado;
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

	public int getIntervaloMinutos() {
		return intervaloMinutos;
	}

	public void setIntervaloMinutos(int intervaloMinutos) {
		this.intervaloMinutos = intervaloMinutos;
	}

	public BandaTB getBandaTb() {
		return bandaTb;
	}

	public void setBandaTb(BandaTB bandaTb) {
		this.bandaTb = bandaTb;
	}

	public SalaTB getSalaTb() {
		return salaTb;
	}

	public void setSalaTb(SalaTB salaTb) {
		this.salaTb = salaTb;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<BloqueTB> getListaBloqueTb() {
		return listaBloqueTb;
	}

	public void setListaBloqueTb(List<BloqueTB> listaBloqueTb) {
		this.listaBloqueTb = listaBloqueTb;
	}

}
