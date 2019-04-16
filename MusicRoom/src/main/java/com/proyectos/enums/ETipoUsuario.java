package com.proyectos.enums;

public enum ETipoUsuario {
	VACIO("Selecciona un Tipo de Usuario"), CLIENTE("Cliente"), EMPLEADO("Empleado"), ADMINISTRADOR("Administrador");

	private final String nombre;

	private ETipoUsuario(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
