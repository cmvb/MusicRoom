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
import com.proyectos.dao.IUsuarioDao;
import com.proyectos.dao.jpa.IUsuarioJPARepoDao;
import com.proyectos.model.UsuarioTB;
import com.proyectos.util.Util;

@Repository
public class UsuarioDaoImpl extends AbstractDao<UsuarioTB> implements IUsuarioDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IUsuarioJPARepoDao usuarioJPADAO;

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
			JPQL.append("AND LOWER(t.usuario) = LOWER(:USUARIO) ");
			pamameters.put("USUARIO", usuarioFiltro.getUsuario());
		}
		// Q. E-Mail
		if (StringUtils.isNotBlank(usuarioFiltro.getEmail())) {
			JPQL.append("AND LOWER(t.email) = LOWER(:EMAIL) ");
			pamameters.put("EMAIL", usuarioFiltro.getEmail());
		}
		// Q. Nombre
		if (StringUtils.isNotBlank(usuarioFiltro.getNombre())) {
			JPQL.append(" AND LOWER(t.nombre) LIKE LOWER(:NOMBRE) ");
			pamameters.put("NOMBRE", Util.COMODIN + usuarioFiltro.getNombre() + Util.COMODIN);
		}
		// Q. Apellido
		if (StringUtils.isNotBlank(usuarioFiltro.getApellido())) {
			JPQL.append(" AND LOWER(t.apellido) LIKE LOWER(:APELLIDO) ");
			pamameters.put("APELLIDO", Util.COMODIN + usuarioFiltro.getApellido() + Util.COMODIN);
		}
		// Q. Número Documento
		if (StringUtils.isNotBlank(usuarioFiltro.getNumeroDocumento())) {
			JPQL.append("AND t.numeroDocumento = :NUMERO_DOCUMENTO ");
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
	public List<UsuarioTB> consultarUsuarioPorUsuarioPasswordEnc(String usuario, String passwordEncriptada) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UsuarioTB t WHERE 1 = 1 ");
		// Q. Usuario
		JPQL.append("AND t.usuario = :USUARIO  ");
		pamameters.put("USUARIO", usuario);
		// Q. Password
		JPQL.append("AND t.password = :PASSWORD  ");
		pamameters.put("PASSWORD", passwordEncriptada);
		// Q. Order By
		JPQL.append(" ORDER BY t.idUsuario");
		// END QUERY

		TypedQuery<UsuarioTB> query = em.createQuery(JPQL.toString(), UsuarioTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public UsuarioTB consultarPorId(long idUsuario) {
		super.setClazz(UsuarioTB.class);
		return super.findOne(idUsuario);
	}

	@Override
	public UsuarioTB crear(UsuarioTB usuario) {
		usuario = colocarValoresDefecto(usuario);
		super.create(usuario);
		return usuario;
	}

	@Override
	public UsuarioTB modificar(UsuarioTB usuario) {
		usuario = colocarValoresDefecto(usuario);
		super.update(usuario);
		return usuario;
	}

	@Override
	public void eliminar(long idUsuario) {
		super.setClazz(UsuarioTB.class);
		deleteById(idUsuario);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private UsuarioTB colocarValoresDefecto(UsuarioTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

	@Override
	public UsuarioTB consultarPorUsername(String usuario) {
		UsuarioTB result = null;

		List<UsuarioTB> x = usuarioJPADAO.findAll();

		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UsuarioTB t WHERE 1 = 1 ");
		// Q. Usuario
		JPQL.append("AND t.usuario = :USUARIO  ");
		pamameters.put("USUARIO", usuario);
		// END QUERY

		TypedQuery<UsuarioTB> query = em.createQuery(JPQL.toString(), UsuarioTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		List<UsuarioTB> optionalUsuarioTb = query.getResultList();
		if (optionalUsuarioTb != null && !optionalUsuarioTb.isEmpty()) {
			UsuarioTB usuarioTb = optionalUsuarioTb.get(0);
			result = usuarioTb;
		}

		return result;
	}

}
