package com.proyectos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.proyectos.model.UsuarioTB;

@Repository
public class UsuarioDaoImpl extends AbstractDao<UsuarioTB> implements IUsuarioDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public List<UsuarioTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UsuarioTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idUsuario");
		// END QUERY

		TypedQuery<UsuarioTB> query = em.createQuery(JPQL.toString(), UsuarioTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<UsuarioTB> consultarPorFiltros(UsuarioTB usuarioFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UsuarioTB t WHERE 1 = 1 ");
		// Q. Usuario
		if (StringUtils.isNotBlank(usuarioFiltro.getUsuario())) {
			JPQL.append("AND t.usuario = :USUARIO  ");
			pamameters.put("USUARIO", usuarioFiltro.getUsuario());
		}
		// Q. Nombre
		if (StringUtils.isNotBlank(usuarioFiltro.getNombre())) {
			JPQL.append(" AND LOWER(t.nombre) LIKE LOWER(:NOMBRE) ");
			pamameters.put("NOMBRE", usuarioFiltro.getNombre());
		}
		// Q. Apellido
		if (StringUtils.isNotBlank(usuarioFiltro.getApellido())) {
			JPQL.append(" AND LOWER(t.apellido) LIKE LOWER(:APELLIDO) ");
			pamameters.put("APELLIDO", usuarioFiltro.getApellido());
		}
		// Q. Número Documento
		if (StringUtils.isNotBlank(usuarioFiltro.getNumeroDocumento())) {
			JPQL.append("AND t.numeroDocumento = :NUMERO_DOCUMENTO  ");
			pamameters.put("NUMERO_DOCUMENTO", usuarioFiltro.getNumeroDocumento());
		}
		// Q. Order By
		JPQL.append(" ORDER BY t.idUsuario");
		// END QUERY

		TypedQuery<UsuarioTB> query = em.createQuery(JPQL.toString(), UsuarioTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public UsuarioTB consultarPorId(long idUsuario) {
		return super.findOne(idUsuario);
	}

	@Override
	public UsuarioTB crear(UsuarioTB usuario) {
		super.create(usuario);
		return usuario;
	}

	@Override
	public UsuarioTB modificar(UsuarioTB usuario) {
		super.update(usuario);
		return usuario;
	}

	@Override
	public void eliminar(long idUsuario) {
		deleteById(idUsuario);
	}

}
