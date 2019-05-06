package com.proyectos.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.ITerceroDao;
import com.proyectos.model.TerceroTB;
import com.proyectos.util.Util;

@Repository
public class TerceroDaoImpl extends AbstractDao<TerceroTB> implements ITerceroDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public List<TerceroTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM TerceroTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idTercero");
		// END QUERY

		TypedQuery<TerceroTB> query = em.createQuery(JPQL.toString(), TerceroTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<TerceroTB> consultarPorFiltros(TerceroTB terceroFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM TerceroTB t WHERE 1 = 1 ");
		// Q. Usuario
		if (StringUtils.isNotBlank(terceroFiltro.getNit())) {
			JPQL.append("AND LOWER(t.nit) = LOWER(:NIT) ");
			pamameters.put("NIT", terceroFiltro.getNit());
		}
		// Q. Nombre
		if (StringUtils.isNotBlank(terceroFiltro.getRazonSocial())) {
			JPQL.append(" AND LOWER(t.razonSocial) LIKE LOWER(:RAZON_SOCIAL) ");
			pamameters.put("RAZON_SOCIAL", Util.COMODIN + terceroFiltro.getRazonSocial() + Util.COMODIN);
		}
		// Q. Apellido
		if (StringUtils.isNotBlank(terceroFiltro.getTelefono1())) {
			JPQL.append(" AND LOWER(t.telefono1) LIKE LOWER(:TELEFONO1) ");
			pamameters.put("TELEFONO1", Util.COMODIN + terceroFiltro.getTelefono1() + Util.COMODIN);
		}
		// Q. Número Documento
		if (StringUtils.isNotBlank(terceroFiltro.getDireccion())) {
			JPQL.append("AND t.direccion = :DIRECCION ");
			pamameters.put("DIRECCION", Util.COMODIN + terceroFiltro.getDireccion() + Util.COMODIN);
		}
		// Q. Ubicación
		if (terceroFiltro.getUbicacionTb() != null && terceroFiltro.getUbicacionTb().getIdUbicacion() > 0) {
			JPQL.append("AND t.ubicacionTb.idUbicacion = :UBICACION ");
			pamameters.put("UBICACION", terceroFiltro.getUbicacionTb().getIdUbicacion());
		}
		// Q. Order By
		JPQL.append(" ORDER BY t.idTercero");
		// END QUERY

		TypedQuery<TerceroTB> query = em.createQuery(JPQL.toString(), TerceroTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public TerceroTB consultarPorId(long idTercero) {
		super.setClazz(TerceroTB.class);
		return super.findOne(idTercero);
	}

	@Override
	public TerceroTB crear(TerceroTB tercero) {
		tercero = colocarValoresDefecto(tercero);
		super.create(tercero);
		return tercero;
	}

	@Override
	public TerceroTB modificar(TerceroTB tercero) {
		tercero = colocarValoresDefecto(tercero);
		super.update(tercero);
		return tercero;
	}

	@Override
	public void eliminar(long idTercero) {
		super.setClazz(TerceroTB.class);
		deleteById(idTercero);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private TerceroTB colocarValoresDefecto(TerceroTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
