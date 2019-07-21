package com.proyectos.dao;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.CodigoVerificacionTB;

@EnableTransactionManagement
public interface ICodigoVerificacionDao {

	/*
	 * Metodo para crear sesiones
	 */
	CodigoVerificacionTB crear(CodigoVerificacionTB vCodeTB);

	/*
	 * Metodo para modificar sesiones
	 */
	CodigoVerificacionTB modificar(CodigoVerificacionTB vCodeTB);

	/*
	 * Metodo para eliminar una sesion por su ID
	 */
	void eliminar(long idCodigoVerificacion);

	/*
	 * Metodo para obtener el Codigo de Verificacion a partir de su email
	 */
	CodigoVerificacionTB consultarVCodePorCorreo(String email);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);

	/*
	 * Metodo para consultar un registro de verificacion dado su codigo
	 */
	CodigoVerificacionTB consultarVCodePorCodigoVerificacion(CodigoVerificacionTB vCode);

}
