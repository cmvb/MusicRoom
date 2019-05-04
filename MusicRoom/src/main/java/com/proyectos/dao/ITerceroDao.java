package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.TerceroTB;

@EnableTransactionManagement
public interface ITerceroDao {

	/*
	 * Metodo para consultar todos los terceros
	 */
	List<TerceroTB> consultarTodos();

	/*
	 * Metodo para consultar los terceros que cumplan con los filtros
	 */
	List<TerceroTB> consultarPorFiltros(TerceroTB terceroFiltro);

	/*
	 * Metodo para consultar un tercero por su ID
	 */
	TerceroTB consultarPorId(long idTercero);

	/*
	 * Metodo para crear terceros
	 */
	TerceroTB crear(TerceroTB tercero);

	/*
	 * Metodo para modificar terceros
	 */
	TerceroTB modificar(TerceroTB tercero);

	/*
	 * Metodo para eliminar un tercero por su ID
	 */
	void eliminar(long idTercero);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);
}
