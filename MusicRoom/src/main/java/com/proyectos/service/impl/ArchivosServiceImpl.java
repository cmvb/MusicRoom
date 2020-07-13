package com.proyectos.service.impl;

import java.io.InputStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IArchivoDao;
import com.proyectos.model.ArchivoTB;
import com.proyectos.ot.services.ISFTPService;
import com.proyectos.service.IArchivosService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class ArchivosServiceImpl implements IArchivosService {

	final private String SEPARADOR = "/";
	final private String PUNTO = ".";
	final private String RUTA_SFTP = "/data/desplieguesQA/EAP-C7/dist-angular/Pagos-EAF/pruebaSFTP/";

	@Value("${sftp.servidor}")
	private String SERVIDOR_SFTP;

	@Value("${sftp.puerto}")
	private int PUERTO_SFTP;

	@Value("${sftp.usuario}")
	private String USUARIO_SFTP;

	@Value("${sftp.password}")
	private String PASSWORD_SFTP;

	@Autowired
	private IArchivoDao archivoDAO;

	@Autowired
	private ISFTPService sftpService;

	@Transactional
	@Override
	public ArchivoTB guardarArchivo(ArchivoTB archivo, InputStream inputStreamFile) {
		// Conectando con servidor SFTP
		boolean conectadoSFTP = this.sftpService.conectarServidor(this.SERVIDOR_SFTP, this.PUERTO_SFTP,
				this.USUARIO_SFTP, this.PASSWORD_SFTP);

		if (conectadoSFTP) {
			// Definir rutas de los archivos
			String rutaSFTP = this.RUTA_SFTP + archivo.getNombreArchivo() + this.PUNTO + archivo.getTipoArchivo();

			// Transferir archivo SFTP
			boolean crearArchivo = this.sftpService.guardarArchivoServidor(inputStreamFile, rutaSFTP);

			// Cerrar conexión con servidor SFTP
			this.sftpService.cerrarConexion();

			if (crearArchivo) {
				archivo.setRutaArchivo(this.RUTA_SFTP);
				archivo.setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
				return archivoDAO.guardarArchivo(archivo);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public byte[] leerArchivo(Long idArchivo) {
		return archivoDAO.leerArchivo(idArchivo);
	}

	@Override
	public ArchivoTB consultarPorId(Long idArchivo) {
		return archivoDAO.consultarPorId(idArchivo);
	}

}
