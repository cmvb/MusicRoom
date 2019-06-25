package com.proyectos.dao;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.ResetTokenTB;

@EnableTransactionManagement
public interface IResetTokenDao {

	/*
	 * Metodo para encontrar un registro por su Token
	 */
	ResetTokenTB consultarPorToken(String token);

	/*
	 * Metodo para guardar un Código de Verificación de un Usuario
	 */
	ResetTokenTB crear(ResetTokenTB token);

	/*
	 * Metodo Metodo para eliminar un Código de Verificación de un Usuario
	 */
	void eliminar(ResetTokenTB token);

	/*
	 * Metodo para obtener el ID de una tabla
	 */
	long obtenerConsecutivo(String tabla);
}
