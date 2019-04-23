package com.proyectos.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.util.Util;

@Repository
public class SesionDaoImpl extends AbstractDao<SesionTB> implements ISesionDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Override
	public SesionTB login(String usuario, String password) {
		String passwordEncriptada = Util.encriptar(password, String.valueOf(new Date()));

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

		List<UsuarioTB> listaUsuarios = query.getResultList();
		SesionTB sesion = null;
		if (listaUsuarios != null & !listaUsuarios.isEmpty()) {
			UsuarioTB usuarioSesion = listaUsuarios.get(0);
			sesion = new SesionTB();
			sesion.setUsuarioTb(usuarioSesion);
		}

		return sesion;
	}

	@Override
	public SesionTB crear(SesionTB sesion) {
		super.create(sesion);
		return sesion;
	}

	@Override
	public SesionTB modificar(SesionTB sesion) {
		super.update(sesion);
		return sesion;
	}

	@Override
	public void eliminar(long idSesion) {
		deleteById(idSesion);
	}

}
