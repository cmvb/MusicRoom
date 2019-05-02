package com.proyectos.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.SesionTB;

@EnableTransactionManagement
public interface ISesionDao {

	/*
	 * Metodo para realizar el Logueo de la Aplicación
	 */
	@Query
	SesionTB login(String usuario, String password);

	/*
	 * Metodo para crear sesiones
	 */
	SesionTB crear(SesionTB sesion);

	/*
	 * Metodo para modificar sesiones
	 */
	SesionTB modificar(SesionTB sesion);

	/*
	 * Metodo para eliminar una sesion por su ID
	 */
	void eliminar(long idSesion);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);

}
