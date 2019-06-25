package com.proyectos.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.proyectos.enums.EEstado;
import com.proyectos.exception.ModelNotFoundException;
import com.proyectos.model.ArchivoTB;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.model.dto.UsuarioDTO;
import com.proyectos.service.IArchivosService;
import com.proyectos.service.IUsuarioService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;

@RestController
@RequestMapping("/music-room/usuario")
public class ControladorRestUsuario {

	private final String PERMISO_ADMINISTRADOR = "@restAuthService.hasAccess('/music-room', '/')";

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	IArchivosService archivoService;

	@GetMapping(value = "/consultarUsuariosRegister", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioDTO>> consultarUsuariosRegister() {
		List<UsuarioTB> listaUsuarios = usuarioService.consultarTodos();
		List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();

		for (UsuarioTB user : listaUsuarios) {
			UsuarioDTO userDto = new UsuarioDTO();
			userDto.setUsuario(user.getUsuario());
			userDto.setEmail(user.getEmail());
			userDto.setNumeroDocumento(user.getNumeroDocumento());
			userDto.setTipoDocumento(user.getTipoDocumento());

			listaUsuariosDTO.add(userDto);
		}

		return new ResponseEntity<List<UsuarioDTO>>(listaUsuariosDTO, HttpStatus.OK);
	}

	// @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
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
					if (!StringUtils.isBlank(sesion.getUsuarioTb().getPassword())) {
						if (sesion.getUsuarioTb().getEstado() == EEstado.INACTIVO.ordinal()) {
							mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.noAccesoApp.usuarioInactivo");

							throw new ModelNotFoundException(mensaje);
						}
					} else {
						mensaje = PropertiesUtil
								.getProperty("musicroom.msg.validate.noAccesoApp.usuarioPendienteActivar");

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
	@RequestMapping("/enviarCodigoVerificacion")
	public ResponseEntity<List<UsuarioTB>> enviarCodigoVerificacion(@RequestBody UsuarioTB usuario) {
		return new ResponseEntity<List<UsuarioTB>>(usuarioService.consultarPorFiltros(usuario), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/registrarse")
	public ResponseEntity<UsuarioTB> registrarse(@RequestBody UsuarioTB usuario) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_USUARIO_TB, usuario);
		UsuarioTB usuarioNuevo = new UsuarioTB();
		if (errores.isEmpty()) {
			usuarioNuevo = new UsuarioTB();
			usuario.setPassword(Util.encriptar(usuario.getPassword(), usuario.getUsuario()));
			ArchivoTB fotoTb = archivoService.consultarPorId(1l);
			usuario.setFotoTb(fotoTb);
			usuario.setListaRoles(null);
			usuarioNuevo = usuarioService.crear(usuario);
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<UsuarioTB>(usuarioNuevo, HttpStatus.OK);
	}

	// Métodos que requieren autorización de roles

	@PreAuthorize("@restAuthService.hasAccess('/music-room/usuario', '/consultarTodos') or " + PERMISO_ADMINISTRADOR)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioTB>> consultarTodos() {
		return new ResponseEntity<List<UsuarioTB>>(usuarioService.consultarTodos(), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/usuario', '/consultarPorId') or " + PERMISO_ADMINISTRADOR)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioTB> consultarPorId(@PathVariable("id") Long idUsuario) {
		UsuarioTB usuario = usuarioService.consultarPorId(idUsuario);
		if (usuario == null) {
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.usuarioNoEncontrado", idUsuario);

			throw new ModelNotFoundException(mensaje);
		}

		return new ResponseEntity<UsuarioTB>(usuario, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/usuario', '/consultarPorFiltros') or "
			+ PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/consultarPorFiltros")
	public ResponseEntity<List<UsuarioTB>> consultarPorFiltros(@RequestBody UsuarioTB usuario) {
		return new ResponseEntity<List<UsuarioTB>>(usuarioService.consultarPorFiltros(usuario), HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/usuario', '/crearUsuario') or " + PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/crearUsuario")
	public ResponseEntity<UsuarioTB> crear(@RequestBody UsuarioTB usuario) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_USUARIO_TB, usuario);
		UsuarioTB usuarioNuevo = new UsuarioTB();
		if (errores.isEmpty()) {
			usuarioNuevo = new UsuarioTB();
			usuarioNuevo = usuarioService.crear(usuario);
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<UsuarioTB>(usuarioNuevo, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/usuario', '/modificarUsuario') or " + PERMISO_ADMINISTRADOR)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/modificarUsuario")
	public ResponseEntity<UsuarioTB> modificar(@RequestBody UsuarioTB usuario) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_USUARIO_TB, usuario);
		UsuarioTB usuarioNuevo = new UsuarioTB();
		if (errores.isEmpty()) {
			usuarioNuevo = new UsuarioTB();
			usuarioNuevo = usuarioService.modificar(usuario);
		} else {
			StringBuilder mensajeErrores = new StringBuilder();
			String erroresTitle = PropertiesUtil.getProperty("musicroom.msg.validate.erroresEncontrados");

			for (String error : errores) {
				mensajeErrores.append(error);
			}

			throw new ModelNotFoundException(erroresTitle + mensajeErrores);
		}

		return new ResponseEntity<UsuarioTB>(usuarioNuevo, HttpStatus.OK);
	}

	@PreAuthorize("@restAuthService.hasAccess('/music-room/usuario', '/eliminarUsuario') or " + PERMISO_ADMINISTRADOR)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/eliminarUsuario")
	public void eliminar(@RequestBody UsuarioTB usuario) {
		if (usuario != null) {
			usuarioService.eliminar(usuario.getIdUsuario());
		}
	}

}
