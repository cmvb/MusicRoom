package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.IntegranteTB;

@EnableTransactionManagement
public interface IIntegranteDao {

	/*
	 * Metodo para consultar todos los integrantes
	 */
	List<IntegranteTB> consultarTodos();

	/*
	 * Metodo para consultar las integrantes que cumplan con los filtros
	 */
	List<IntegranteTB> consultarPorFiltros(IntegranteTB integranteFiltro);

	/*
	 * Metodo para consultar una integrante por su ID
	 */
	IntegranteTB consultarPorId(long idIntegrante);

	/*
	 * Metodo para crear integrantes
	 */
	IntegranteTB crear(IntegranteTB integrante);

	/*
	 * Metodo para modificar integrantes
	 */
	IntegranteTB modificar(IntegranteTB integrante);

	/*
	 * Metodo para eliminar una integrante por su ID
	 */
	void eliminar(long idIntegrante);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);
}
