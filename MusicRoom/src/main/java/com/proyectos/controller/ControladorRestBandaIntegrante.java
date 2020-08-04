package com.proyectos.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.model.dto.BandaIntegranteDTO;
import com.proyectos.service.IArchivosService;
import com.proyectos.service.IBandaIntegranteService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;

@RestController
@RequestMapping("/music-room/bandaIntegrante")
public class ControladorRestBandaIntegrante {

	private final String PERMISO_ADMINISTRADOR = "@restAuthService.hasAccess('/music-room', '/')";

	@Autowired
	IArchivosService archivoService;

	@Autowired
	IBandaIntegranteService bandaIntegranteService;

	@PreAuthorize("@restAuthService.hasAccess('/music-room/bandaIntegrante', '/consultarTodos') or "
			+ PERMISO_ADMINISTRADOR)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<BandaTB, List<IntegranteTB>>> consultarTodos() {
		return new ResponseEntity<HashMap<BandaTB, List<IntegranteTB>>>(
				bandaIntegranteService.consultarMapaBandaXIntegrante(), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/bandaIntegrante', '/consultarPorFiltros') or "
			+ PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<BandaIntegranteDTO>> consultarPorFiltros(@RequestBody BandaTB banda) {
		return new ResponseEntity<List<BandaIntegranteDTO>>(bandaIntegranteService.consultarPorFiltros(banda),
				HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/bandaIntegrante', '/crearBandaIntegrante') or "
			+ PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearBanda")
	public ResponseEntity<BandaIntegranteDTO> crear(@RequestBody BandaIntegranteDTO bandaIntegrantes) {
		BandaTB bandaTb = bandaIntegrantes.getBandaTb();
		List<IntegranteTB> listaIntegrantes = bandaIntegrantes.getListaIntegrantesTb();
		List<String> erroresBanda = Util.validaDatos(ConstantesTablasNombre.MRA_BANDA_TB, bandaTb);
		List<String> erroresIntegrantes = Util.validaDatos(ConstantesTablasNombre.MRA_BANDA_TB, listaIntegrantes);
		List<String> otrosErrores = new ArrayList<>();

		BandaIntegranteDTO bandaIntegranteNueva = new BandaIntegranteDTO();
		if (erroresBanda.isEmpty() && erroresIntegrantes.isEmpty()) {
			HashMap<BandaTB, List<IntegranteTB>> mapaBandaIntegrantes = bandaIntegranteService
					.consultarMapaBandaXIntegrante();
			Map<String, BandaTB> mapabandas = new HashMap<>();
			Set<String> nombresbandas = new HashSet<>();

			for (BandaTB bandaMapaTb : mapaBandaIntegrantes.keySet()) {
				if (!mapabandas.containsKey(bandaMapaTb.getNombreBanda())) {
					mapabandas.put(bandaMapaTb.getNombreBanda(), bandaMapaTb);
					nombresbandas.add(bandaMapaTb.getNombreBanda());
				}
			}

			if (mapabandas.containsKey(bandaTb.getNombreBanda())) {
				otrosErrores.add(PropertiesUtil.getProperty("lbl_mtto_banda_nombre_repetido"));
			}

			if (otrosErrores.isEmpty()) {
				bandaIntegranteNueva = new BandaIntegranteDTO();
				bandaIntegranteNueva = bandaIntegranteService.crear(bandaIntegrantes);
				if (bandaIntegranteNueva == null) {
					String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");
					String mensajeError = PropertiesUtil.getProperty("lbl_mtto_banda_foto") + " | "
							+ PropertiesUtil.getProperty("lbl_mtto_banda_foto")
							+ PropertiesUtil.getProperty("lbl_mtto_banda_error_transferir_sftp_foto_logo");
					throw new ModelNotFoundException(erroresTitle + mensajeError);
				}
			} else {
				StringBuilder mensajeErrores = new StringBuilder();
				String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

				for (String error : otrosErrores) {
					mensajeErrores.append(error);
				}

				throw new ModelNotFoundException(erroresTitle + mensajeErrores);
			}
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : otrosErrores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<BandaIntegranteDTO>(bandaIntegranteNueva, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/bandaIntegrante', '/modificarBandaIntegrante') or "
			+ PERMISO_ADMINISTRADOR)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarBandaIntegrante")
	public ResponseEntity<BandaIntegranteDTO> modificar(@RequestBody BandaIntegranteDTO bandaIntegrantes) {
		BandaTB bandaTb = bandaIntegrantes.getBandaTb();
		List<IntegranteTB> listaIntegrantes = bandaIntegrantes.getListaIntegrantesTb();
		List<String> erroresBanda = Util.validaDatos(ConstantesTablasNombre.MRA_BANDA_TB, bandaTb);
		List<String> erroresIntegrantes = Util.validaDatos(ConstantesTablasNombre.MRA_BANDA_TB, listaIntegrantes);
		List<String> otrosErrores = new ArrayList<>();

		BandaIntegranteDTO bandaIntegranteNueva = new BandaIntegranteDTO();
		if (erroresBanda.isEmpty() && erroresIntegrantes.isEmpty()) {
			HashMap<BandaTB, List<IntegranteTB>> mapaBandaIntegrantes = bandaIntegranteService
					.consultarMapaBandaXIntegrante();
			Map<String, BandaTB> mapabandas = new HashMap<>();
			Set<String> nombresbandas = new HashSet<>();

			for (BandaTB bandaMapaTb : mapaBandaIntegrantes.keySet()) {
				if (!mapabandas.containsKey(bandaMapaTb.getNombreBanda())) {
					mapabandas.put(bandaMapaTb.getNombreBanda(), bandaMapaTb);
					nombresbandas.add(bandaMapaTb.getNombreBanda());
				}
			}

			if (mapabandas.containsKey(bandaTb.getNombreBanda())) {
				otrosErrores.add(PropertiesUtil.getProperty("lbl_mtto_banda_nombre_repetido"));
			}

			if (otrosErrores.isEmpty()) {
				bandaIntegranteNueva = new BandaIntegranteDTO();
				bandaIntegranteNueva = bandaIntegranteService.modificar(bandaIntegranteNueva);
				if (bandaIntegranteNueva == null) {
					String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");
					String mensajeError = PropertiesUtil.getProperty("lbl_mtto_banda_foto") + " | "
							+ PropertiesUtil.getProperty("lbl_mtto_banda_foto")
							+ PropertiesUtil.getProperty("lbl_mtto_banda_error_transferir_sftp_foto_logo");
					throw new ModelNotFoundException(erroresTitle + mensajeError);
				}
			} else {
				StringBuilder mensajeErrores = new StringBuilder();
				String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

				for (String error : otrosErrores) {
					mensajeErrores.append(error);
				}

				throw new ModelNotFoundException(erroresTitle + mensajeErrores);
			}
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : otrosErrores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<BandaIntegranteDTO>(bandaIntegranteNueva, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/bandaIntegrante', '/eliminarBandaIntegrante') or "
			+ PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/eliminarBandaIntegrante")
	public void eliminar(@RequestBody BandaTB banda) {
		if (banda != null) {
			bandaIntegranteService.eliminarBanda(banda.getIdBanda());
		}
	}

}
