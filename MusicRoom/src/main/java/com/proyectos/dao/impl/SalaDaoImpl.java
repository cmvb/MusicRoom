package com.proyectos.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.ISalaDao;
import com.proyectos.model.SalaTB;
import org.apache.commons.lang3.StringUtils;

@Repository
public class SalaDaoImpl extends AbstractDao<SalaTB> implements ISalaDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public List<SalaTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM SalaTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idSala");
		// END QUERY

		TypedQuery<SalaTB> query = em.createQuery(JPQL.toString(), SalaTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<SalaTB> consultarPorFiltros(SalaTB salaFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM SalaTB t WHERE 1 = 1 ");
		// Q. Usuario
		if (StringUtils.isNotBlank(salaFiltro.getNombreSala())) {
			JPQL.append("AND LOWER(t.nombreSala) = LOWER(:NOMBRESALA) ");
			pamameters.put("NOMBRESALA", salaFiltro.getNombreSala());
		}
		// Q. Tercero
		if (salaFiltro.getTerceroTb() != null && salaFiltro.getTerceroTb().getIdTercero() > 0) {
			JPQL.append("AND t.ubicacionTb.idUbicacion = :UBICACION ");
			pamameters.put("UBICACION", salaFiltro.getTerceroTb().getIdTercero());
		}
		// Q. Order By
		JPQL.append(" ORDER BY t.idSala");
		// END QUERY

		TypedQuery<SalaTB> query = em.createQuery(JPQL.toString(), SalaTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public SalaTB consultarPorId(long idSala) {
		super.setClazz(SalaTB.class);
		return super.findOne(idSala);
	}

	@Override
	public SalaTB crear(SalaTB sala) {
		sala = colocarValoresDefecto(sala);
		super.create(sala);
		return sala;
	}

	@Override
	public SalaTB modificar(SalaTB sala) {
		sala = colocarValoresDefecto(sala);
		super.update(sala);
		return sala;
	}

	@Override
	public void eliminar(long idSala) {
		super.setClazz(SalaTB.class);
		deleteById(idSala);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private SalaTB colocarValoresDefecto(SalaTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
