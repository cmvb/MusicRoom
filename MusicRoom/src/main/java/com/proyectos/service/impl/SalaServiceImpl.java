package com.proyectos.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IArchivoDao;
import com.proyectos.dao.ISalaDao;
import com.proyectos.model.ArchivoTB;
import com.proyectos.model.SalaTB;
import com.proyectos.ot.services.ISFTPService;
import com.proyectos.service.ISalaService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class SalaServiceImpl implements ISalaService {

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
		// Conectando con servidor SFTP
		boolean conectadoSFTP = this.sftpService.conectarServidor(this.SERVIDOR_SFTP, this.PUERTO_SFTP,
				this.USUARIO_SFTP, this.PASSWORD_SFTP);

		if (conectadoSFTP) {
			// Definir rutas sala
			String rutaSFTPSala = this.RUTA_SFTP + sala.getNombreSala().toUpperCase();
			String rutaSFTPFilePrincipal = this.RUTA_SFTP + sala.getFotoPrincipalTb().getNombreArchivo() + this.PUNTO
					+ sala.getFotoPrincipalTb().getTipoArchivo();
			String rutaSFTPFile1 = sala.getFoto1Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto1Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto1Tb().getTipoArchivo();
			String rutaSFTPFile2 = sala.getFoto2Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto2Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto2Tb().getTipoArchivo();
			String rutaSFTPFile3 = sala.getFoto3Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto3Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto3Tb().getTipoArchivo();
			String rutaSFTPFile4 = sala.getFoto4Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto4Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto4Tb().getTipoArchivo();

			// Validar existencia del directorio en servidor SFTP
			if (!this.sftpService.esValidaRuta(rutaSFTPSala)) {
				// Se crea el directorio para los archivos de la sala en servidor SFTP
				this.sftpService.crearDirectorio(rutaSFTPSala);

				// Transferir archivos
				boolean crearArchivoPrincipal = this.sftpService.moverArchivoServidor(rutaSFTPFilePrincipal,
						rutaSFTPSala + this.SEPARADOR + sala.getFotoPrincipalTb().getNombreArchivo() + this.PUNTO
								+ sala.getFotoPrincipalTb().getTipoArchivo());
				boolean crearArchivo1 = sala.getFoto1Tb() == null ? false
						: this.sftpService.moverArchivoServidor(rutaSFTPFile1,
								rutaSFTPSala + this.SEPARADOR + sala.getFoto1Tb().getNombreArchivo() + this.PUNTO
										+ sala.getFoto1Tb().getTipoArchivo());
				boolean crearArchivo2 = sala.getFoto2Tb() == null ? false
						: this.sftpService.moverArchivoServidor(rutaSFTPFile2,
								rutaSFTPSala + this.SEPARADOR + sala.getFoto2Tb().getNombreArchivo() + this.PUNTO
										+ sala.getFoto2Tb().getTipoArchivo());
				boolean crearArchivo3 = sala.getFoto3Tb() == null ? false
						: this.sftpService.moverArchivoServidor(rutaSFTPFile3,
								rutaSFTPSala + this.SEPARADOR + sala.getFoto3Tb().getNombreArchivo() + this.PUNTO
										+ sala.getFoto3Tb().getTipoArchivo());
				boolean crearArchivo4 = sala.getFoto4Tb() == null ? false
						: this.sftpService.moverArchivoServidor(rutaSFTPFile4,
								rutaSFTPSala + this.SEPARADOR + sala.getFoto4Tb().getNombreArchivo() + this.PUNTO
										+ sala.getFoto4Tb().getTipoArchivo());

				// Cerrar conexión con servidor SFTP
				this.sftpService.cerrarConexion();

				if (crearArchivoPrincipal) {
					sala.getFotoPrincipalTb().setRutaArchivo(rutaSFTPSala);
					this.archivoDAO.modificarArchivo(sala.getFotoPrincipalTb());
					if (crearArchivo1) {
						sala.getFoto1Tb().setRutaArchivo(rutaSFTPSala);
						this.archivoDAO.modificarArchivo(sala.getFoto1Tb());
					}
					if (crearArchivo2) {
						sala.getFoto2Tb().setRutaArchivo(rutaSFTPSala);
						this.archivoDAO.modificarArchivo(sala.getFoto2Tb());
					}
					if (crearArchivo3) {
						sala.getFoto3Tb().setRutaArchivo(rutaSFTPSala);
						this.archivoDAO.modificarArchivo(sala.getFoto3Tb());
					}
					if (crearArchivo4) {
						sala.getFoto4Tb().setRutaArchivo(rutaSFTPSala);
						this.archivoDAO.modificarArchivo(sala.getFoto4Tb());
					}

					this.archivoDAO.flushCommitEM();
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
		SalaTB salaAnterior = this.salaDAO.consultarPorId(sala.getIdSala());

		// Conectando con servidor SFTP
		boolean conectadoSFTP = this.sftpService.conectarServidor(this.SERVIDOR_SFTP, this.PUERTO_SFTP,
				this.USUARIO_SFTP, this.PASSWORD_SFTP);

		if (conectadoSFTP) {
			// Definir rutas sala
			String rutaSFTPSalaNueva = this.RUTA_SFTP + sala.getNombreSala().toUpperCase();
			String rutaSFTPSalaAnterior = this.RUTA_SFTP + salaAnterior.getNombreSala().toUpperCase();

			// Rutas Principal
			String rutaSFTPFilePrincipalNuevo = this.RUTA_SFTP + sala.getFotoPrincipalTb().getNombreArchivo()
					+ this.PUNTO + sala.getFotoPrincipalTb().getTipoArchivo();
			String rutaSFTPFilePrincipalAnterior = rutaSFTPSalaAnterior + this.SEPARADOR
					+ salaAnterior.getFotoPrincipalTb().getNombreArchivo() + this.PUNTO
					+ salaAnterior.getFotoPrincipalTb().getTipoArchivo();

			// Rutas 1
			String rutaSFTPFile1Nuevo = sala.getFoto1Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto1Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto1Tb().getTipoArchivo();
			String rutaSFTPFile1Anterior = salaAnterior.getFoto1Tb() == null ? ""
					: rutaSFTPSalaAnterior + this.SEPARADOR + salaAnterior.getFoto1Tb().getNombreArchivo() + this.PUNTO
							+ salaAnterior.getFoto1Tb().getTipoArchivo();

			// Rutas 2
			String rutaSFTPFile2Nuevo = sala.getFoto2Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto2Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto2Tb().getTipoArchivo();
			String rutaSFTPFile2Anterior = salaAnterior.getFoto2Tb() == null ? ""
					: rutaSFTPSalaAnterior + this.SEPARADOR + salaAnterior.getFoto2Tb().getNombreArchivo() + this.PUNTO
							+ salaAnterior.getFoto2Tb().getTipoArchivo();

			// Rutas 3
			String rutaSFTPFile3Nuevo = sala.getFoto3Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto3Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto3Tb().getTipoArchivo();
			String rutaSFTPFile3Anterior = salaAnterior.getFoto3Tb() == null ? ""
					: rutaSFTPSalaAnterior + this.SEPARADOR + salaAnterior.getFoto3Tb().getNombreArchivo() + this.PUNTO
							+ salaAnterior.getFoto3Tb().getTipoArchivo();

			// Rutas 4
			String rutaSFTPFile4Nuevo = sala.getFoto4Tb() == null ? ""
					: this.RUTA_SFTP + sala.getFoto4Tb().getNombreArchivo() + this.PUNTO
							+ sala.getFoto4Tb().getTipoArchivo();
			String rutaSFTPFile4Anterior = salaAnterior.getFoto4Tb() == null ? ""
					: rutaSFTPSalaAnterior + this.SEPARADOR + salaAnterior.getFoto4Tb().getNombreArchivo() + this.PUNTO
							+ salaAnterior.getFoto4Tb().getTipoArchivo();

			if (!rutaSFTPSalaNueva.equalsIgnoreCase(rutaSFTPSalaAnterior)) {
				// Validar existencia del directorio en servidor SFTP
				if (!this.sftpService.esValidaRuta(rutaSFTPSalaNueva)) {
					// Se crea el directorio para los archivos de la sala en servidor SFTP
					this.sftpService.crearDirectorio(rutaSFTPSalaNueva);

					// Transferir archivo principal
					if (salaAnterior.getFotoPrincipalTb().getIdArchivo() != sala.getFotoPrincipalTb().getIdArchivo()) {
						boolean crearArchivoPrincipal = this.sftpService
								.moverArchivoServidor(rutaSFTPFilePrincipalNuevo,
										rutaSFTPSalaNueva + this.SEPARADOR
												+ sala.getFotoPrincipalTb().getNombreArchivo() + this.PUNTO
												+ sala.getFotoPrincipalTb().getTipoArchivo());

						if (crearArchivoPrincipal) {
							if (salaAnterior.getFotoPrincipalTb().getIdArchivo() != sala.getFotoPrincipalTb()
									.getIdArchivo()) {
								this.archivoDAO.eliminar(salaAnterior.getFotoPrincipalTb().getIdArchivo());
							}
							sala.getFotoPrincipalTb().setRutaArchivo(rutaSFTPSalaNueva);
							this.archivoDAO.modificarArchivo(sala.getFotoPrincipalTb());
						}
					} else {
						boolean crearArchivoPrincipal = this.sftpService
								.moverArchivoServidor(rutaSFTPFilePrincipalAnterior,
										rutaSFTPSalaNueva + this.SEPARADOR
												+ sala.getFotoPrincipalTb().getNombreArchivo() + this.PUNTO
												+ sala.getFotoPrincipalTb().getTipoArchivo());

						if (crearArchivoPrincipal) {
							if (salaAnterior.getFotoPrincipalTb().getIdArchivo() != sala.getFotoPrincipalTb()
									.getIdArchivo()) {
								this.archivoDAO.eliminar(salaAnterior.getFotoPrincipalTb().getIdArchivo());
							}
							sala.getFotoPrincipalTb().setRutaArchivo(rutaSFTPSalaNueva);
							this.archivoDAO.modificarArchivo(sala.getFotoPrincipalTb());
						}
					}

					// Transferir archivo 1
					this.transferirArchivoNuevaSala(rutaSFTPSalaNueva, rutaSFTPFile1Anterior, rutaSFTPFile1Nuevo,
							salaAnterior.getFoto1Tb(), sala.getFoto1Tb());

					// Transferir archivo 2
					this.transferirArchivoNuevaSala(rutaSFTPSalaNueva, rutaSFTPFile2Anterior, rutaSFTPFile2Nuevo,
							salaAnterior.getFoto2Tb(), sala.getFoto2Tb());

					// Transferir archivo 3
					this.transferirArchivoNuevaSala(rutaSFTPSalaNueva, rutaSFTPFile3Anterior, rutaSFTPFile3Nuevo,
							salaAnterior.getFoto3Tb(), sala.getFoto3Tb());

					// Transferir archivo 4
					this.transferirArchivoNuevaSala(rutaSFTPSalaNueva, rutaSFTPFile4Anterior, rutaSFTPFile4Nuevo,
							salaAnterior.getFoto4Tb(), sala.getFoto4Tb());

					// Borrar directorio anterior en el servidor SFTP
					this.sftpService.borrarDirectorioServidor(rutaSFTPSalaAnterior + this.SEPARADOR);

					// Cerrar conexión con servidor SFTP
					this.sftpService.cerrarConexion();

					return salaDAO.modificar(sala);
				} else {
					return null;
				}
			} else {
				// Transferir archivo principal
				if (salaAnterior.getFotoPrincipalTb().getIdArchivo() != sala.getFotoPrincipalTb().getIdArchivo()) {
					// Se borra el directorio anterior
					this.sftpService.borrarArchivoServidor(rutaSFTPFilePrincipalAnterior);

					boolean crearArchivoPrincipal = this.sftpService.moverArchivoServidor(rutaSFTPFilePrincipalNuevo,
							rutaSFTPSalaAnterior + this.SEPARADOR + sala.getFotoPrincipalTb().getNombreArchivo()
									+ this.PUNTO + sala.getFotoPrincipalTb().getTipoArchivo());

					if (crearArchivoPrincipal) {
						this.archivoDAO.eliminar(salaAnterior.getFotoPrincipalTb().getIdArchivo());
						sala.getFotoPrincipalTb().setRutaArchivo(rutaSFTPSalaAnterior);
						this.archivoDAO.modificarArchivo(sala.getFotoPrincipalTb());
					}
				}

				// Transferir archivo 1
				this.transferirArchivoMismaSala(rutaSFTPSalaAnterior, rutaSFTPFile1Anterior, rutaSFTPFile1Nuevo,
						salaAnterior.getFoto1Tb(), sala.getFoto1Tb());

				// Transferir archivo 2
				this.transferirArchivoMismaSala(rutaSFTPSalaAnterior, rutaSFTPFile2Anterior, rutaSFTPFile2Nuevo,
						salaAnterior.getFoto2Tb(), sala.getFoto2Tb());

				// Transferir archivo 3
				this.transferirArchivoMismaSala(rutaSFTPSalaAnterior, rutaSFTPFile3Anterior, rutaSFTPFile3Nuevo,
						salaAnterior.getFoto3Tb(), sala.getFoto3Tb());

				// Transferir archivo 4
				this.transferirArchivoMismaSala(rutaSFTPSalaAnterior, rutaSFTPFile4Anterior, rutaSFTPFile4Nuevo,
						salaAnterior.getFoto4Tb(), sala.getFoto4Tb());

				// Cerrar conexión con servidor SFTP
				this.sftpService.cerrarConexion();

				return salaDAO.modificar(sala);
			}
		} else {
			return null;
		}
	}

	private void transferirArchivoNuevaSala(String rutaSFTPSalaNueva, String rutaSFTPFileAnterior,
			String rutaSFTPFileNuevo, ArchivoTB fotoAnteriorTB, ArchivoTB fotoNuevaTB) {
		if (!StringUtils.isBlank(rutaSFTPFileAnterior) && !StringUtils.isBlank(rutaSFTPFileNuevo)) {
			if (fotoAnteriorTB.getIdArchivo() != fotoNuevaTB.getIdArchivo()) {
				boolean crearArchivo2 = this.sftpService.moverArchivoServidor(rutaSFTPFileNuevo, rutaSFTPSalaNueva
						+ this.SEPARADOR + fotoNuevaTB.getNombreArchivo() + this.PUNTO + fotoNuevaTB.getTipoArchivo());

				if (crearArchivo2) {
					if (fotoAnteriorTB.getIdArchivo() != fotoNuevaTB.getIdArchivo()) {
						this.archivoDAO.eliminar(fotoAnteriorTB.getIdArchivo());
					}
					fotoNuevaTB.setRutaArchivo(rutaSFTPSalaNueva);
					this.archivoDAO.modificarArchivo(fotoNuevaTB);
				}
			} else {
				boolean crearArchivo2 = this.sftpService.moverArchivoServidor(rutaSFTPFileAnterior, rutaSFTPSalaNueva
						+ this.SEPARADOR + fotoNuevaTB.getNombreArchivo() + this.PUNTO + fotoNuevaTB.getTipoArchivo());

				if (crearArchivo2) {
					if (fotoAnteriorTB.getIdArchivo() != fotoNuevaTB.getIdArchivo()) {
						this.archivoDAO.eliminar(fotoAnteriorTB.getIdArchivo());
					}
					fotoNuevaTB.setRutaArchivo(rutaSFTPSalaNueva);
					this.archivoDAO.modificarArchivo(fotoNuevaTB);
				}
			}
		} else {
			if (!StringUtils.isBlank(rutaSFTPFileAnterior)) {
				this.archivoDAO.eliminar(fotoAnteriorTB.getIdArchivo());
			}
			if (fotoNuevaTB != null) {
				boolean crearArchivo2 = this.sftpService.moverArchivoServidor(rutaSFTPFileNuevo, rutaSFTPSalaNueva
						+ this.SEPARADOR + fotoNuevaTB.getNombreArchivo() + this.PUNTO + fotoNuevaTB.getTipoArchivo());

				if (crearArchivo2) {
					fotoNuevaTB.setRutaArchivo(rutaSFTPSalaNueva);
					this.archivoDAO.modificarArchivo(fotoNuevaTB);
				}
			}
		}
	}

	private void transferirArchivoMismaSala(String rutaSFTPSalaAnterior, String rutaSFTPFileAnterior,
			String rutaSFTPFileNuevo, ArchivoTB fotoAnteriorTB, ArchivoTB fotoNuevaTB) {
		if (!StringUtils.isBlank(rutaSFTPFileAnterior) && !StringUtils.isBlank(rutaSFTPFileNuevo)) {
			if (fotoAnteriorTB.getIdArchivo() != fotoNuevaTB.getIdArchivo()) {
				// Se borra el directorio anterior
				this.sftpService.borrarArchivoServidor(rutaSFTPFileAnterior);

				boolean crearArchivo1 = this.sftpService.moverArchivoServidor(rutaSFTPFileNuevo, rutaSFTPSalaAnterior
						+ this.SEPARADOR + fotoNuevaTB.getNombreArchivo() + this.PUNTO + fotoNuevaTB.getTipoArchivo());

				if (crearArchivo1) {
					this.archivoDAO.eliminar(fotoAnteriorTB.getIdArchivo());
					fotoNuevaTB.setRutaArchivo(rutaSFTPSalaAnterior);
					this.archivoDAO.modificarArchivo(fotoNuevaTB);
				}
			}
		} else {
			if (!StringUtils.isBlank(rutaSFTPFileAnterior)) {
				this.archivoDAO.eliminar(fotoAnteriorTB.getIdArchivo());
			}
			if (fotoNuevaTB != null) {
				boolean crearArchivo1 = this.sftpService.moverArchivoServidor(rutaSFTPFileNuevo, rutaSFTPSalaAnterior
						+ this.SEPARADOR + fotoNuevaTB.getNombreArchivo() + this.PUNTO + fotoNuevaTB.getTipoArchivo());

				if (crearArchivo1) {
					fotoNuevaTB.setRutaArchivo(rutaSFTPSalaAnterior);
					this.archivoDAO.modificarArchivo(fotoNuevaTB);
				}
			}
		}
	}

	@Transactional
	@Override
	public void eliminar(long idSala) {
		salaDAO.eliminar(idSala);
	}
}
