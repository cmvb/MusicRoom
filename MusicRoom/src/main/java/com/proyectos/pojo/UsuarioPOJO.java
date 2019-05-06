package com.proyectos.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties
@Getter
@Setter
@SuppressWarnings("unused")
public class UsuarioPOJO implements Serializable {
	private static final long serialVersionUID = 7568918721144974402L;

	private long idUsuario;

	private String usuario;

	private String password;

	private String nombre;

	private String apellido;

	private String numeroDocumento;

	private short tipoDocumento;

	private short tipoUsuario;

	private String email;

	private Date fechaNacimiento;

}
