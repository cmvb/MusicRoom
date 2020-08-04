
package com.proyectos.ot.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.proyectos.enums.ETipoDirectorio;
import com.proyectos.model.dto.DirectorioDTO;
import com.proyectos.ot.services.ISFTPService;

@Service
public class SFTPServiceImpl implements ISFTPService {

	private JSch jsch;
	private Session session;
	private ChannelSftp channelSftp;
	private Properties config;
	final private String SEPARADOR = "/";

	@Override
	public boolean conectarServidor(String Servidor, int puerto, String usuario, String clave) {
		boolean resultado = false;
		try {
			this.config = new Properties();
			this.jsch = new JSch();
			this.session = this.jsch.getSession(usuario, Servidor, puerto);
			this.session.setPassword(clave);
			this.config.put("StrictHostKeyChecking", "no");
			JSch.setConfig(config);
			this.session.connect();
			this.channelSftp = (ChannelSftp) this.session.openChannel("sftp");
			this.channelSftp.connect();
			if (this.channelSftp != null) {
				resultado = true;
			}
		} catch (JSchException e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	@Override
	public void cerrarConexion() {
		if (this.channelSftp != null) {
			this.channelSftp.disconnect();
			this.session.disconnect();
			this.channelSftp = null;
			this.session = null;
			this.jsch = null;
		}
	}

	@Override
	public boolean guardarArchivoServidor(byte[] bytesFile, String rutaLocal, String rutaSFTP) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				File file = new File(rutaLocal);
				OutputStream fos = new FileOutputStream(file);
				fos.write(bytesFile);
				this.borrarArchivoServidor(rutaSFTP);
				this.channelSftp.put(rutaLocal, rutaSFTP);
				fos.close();
				resultado = true;
				file.delete();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	@Override
	public boolean guardarArchivoServidor(InputStream inputStreamFile, String rutaSFTP) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				this.borrarArchivoServidor(rutaSFTP);
				this.channelSftp.put(inputStreamFile, rutaSFTP);
				resultado = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	@Override
	public boolean moverArchivoServidor(String rutaOrigenSFTP, String rutaDestinoSFTP) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				this.channelSftp.rename(rutaOrigenSFTP, rutaDestinoSFTP);
				this.borrarArchivoServidor(rutaOrigenSFTP);
				resultado = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	@Override
	public boolean borrarArchivoServidor(String rutaServidor) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				if (this.channelSftp.realpath(rutaServidor) != null) {
					this.channelSftp.rm(rutaServidor);
					resultado = true;
				}
			}
		} catch (SftpException e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	@Override
	public void borrarDirectorioServidor(String rutaServidor) {
		try {
			if (this.channelSftp != null) {
				if (this.channelSftp.realpath(rutaServidor) != null) {
					List<DirectorioDTO> directoriosSFTP = this.listarDirectoriosSFTP(rutaServidor);
					if (directoriosSFTP != null && !directoriosSFTP.isEmpty()) {
						for (DirectorioDTO dirDto : directoriosSFTP) {
							if (dirDto.getTipoDirectorio().ordinal() == ETipoDirectorio.ARCHIVO.ordinal()) {
								this.borrarArchivoServidor(dirDto.getRuta() + dirDto.getNombre());
								if (directoriosSFTP.size() == 1) {
									this.channelSftp.rmdir(rutaServidor);
								}
							} else {
								this.borrarDirectorioServidor(dirDto.getRuta() + dirDto.getNombre() + this.SEPARADOR);
							}
						}
					} else {
						this.channelSftp.rmdir(rutaServidor);
					}
				}
			}
		} catch (SftpException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean descargarArchivo(String rutaSFTP, String rutaLocal) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				this.channelSftp.get(rutaSFTP, rutaLocal);
				resultado = true;
			}
		} catch (SftpException e) {
			System.out.println(Arrays.toString(e.getStackTrace()));
		}

		return resultado;
	}

	@Override
	public InputStream obtenerInputStreamArchivo(String rutaSFTP) {
		InputStream resultado = null;
		try {
			if (this.channelSftp != null) {
				resultado = this.channelSftp.get(rutaSFTP);
			}
		} catch (SftpException e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	@Override
	public boolean esValidaRuta(String rutaSFTP) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				SftpATTRS stat = this.channelSftp.stat(rutaSFTP);
				if (stat != null) {
					resultado = stat.isDir();
					resultado = true;
				}
			}
		} catch (SftpException e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	@Override
	public boolean crearDirectorio(String rutaSFTP) {
		boolean resultado = false;
		try {
			if (this.channelSftp != null) {
				this.channelSftp.mkdir(rutaSFTP);
				resultado = true;
			}
		} catch (SftpException e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DirectorioDTO> listarDirectoriosSFTP(String rutaSFTP) {
		List<DirectorioDTO> listaCarpetas = new ArrayList<>();
		try {
			if (this.channelSftp != null) {
				List<LsEntry> carpetas = this.channelSftp.ls(rutaSFTP);
				for (LsEntry carpeta : carpetas) {
					if (!carpeta.getFilename().equalsIgnoreCase(".") && !carpeta.getFilename().equalsIgnoreCase("..")) {
						DirectorioDTO dto = construirDirectorioDto(carpeta.getFilename(), rutaSFTP);
						listaCarpetas.add(dto);
					}
				}
			}
		} catch (SftpException ex) {
			System.out.println(ex.getMessage());
		}

		return listaCarpetas;
	}

	/**
	 * Método para construir un DTO de directorio desde un Servidor SFTP
	 */
	@SuppressWarnings("unchecked")
	private DirectorioDTO construirDirectorioDto(String nombreArchivo, String rutaSFTP) {
		DirectorioDTO dto = new DirectorioDTO();
		try {
			dto.setNombre(nombreArchivo);
			dto.setRuta(rutaSFTP);
			List<LsEntry> carpetasInternas = this.channelSftp.ls(rutaSFTP + nombreArchivo);
			if (carpetasInternas.size() > 1) {
				dto.setTipoDirectorio(ETipoDirectorio.CARPETA);
			} else {
				dto.setTipoDirectorio(ETipoDirectorio.ARCHIVO);
			}
		} catch (SftpException ex) {
			System.out.println(ex.getMessage());
		}

		return dto;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> listarArchivosFiltrados(String rutaSFTP, String nombre) {
		List<String> resultado = new ArrayList<>();
		try {
			List<LsEntry> carpetasInternas = this.channelSftp.ls(rutaSFTP);

			Predicate<LsEntry> f = g -> g.getFilename().matches(nombre + "-[0-9]{1,2}");
			carpetasInternas.stream().filter(f).forEach(e -> resultado.add(e.getFilename()));
		} catch (SftpException ex) {
			System.out.println(ex.getMessage());
		}

		return resultado;
	}

}
