package com.proyectos.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.IUsuarioService;

@RestController
@RequestMapping("/music-room/usuario")
public class ControladorRest {

	@Autowired
	IUsuarioService usuarioService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioTB>> consultarTodos() {
		return new ResponseEntity<List<UsuarioTB>>(usuarioService.consultarTodos(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioTB> consultarPorId(@PathVariable("id") Long idUsuario) {
		UsuarioTB usuario = usuarioService.consultarPorId(idUsuario);
		if (usuario == null) {
			throw new ModelNotFoundException("Usuario con el ID: " + idUsuario + " no fue encontrado.");
		}

		return new ResponseEntity<UsuarioTB>(usuario, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<UsuarioTB>> consultarPorFiltros(@Valid @RequestBody UsuarioTB usuario) {
		return new ResponseEntity<List<UsuarioTB>>(usuarioService.consultarPorFiltros(usuario), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearUsuario")
	public ResponseEntity<UsuarioTB> crear(@Valid @RequestBody UsuarioTB usuario) {
		UsuarioTB usuarioNuevo = new UsuarioTB();
		usuarioNuevo = usuarioService.crear(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(usuarioNuevo.getIdUsuario()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarUsuario")
	public ResponseEntity<UsuarioTB> modificar(@Valid @RequestBody UsuarioTB usuario) {
		UsuarioTB usuarioNuevo = new UsuarioTB();
		usuarioNuevo = usuarioService.modificar(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(usuarioNuevo.getIdUsuario()).toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping
	@RequestMapping("/eliminarUsuario")
	public void eliminar(@PathVariable("id") Long idUsuario) {
		usuarioService.eliminar(idUsuario);
	}

}
