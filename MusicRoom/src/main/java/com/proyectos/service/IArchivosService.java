package com.proyectos.service;

import java.io.InputStream;

import com.proyectos.model.ArchivoTB;

public interface IArchivosService {

	/*
	 * Método para guardar un archivo
	 */
	public ArchivoTB guardarArchivo(ArchivoTB archivo, InputStream inputStream);

	/*
	 * Método para leer un archivo
	 */
	public byte[] leerArchivo(Long idArchivo);

	/*
	 * Método para leer un archivo
	 */
	public ArchivoTB consultarPorId(Long idArchivo);

}
