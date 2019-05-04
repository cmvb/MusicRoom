package com.proyectos.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.ISesionDao;
import com.proyectos.dao.IUsuarioDao;
import com.proyectos.enums.EEstado;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.IUsuarioService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.Util;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDAO;

	@Autowired
	private ISesionDao sesionDAO;

	@Transactional
	@Override
	public SesionTB login(UsuarioTB usuario) {
		SesionTB sesion = sesionDAO.login(usuario.getUsuario(), usuario.getPassword());

		if (sesion != null && sesion.getUsuarioTb() != null) {
			if (sesion.getUsuarioTb().getEstado() == EEstado.ACTIVO.ordinal()) {
				sesion.setTokenSesion(Util.generarToken(usuario.getUsuario()));
				sesion.setIdSesion(sesionDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_SESION_TB));
				sesion = sesionDAO.crear(sesion);
			}
		}

		return sesion;
	}

	@Override
	public List<UsuarioTB> consultarTodos() {
		return usuarioDAO.consultarTodos();
	}

	@Override
	public List<UsuarioTB> consultarPorFiltros(UsuarioTB usuarioFiltro) {
		return usuarioDAO.consultarPorFiltros(usuarioFiltro);
	}

	@Override
	public UsuarioTB consultarPorId(long idUsuario) {
		return usuarioDAO.consultarPorId(idUsuario);
	}

	@Transactional
	@Override
	public UsuarioTB crear(UsuarioTB usuario) {
		usuario.setIdUsuario(usuarioDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_USUARIO_TB));
		return usuarioDAO.crear(usuario);
	}

	@Transactional
	@Override
	public UsuarioTB modificar(UsuarioTB usuario) {
		return usuarioDAO.modificar(usuario);
	}

	@Transactional
	@Override
	public void eliminar(long idUsuario) {
		usuarioDAO.eliminar(idUsuario);
	}
}
