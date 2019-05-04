package com.proyectos.service;

import java.util.List;

import com.proyectos.model.TerceroTB;

public interface ITerceroService {

	/*
	 * Metodo para consultar todos los terceros
	 */
	public List<TerceroTB> consultarTodos();

	/*
	 * Metodo para consultar los terceros que cumplan con los filtros
	 */
	public List<TerceroTB> consultarPorFiltros(TerceroTB terceroFiltro);

	/*
	 * Metodo para consultar un tercero por su ID
	 */
	public TerceroTB consultarPorId(long idTercero);

	/*
	 * Metodo para crear terceros
	 */
	public TerceroTB crear(TerceroTB tercero);

	/*
	 * Metodo para modificar terceros
	 */
	public TerceroTB modificar(TerceroTB tercero);

	/*
	 * Metodo para eliminar un tercero por su ID
	 */
	public void eliminar(long idTercero);

}
