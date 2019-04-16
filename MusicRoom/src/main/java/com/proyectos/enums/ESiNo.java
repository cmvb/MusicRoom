package com.proyectos.enums;

public enum ESiNo {
	NO("No"), SI("Si");

	private final String nombre;

	private ESiNo(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
