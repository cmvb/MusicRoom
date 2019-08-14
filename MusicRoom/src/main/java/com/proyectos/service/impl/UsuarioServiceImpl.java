package com.proyectos.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyectos.dao.ICodigoVerificacionDao;
import com.proyectos.dao.IRolDao;
import com.proyectos.dao.ISesionDao;
import com.proyectos.dao.IUsuarioDao;
import com.proyectos.enums.EEstado;
import com.proyectos.model.CodigoVerificacionTB;
import com.proyectos.model.RolTB;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.IUsuarioService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.Util;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

	@Autowired
	private ISesionDao sesionDAO;

	@Autowired
	private IUsuarioDao usuarioDAO;

	@Autowired
	private IRolDao rolDAO;

	@Autowired
	private ICodigoVerificacionDao vCodeDAO;

	@Transactional
	@Override
	public SesionTB login(UsuarioTB usuario) {
		SesionTB sesion = sesionDAO.login(usuario.getUsuario(), usuario.getPassword());

		if (sesion != null && sesion.getUsuarioTb() != null) {
			if (sesion.getUsuarioTb().getEstado() == EEstado.ACTIVO.ordinal()) {
				// Se inactivan las sesiones anteriores
				sesionDAO.inactivarRegistrosToken();

				sesion.setTokenSesion(Util.generarToken(usuario.getUsuario()));
				sesion.setIdSesion(sesionDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_SESION_TB));
				sesion = sesionDAO.crear(sesion);
			}
		}

		return sesion;
	}

	@Transactional
	@Override
	public SesionTB consultarSesionPorToken(String tokenSesion) {
		return sesionDAO.consultarPorToken(tokenSesion);
	}

	@Transactional
	@Override
	public SesionTB modificarSesion(SesionTB sesion) {
		return sesionDAO.modificar(sesion);
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
	public CodigoVerificacionTB crearVCode(CodigoVerificacionTB vCodeTB) {
		vCodeTB.setIdCodigoVerificacion(vCodeDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_CODIGO_VERIFICACION_TB));
		return vCodeDAO.crear(vCodeTB);
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioTB usuario = usuarioDAO.consultarPorUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException(String.format("Usuario no Existe", username));
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		usuario.getListaRoles().forEach(rol -> {
			authorities.add(new SimpleGrantedAuthority(rol.getCodigo()));
		});

		UserDetails userDetails = new User(usuario.getUsuario(), usuario.getPassword(), authorities);

		return userDetails;
	}

	@Override
	public CodigoVerificacionTB consultarVCodePorCorreo(String email) {
		return vCodeDAO.consultarVCodePorCorreo(email);
	}

	@Override
	public List<RolTB> consultarRolesListaCodigosRol(List<String> listaRoles) {
		return rolDAO.consultarRolesListaCodigosRol(listaRoles);
	}

	@Override
	public CodigoVerificacionTB consultarVCodePorCodigoVerificacion(CodigoVerificacionTB vCode) {
		return vCodeDAO.consultarVCodePorCodigoVerificacion(vCode);
	}
}
