package com.proyectos.enums;

public enum ETipoFactura {
	VACIO("Selecciona un Tipo de Factura"), VENTA("Venta"), COMPRA("Compra");

	private final String nombre;

	private ETipoFactura(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
