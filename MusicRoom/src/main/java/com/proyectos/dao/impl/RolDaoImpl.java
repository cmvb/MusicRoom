package com.proyectos.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.IRolDao;
import com.proyectos.model.RolTB;
import com.proyectos.util.Util;

@Repository
public class RolDaoImpl extends AbstractDao<RolTB> implements IRolDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public List<RolTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM RolTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idRol");
		// END QUERY

		TypedQuery<RolTB> query = em.createQuery(JPQL.toString(), RolTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<RolTB> consultarPorFiltros(RolTB rolFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM RolTB t WHERE 1 = 1 ");
		// Q. Codigo
		if (StringUtils.isNotBlank(rolFiltro.getCodigo())) {
			JPQL.append("AND LOWER(t.codigo) = LOWER(:CODIGO) ");
			pamameters.put("CODIGO", rolFiltro.getCodigo());
		}
		// Q. Descripcion
		if (StringUtils.isNotBlank(rolFiltro.getDescripcion())) {
			JPQL.append(" AND LOWER(t.descripcion) LIKE LOWER(:DESCRIPCION) ");
			pamameters.put("DESCRIPCION", Util.COMODIN + rolFiltro.getDescripcion() + Util.COMODIN);
		}
		// Q. Path
		if (StringUtils.isNotBlank(rolFiltro.getPath())) {
			JPQL.append("AND t.path = :PATH ");
			pamameters.put("PATH", rolFiltro.getPath());
		}
		// Q. Sub Path
		if (StringUtils.isNotBlank(rolFiltro.getSubPath())) {
			JPQL.append("AND t.subPath = :SUB_PATH ");
			pamameters.put("SUB_PATH", rolFiltro.getSubPath());
		}
		// Q. Order By
		JPQL.append(" ORDER BY t.idRol");
		// END QUERY

		TypedQuery<RolTB> query = em.createQuery(JPQL.toString(), RolTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public RolTB consultarPorId(long idRol) {
		super.setClazz(RolTB.class);
		return super.findOne(idRol);
	}

	@Override
	public RolTB crear(RolTB rol) {
		rol = colocarValoresDefecto(rol);
		super.create(rol);
		return rol;
	}

	@Override
	public RolTB modificar(RolTB rol) {
		rol = colocarValoresDefecto(rol);
		super.update(rol);
		return rol;
	}

	@Override
	public void eliminar(long idRol) {
		super.setClazz(RolTB.class);
		deleteById(idRol);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	@Override
	public String obtenerRolPorPathSubPath(String path, String subPath) {
		String rol = null;

		if (!StringUtils.isBlank(path) && !StringUtils.isBlank(subPath)) {
			// PARAMETROS
			Map<String, Object> pamameters = new HashMap<>();

			// QUERY
			StringBuilder JPQL = new StringBuilder("SELECT t FROM RolTB t WHERE 1 = 1 ");

			// Q. Path
			JPQL.append("AND t.path = :PATH ");
			pamameters.put("PATH", path);
			// Q. Sub Path
			JPQL.append("AND t.subPath = :SUB_PATH ");
			pamameters.put("SUB_PATH", subPath);

			// Q. Order By
			JPQL.append(" ORDER BY t.idRol");
			// END QUERY

			TypedQuery<RolTB> query = em.createQuery(JPQL.toString(), RolTB.class);
			pamameters.forEach((k, v) -> query.setParameter(k, v));

			Optional<RolTB> optionalRolTb = Optional.of(query.getSingleResult());

			if (optionalRolTb.isPresent()) {
				RolTB rolTb = optionalRolTb.get();
				rol = rolTb.getCodigo();
			}
		}

		return rol;
	}

	@Override
	public List<RolTB> consultarRolesListaCodigosRol(List<String> listaCodigosRol) {
		List<RolTB> listaRolesTB = new ArrayList<>();

		if (listaCodigosRol != null && !listaCodigosRol.isEmpty()) {
			// PARAMETROS
			Map<String, Object> pamameters = new HashMap<>();

			// QUERY
			StringBuilder JPQL = new StringBuilder("SELECT t FROM RolTB t WHERE 1 = 1 ");

			// Q. Codigo
			JPQL.append("AND t.codigo IN :CODIGO_ROL ");
			pamameters.put("CODIGO_ROL", listaCodigosRol);

			// Q. Order By
			JPQL.append(" ORDER BY t.idRol");
			// END QUERY

			TypedQuery<RolTB> query = em.createQuery(JPQL.toString(), RolTB.class);
			pamameters.forEach((k, v) -> query.setParameter(k, v));

			listaRolesTB = query.getResultList();
		}

		return listaRolesTB;
	}

	private RolTB colocarValoresDefecto(RolTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
