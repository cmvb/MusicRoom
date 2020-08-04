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
import com.proyectos.dao.IBandaDao;
import com.proyectos.dao.jpa.IBandaJPARepoDao;
import com.proyectos.model.BandaIntegranteTB;
import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.model.dto.BandaIntegranteDTO;
import com.proyectos.util.Util;

@Repository
public class BandaDaoImpl extends AbstractDao<BandaTB> implements IBandaDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IBandaJPARepoDao bandaJPADAO;

	@Override
	public List<BandaTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM BandaTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idBanda");
		// END QUERY

		TypedQuery<BandaTB> query = em.createQuery(JPQL.toString(), BandaTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public HashMap<BandaTB, List<IntegranteTB>> consultarMapaBandaXIntegrante() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();
		HashMap<BandaTB, List<IntegranteTB>> mapaBandaXIntegrante = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM BandaIntegranteTB t WHERE 1 = 1 ");
		JPQL.append("ORDER BY t.bandaTb.idBanda");
		// END QUERY
		TypedQuery<BandaIntegranteTB> query = em.createQuery(JPQL.toString(), BandaIntegranteTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		List<BandaIntegranteTB> listaBandasIntegrantes = query.getResultList();
		List<IntegranteTB> listaTempIntegrantes = new ArrayList<>();
		for (BandaIntegranteTB bandaIntegrante : listaBandasIntegrantes) {
			if (mapaBandaXIntegrante.isEmpty()) {
				listaTempIntegrantes.add(bandaIntegrante.getIntegranteTb());
				mapaBandaXIntegrante.put(bandaIntegrante.getBandaTb(), listaTempIntegrantes);
			} else {
				if (mapaBandaXIntegrante.containsKey(bandaIntegrante.getBandaTb())) {
					mapaBandaXIntegrante.get(bandaIntegrante.getBandaTb()).add(bandaIntegrante.getIntegranteTb());
				} else {
					listaTempIntegrantes = new ArrayList<>();
					listaTempIntegrantes.add(bandaIntegrante.getIntegranteTb());
					mapaBandaXIntegrante.put(bandaIntegrante.getBandaTb(), listaTempIntegrantes);
				}
			}
		}

		return mapaBandaXIntegrante;
	}

	@Override
	public List<BandaTB> consultarBandas(long idIntegrante) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();
		List<BandaTB> listaBandas = new ArrayList<>();

		if (idIntegrante > 0) {
			// QUERY
			StringBuilder JPQL = new StringBuilder("SELECT t FROM BandaIntegranteTB t WHERE 1 = 1 ");
			// Q. ID Integrante
			JPQL.append(" AND t.integranteTb.idIntegrante = :INTEGRANTE ");
			pamameters.put("INTEGRANTE", idIntegrante);
			JPQL.append("ORDER BY t.bandaTb.idBanda");
			// END QUERY
			TypedQuery<BandaIntegranteTB> query = em.createQuery(JPQL.toString(), BandaIntegranteTB.class);
			pamameters.forEach((k, v) -> query.setParameter(k, v));

			List<BandaIntegranteTB> listaBandasIntegrantes = query.getResultList();
			for (BandaIntegranteTB bandaIntegrante : listaBandasIntegrantes) {
				listaBandas.add(bandaIntegrante.getBandaTb());
			}
		}

		return listaBandas;
	}

	@Override
	public List<BandaIntegranteDTO> consultarPorFiltros(BandaTB bandaFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();
		Map<BandaTB, List<IntegranteTB>> mapaBandaXIntegrante = new HashMap<>();
		List<BandaIntegranteDTO> result = new ArrayList<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM BandaIntegranteTB t WHERE 1 = 1 ");
		// Q. Nombre
		if (StringUtils.isNotBlank(bandaFiltro.getNombreBanda())) {
			JPQL.append(" AND LOWER(t.bandaTb.nombreBanda) LIKE LOWER(:NOMBREBANDA) ");
			pamameters.put("NOMBREBANDA", Util.COMODIN + bandaFiltro.getNombreBanda() + Util.COMODIN);
		}
		// Q. Género
		if (StringUtils.isNotBlank(bandaFiltro.getGenero())) {
			JPQL.append(" AND LOWER(t.bandaTb.genero) LIKE LOWER(:GENERO) ");
			pamameters.put("GENERO", Util.COMODIN + bandaFiltro.getGenero() + Util.COMODIN);
		}
		// Q. Order By
		JPQL.append("ORDER BY t.idBanda");
		// END QUERY

		TypedQuery<BandaIntegranteTB> query = em.createQuery(JPQL.toString(), BandaIntegranteTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		List<BandaIntegranteTB> listaBandaIntegrantes = query.getResultList();
		if (listaBandaIntegrantes != null && !listaBandaIntegrantes.isEmpty()) {
			List<IntegranteTB> listaTempIntegrantes = new ArrayList<>();
			for (BandaIntegranteTB bandaIntegrante : listaBandaIntegrantes) {
				if (mapaBandaXIntegrante.isEmpty()) {
					listaTempIntegrantes.add(bandaIntegrante.getIntegranteTb());
					mapaBandaXIntegrante.put(bandaIntegrante.getBandaTb(), listaTempIntegrantes);
				} else {
					if (mapaBandaXIntegrante.containsKey(bandaIntegrante.getBandaTb())) {
						mapaBandaXIntegrante.get(bandaIntegrante.getBandaTb()).add(bandaIntegrante.getIntegranteTb());
					} else {
						listaTempIntegrantes = new ArrayList<>();
						listaTempIntegrantes.add(bandaIntegrante.getIntegranteTb());
						mapaBandaXIntegrante.put(bandaIntegrante.getBandaTb(), listaTempIntegrantes);
					}
				}
			}
		}

		for (BandaTB banda : mapaBandaXIntegrante.keySet()) {
			BandaIntegranteDTO bandaIntegranteDto = new BandaIntegranteDTO();
			bandaIntegranteDto.setBandaTb(banda);
			bandaIntegranteDto.setListaIntegrantesTb(mapaBandaXIntegrante.get(banda));
			result.add(bandaIntegranteDto);
		}

		return result;
	}

	@Override
	public BandaTB consultarPorId(long idBanda) {
		super.setClazz(BandaTB.class);
		return super.findOne(idBanda);
	}

	@Override
	public BandaTB crear(BandaTB banda) {
		banda = colocarValoresDefecto(banda);
		super.create(banda);
		return banda;
	}

	@Override
	public BandaTB modificar(BandaTB banda) {
		banda = colocarValoresDefecto(banda);
		super.update(banda);
		return banda;
	}

	@Override
	public void eliminar(long idBanda) {
		super.setClazz(BandaTB.class);
		deleteById(idBanda);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private BandaTB colocarValoresDefecto(BandaTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
