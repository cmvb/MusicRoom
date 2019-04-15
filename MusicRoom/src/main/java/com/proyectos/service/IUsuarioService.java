package com.proyectos.service;

import java.util.List;

import com.proyectos.model.UsuarioTB;

public interface IUsuarioService {

	/*
	 * Metodo para crear usuarios
	 */
	public UsuarioTB crear(UsuarioTB idUsuario);

	/*
	 * Metodo para modificar usuarios
	 */
	public UsuarioTB modificar(UsuarioTB usuario);

	/*
	 * Metodo para eliminar un usuario por su ID
	 */
	public void eliminar(long idUsuario);

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

}
