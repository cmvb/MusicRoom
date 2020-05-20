package com.proyectos.dao.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.IArchivoDao;
import com.proyectos.dao.jpa.IArchivoJPARepoDao;
import com.proyectos.model.ArchivoTB;

@Repository
public class ArchivoDaoImpl extends AbstractDao<ArchivoTB> implements IArchivoDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IArchivoJPARepoDao archivoJPADAO;

	@Override
	public ArchivoTB guardarArchivo(ArchivoTB archivo) {
		archivo = colocarValoresDefecto(archivo);
		super.create(archivo);
		return archivo;
	}

	@Override
	public byte[] leerArchivo(Long idArchivo) {
		super.setClazz(ArchivoTB.class);
		return super.findOne(idArchivo).getValor();
	}

	@Override
	public ArchivoTB consultarPorId(long idArchivo) {
		super.setClazz(ArchivoTB.class);
		return super.findOne(idArchivo);
	}

	@Override
	public void eliminar(long idArchivo) {
		super.setClazz(ArchivoTB.class);
		deleteById(idArchivo);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private ArchivoTB colocarValoresDefecto(ArchivoTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
