package com.proyectos.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.model.dto.BandaIntegranteDTO;

@EnableTransactionManagement
public interface IBandaDao {

	/*
	 * Metodo para consultar todas las bandas
	 */
	List<BandaTB> consultarTodos();

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
	BandaTB consultarPorId(long idBanda);

	/*
	 * Metodo para crear bandas
	 */
	BandaTB crear(BandaTB banda);

	/*
	 * Metodo para modificar bandas
	 */
	BandaTB modificar(BandaTB banda);

	/*
	 * Metodo para eliminar una banda por su ID
	 */
	void eliminar(long idBanda);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);
}
