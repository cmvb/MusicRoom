package com.proyectos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class ControladorRest {

	@Autowired
	IUsuarioService usuarioService;

	@GetMapping
	public List<UsuarioTB> consultarTodos() {
		return usuarioService.consultarTodos();
	}

	@PostMapping
	@RequestMapping("/consultarPorFiltros")
	public List<UsuarioTB> consultarPorFiltros(@RequestBody UsuarioTB usuario) {
		return usuarioService.consultarPorFiltros(usuario);
	}

	@GetMapping(value = "/{id}")
	public UsuarioTB consultarPorId(@PathVariable("id") Long idUsuario) {
		UsuarioTB usuario = usuarioService.consultarPorId(idUsuario);
		if (usuario == null) {
			throw new ModelNotFoundException("Usuario con el ID: " + idUsuario + " no fue encontrado.");
		}

		return usuario;
	}

	@PostMapping
	@RequestMapping("/crearUsuario")
	public UsuarioTB crear(@RequestBody UsuarioTB usuario) {
		return usuarioService.crear(usuario);
	}

	@PutMapping
	@RequestMapping("/modificarUsuario")
	public UsuarioTB modificar(@RequestBody UsuarioTB usuario) {
		return usuarioService.modificar(usuario);
	}

	@DeleteMapping
	@RequestMapping("/eliminarUsuario")
	public void eliminar(@PathVariable("id") Long idUsuario) {
		usuarioService.eliminar(idUsuario);
	}

}
