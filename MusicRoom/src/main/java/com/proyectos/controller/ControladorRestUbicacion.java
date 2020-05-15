package com.proyectos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.enums.ETipoUbicacion;
import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.UbicacionTB;
import com.proyectos.service.IUbicacionService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;

@RestController
@RequestMapping("/music-room/ubicacion")
public class ControladorRestUbicacion {

	private final String PERMISO_ADMINISTRADOR = "@restAuthService.hasAccess('/music-room', '/')";

	@Autowired
	IUbicacionService ubicacionService;

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/consultarTodos') or " + PERMISO_ADMINISTRADOR)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UbicacionTB>> consultarTodos() {
		return new ResponseEntity<List<UbicacionTB>>(ubicacionService.consultarTodos(), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/consultarPorId') or " + PERMISO_ADMINISTRADOR)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UbicacionTB> consultarPorId(@PathVariable("id") Long idUbicacion) {
		UbicacionTB ubicacion = ubicacionService.consultarPorId(idUbicacion);
		if (ubicacion == null) {
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.ubicacionNoEncontrado", idUbicacion);

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<UbicacionTB>(ubicacion, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/consultarPorTipo') or "
			+ PERMISO_ADMINISTRADOR)
	@GetMapping(value = "/consultarPorTipo/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UbicacionTB>> consultarPorTipo(@PathVariable("tipo") Integer tipoUbicacion) {
		return new ResponseEntity<List<UbicacionTB>>(ubicacionService.consultarPorTipo(tipoUbicacion), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/consultarPorFiltros') or "
			+ PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<UbicacionTB>> consultarPorFiltros(@RequestBody UbicacionTB ubicacion) {
		return new ResponseEntity<List<UbicacionTB>>(ubicacionService.consultarPorFiltros(ubicacion), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/crearUbicacion') or " + PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearUbicacion")
	public ResponseEntity<UbicacionTB> crear(@RequestBody UbicacionTB ubicacion) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_UBICACION_TB, ubicacion);

		UbicacionTB ubicacionNuevo = new UbicacionTB();
		if (errores.isEmpty()) {
			List<UbicacionTB> listaUbicaciones = ubicacionService.consultarTodos();
			Map<String, UbicacionTB> mapaPaises = new HashMap<>();
			Map<String, UbicacionTB> mapaDepartamentos = new HashMap<>();
			Map<String, UbicacionTB> mapaCiudades = new HashMap<>();

			for (UbicacionTB ubicacionTb : listaUbicaciones) {
				if (ubicacionTb.getTipoUbicacion() == ETipoUbicacion.PAIS.ordinal()) {
					if (!mapaPaises.containsKey(ubicacionTb.getCodigoPais())) {
						mapaPaises.put(ubicacionTb.getCodigoPais(), ubicacionTb);
					}
				} else if (ubicacionTb.getTipoUbicacion() == ETipoUbicacion.DEPARTAMENTO.ordinal()) {
					if (!mapaDepartamentos.containsKey(ubicacionTb.getCodigoDepartamento())) {
						mapaDepartamentos.put(ubicacionTb.getCodigoDepartamento(), ubicacionTb);
					}
				} else if (ubicacionTb.getTipoUbicacion() == ETipoUbicacion.CIUDAD.ordinal()) {
					if (!mapaCiudades.containsKey(ubicacionTb.getCodigoCiudad())) {
						mapaCiudades.put(ubicacionTb.getCodigoCiudad(), ubicacionTb);
					}
				}
			}

			ETipoUbicacion tipoUbicacionGuardar = ETipoUbicacion.values()[ubicacion.getTipoUbicacion()];

			switch (tipoUbicacionGuardar) {
			case PAIS:
				if (mapaPaises.containsKey(ubicacion.getCodigoPais())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_pais_repetido"));
				}

				break;
			case DEPARTAMENTO:
				if (mapaDepartamentos.containsKey(ubicacion.getCodigoDepartamento())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_departamento_repetido"));
				}

				break;
			case CIUDAD:
				if (mapaCiudades.containsKey(ubicacion.getCodigoCiudad())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_ciudad_repetida"));
				}

				break;
			}

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

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/modificarUbicacion') or "
			+ PERMISO_ADMINISTRADOR)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarUbicacion")
	public ResponseEntity<UbicacionTB> modificar(@RequestBody UbicacionTB ubicacion) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_UBICACION_TB, ubicacion);

		UbicacionTB ubicacionNuevo = new UbicacionTB();
		if (errores.isEmpty()) {
			List<UbicacionTB> listaUbicaciones = ubicacionService.consultarTodos();
			Map<String, UbicacionTB> mapaPaises = new HashMap<>();
			Map<String, UbicacionTB> mapaDepartamentos = new HashMap<>();
			Map<String, UbicacionTB> mapaCiudades = new HashMap<>();

			for (UbicacionTB ubicacionTb : listaUbicaciones) {
				if (ubicacionTb.getTipoUbicacion() == ETipoUbicacion.PAIS.ordinal()) {
					if (!mapaPaises.containsKey(ubicacionTb.getCodigoPais())
							&& ubicacion.getIdUbicacion() != ubicacionTb.getIdUbicacion()) {
						mapaPaises.put(ubicacionTb.getCodigoPais(), ubicacionTb);
					}
				} else if (ubicacionTb.getTipoUbicacion() == ETipoUbicacion.DEPARTAMENTO.ordinal()) {
					if (!mapaDepartamentos.containsKey(ubicacionTb.getCodigoDepartamento())
							&& ubicacion.getIdUbicacion() != ubicacionTb.getIdUbicacion()) {
						mapaDepartamentos.put(ubicacionTb.getCodigoDepartamento(), ubicacionTb);
					}
				} else if (ubicacionTb.getTipoUbicacion() == ETipoUbicacion.CIUDAD.ordinal()) {
					if (!mapaCiudades.containsKey(ubicacionTb.getCodigoCiudad())
							&& ubicacion.getIdUbicacion() != ubicacionTb.getIdUbicacion()) {
						mapaCiudades.put(ubicacionTb.getCodigoCiudad(), ubicacionTb);
					}
				}
			}

			ETipoUbicacion tipoUbicacionGuardar = ETipoUbicacion.values()[ubicacion.getTipoUbicacion()];

			switch (tipoUbicacionGuardar) {
			case PAIS:
				if (mapaPaises.containsKey(ubicacion.getCodigoPais())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_pais_repetido"));
				}

				break;
			case DEPARTAMENTO:
				if (mapaDepartamentos.containsKey(ubicacion.getCodigoDepartamento())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_departamento_repetido"));
				}

				break;
			case CIUDAD:
				if (mapaCiudades.containsKey(ubicacion.getCodigoCiudad())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_ciudad_repetida"));
				}

				break;
			}

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

	@PreAuthorize("@restAuthService.hasAccess('/music-room/ubicacion', '/eliminarUbicacion') or "
			+ PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/eliminarUbicacion")
	public void eliminar(@RequestBody UbicacionTB ubicacion) {
		if (ubicacion != null) {
			ubicacionService.eliminar(ubicacion.getIdUbicacion());
		}
	}

}
