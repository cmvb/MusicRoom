package com.proyectos.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.proyectos.model.TerceroTB;
import com.proyectos.service.ITerceroService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;

@RestController
@RequestMapping("/music-room/tercero")
public class ControladorRestTercero {

	@Autowired
	ITerceroService terceroService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TerceroTB>> consultarTodos() {
		return new ResponseEntity<List<TerceroTB>>(terceroService.consultarTodos(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TerceroTB> consultarPorId(@PathVariable("id") Long idTercero) {
		TerceroTB tercero = terceroService.consultarPorId(idTercero);
		if (tercero == null) {
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.terceroNoEncontrado", idTercero);

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<TerceroTB>(tercero, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<TerceroTB>> consultarPorFiltros(@RequestBody TerceroTB tercero) {
		return new ResponseEntity<List<TerceroTB>>(terceroService.consultarPorFiltros(tercero), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearTercero")
	public ResponseEntity<TerceroTB> crear(@RequestBody TerceroTB tercero) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_TERCERO_TB, tercero);
		TerceroTB terceroNuevo = new TerceroTB();
		if (errores.isEmpty()) {
			List<TerceroTB> listaTerceros = terceroService.consultarTodos();
			Map<String, TerceroTB> mapaTerceros = new HashMap<>();
			Set<String> nombresTerceros = new HashSet<>();

			for (TerceroTB terceroTb : listaTerceros) {
				if (!mapaTerceros.containsKey(terceroTb.getNit())) {
					mapaTerceros.put(terceroTb.getNit(), terceroTb);
					nombresTerceros.add(terceroTb.getRazonSocial());
				}
			}

			if (mapaTerceros.containsKey(tercero.getNit())) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_nit_repetido"));
			}
			if (nombresTerceros.contains(tercero.getRazonSocial())) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_razon_social_repetida"));
			}

			if (errores.isEmpty()) {
				terceroNuevo = new TerceroTB();
				terceroNuevo = terceroService.crear(tercero);
			} else {
				StringBuilder mensajeErrores = new StringBuilder();
				String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

				for (String error : errores) {
					mensajeErrores.append(error);
				}

				throw new ModelNotFoundException(erroresTitle + mensajeErrores);
			}
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<TerceroTB>(terceroNuevo, HttpStatus.OK);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarTercero")
	public ResponseEntity<TerceroTB> modificar(@RequestBody TerceroTB tercero) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_TERCERO_TB, tercero);
		TerceroTB terceroNuevo = new TerceroTB();
		if (errores.isEmpty()) {
			List<TerceroTB> listaTerceros = terceroService.consultarTodos();
			Map<String, TerceroTB> mapaTerceros = new HashMap<>();
			Set<String> nombresTerceros = new HashSet<>();

			for (TerceroTB terceroTb : listaTerceros) {
				if (!mapaTerceros.containsKey(terceroTb.getNit())
						&& !tercero.getNit().equalsIgnoreCase(terceroTb.getNit())) {
					mapaTerceros.put(terceroTb.getNit(), terceroTb);
					nombresTerceros.add(terceroTb.getRazonSocial());
				}
			}

			if (mapaTerceros.containsKey(tercero.getNit())) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_nit_repetido"));
			}
			if (nombresTerceros.contains(tercero.getRazonSocial())) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_razon_social_repetida"));
			}

			if (errores.isEmpty()) {
				terceroNuevo = new TerceroTB();
				terceroNuevo = terceroService.modificar(tercero);
			} else {
				StringBuilder mensajeErrores = new StringBuilder();
				String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

				for (String error : errores) {
					mensajeErrores.append(error);
				}

				throw new ModelNotFoundException(erroresTitle + mensajeErrores);
			}
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<TerceroTB>(terceroNuevo, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/eliminarTercero")
	public void eliminar(@RequestBody TerceroTB tercero) {
		if (tercero != null) {
			terceroService.eliminar(tercero.getIdTercero());
		}
	}

}
