package com.proyectos.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	public ArchivoTB modificarArchivo(ArchivoTB archivo) {
		archivo = colocarValoresDefecto(archivo);
		super.update(archivo);
		return archivo;
	}

	@Override
	public void flushCommitEM() {
		super.flushCommitEM();
	}

	@Override
	public boolean modificarRutaArchivo(String nombreArchivo, String nuevaRuta) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM ArchivoTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idArchivo");
		// END QUERY

		TypedQuery<ArchivoTB> query = em.createQuery(JPQL.toString(), ArchivoTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		List<ArchivoTB> listaArchivos = query.getResultList();
		ArchivoTB archivoModificado = null;
		if (listaArchivos != null) {
			for (ArchivoTB archivo : listaArchivos) {
				if ((archivo.getNombreArchivo() + archivo.getTipoArchivo()).toUpperCase()
						.equals(nombreArchivo.toUpperCase())) {
					archivo.setRutaArchivo(nuevaRuta);
					archivoModificado = archivo;
					break;
				}
			}
		}

		if (archivoModificado != null) {
			this.modificarArchivo(archivoModificado);
			return true;
		} else {
			return false;
		}
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
