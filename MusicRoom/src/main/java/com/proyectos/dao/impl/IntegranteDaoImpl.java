package com.proyectos.dao.impl;

import java.util.ArrayList;
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
import com.proyectos.dao.IIntegranteDao;
import com.proyectos.dao.jpa.IIntegranteJPARepoDao;
import com.proyectos.model.BandaIntegranteTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.util.Util;

@Repository
public class IntegranteDaoImpl extends AbstractDao<IntegranteTB> implements IIntegranteDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IIntegranteJPARepoDao integranteJPADAO;

	@Override
	public List<IntegranteTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM IntegranteTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idIntegrante");
		// END QUERY

		TypedQuery<IntegranteTB> query = em.createQuery(JPQL.toString(), IntegranteTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<IntegranteTB> consultarIntegrantes(long idBanda) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();
		List<IntegranteTB> listaIntegrantes = new ArrayList<>();

		if (idBanda > 0) {
			// QUERY
			StringBuilder JPQL = new StringBuilder("SELECT t FROM BandaIntegranteTB t WHERE 1 = 1 ");
			// Q. ID Integrante
			JPQL.append(" AND t.bandaTb.idBanda = :BANDA ");
			pamameters.put("BANDA", idBanda);
			JPQL.append("ORDER BY t.bandaTb.idBanda");
			// END QUERY
			TypedQuery<BandaIntegranteTB> query = em.createQuery(JPQL.toString(), BandaIntegranteTB.class);
			pamameters.forEach((k, v) -> query.setParameter(k, v));

			List<BandaIntegranteTB> listaBandasIntegrantes = query.getResultList();
			for (BandaIntegranteTB bandaIntegrante : listaBandasIntegrantes) {
				listaIntegrantes.add(bandaIntegrante.getIntegranteTb());
			}
		}

		return listaIntegrantes;
	}

	@Override
	public List<IntegranteTB> consultarPorFiltros(IntegranteTB integranteFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM IntegranteTB t WHERE 1 = 1 ");
		// Q. Nombre
		if (StringUtils.isNotBlank(integranteFiltro.getNombreIntegrante())) {
			JPQL.append(" AND LOWER(t.nombreIntegrante) LIKE LOWER(:NOMBREINTEGRANTE) ");
			pamameters.put("NOMBREINTEGRANTE", Util.COMODIN + integranteFiltro.getNombreIntegrante() + Util.COMODIN);
		}
		// Q. Instrumento
		if (integranteFiltro.getInstrumentoAccesorio() >= 0) {
			JPQL.append(" AND t.instrumentoAccesorio = :INSTRUMENTOACCESORIO) ");
			pamameters.put("INSTRUMENTOACCESORIO", integranteFiltro.getInstrumentoAccesorio());
		}
		// Q. Order By
		JPQL.append("ORDER BY t.idIntegrante");
		// END QUERY

		TypedQuery<IntegranteTB> query = em.createQuery(JPQL.toString(), IntegranteTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public IntegranteTB consultarPorId(long idIntegrante) {
		super.setClazz(IntegranteTB.class);
		return super.findOne(idIntegrante);
	}

	@Override
	public IntegranteTB crear(IntegranteTB integrante) {
		integrante = colocarValoresDefecto(integrante);
		super.create(integrante);
		return integrante;
	}

	@Override
	public IntegranteTB modificar(IntegranteTB integrante) {
		integrante = colocarValoresDefecto(integrante);
		super.update(integrante);
		return integrante;
	}

	@Override
	public void eliminar(long idIntegrante) {
		super.setClazz(IntegranteTB.class);
		deleteById(idIntegrante);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private IntegranteTB colocarValoresDefecto(IntegranteTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
