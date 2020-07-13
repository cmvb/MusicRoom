package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Archivo")
@Entity
@Table(name = "MRA_ARCHIVO_TB")
public class ArchivoTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -219479050642422202L;

	@Id
	@Column(name = "arc_id_archivo", nullable = false, length = 10)
	private long idArchivo;

	@NotNull
	@Column(name = "arc_nombre_archivo", nullable = false, length = 150)
	private String nombreArchivo;

	@NotNull
	@Column(name = "arc_tipo_archivo", nullable = false, length = 20)
	private String tipoArchivo;

	@Column(name = "arc_ruta_archivo", nullable = false, length = 150)
	private String rutaArchivo;

	@Column(name = "arc_valor", nullable = false)
	private byte[] valor;

	public long getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(long idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public byte[] getValor() {
		return valor;
	}

	public void setValor(byte[] valor) {
		this.valor = valor;
	}
}