package com.proyectos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IArchivoDao;
import com.proyectos.dao.IBandaDao;
import com.proyectos.dao.IBandaIntegranteDao;
import com.proyectos.dao.IIntegranteDao;
import com.proyectos.model.BandaIntegranteTB;
import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.model.dto.BandaIntegranteDTO;
import com.proyectos.ot.services.ISFTPService;
import com.proyectos.service.IBandaIntegranteService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class BandaIntegranteServiceImpl implements IBandaIntegranteService {

	final private String SEPARADOR = "/";
	final private String PUNTO = ".";
	final private String RUTA_SFTP = "/data/desplieguesQA/EAP-C7/dist-angular/Pagos-EAF/pruebaSFTP/bandas/";

	@Value("${sftp.servidor}")
	private String SERVIDOR_SFTP;

	@Value("${sftp.puerto}")
	private int PUERTO_SFTP;

	@Value("${sftp.usuario}")
	private String USUARIO_SFTP;

	@Value("${sftp.password}")
	private String PASSWORD_SFTP;

	@Autowired
	private IBandaDao bandaDAO;

	@Autowired
	private IIntegranteDao integranteDAO;

	@Autowired
	private IBandaIntegranteDao bandaIntegranteDAO;

	@Autowired
	private IArchivoDao archivoDAO;

	@Autowired
	private ISFTPService sftpService;

	@Override
	public List<BandaTB> consultarTodasBandas() {
		return bandaDAO.consultarTodos();
	}

	@Override
	public List<IntegranteTB> consultarTodosIntegrantes() {
		return integranteDAO.consultarTodos();
	}

	@Override
	public List<BandaIntegranteDTO> consultarPorFiltros(BandaTB bandaFiltro) {
		return bandaDAO.consultarPorFiltros(bandaFiltro);
	}

	@Override
	public List<IntegranteTB> consultarPorFiltros(IntegranteTB integranteFiltro) {
		return integranteDAO.consultarPorFiltros(integranteFiltro);
	}

	@Override
	public List<BandaTB> consultarBandas(long idIntegrante) {
		return bandaDAO.consultarBandas(idIntegrante);
	}

	@Override
	public HashMap<BandaTB, List<IntegranteTB>> consultarMapaBandaXIntegrante() {
		return bandaDAO.consultarMapaBandaXIntegrante();
	}

	@Override
	public BandaTB consultarBandaPorId(long idBanda) {
		return bandaDAO.consultarPorId(idBanda);
	}

	@Override
	public IntegranteTB consultarIntegrantePorId(long idIntegrante) {
		return integranteDAO.consultarPorId(idIntegrante);
	}

	@Transactional
	@Override
	public BandaIntegranteDTO crear(BandaIntegranteDTO bandaIntegrante) {
		BandaIntegranteDTO bandaIntegranteResult = null;
		BandaTB banda = bandaIntegrante.getBandaTb();
		List<IntegranteTB> listaIntegrantes = bandaIntegrante.getListaIntegrantesTb();

		// Conectando con servidor SFTP
		boolean conectadoSFTP = this.sftpService.conectarServidor(this.SERVIDOR_SFTP, this.PUERTO_SFTP,
				this.USUARIO_SFTP, this.PASSWORD_SFTP);

		if (conectadoSFTP) {
			// Definir rutas banda
			String rutaSFTPBanda = this.RUTA_SFTP + banda.getNombreBanda().toUpperCase();
			String rutaSFTPFoto = this.RUTA_SFTP + banda.getFotoTb().getNombreArchivo() + this.PUNTO
					+ banda.getFotoTb().getTipoArchivo();
			String rutaSFTPLogo = banda.getLogoTb() == null ? ""
					: this.RUTA_SFTP + banda.getLogoTb().getNombreArchivo() + this.PUNTO
							+ banda.getLogoTb().getTipoArchivo();

			// Validar existencia del directorio en servidor SFTP
			if (!this.sftpService.esValidaRuta(rutaSFTPBanda)) {
				// Se crea el directorio para los archivos de la banda en servidor SFTP
				this.sftpService.crearDirectorio(rutaSFTPBanda);

				// Transferir archivos
				boolean crearArchivoFoto = this.sftpService.moverArchivoServidor(rutaSFTPFoto,
						rutaSFTPBanda + this.SEPARADOR + banda.getFotoTb().getNombreArchivo() + this.PUNTO
								+ banda.getFotoTb().getTipoArchivo());
				boolean crearArchivoLogo = banda.getLogoTb() == null ? false
						: this.sftpService.moverArchivoServidor(rutaSFTPLogo,
								rutaSFTPBanda + this.SEPARADOR + banda.getLogoTb().getNombreArchivo() + this.PUNTO
										+ banda.getLogoTb().getTipoArchivo());

				// Cerrar conexión con servidor SFTP
				this.sftpService.cerrarConexion();

				if (crearArchivoFoto && crearArchivoLogo) {
					// Crear Banda
					banda.getFotoTb().setRutaArchivo(rutaSFTPBanda);
					this.archivoDAO.modificarArchivo(banda.getFotoTb());
					banda.getLogoTb().setRutaArchivo(rutaSFTPBanda);
					this.archivoDAO.modificarArchivo(banda.getLogoTb());

					this.archivoDAO.flushCommitEM();
					banda.setIdBanda(bandaDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_BANDA_TB));
					BandaTB bandaCreada = bandaDAO.crear(banda);

					// Crear Integrantes de la Banda
					List<IntegranteTB> listaIntegrantesCreados = this.crearIntegrantes(listaIntegrantes, bandaCreada);

					bandaIntegranteResult = new BandaIntegranteDTO();
					bandaIntegranteResult.setBandaTb(bandaCreada);
					bandaIntegranteResult.setListaIntegrantesTb(listaIntegrantesCreados);

					return bandaIntegranteResult;
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

	private List<IntegranteTB> crearIntegrantes(List<IntegranteTB> listaIntegrantes, BandaTB bandaTb) {
		// Lógica para crear integrante
		List<IntegranteTB> listaIntegrantesCreados = new ArrayList<>();
		for (IntegranteTB integrante : listaIntegrantes) {
			IntegranteTB integranteCreado = integranteDAO.crear(integrante);
			BandaIntegranteTB bandaIntegranteTb = new BandaIntegranteTB();
			bandaIntegranteTb.setBandaTb(bandaTb);
			bandaIntegranteTb.setIntegranteTb(integranteCreado);
			bandaIntegranteDAO.crear(bandaIntegranteTb);

			listaIntegrantesCreados.add(integranteCreado);
		}

		return listaIntegrantesCreados;
	}

	@Transactional
	@Override
	public BandaIntegranteDTO modificar(BandaIntegranteDTO bandaIntegrante) {
		BandaIntegranteDTO bandaIntegranteResult = null;
		BandaTB banda = bandaIntegrante.getBandaTb();
		List<IntegranteTB> listaIntegrantes = bandaIntegrante.getListaIntegrantesTb();
		BandaTB bandaAnterior = this.bandaDAO.consultarPorId(banda.getIdBanda());

		// Conectando con servidor SFTP
		boolean conectadoSFTP = this.sftpService.conectarServidor(this.SERVIDOR_SFTP, this.PUERTO_SFTP,
				this.USUARIO_SFTP, this.PASSWORD_SFTP);

		if (conectadoSFTP) {
			// Definir rutas banda
			String rutaSFTPBandaNueva = this.RUTA_SFTP + banda.getNombreBanda().toUpperCase();
			String rutaSFTPBandaAnterior = this.RUTA_SFTP + bandaAnterior.getNombreBanda().toUpperCase();

			// Rutas Foto
			String rutaSFTPFotoNuevo = this.RUTA_SFTP + banda.getFotoTb().getNombreArchivo() + this.PUNTO
					+ banda.getFotoTb().getTipoArchivo();
			String rutaSFTPFotoAnterior = rutaSFTPBandaAnterior + this.SEPARADOR
					+ bandaAnterior.getFotoTb().getNombreArchivo() + this.PUNTO
					+ bandaAnterior.getFotoTb().getTipoArchivo();

			// Rutas Logo
			String rutaSFTPLogoNuevo = banda.getLogoTb() == null ? ""
					: this.RUTA_SFTP + banda.getLogoTb().getNombreArchivo() + this.PUNTO
							+ banda.getLogoTb().getTipoArchivo();
			String rutaSFTPLogoAnterior = bandaAnterior.getLogoTb() == null ? ""
					: rutaSFTPBandaAnterior + this.SEPARADOR + bandaAnterior.getLogoTb().getNombreArchivo() + this.PUNTO
							+ bandaAnterior.getLogoTb().getTipoArchivo();

			if (!rutaSFTPBandaNueva.equalsIgnoreCase(rutaSFTPBandaAnterior)) {
				// Validar existencia del directorio en servidor SFTP
				if (!this.sftpService.esValidaRuta(rutaSFTPBandaNueva)) {
					// Se crea el directorio para los archivos de la banda en servidor SFTP
					this.sftpService.crearDirectorio(rutaSFTPBandaNueva);

					// Transferir archivo foto
					if (bandaAnterior.getFotoTb().getIdArchivo() != banda.getFotoTb().getIdArchivo()) {
						boolean crearArchivoFoto = this.sftpService.moverArchivoServidor(rutaSFTPFotoNuevo,
								rutaSFTPBandaNueva + this.SEPARADOR + banda.getFotoTb().getNombreArchivo() + this.PUNTO
										+ banda.getFotoTb().getTipoArchivo());

						if (crearArchivoFoto) {
							banda.getFotoTb().setRutaArchivo(rutaSFTPBandaNueva);
							this.archivoDAO.modificarArchivo(banda.getFotoTb());
							if (bandaAnterior.getFotoTb().getIdArchivo() != banda.getFotoTb().getIdArchivo()) {
								this.archivoDAO.eliminar(bandaAnterior.getFotoTb().getIdArchivo());
							}
						}
					} else {
						boolean crearArchivoFoto = this.sftpService.moverArchivoServidor(rutaSFTPFotoAnterior,
								rutaSFTPBandaNueva + this.SEPARADOR + banda.getFotoTb().getNombreArchivo() + this.PUNTO
										+ banda.getFotoTb().getTipoArchivo());

						if (crearArchivoFoto) {
							banda.getFotoTb().setRutaArchivo(rutaSFTPBandaNueva);
							this.archivoDAO.modificarArchivo(banda.getFotoTb());
							if (bandaAnterior.getFotoTb().getIdArchivo() != banda.getFotoTb().getIdArchivo()) {
								this.archivoDAO.eliminar(bandaAnterior.getFotoTb().getIdArchivo());
							}
						}
					}

					// Transferir archivo logo
					if (bandaAnterior.getLogoTb().getIdArchivo() != banda.getLogoTb().getIdArchivo()) {
						boolean crearArchivoLogo = this.sftpService.moverArchivoServidor(rutaSFTPLogoNuevo,
								rutaSFTPBandaNueva + this.SEPARADOR + banda.getLogoTb().getNombreArchivo() + this.PUNTO
										+ banda.getLogoTb().getTipoArchivo());

						if (crearArchivoLogo) {
							banda.getLogoTb().setRutaArchivo(rutaSFTPBandaNueva);
							this.archivoDAO.modificarArchivo(banda.getLogoTb());
							if (bandaAnterior.getLogoTb().getIdArchivo() != banda.getLogoTb().getIdArchivo()) {
								this.archivoDAO.eliminar(bandaAnterior.getLogoTb().getIdArchivo());
							}
						}
					} else {
						boolean crearArchivoLogo = this.sftpService.moverArchivoServidor(rutaSFTPLogoAnterior,
								rutaSFTPBandaNueva + this.SEPARADOR + banda.getLogoTb().getNombreArchivo() + this.PUNTO
										+ banda.getLogoTb().getTipoArchivo());

						if (crearArchivoLogo) {
							// Modificar Banda
							banda.getLogoTb().setRutaArchivo(rutaSFTPBandaNueva);
							this.archivoDAO.modificarArchivo(banda.getLogoTb());
							if (bandaAnterior.getLogoTb().getIdArchivo() != banda.getLogoTb().getIdArchivo()) {
								this.archivoDAO.eliminar(bandaAnterior.getLogoTb().getIdArchivo());
							}
						}
					}

					// Borrar directorio anterior en el servidor SFTP
					this.sftpService.borrarDirectorioServidor(rutaSFTPBandaAnterior);

					// Cerrar conexión con servidor SFTP
					this.sftpService.cerrarConexion();

					// Modificar Banda
					BandaTB bandaModificada = bandaDAO.modificar(banda);

					// Modificar Integrantes
					bandaIntegranteResult = new BandaIntegranteDTO();
					bandaIntegranteResult.setBandaTb(bandaModificada);
					bandaIntegranteResult
							.setListaIntegrantesTb(this.modificarIntegrantes(listaIntegrantes, bandaModificada));

					return bandaIntegranteResult;
				} else {
					return null;
				}
			} else {
				// Transferir archivo foto
				if (bandaAnterior.getFotoTb().getIdArchivo() != banda.getFotoTb().getIdArchivo()) {
					// Se borra el directorio anterior
					this.sftpService.borrarArchivoServidor(rutaSFTPFotoAnterior);

					boolean crearArchivoFoto = this.sftpService.moverArchivoServidor(rutaSFTPFotoNuevo,
							rutaSFTPBandaAnterior + this.SEPARADOR + banda.getFotoTb().getNombreArchivo() + this.PUNTO
									+ banda.getFotoTb().getTipoArchivo());

					if (crearArchivoFoto) {
						banda.getFotoTb().setRutaArchivo(rutaSFTPBandaAnterior);
						this.archivoDAO.modificarArchivo(banda.getFotoTb());
						this.archivoDAO.eliminar(bandaAnterior.getFotoTb().getIdArchivo());
					}
				}

				// Transferir archivo logo
				if (bandaAnterior.getLogoTb().getIdArchivo() != banda.getLogoTb().getIdArchivo()) {
					// Se borra el directorio anterior
					this.sftpService.borrarArchivoServidor(rutaSFTPLogoAnterior);

					boolean crearArchivoLogo = this.sftpService.moverArchivoServidor(rutaSFTPLogoNuevo,
							rutaSFTPBandaAnterior + this.SEPARADOR + banda.getLogoTb().getNombreArchivo() + this.PUNTO
									+ banda.getLogoTb().getTipoArchivo());

					if (crearArchivoLogo) {
						banda.getLogoTb().setRutaArchivo(rutaSFTPBandaAnterior);
						this.archivoDAO.modificarArchivo(banda.getLogoTb());
						this.archivoDAO.eliminar(bandaAnterior.getLogoTb().getIdArchivo());
					}
				}

				// Cerrar conexión con servidor SFTP
				this.sftpService.cerrarConexion();

				// Modificar Banda
				BandaTB bandaModificada = bandaDAO.modificar(banda);

				// Modificar Integrantes
				bandaIntegranteResult = new BandaIntegranteDTO();
				bandaIntegranteResult.setBandaTb(bandaModificada);
				bandaIntegranteResult
						.setListaIntegrantesTb(this.modificarIntegrantes(listaIntegrantes, bandaModificada));

				return bandaIntegranteResult;
			}
		} else {
			return null;
		}
	}

	private List<IntegranteTB> modificarIntegrantes(List<IntegranteTB> listaIntegrantes, BandaTB bandaTb) {
		// Lógica para modificar integrante
		List<IntegranteTB> listaIntegrantesModificados = new ArrayList<>();
		for (IntegranteTB integrante : listaIntegrantes) {
			IntegranteTB integranteModificado = integranteDAO.modificar(integrante);
			listaIntegrantesModificados.add(integranteModificado);
		}

		return listaIntegrantesModificados;
	}

	@Transactional
	@Override
	public void eliminarBanda(long idBanda) {
		List<BandaIntegranteTB> listaBandaIntegrante = bandaIntegranteDAO.consultarPorIdBanda(idBanda);
		for (BandaIntegranteTB bandaIntegrante : listaBandaIntegrante) {
			bandaIntegranteDAO.eliminar(bandaIntegrante.getBandaTb().getIdBanda(),
					bandaIntegrante.getIntegranteTb().getIdIntegrante());
			integranteDAO.eliminar(bandaIntegrante.getIntegranteTb().getIdIntegrante());
		}
		bandaDAO.eliminar(idBanda);
	}

	@Transactional
	@Override
	public void eliminarIntegrante(long idIntegrante) {
		List<BandaIntegranteTB> listaBandaIntegrante = bandaIntegranteDAO.consultarPorIdIntegrante(idIntegrante);
		bandaIntegranteDAO.eliminar(listaBandaIntegrante.get(0).getBandaTb().getIdBanda(), idIntegrante);
		integranteDAO.eliminar(idIntegrante);
	}

	@Override
	public List<IntegranteTB> consultarIntegrantes(long idBanda) {
		return integranteDAO.consultarIntegrantes(idBanda);
	}

}
