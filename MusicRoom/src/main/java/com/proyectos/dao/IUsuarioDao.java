package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.UsuarioTB;

@EnableTransactionManagement
public interface IUsuarioDao {

	/*
	 * Metodo para consultar todos los usuarios
	 */
	List<UsuarioTB> consultarTodos();

	/*
	 * Metodo para consultar los usuarios que cumplan con los filtros
	 */
	List<UsuarioTB> consultarPorFiltros(UsuarioTB usuarioFiltro);

	/*
	 * Metodo para consultar un usuario por su ID
	 */
	UsuarioTB consultarPorId(long idUsuario);

	/*
	 * Metodo para crear usuarios
	 */
	UsuarioTB crear(UsuarioTB usuario);

	/*
	 * Metodo para modificar usuarios
	 */
	UsuarioTB modificar(UsuarioTB usuario);

	/*
	 * Metodo para eliminar un usuario por su ID
	 */
	void eliminar(long idUsuario);
}
