package com.proyectos.service;

import com.proyectos.model.ResetTokenTB;

public interface IResetTokenService {

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

}
