package com.proyectos.service;

import java.util.List;

import com.proyectos.model.SalaTB;

public interface ISalaService {

	/*
	 * Metodo para consultar todas las salas
	 */
	public List<SalaTB> consultarTodos();

	/*
	 * Metodo para consultar las salas que cumplan con los filtros
	 */
	public List<SalaTB> consultarPorFiltros(SalaTB salaFiltro);

	/*
	 * Metodo para consultar una Sala por su ID
	 */
	public SalaTB consultarPorId(long idSala);

	/*
	 * Metodo para crear Salas
	 */
	public SalaTB crear(SalaTB sala);

	/*
	 * Metodo para modificar Salas
	 */
	public SalaTB modificar(SalaTB sala);

	/*
	 * Metodo para eliminar una Sala por su ID
	 */
	public void eliminar(long idSala);


}
