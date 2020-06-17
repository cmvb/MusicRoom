package com.proyectos.enums;

public enum ETipoDirectorio {
	CARPETA("Carpeta"), ARCHIVO("Archivo");

	private final String nombre;

	private ETipoDirectorio(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
