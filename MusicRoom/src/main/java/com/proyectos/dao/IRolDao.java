package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.RolTB;

@EnableTransactionManagement
public interface IRolDao {

	/*
	 * Metodo para consultar todos los roles
	 */
	List<RolTB> consultarTodos();

	/*
	 * Metodo para consultar los roles que cumplan con los filtros
	 */
	List<RolTB> consultarPorFiltros(RolTB rolFiltro);

	/*
	 * Metodo para consultar un rol por su ID
	 */
	RolTB consultarPorId(long idRol);

	/*
	 * Metodo para crear roles
	 */
	RolTB crear(RolTB rol);

	/*
	 * Metodo para modificar roles
	 */
	RolTB modificar(RolTB usuario);

	/*
	 * Metodo para eliminar un rol por su ID
	 */
	void eliminar(long idUsuario);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);

	/*
	 * Metodo para obtener el rol de un path/subpath especifico
	 */
	String obtenerRolPorPathSubPath(String path, String subPath);

	/*
	 * Metodo para consultar una lista de roles dados sus codigos
	 */
	List<RolTB> consultarRolesListaCodigosRol(List<String> listaRoles);
}
