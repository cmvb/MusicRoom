package com.proyectos.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.ArchivoTB;
import com.proyectos.model.SalaTB;
import com.proyectos.service.IArchivosService;
import com.proyectos.service.IReportesService;
import com.proyectos.service.ISalaService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;

@RestController
@RequestMapping("/music-room/sala")
public class ControladorRestSala {

	private final String PERMISO_ADMINISTRADOR = "@restAuthService.hasAccess('/music-room', '/')";

	@Autowired
	IReportesService reporteService;

	@Autowired
	IArchivosService archivoService;

	@Autowired
	ISalaService salaService;

	@PreAuthorize("@restAuthService.hasAccess('/music-room/sala', '/consultarTodos') or " + PERMISO_ADMINISTRADOR)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SalaTB>> consultarTodos() {
		return new ResponseEntity<List<SalaTB>>(salaService.consultarTodos(), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/sala', '/consultarPorId') or " + PERMISO_ADMINISTRADOR)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SalaTB> consultarPorId(@PathVariable("id") Long idSala) {
		SalaTB sala = salaService.consultarPorId(idSala);
		if (sala == null) {
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.salaNoEncontrada", idSala);

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<SalaTB>(sala, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/sala', '/consultarPorFiltros') or " + PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<SalaTB>> consultarPorFiltros(@RequestBody SalaTB sala) {
		return new ResponseEntity<List<SalaTB>>(salaService.consultarPorFiltros(sala), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/sala', '/crearSala') or " + PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearSala")
	public ResponseEntity<SalaTB> crear(@RequestBody SalaTB sala) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_SALA_TB, sala);
		SalaTB salaNueva = new SalaTB();
		if (errores.isEmpty()) {
			List<SalaTB> listaSalas = salaService.consultarTodos();
			Map<String, SalaTB> mapaSalas = new HashMap<>();
			Set<String> nombresSalas = new HashSet<>();

			for (SalaTB salaTB : listaSalas) {
				if (!mapaSalas.containsKey(salaTB.getNombreSala())) {
					mapaSalas.put(salaTB.getNombreSala(), salaTB);
					nombresSalas.add(salaTB.getNombreSala());
				}
			}

			if (mapaSalas.containsKey(sala.getNombreSala())) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_sala_nombre_repetido"));
			}

			if (errores.isEmpty()) {
				salaNueva = new SalaTB();
				salaNueva = salaService.crear(sala);
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

		return new ResponseEntity<SalaTB>(salaNueva, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/sala', '/modificarSala') or " + PERMISO_ADMINISTRADOR)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarSala")
	public ResponseEntity<SalaTB> modificar(@RequestBody SalaTB sala) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_SALA_TB, sala);
		SalaTB salaNueva = new SalaTB();
		if (errores.isEmpty()) {
			List<SalaTB> listaSalas = salaService.consultarTodos();
			Map<String, SalaTB> mapaSalas = new HashMap<>();
			Set<String> nombresSalas = new HashSet<>();

			for (SalaTB salaTB : listaSalas) {
				if (!mapaSalas.containsKey(salaTB.getNombreSala())
						&& !sala.getNombreSala().equalsIgnoreCase(salaTB.getNombreSala())) {
					mapaSalas.put(salaTB.getNombreSala(), salaTB);
					nombresSalas.add(salaTB.getNombreSala());
				}
			}

			if (mapaSalas.containsKey(sala.getNombreSala())) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_sala_nombre_repetido"));
			}

			if (errores.isEmpty()) {
				salaNueva = new SalaTB();
				salaNueva = salaService.modificar(sala);
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

		return new ResponseEntity<SalaTB>(salaNueva, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/sala', '/eliminarSala') or " + PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/eliminarSala")
	public void eliminar(@RequestBody SalaTB sala) {
		if (sala != null) {
			salaService.eliminar(sala.getIdSala());
		}
	}

	///////////////////////////////

	@GetMapping(value = "/generarReporteEJM", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> generarReporteEJM() {
		byte[] data = reporteService.generarReporteEJM("consultaUsuarios.jasper");
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}

	@PostMapping(value = "/guardarArchivo", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ArchivoTB> guardarArchivo(@RequestParam("file") MultipartFile file) throws IOException {
		ArchivoTB archivo = new ArchivoTB();
		archivo.setNombreArchivo(file.getName());
		archivo.setValor(file.getBytes());
		archivo.setTipoArchivo(".png");

		ArchivoTB resultado = archivoService.guardarArchivo(archivo);
		return new ResponseEntity<ArchivoTB>(resultado, HttpStatus.OK);
	}

	@GetMapping(value = "/leerArchivo/{idArchivo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> leerArchivo(@PathVariable("idArchivo") Long idArchivo) throws IOException {
		byte[] resultado = archivoService.leerArchivo(idArchivo);

		return new ResponseEntity<byte[]>(resultado, HttpStatus.OK);
	}
}
