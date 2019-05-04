package com.proyectos.service;

import java.util.List;

import com.proyectos.model.UbicacionTB;

public interface IUbicacionService {

	/*
	 * Metodo para consultar todos los ubicaciones
	 */
	public List<UbicacionTB> consultarTodos();

	/*
	 * Metodo para consultar los ubicaciones que cumplan con los filtros
	 */
	public List<UbicacionTB> consultarPorFiltros(UbicacionTB ubicacionFiltro);

	/*
	 * Metodo para consultar un ubicación por su ID
	 */
	public UbicacionTB consultarPorId(long idUbicacion);

	/*
	 * Metodo para crear ubicaciones
	 */
	public UbicacionTB crear(UbicacionTB ubicacion);

	/*
	 * Metodo para modificar ubicaciones
	 */
	public UbicacionTB modificar(UbicacionTB ubicacion);

	/*
	 * Metodo para eliminar un ubicación por su ID
	 */
	public void eliminar(long idUbicacion);

}
