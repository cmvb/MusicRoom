package com.proyectos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@ApiModel(description = "Información de la Banda")
@Entity
@Table(name = "MRA_BANDA_TB")
public class BandaTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 9217261134148939637L;

	@Id
	@Column(name = "ban_id_banda", nullable = false, length = 10)
	private long idBanda;

	@NotNull
	@Column(name = "ban_nombre_banda", nullable = false, length = 50, unique = true)
	private String nombreBanda;

	@NotNull
	@Column(name = "ban_genero", nullable = false, length = 50)
	private String genero;

	@NotNull
	@Column(name = "ban_fecha_inicio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@NotNull
	@OneToMany(mappedBy = "bandaTb", fetch = FetchType.EAGER)
	private List<IntegranteTB> listaIntegrantesTb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ban_foto")
	private ArchivoTB fotoTb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ban_logo")
	private ArchivoTB logoTb;

	public long getIdBanda() {
		return idBanda;
	}

	public void setIdBanda(long idBanda) {
		this.idBanda = idBanda;
	}

	public String getNombreBanda() {
		return nombreBanda;
	}

	public void setNombreBanda(String nombreBanda) {
		this.nombreBanda = nombreBanda;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public List<IntegranteTB> getListaIntegrantesTb() {
		return listaIntegrantesTb;
	}

	public void setListaIntegrantesTb(List<IntegranteTB> listaIntegrantesTb) {
		this.listaIntegrantesTb = listaIntegrantesTb;
	}

	public ArchivoTB getFotoTb() {
		return fotoTb;
	}

	public void setFotoTb(ArchivoTB fotoTb) {
		this.fotoTb = fotoTb;
	}

	public ArchivoTB getLogoTb() {
		return logoTb;
	}

	public void setLogoTb(ArchivoTB logoTb) {
		this.logoTb = logoTb;
	}

}