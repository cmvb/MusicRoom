package com.proyectos.enums;

public enum ETipoUbicacion {
	PAIS("País"), DEPARTAMENTO("Departamento"), CIUDAD("Ciudad");

	private final String nombre;

	private ETipoUbicacion(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
