package com.proyectos.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.ISesionDao;
import com.proyectos.dao.IUsuarioDao;
import com.proyectos.dao.jpa.ISesionJPARepoDao;
import com.proyectos.dao.jpa.IUsuarioJPARepoDao;
import com.proyectos.enums.EEstado;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;

@Repository
public class SesionDaoImpl extends AbstractDao<SesionTB> implements ISesionDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IUsuarioDao usuarioDAO;

	@Autowired
	private ISesionJPARepoDao sesionJPADAO;

	@Autowired
	private IUsuarioJPARepoDao usuarioJPADAO;

	@Override
	public SesionTB login(String usuario, String password) {
		UsuarioTB usuarioSesion = usuarioDAO.consultarPorUsername(usuario);

		SesionTB sesion = null;
		if (usuarioSesion != null && BCrypt.checkpw(password, usuarioSesion.getPassword())) {
			sesion = new SesionTB();
			sesion.setUsuarioTb(usuarioSesion);
		}

		return sesion;
	}

	@Override
	public SesionTB consultarPorToken(String token) {
		SesionTB result = null;

		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM SesionTB t WHERE 1 = 1 ");
		// Q. Usuario
		JPQL.append("AND t.tokenSesion = :TOKEN  ");
		pamameters.put("TOKEN", token);
		// END QUERY

		TypedQuery<SesionTB> query = em.createQuery(JPQL.toString(), SesionTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		Optional<SesionTB> optionalSesionTb = Optional.of(query.getSingleResult());

		if (optionalSesionTb.isPresent()) {
			SesionTB sesionTb = optionalSesionTb.get();
			result = sesionTb;
		}

		return result;
	}

	@Override
	public SesionTB crear(SesionTB sesion) {
		sesion = colocarValoresDefecto(sesion);
		super.create(sesion);
		return sesion;
	}

	@Override
	public SesionTB modificar(SesionTB sesion) {
		sesion = colocarValoresDefecto(sesion);
		super.update(sesion);
		return sesion;
	}

	@Override
	public void inactivarRegistrosToken() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM SesionTB t WHERE 1 = 1 ");
		// END QUERY

		TypedQuery<SesionTB> query = em.createQuery(JPQL.toString(), SesionTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		List<SesionTB> listaSesiones = query.getResultList();
		if (listaSesiones != null) {
			for (SesionTB sesionTb : listaSesiones) {
				sesionTb.setEstado((short) EEstado.INACTIVO.ordinal());
				this.modificar(sesionTb);
			}
		}
	}

	@Override
	public void eliminar(long idSesion) {
		super.setClazz(SesionTB.class);
		deleteById(idSesion);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private SesionTB colocarValoresDefecto(SesionTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
