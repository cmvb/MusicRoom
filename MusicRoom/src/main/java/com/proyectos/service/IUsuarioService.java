package com.proyectos.service;

import java.util.List;

import com.proyectos.model.CodigoVerificacionTB;
import com.proyectos.model.RolTB;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;

public interface IUsuarioService {

	/*
	 * Metodo para realizar el Logueo de la Aplicación
	 */
	public SesionTB login(UsuarioTB usuarioFiltro);

	/*
	 * Metodo para consultar una Sesión por su Token
	 */
	public SesionTB consultarSesionPorToken(String tokenSesion);

	/*
	 * Metodo para modificar sesiones
	 */
	public SesionTB modificarSesion(SesionTB sesion);

	/*
	 * Metodo para consultar todos los usuarios
	 */
	public List<UsuarioTB> consultarTodos();

	/*
	 * Metodo para consultar los usuarios que cumplan con los filtros
	 */
	public List<UsuarioTB> consultarPorFiltros(UsuarioTB usuarioFiltro);

	/*
	 * Metodo para consultar un usuario por su ID
	 */
	public UsuarioTB consultarPorId(long idUsuario);

	/*
	 * Metodo para crear usuarios
	 */
	public UsuarioTB crear(UsuarioTB usuario);

	/*
	 * Metodo para modificar usuarios
	 */
	public UsuarioTB modificar(UsuarioTB usuario);

	/*
	 * Metodo para eliminar un usuario por su ID
	 */
	public void eliminar(long idUsuario);

	/*
	 * Metodo para crear un Codigo de Verificacion del Usuario
	 */
	public CodigoVerificacionTB crearVCode(CodigoVerificacionTB vCodeTB);

	/*
	 * Metodo para consultar un Codigo de Verificacion del Usuario por su email
	 */
	public CodigoVerificacionTB consultarVCodePorCorreo(String email);

	/*
	 * Metodo para consultar una lista de roles dados sus codigos
	 */
	public List<RolTB> consultarRolesListaCodigosRol(List<String> listaRoles);

	/*
	 * Metodo para consultar un registro de verificacion dado su codigo
	 */
	public CodigoVerificacionTB consultarVCodePorCodigoVerificacion(CodigoVerificacionTB vCode);

}
