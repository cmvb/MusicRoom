package com.proyectos.enums;

public enum ETipoDocumento {
	VACIO("Selecciona un Tipo de Documento"), CC("CC"), TI("TI"), CE("CE");

	private final String nombre;

	private ETipoDocumento(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
