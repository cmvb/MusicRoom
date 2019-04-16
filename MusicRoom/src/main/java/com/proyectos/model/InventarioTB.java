package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Inventario")
@Entity
@Table(name = "MRA_INVENTARIO_TB")
public class InventarioTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -7572402004665634398L;

	@Id
	@Column(name = "inv_id_inventario", nullable = false, length = 10)
	private long idInventario;

	@NotNull
	@Column(name = "inv_descripcion", nullable = false, length = 50)
	private String descripcion;

	@NotNull
	@Column(name = "inv_disponible", nullable = false, length = 10)
	private short disponible;

	@NotNull
	@Column(name = "inv_info_adicional", nullable = false, length = 100)
	private String infoAdicional;

	public long getIdInventario() {
		return idInventario;
	}

	public void setIdInventario(long idInventario) {
		this.idInventario = idInventario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public short getDisponible() {
		return disponible;
	}

	public void setDisponible(short disponible) {
		this.disponible = disponible;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

}
