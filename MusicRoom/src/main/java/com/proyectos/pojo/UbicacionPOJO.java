package com.proyectos.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties
@Getter
@Setter
@SuppressWarnings("unused")
public class UbicacionPOJO implements Serializable {
	private static final long serialVersionUID = 7018437359917232092L;

	private long idUbicacion;

	private String codigoCiudad;

	private String nombreCiudad;

	private String codigoDepartamento;

	private String nombreDepartamento;

	private String codigoPais;

	private String nombrePais;

	private int tipoUbicacion;

}
