package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Préstamo del Inventario (Instrumento, accesorio... etc.) en el Bloque del Ensayo")
@Entity
@Table(name = "MRA_PRESTAMO_TB")
public class PrestamoTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 1205621049850413222L;

	@Id
	@Column(name = "pre_id_prestamo", nullable = false, length = 10)
	private long idPrestamo;

	@NotNull
	@Column(name = "pre_nombre_responsable", nullable = false, length = 50)
	private String nombreResponsable;

	@NotNull
	@Column(name = "pre_documento_responsable", nullable = false, length = 20)
	private String documentoResponsable;

	@NotNull
	@OneToOne
	@JoinColumn(name = "pre_inventario", nullable = false, insertable = false)
	private InventarioTB inventarioTb;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pre_bloque", nullable = false, insertable = false)
	private BloqueTB bloqueTb;

	public long getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(long idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public String getNombreResponsable() {
		return nombreResponsable;
	}

	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	public String getDocumentoResponsable() {
		return documentoResponsable;
	}

	public void setDocumentoResponsable(String documentoResponsable) {
		this.documentoResponsable = documentoResponsable;
	}

	public InventarioTB getInventarioTb() {
		return inventarioTb;
	}

	public void setInventarioTb(InventarioTB inventarioTb) {
		this.inventarioTb = inventarioTb;
	}

	public BloqueTB getBloqueTb() {
		return bloqueTb;
	}

	public void setBloqueTb(BloqueTB bloqueTb) {
		this.bloqueTb = bloqueTb;
	}

}
