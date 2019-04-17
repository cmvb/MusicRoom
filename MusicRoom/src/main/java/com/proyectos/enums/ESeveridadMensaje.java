package com.proyectos.enums;

public enum ESeveridadMensaje {
	DEFAULT("default"), INFO("info"), SUCCESS("success"), WARNING("warning"), DANGER("danger");

	private final String nombre;

	private ESeveridadMensaje(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
