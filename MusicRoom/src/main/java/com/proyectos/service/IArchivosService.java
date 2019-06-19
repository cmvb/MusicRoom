package com.proyectos.service;

import com.proyectos.model.ArchivoTB;

public interface IArchivosService {

	/*
	 * Método para guardar un archivo
	 */
	public ArchivoTB guardarArchivo(ArchivoTB archivo);

	/*
	 * Método para leer un archivo
	 */
	public byte[] leerArchivo(Long idArchivo);

	/*
	 * Método para leer un archivo
	 */
	public ArchivoTB consultarPorId(Long idArchivo);

}
