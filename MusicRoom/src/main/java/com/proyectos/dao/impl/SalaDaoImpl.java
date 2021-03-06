package com.proyectos.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.ISalaDao;
import com.proyectos.dao.jpa.ISalaJPARepoDao;
import com.proyectos.model.SalaTB;
import com.proyectos.util.Util;

@Repository
public class SalaDaoImpl extends AbstractDao<SalaTB> implements ISalaDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private ISalaJPARepoDao salaJPADAO;

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
		// Q. Nombre
		if (StringUtils.isNotBlank(salaFiltro.getNombreSala())) {
			JPQL.append(" AND LOWER(t.nombreSala) LIKE LOWER(:NOMBRESALA) ");
			pamameters.put("NOMBRESALA", Util.COMODIN + salaFiltro.getNombreSala() + Util.COMODIN);
		}
		// Q. Tercero
		if (salaFiltro.getTerceroTb() != null && salaFiltro.getTerceroTb().getIdTercero() > 0) {
			JPQL.append("AND t.terceroTb.idTercero = :TERCERO ");
			pamameters.put("TERCERO", salaFiltro.getTerceroTb().getIdTercero());
		}
		// Q. Order By
		JPQL.append("ORDER BY t.idSala");
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
