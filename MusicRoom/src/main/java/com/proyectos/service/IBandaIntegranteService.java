package com.proyectos.service;

import java.util.HashMap;
import java.util.List;

import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.model.dto.BandaIntegranteDTO;

public interface IBandaIntegranteService {

	/*
	 * Metodo para consultar todas las bandas con todos sus integrantes
	 */
	List<BandaTB> consultarTodasBandas();

	/*
	 * Metodo para consultar un mapa de todas las bandas con sus integrantes
	 */
	HashMap<BandaTB, List<IntegranteTB>> consultarMapaBandaXIntegrante();

	/*
	 * Metodo para consultar todas las bandas a las que pertenece el integrante por
	 * su ID
	 */
	List<BandaTB> consultarBandas(long idIntegrante);

	/*
	 * Metodo para consultar las bandas que cumplan con los filtros
	 */
	List<BandaIntegranteDTO> consultarPorFiltros(BandaTB bandaFiltro);

	/*
	 * Metodo para consultar una banda por su ID
	 */
	BandaTB consultarBandaPorId(long idBanda);

	/*
	 * Metodo para crear bandas con sus integrantes
	 */
	BandaIntegranteDTO crear(BandaIntegranteDTO bandaIntegrante);

	/*
	 * Metodo para modificar bandas con sus integrantes
	 */
	BandaIntegranteDTO modificar(BandaIntegranteDTO bandaIntegrante);

	/*
	 * Metodo para eliminar una banda por su ID
	 */
	void eliminarBanda(long idBanda);

	/*
	 * Metodo para consultar todos los integrantes
	 */
	List<IntegranteTB> consultarTodosIntegrantes();

	/*
	 * Metodo para consultar los integrantes de la banda por su ID
	 */
	List<IntegranteTB> consultarIntegrantes(long idBanda);

	/*
	 * Metodo para consultar las integrantes que cumplan con los filtros
	 */
	List<IntegranteTB> consultarPorFiltros(IntegranteTB integranteFiltro);

	/*
	 * Metodo para consultar una integrante por su ID
	 */
	IntegranteTB consultarIntegrantePorId(long idIntegrante);

	/*
	 * Metodo para eliminar una integrante por su ID
	 */
	void eliminarIntegrante(long idIntegrante);

}
