package com.proyectos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.UbicacionTB;
import com.proyectos.service.IUbicacionService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;

@RestController
@RequestMapping("/music-room/ubicacion")
public class ControladorRestUbicacion {

	@Autowired
	IUbicacionService ubicacionService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UbicacionTB>> consultarTodos() {
		return new ResponseEntity<List<UbicacionTB>>(ubicacionService.consultarTodos(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UbicacionTB> consultarPorId(@PathVariable("id") Long idUbicacion) {
		UbicacionTB ubicacion = ubicacionService.consultarPorId(idUbicacion);
		if (ubicacion == null) {
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.ubicacionNoEncontrado", idUbicacion);

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<UbicacionTB>(ubicacion, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<UbicacionTB>> consultarPorFiltros(@RequestBody UbicacionTB ubicacion) {
		return new ResponseEntity<List<UbicacionTB>>(ubicacionService.consultarPorFiltros(ubicacion), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearUbicacion")
	public ResponseEntity<UbicacionTB> crear(@RequestBody UbicacionTB ubicacion) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_USUARIO_TB, ubicacion);
		UbicacionTB ubicacionNuevo = new UbicacionTB();
		if (errores.isEmpty()) {
			ubicacionNuevo = new UbicacionTB();
			ubicacionNuevo = ubicacionService.crear(ubicacion);
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<UbicacionTB>(ubicacionNuevo, HttpStatus.OK);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarUbicacion")
	public ResponseEntity<UbicacionTB> modificar(@RequestBody UbicacionTB ubicacion) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_USUARIO_TB, ubicacion);
		UbicacionTB ubicacionNuevo = new UbicacionTB();
		if (errores.isEmpty()) {
			ubicacionNuevo = new UbicacionTB();
			ubicacionNuevo = ubicacionService.modificar(ubicacion);
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<UbicacionTB>(ubicacionNuevo, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/eliminarUbicacion")
	public void eliminar(@RequestBody UbicacionTB ubicacion) {
		if (ubicacion != null) {
			ubicacionService.eliminar(ubicacion.getIdUbicacion());
		}
	}

}
