package com.proyectos.enums;

public enum EEstado {
	INACTIVO("I"), ACTIVO("A");

	private final String nombre;

	private EEstado(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
