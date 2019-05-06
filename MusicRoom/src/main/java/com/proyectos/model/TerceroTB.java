package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Tercero (Empresa o Sede)")
@Entity
@Table(name = "MRA_TERCERO_TB")
public class TerceroTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 5998207923985052392L;

	@Id
	@Column(name = "ter_id_tercero", nullable = false, length = 10)
	private long idTercero;

	@NotNull
	@Column(name = "ter_razon_social", nullable = false, length = 50)
	private String razonSocial;

	@NotNull
	@Column(name = "ter_direccion", nullable = false, length = 50)
	private String direccion;

	@NotNull
	@Column(name = "ter_telefono1", nullable = false, length = 20)
	private String telefono1;

	@Column(name = "ter_telefono2", nullable = false, length = 20)
	private String telefono2;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ter_ubicacion", nullable = false)
	private UbicacionTB ubicacionTb;

	@Column(name = "ter_info_adicional", nullable = false, length = 100)
	private String infoAdicional;

	@NotNull
	@Column(name = "ter_nit", nullable = false, length = 20)
	private String nit;

	public long getIdTercero() {
		return idTercero;
	}

	public void setIdTercero(long idTercero) {
		this.idTercero = idTercero;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public UbicacionTB getUbicacionTb() {
		return ubicacionTb;
	}

	public void setUbicacionTb(UbicacionTB ubicacionTb) {
		this.ubicacionTb = ubicacionTb;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

}
