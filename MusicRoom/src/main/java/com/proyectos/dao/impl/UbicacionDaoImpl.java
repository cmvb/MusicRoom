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
import com.proyectos.dao.IUbicacionDao;
import com.proyectos.model.UbicacionTB;

@Repository
public class UbicacionDaoImpl extends AbstractDao<UbicacionTB> implements IUbicacionDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public List<UbicacionTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UbicacionTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idUbicacion");
		// END QUERY

		TypedQuery<UbicacionTB> query = em.createQuery(JPQL.toString(), UbicacionTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<UbicacionTB> consultarPorFiltros(UbicacionTB ubicacionFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UbicacionTB t WHERE 1 = 1 ");
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
		JPQL.append(" ORDER BY t.idUbicacion");
		// END QUERY

		TypedQuery<UbicacionTB> query = em.createQuery(JPQL.toString(), UbicacionTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public UbicacionTB consultarPorId(long idUbicacion) {
		return super.findOne(idUbicacion);
	}

	@Override
	public UbicacionTB crear(UbicacionTB ubicacion) {
		ubicacion = colocarValoresDefecto(ubicacion);
		super.create(ubicacion);
		return ubicacion;
	}

	@Override
	public UbicacionTB modificar(UbicacionTB ubicacion) {
		ubicacion = colocarValoresDefecto(ubicacion);
		super.update(ubicacion);
		return ubicacion;
	}

	@Override
	public void eliminar(long idUbicacion) {
		super.setClazz(UbicacionTB.class);
		deleteById(idUbicacion);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private UbicacionTB colocarValoresDefecto(UbicacionTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
