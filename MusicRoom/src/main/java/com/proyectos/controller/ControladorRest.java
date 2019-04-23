package com.proyectos.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.proyectos.enums.EEstado;
import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.IUsuarioService;
import com.proyectos.util.PropertiesUtil;

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
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.usuarioNoEncontrado", idUsuario);

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<UsuarioTB>(usuario, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/login")
	public ResponseEntity<SesionTB> login(@RequestBody UsuarioTB usuario) {
		SesionTB sesion = null;
		String mensaje = "";

		if (usuario != null) {
			if (!StringUtils.isBlank(usuario.getUsuario()) && !StringUtils.isBlank(usuario.getPassword())) {
				sesion = usuarioService.login(usuario);
				if (sesion == null) {
					mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.noAccesoApp.usuarioNoExiste");

					throw new ModelNotFoundException(mensaje);
				} else {
					if (sesion.getUsuarioTb().getEstado() == EEstado.INACTIVO.ordinal()) {
						mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.noAccesoApp.usuarioInactivo");

						throw new ModelNotFoundException(mensaje);
					}
				}
			} else {
				if (usuario != null) {
					mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.camposIncorrectos");
					List<String> labelString = new ArrayList<>();
					if (StringUtils.isBlank(usuario.getUsuario())) {
						labelString.add("Usuario");
					}

					if (StringUtils.isBlank(usuario.getPassword())) {
						labelString.add("Clave");
					}
					mensaje = mensaje + labelString.toString();

					throw new ModelNotFoundException(mensaje);
				}
			}
		} else {
			mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.digitarDataRepetida");

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<SesionTB>(sesion, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<UsuarioTB>> consultarPorFiltros(@RequestBody UsuarioTB usuario) {
		return new ResponseEntity<List<UsuarioTB>>(usuarioService.consultarPorFiltros(usuario), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearUsuario")
	public ResponseEntity<UsuarioTB> crear(@RequestBody UsuarioTB usuario) {
		UsuarioTB usuarioNuevo = new UsuarioTB();
		usuarioNuevo = usuarioService.crear(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(usuarioNuevo.getIdUsuario()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarUsuario")
	public ResponseEntity<UsuarioTB> modificar(@RequestBody UsuarioTB usuario) {
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
