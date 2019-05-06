package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información de la Ubicación (ciudad, departamento, pais)")
@Entity
@Table(name = "MRA_UBICACION_TB")
public class UbicacionTB extends BaseEntidadTB implements Serializable {
	private static final long serialVersionUID = 1512069790193136335L;

	@Id
	@Column(name = "ubi_id_ubicacion", nullable = false, length = 10)
	private long idUbicacion;

	@Null
	@Column(name = "ubi_codigo_ciudad", nullable = false, length = 20, unique = true)
	private String codigoCiudad;

	@Null
	@Column(name = "ubi_nombre_ciudad", nullable = false, length = 50)
	private String nombreCiudad;

	@Null
	@Column(name = "ubi_codigo_departamento", nullable = false, length = 20)
	private String codigoDepartamento;

	@Null
	@Column(name = "ubi_nombre_departamento", nullable = false, length = 50)
	private String nombreDepartamento;

	@NotNull
	@Column(name = "ubi_codigo_pais", nullable = false, length = 20)
	private String codigoPais;

	@NotNull
	@Column(name = "ubi_nombre_pais", nullable = false, length = 50)
	private String nombrePais;

	@NotNull
	@Column(name = "ubi_tipo_ubicacion", nullable = false, length = 10)
	private int tipoUbicacion;

	public long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getCodigoCiudad() {
		return codigoCiudad;
	}

	public void setCodigoCiudad(String codigoCiudad) {
		this.codigoCiudad = codigoCiudad;
	}

	public String getNombreCiudad() {
		return nombreCiudad;
	}

	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public int getTipoUbicacion() {
		return tipoUbicacion;
	}

	public void setTipoUbicacion(int tipoUbicacion) {
		this.tipoUbicacion = tipoUbicacion;
	}

}
