package com.proyectos.service.impl;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IArchivoDao;
import com.proyectos.dao.ISalaDao;
import com.proyectos.model.SalaTB;
import com.proyectos.ot.services.ISFTPService;
import com.proyectos.service.ISalaService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class SalaServiceImpl implements ISalaService {

	final private String SEPARADOR = "/";
	final private String SERVIDOR_SFTP = "100.126.0.150";
	final private String RUTA_SFTP = "/data/desplieguesQA/EAP-C7/dist-angular/Pagos-EAF/pruebaSFTP/";
	final private int PUERTO_SFTP = 22;
	final private String USUARIO_SFTP = "oracle";
	final private String PASSWORD_SFTP = "oracle2";
	final private String RAIZ_USER_HOME_SFTP = System.getProperty("user.home") + File.separator;
	final private String RAIZ_M_SFTP = File.separator + "Users";
	final private String RAIZ_L_SFTP = File.separator + "home";
	final private String RAIZ_W_SFTP = this.RAIZ_USER_HOME_SFTP + File.separator + "Desktop" + File.separator
			+ "CargaArchivos" + File.separator;
	final private static String OS = System.getProperty("os.name").toUpperCase();

	@Autowired
	private ISalaDao salaDAO;

	@Autowired
	private IArchivoDao archivoDAO;

	@Autowired
	private ISFTPService sftpService;

	@Override
	public List<SalaTB> consultarTodos() {
		return salaDAO.consultarTodos();
	}

	@Override
	public List<SalaTB> consultarPorFiltros(SalaTB salaFiltro) {
		return salaDAO.consultarPorFiltros(salaFiltro);
	}

	@Override
	public SalaTB consultarPorId(long idSala) {
		return salaDAO.consultarPorId(idSala);
	}

	@Transactional
	@Override
	public SalaTB crear(SalaTB sala) {
		// Definiendo rutas locales
		String rutaCliente = "";
		if (this.OS.indexOf("WIN") >= 0) {
			rutaCliente = this.RAIZ_W_SFTP;
		} else if (this.OS.indexOf("MAC") >= 0) {
			rutaCliente = this.RAIZ_M_SFTP;
		} else if (this.OS.indexOf("NIX") >= 0 || OS.indexOf("NUX") >= 0 || OS.indexOf("AIX") > 0) {
			rutaCliente = this.RAIZ_L_SFTP;
		} else if (this.OS.indexOf("SUNOS") >= 0) {
			rutaCliente = this.RAIZ_L_SFTP;
		}

		// Conectando con servidor SFTP
		boolean conectadoSFTP = this.sftpService.conectarServidor(this.SERVIDOR_SFTP, this.PUERTO_SFTP,
				this.USUARIO_SFTP, this.PASSWORD_SFTP);

		if (conectadoSFTP) {
			// Validar existencia del directorio en servidor SFTP
			if (!this.sftpService.esValidaRuta(this.RUTA_SFTP + sala.getNombreSala().toUpperCase())) {
				// Se crea el directorio para los archivos de la sala en servidor SFTP
				this.sftpService.crearDirectorio(this.RUTA_SFTP + sala.getNombreSala().toUpperCase());

				// Definir rutas de los archivos
				String rutaSFTPPrincipal = this.RUTA_SFTP + sala.getNombreSala().toUpperCase() + this.SEPARADOR
						+ sala.getFotoPrincipalTb().getNombreArchivo();
				String rutaSFTP1 = sala.getFoto1Tb() == null ? ""
						: this.RUTA_SFTP + sala.getNombreSala().toUpperCase() + this.SEPARADOR
								+ sala.getFoto1Tb().getNombreArchivo();
				String rutaSFTP2 = sala.getFoto2Tb() == null ? ""
						: this.RUTA_SFTP + sala.getNombreSala().toUpperCase() + this.SEPARADOR
								+ sala.getFoto2Tb().getNombreArchivo();
				String rutaSFTP3 = sala.getFoto3Tb() == null ? ""
						: this.RUTA_SFTP + sala.getNombreSala().toUpperCase() + this.SEPARADOR
								+ sala.getFoto3Tb().getNombreArchivo();
				String rutaSFTP4 = sala.getFoto4Tb() == null ? ""
						: this.RUTA_SFTP + sala.getNombreSala().toUpperCase() + this.SEPARADOR
								+ sala.getFoto4Tb().getNombreArchivo();

				// Transferir archivos
				boolean crearArchivoPrincipal = this.sftpService.guardarArchivoServidor(
						sala.getFotoPrincipalTb().getValor(),
						rutaCliente + sala.getFotoPrincipalTb().getNombreArchivo(), rutaSFTPPrincipal);
				boolean crearArchivo1 = sala.getFoto1Tb() == null ? true
						: this.sftpService.guardarArchivoServidor(sala.getFoto1Tb().getValor(),
								rutaCliente + sala.getFoto1Tb().getNombreArchivo(), rutaSFTP1);
				boolean crearArchivo2 = sala.getFoto2Tb() == null ? true
						: this.sftpService.guardarArchivoServidor(sala.getFoto2Tb().getValor(),
								rutaCliente + sala.getFoto2Tb().getNombreArchivo(), rutaSFTP2);
				boolean crearArchivo3 = sala.getFoto3Tb() == null ? true
						: this.sftpService.guardarArchivoServidor(sala.getFoto3Tb().getValor(),
								rutaCliente + sala.getFoto3Tb().getNombreArchivo(), rutaSFTP3);
				boolean crearArchivo4 = sala.getFoto4Tb() == null ? true
						: this.sftpService.guardarArchivoServidor(sala.getFoto4Tb().getValor(),
								rutaCliente + sala.getFoto4Tb().getNombreArchivo(), rutaSFTP4);

				// Cerrar conexión con servidor SFTP
				this.sftpService.cerrarConexion();

				if (crearArchivoPrincipal) {
					sala.getFotoPrincipalTb().setRutaArchivo(rutaSFTPPrincipal);
					sala.getFotoPrincipalTb().setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
					sala.setFotoPrincipalTb(archivoDAO.guardarArchivo(sala.getFotoPrincipalTb()));

					if (crearArchivo1) {
						sala.getFoto1Tb().setRutaArchivo(rutaSFTP1);
						sala.getFoto1Tb().setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
						sala.setFoto1Tb(archivoDAO.guardarArchivo(sala.getFoto1Tb()));
					}
					if (crearArchivo2) {
						sala.getFoto2Tb().setRutaArchivo(rutaSFTP2);
						sala.getFoto2Tb().setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
						sala.setFoto2Tb(archivoDAO.guardarArchivo(sala.getFoto2Tb()));
					}
					if (crearArchivo3) {
						sala.getFoto3Tb().setRutaArchivo(rutaSFTP3);
						sala.getFoto3Tb().setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
						sala.setFoto3Tb(archivoDAO.guardarArchivo(sala.getFoto3Tb()));
					}
					if (crearArchivo4) {
						sala.getFoto4Tb().setRutaArchivo(rutaSFTP4);
						sala.getFoto4Tb().setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
						sala.setFoto4Tb(archivoDAO.guardarArchivo(sala.getFoto4Tb()));
					}

					sala.setIdSala(salaDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_SALA_TB));
					return salaDAO.crear(sala);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public SalaTB modificar(SalaTB sala) {
		return salaDAO.modificar(sala);
	}

	@Transactional
	@Override
	public void eliminar(long idSala) {
		salaDAO.eliminar(idSala);
	}
}
