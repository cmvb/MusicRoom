package com.proyectos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IUsuarioDao;
import com.proyectos.model.UsuarioTB;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao dao;

	@Transactional
	@Override
	public UsuarioTB crear(UsuarioTB usuario) {
		return dao.save(usuario);
	}

	@Transactional
	@Override
	public UsuarioTB modificar(UsuarioTB usuario) {
		return dao.save(usuario);
	}

	@Transactional
	@Override
	public void eliminar(long idUsuario) {
		dao.delete(idUsuario);
	}

	@Override
	public List<UsuarioTB> consultarTodos() {
		return dao.findAll();
	}

	@Override
	public List<UsuarioTB> consultarPorFiltros(UsuarioTB usuarioFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioTB consultarPorId(long idUsuario) {
		return dao.findOne(idUsuario);
	}

}
