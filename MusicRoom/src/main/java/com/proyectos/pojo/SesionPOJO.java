package com.proyectos.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties
@Getter
@Setter
@SuppressWarnings("unused")
public class SesionPOJO implements Serializable {
	private static final long serialVersionUID = -4343230843818579777L;

	private long idSesion;

	private String tokenSesion;

	private UsuarioPOJO usuarioTb;

}
