package com.proyectos.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IArchivoDao;
import com.proyectos.model.ArchivoTB;
import com.proyectos.service.IArchivosService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class ArchivosServiceImpl implements IArchivosService {

	@Autowired
	private IArchivoDao archivoDAO;

	@Transactional
	@Override
	public ArchivoTB guardarArchivo(ArchivoTB archivo) {
		archivo.setIdArchivo(archivoDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_ARCHIVO_TB));
		return archivoDAO.guardarArchivo(archivo);
	}

	@Override
	public byte[] leerArchivo(Long idArchivo) {
		return archivoDAO.leerArchivo(idArchivo);
	}

}
