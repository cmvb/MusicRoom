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
import com.proyectos.dao.ITerceroDao;
import com.proyectos.model.TerceroTB;
import com.proyectos.model.UbicacionTB;

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
//		// Q. Usuario
//		if (StringUtils.isNotBlank(usuarioFiltro.getUsuario())) {
//			JPQL.append("AND LOWER(t.usuario) = LOWER(:USUARIO) ");
//			pamameters.put("USUARIO", usuarioFiltro.getUsuario());
//		}
//		// Q. Nombre
//		if (StringUtils.isNotBlank(usuarioFiltro.getNombre())) {
//			JPQL.append(" AND LOWER(t.nombre) LIKE LOWER(:NOMBRE) ");
//			pamameters.put("NOMBRE", Util.COMODIN + usuarioFiltro.getNombre() + Util.COMODIN);
//		}
//		// Q. Apellido
//		if (StringUtils.isNotBlank(usuarioFiltro.getApellido())) {
//			JPQL.append(" AND LOWER(t.apellido) LIKE LOWER(:APELLIDO) ");
//			pamameters.put("APELLIDO", Util.COMODIN + usuarioFiltro.getApellido() + Util.COMODIN);
//		}
//		// Q. Número Documento
//		if (StringUtils.isNotBlank(usuarioFiltro.getNumeroDocumento())) {
//			JPQL.append("AND t.numeroDocumento = :NUMERO_DOCUMENTO ");
//			pamameters.put("NUMERO_DOCUMENTO", usuarioFiltro.getNumeroDocumento());
//		}
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
