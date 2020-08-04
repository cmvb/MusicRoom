package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.BandaIntegranteTB;

@EnableTransactionManagement
public interface IBandaIntegranteDao {

	/*
	 * Metodo para consultar una relación integrante/banda por sus IDs
	 */
	BandaIntegranteTB consultarPorIds(long idBanda, long idIntegrante);

	/*
	 * Metodo para consultar una relación integrante/banda por el ID de la banda
	 */
	List<BandaIntegranteTB> consultarPorIdBanda(long idBanda);

	/*
	 * Metodo para consultar una relación integrante/banda por el ID del integrante
	 */
	List<BandaIntegranteTB> consultarPorIdIntegrante(long idIntegrante);

	/*
	 * Metodo para crear una relación integrante/banda
	 */
	BandaIntegranteTB crear(BandaIntegranteTB banda);

	/*
	 * Metodo para eliminar una relación integrante/banda por los IDs
	 */
	void eliminar(long idBanda, long idIntegrante);

	/*
	 * Metodo para eliminar una relación integrante/banda por el ID de la banda
	 */
	void eliminarIntegrantesPorBanda(long idBanda);

}
