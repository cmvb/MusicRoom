package com.proyectos.ot.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectos.model.dto.DirectorioDTO;

@Service
public interface ISFTPService {

	/**
	 * Método para abrir la conexión con el Servidor SFTP
	 */
	public boolean conectarServidor(String Servidor, int puerto, String usuario, String clave);

	/**
	 * Metodo para cerrar la conexión con el Servidor SFTP
	 */
	public void cerrarConexion();

	/**
	 * Método para guardar un archivo físico en el Servidor SFTP
	 */
	public boolean guardarArchivoServidor(byte[] bytesFile, String rutaSftp);

	/**
	 * Método para borrar un archivo guardado en el Servidor SFTP
	 */
	public boolean borrarArchivoServidor(String rutaServidor);

	/**
	 * Método para descargar un archivo directamente de un Servidor SFTP
	 */
	public boolean descargarArchivo(String rutaSFTP, String rutaLocal);

	/**
	 * Método para obtener el InputStream de un archivo del Servidor SFTP
	 */
	public InputStream obtenerInputStreamArchivo(String rutaSFTP);

	/**
	 * Método para validar existencia de una carpeta dentro del Servidor SFTP
	 */
	public boolean esValidaRuta(String rutaSFTP);

	/**
	 * Método para crear una carpeta dentro del Servidor SFTP si no existe
	 */
	public boolean crearDirectorio(String rutaSFTP);

	/**
	 * Método para listar directorios y subdirectorios desde un Servidor SFTP
	 */
	public List<DirectorioDTO> listarDirectoriosSFTP(String rutaSFTP);

	/**
	 * Método para obtener la lista de nombres de archivos filtrado por un nombre
	 * desde un Servidor SFTP
	 */
	public List<String> listarArchivosFiltrados(String rutaSFTP, String nombre);

}
