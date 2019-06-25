package com.proyectos.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IResetTokenDao;
import com.proyectos.model.ResetTokenTB;
import com.proyectos.service.IResetTokenService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class ResetTokenServiceImpl implements IResetTokenService {

	@Autowired
	private IResetTokenDao resetTokenDAO;

	@Override
	public ResetTokenTB consultarPorToken(String token) {
		return resetTokenDAO.consultarPorToken(token);
	}

	@Transactional
	@Override
	public ResetTokenTB crear(ResetTokenTB token) {
		token.setIdToken(resetTokenDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_RESET_TOKEN_TB));
		return resetTokenDAO.crear(token);
	}

	@Transactional
	@Override
	public void eliminar(ResetTokenTB token) {
		resetTokenDAO.eliminar(token);
	}
}
