package com.proyectos.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.IResetTokenDao;
import com.proyectos.model.ResetTokenTB;

@Repository
public class ResetTokenDaoImpl extends AbstractDao<ResetTokenTB> implements IResetTokenDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public ResetTokenTB consultarPorToken(String token) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM ResetToken t WHERE 1 = 1 ");
		// Q. Usuario
		JPQL.append("AND t.token = :TOKEN  ");
		pamameters.put("TOKEN", token);
		// END QUERY

		TypedQuery<ResetTokenTB> query = em.createQuery(JPQL.toString(), ResetTokenTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getSingleResult();
	}

	@Override
	public ResetTokenTB crear(ResetTokenTB token) {
		token = colocarValoresDefecto(token);
		super.create(token);
		return token;
	}

	@Override
	public void eliminar(ResetTokenTB token) {
		super.setClazz(ResetTokenTB.class);
		deleteById(token.getIdToken());
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private ResetTokenTB colocarValoresDefecto(ResetTokenTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
