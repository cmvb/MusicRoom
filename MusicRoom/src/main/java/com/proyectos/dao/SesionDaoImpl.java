package com.proyectos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.util.Util;

@Repository
public class SesionDaoImpl extends AbstractDao<SesionTB> implements ISesionDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IUsuarioDao usuarioDAO;

	@Override
	public SesionTB login(String usuario, String password) {
		String passwordEncriptada = Util.encriptar(password, usuario);

		List<UsuarioTB> listaUsuarios = usuarioDAO.consultarUsuarioPorUsuarioPasswordEnc(usuario, passwordEncriptada);
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
