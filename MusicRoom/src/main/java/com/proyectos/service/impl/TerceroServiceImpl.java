package com.proyectos.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.ITerceroDao;
import com.proyectos.model.TerceroTB;
import com.proyectos.service.ITerceroService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class TerceroServiceImpl implements ITerceroService {

	@Autowired
	private ITerceroDao terceroDAO;

	@Override
	public List<TerceroTB> consultarTodos() {
		return terceroDAO.consultarTodos();
	}

	@Override
	public List<TerceroTB> consultarPorFiltros(TerceroTB terceroFiltro) {
		return terceroDAO.consultarPorFiltros(terceroFiltro);
	}

	@Override
	public TerceroTB consultarPorId(long idTercero) {
		return terceroDAO.consultarPorId(idTercero);
	}

	@Transactional
	@Override
	public TerceroTB crear(TerceroTB tercero) {
		tercero.setIdTercero(terceroDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_TERCERO_TB));
		return terceroDAO.crear(tercero);
	}

	@Transactional
	@Override
	public TerceroTB modificar(TerceroTB tercero) {
		return terceroDAO.modificar(tercero);
	}

	@Transactional
	@Override
	public void eliminar(long idTercero) {
		terceroDAO.eliminar(idTercero);
	}
}
