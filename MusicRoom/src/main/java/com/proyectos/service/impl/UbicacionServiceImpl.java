package com.proyectos.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IUbicacionDao;
import com.proyectos.model.UbicacionTB;
import com.proyectos.service.IUbicacionService;
import com.proyectos.util.ConstantesTablasNombre;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

	@Autowired
	private IUbicacionDao ubicacionDAO;

	@Override
	public List<UbicacionTB> consultarTodos() {
		return ubicacionDAO.consultarTodos();
	}

	@Override
	public List<UbicacionTB> consultarPorTipo(int tipoUbicacion) {
		return ubicacionDAO.consultarPorTipo(tipoUbicacion);
	}

	@Override
	public List<UbicacionTB> consultarPorFiltros(UbicacionTB ubicacionFiltro) {
		return ubicacionDAO.consultarPorFiltros(ubicacionFiltro);
	}

	@Override
	public UbicacionTB consultarPorId(long idUbicacion) {
		return ubicacionDAO.consultarPorId(idUbicacion);
	}

	@Transactional
	@Override
	public UbicacionTB crear(UbicacionTB ubicacion) {
		ubicacion.setIdUbicacion(ubicacionDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_UBICACION_TB));
		return ubicacionDAO.crear(ubicacion);
	}

	@Transactional
	@Override
	public UbicacionTB modificar(UbicacionTB ubicacion) {
		return ubicacionDAO.modificar(ubicacion);
	}

	@Transactional
	@Override
	public void eliminar(long idUbicacion) {
		ubicacionDAO.eliminar(idUbicacion);
	}
}
