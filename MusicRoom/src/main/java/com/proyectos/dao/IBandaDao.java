package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.SalaTB;

@EnableTransactionManagement
public interface IBandaDao {

	/*
	 * Metodo para consultar todos los terceros
	 */
	List<SalaTB> consultarTodos();

	/*
	 * Metodo para consultar las salas que cumplan con los filtros
	 */
	List<SalaTB> consultarPorFiltros(SalaTB salaFiltro);

	/*
	 * Metodo para consultar una sala por su ID
	 */
	SalaTB consultarPorId(long idSala);

	/*
	 * Metodo para crear salas
	 */
	SalaTB crear(SalaTB sala);

	/*
	 * Metodo para modificar salas
	 */
	SalaTB modificar(SalaTB sala);

	/*
	 * Metodo para eliminar una sala por su ID
	 */
	void eliminar(long idSala);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);
}
