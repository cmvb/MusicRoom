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
	 * Método para modificar un archivo
	 */
	public ArchivoTB modificarArchivo(ArchivoTB archivo);

	/*
	 * Método para modificar un archivo
	 */
	public boolean modificarRutaArchivo(String nombreArchivo, String nuevaRuta);

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

	/*
	 * Metodo para actualizar el estado de la transaccion y sincronizar la
	 * información con la BD modificada
	 */
	void flushCommitEM();

}
