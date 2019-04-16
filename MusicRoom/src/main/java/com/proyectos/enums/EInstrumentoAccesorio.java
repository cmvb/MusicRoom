package com.proyectos.enums;

public enum EInstrumentoAccesorio {
	VACIO("Selecciona un Instrumento o Accesorio"), VOZ("Voz"), GUITARRA("Guitarra"), BAJO("Bajo"), BATERIA("Batería"),
	PERCUSION_MENOR("Percusión Menor"), TECLADO("Teclado"), VIENTO_SINFONICO("Ins. Viento Sinfónico"),
	CUERDA_SINFONICO("Ins. Cuerda Sinfónico"), PERCUSION_SINFONICO("Ins. Percusión Sinfónico"), CABLE("Cable"),
	PEDAL("Pedal"), OTRO("Otro");

	private final String nombre;

	private EInstrumentoAccesorio(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
