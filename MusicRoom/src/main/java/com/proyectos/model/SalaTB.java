package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sal_tercero")
	private TerceroTB terceroTb;

	@NotNull
	@Column(name = "sal_nombre_sala", nullable = false, length = 50)
	private String nombreSala;

	@NotNull
	@Column(name = "sal_info_adicional", nullable = false, length = 100)
	private String infoAdicional;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sal_foto_principal")
	private ArchivoTB fotoPrincipalTb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sal_foto1")
	private ArchivoTB foto1Tb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sal_foto2")
	private ArchivoTB foto2Tb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sal_foto3")
	private ArchivoTB foto3Tb;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sal_foto4")
	private ArchivoTB foto4Tb;

	public long getIdSala() {
		return idSala;
	}

	public void setIdSala(long idSala) {
		this.idSala = idSala;
	}

	public TerceroTB getTerceroTb() {
		return terceroTb;
	}

	public void setTerceroTb(TerceroTB terceroTb) {
		this.terceroTb = terceroTb;
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

	public ArchivoTB getFotoPrincipalTb() {
		return fotoPrincipalTb;
	}

	public void setFotoPrincipalTb(ArchivoTB fotoPrincipalTb) {
		this.fotoPrincipalTb = fotoPrincipalTb;
	}

	public ArchivoTB getFoto1Tb() {
		return foto1Tb;
	}

	public void setFoto1Tb(ArchivoTB foto1Tb) {
		this.foto1Tb = foto1Tb;
	}

	public ArchivoTB getFoto2Tb() {
		return foto2Tb;
	}

	public void setFoto2Tb(ArchivoTB foto2Tb) {
		this.foto2Tb = foto2Tb;
	}

	public ArchivoTB getFoto3Tb() {
		return foto3Tb;
	}

	public void setFoto3Tb(ArchivoTB foto3Tb) {
		this.foto3Tb = foto3Tb;
	}

	public ArchivoTB getFoto4Tb() {
		return foto4Tb;
	}

	public void setFoto4Tb(ArchivoTB foto4Tb) {
		this.foto4Tb = foto4Tb;
	}

}
