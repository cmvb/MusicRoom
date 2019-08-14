package com.proyectos.dao;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.UbicacionTB;

@EnableTransactionManagement
public interface IUbicacionDao {

	/*
	 * Metodo para consultar todos los ubicaciones
	 */
	List<UbicacionTB> consultarTodos();

	/*
	 * Metodo para consultar los ubicaciones que cumplan con el tipo de ubicación
	 * seleccionado (Pais, Dpto, Ciudad)
	 */
	List<UbicacionTB> consultarPorTipo(int tipoUbicacion);

	/*
	 * Metodo para consultar los ubicaciones que cumplan con los filtros
	 */
	List<UbicacionTB> consultarPorFiltros(UbicacionTB ubicacionFiltro);

	/*
	 * Metodo para consultar un ubicación por su ID
	 */
	UbicacionTB consultarPorId(long idUbicacion);

	/*
	 * Metodo para crear ubicaciones
	 */
	UbicacionTB crear(UbicacionTB ubicacion);

	/*
	 * Metodo para modificar ubicaciones
	 */
	UbicacionTB modificar(UbicacionTB ubicacion);

	/*
	 * Metodo para eliminar un ubicación por su ID
	 */
	void eliminar(long idUbicacion);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);
}
