package com.proyectos.dao.impl;

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
import com.proyectos.dao.ICodigoVerificacionDao;
import com.proyectos.dao.jpa.ICodigoVerificacionJPARepoDao;
import com.proyectos.model.CodigoVerificacionTB;

@Repository
public class CodigoVerificacionDaoImpl extends AbstractDao<CodigoVerificacionTB> implements ICodigoVerificacionDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private ICodigoVerificacionJPARepoDao vCodeJPADAO;

	@Override
	public CodigoVerificacionTB crear(CodigoVerificacionTB vCodeTB) {
		super.create(vCodeTB);
		return vCodeTB;
	}

	@Override
	public CodigoVerificacionTB modificar(CodigoVerificacionTB vCodeTB) {
		super.update(vCodeTB);
		return vCodeTB;
	}

	@Override
	public void eliminar(long idCodigoVerificacion) {
		super.setClazz(CodigoVerificacionTB.class);
		deleteById(idCodigoVerificacion);
	}

	@Override
	public CodigoVerificacionTB consultarVCodePorCorreo(String email) {
		CodigoVerificacionTB vCodeTB = new CodigoVerificacionTB();
		if (StringUtils.isNotBlank(email)) {
			// PARAMETROS
			Map<String, Object> pamameters = new HashMap<>();

			// QUERY
			StringBuilder JPQL = new StringBuilder("SELECT t FROM CodigoVerificacionTB t WHERE 1 = 1 ");
			// Q. Usuario
			JPQL.append(" AND LOWER(t.email) = LOWER(:EMAIL) ");
			pamameters.put("EMAIL", email);
			// Q. Order By
			JPQL.append(" ORDER BY t.idCodigoVerificacion DESC ");
			// END QUERY

			TypedQuery<CodigoVerificacionTB> query = em.createQuery(JPQL.toString(), CodigoVerificacionTB.class);
			pamameters.forEach((k, v) -> query.setParameter(k, v));

			List<CodigoVerificacionTB> listResult = query.getResultList();
			if (listResult != null && !listResult.isEmpty()) {
				vCodeTB = listResult.get(0);
			}
		}

		return vCodeTB;
	}

	@Override
	public CodigoVerificacionTB consultarVCodePorCodigoVerificacion(CodigoVerificacionTB codigoVerificacionTB) {
		CodigoVerificacionTB vCodeResultTB = new CodigoVerificacionTB();

		if (StringUtils.isNotBlank(codigoVerificacionTB.getToken())) {
			// PARAMETROS
			Map<String, Object> pamameters = new HashMap<>();

			// QUERY
			StringBuilder JPQL = new StringBuilder("SELECT t FROM CodigoVerificacionTB t WHERE 1 = 1 ");
			// Q. Usuario
			JPQL.append(" AND LOWER(t.token) = LOWER(:VCODE) ");
			pamameters.put("VCODE", codigoVerificacionTB.getToken());
			// Q. Order By
			JPQL.append(" ORDER BY t.idCodigoVerificacion DESC ");
			// END QUERY

			TypedQuery<CodigoVerificacionTB> query = em.createQuery(JPQL.toString(), CodigoVerificacionTB.class);
			pamameters.forEach((k, v) -> query.setParameter(k, v));

			List<CodigoVerificacionTB> listResult = query.getResultList();
			if (listResult != null && !listResult.isEmpty()) {
				vCodeResultTB = listResult.get(0);
			}
		}

		return vCodeResultTB;
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

}
