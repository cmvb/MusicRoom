package com.proyectos.dao;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.ArchivoTB;

@EnableTransactionManagement
public interface IArchivoDao {

	/*
	 * Método para guardar un archivo
	 */
	public ArchivoTB guardarArchivo(ArchivoTB archivo);

	/*
	 * Método para leer un archivo por su ID
	 */
	public byte[] leerArchivo(Long idArchivo);

	/*
	 * Metodo para consultar un archivo por su ID
	 */
	ArchivoTB consultarPorId(long idUsuario);

	/*
	 * Metodo para eliminar un archivo por su ID
	 */
	void eliminar(long idArchivo);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);

}
