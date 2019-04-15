package com.proyectos.exception;

public class ModelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9215964858678100358L;

	public ModelNotFoundException(String mensaje) {
		super(mensaje);
	}

}
