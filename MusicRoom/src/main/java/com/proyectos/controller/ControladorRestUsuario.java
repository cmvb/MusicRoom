package com.proyectos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
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
import com.proyectos.model.CodigoVerificacionTB;
import com.proyectos.model.RolTB;
import com.proyectos.model.SesionTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.model.dto.MailDTO;
import com.proyectos.model.dto.UsuarioDTO;
import com.proyectos.service.IArchivosService;
import com.proyectos.service.IUsuarioService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.PropertiesUtil;
import com.proyectos.util.Util;
import com.proyectos.util.UtilMail;

@RestController
@RequestMapping("/music-room/usuario")
public class ControladorRestUsuario {

	private final String PERMISO_ADMINISTRADOR = "@restAuthService.hasAccess('/music-room', '/')";

	@Value("${email.servidor}")
	private String EMAIL_SERVIDOR;

	@Value("${ruta.verificar.cuenta.nueva}")
	private String URL_VERIFICAR_CUENTA_NUEVA;

	@Value("${tiempo.expiracion.vcode.minutos}")
	private int TIEMPO_EXPIRACION_VCODE_MINUTOS;

	@Autowired
	IUsuarioService usuarioService;

//	@Autowired
//	IRolService rolService;

	@Autowired
	IArchivosService archivoService;

	@Autowired
	UtilMail mailUtil;

	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

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

	@GetMapping(value = "/anular/{tokenId:.*}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void revocarToken(@PathVariable("tokenId") String token) {
		tokenServices.revokeToken(token);
	}

	@GetMapping(value = "/enviarCodigoVerificacion/{vCode:.*}/{email:.*}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void enviarCodigoVerificacion(@PathVariable("vCode") String codigoVerificacion,
			@PathVariable("email") String email) {
		try {
			if (Util.esCorreoValido(email)) {
				UsuarioTB usuarioFiltro = new UsuarioTB();
				usuarioFiltro.setEmail(email);
				List<UsuarioTB> listaUsuarios = usuarioService.consultarPorFiltros(usuarioFiltro);

				if (listaUsuarios.isEmpty()) {
					CodigoVerificacionTB vCodeTB = new CodigoVerificacionTB();
					vCodeTB.setToken(UUID.randomUUID().toString());
					vCodeTB.setEmail(email);
					vCodeTB.setExpiracion(TIEMPO_EXPIRACION_VCODE_MINUTOS);
					usuarioService.crearVCode(vCodeTB);

					MailDTO mailDto = new MailDTO();
					mailDto.setFrom(EMAIL_SERVIDOR);
					mailDto.setTo(email);
					mailDto.setSubject("ENVÍO CÓDIGO DE VERIFICACIÓN DE CUENTA - MUSIC ROOM");

					Map<String, Object> model = new HashMap<>();
					model.put("email", email);
					model.put("token", vCodeTB.getToken());
					model.put("resetUrl", URL_VERIFICAR_CUENTA_NUEVA + vCodeTB.getToken());
					mailDto.setModel(model);

					mailUtil.sendMail(mailDto);
				} else {
					String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.existeCorreoUsuario");
					throw new ModelNotFoundException(mensaje);
				}
			} else {
				String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.correoInvalido");
				throw new ModelNotFoundException(mensaje);
			}
		} catch (Exception e) {
			String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.envioVCodeIncorrecto");
			throw new ModelNotFoundException(mensaje);
		}
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
	@RequestMapping("/registrarse")
	public ResponseEntity<UsuarioTB> registrarse(@RequestBody UsuarioTB usuario) {
		List<String> errores = Util.validaDatos(ConstantesTablasNombre.MRA_USUARIO_TB, usuario);
		UsuarioTB usuarioNuevo = new UsuarioTB();
		if (errores.isEmpty()) {
			CodigoVerificacionTB vCodeTB = usuarioService.consultarVCodePorCorreo(usuario.getEmail());

			if (vCodeTB != null && StringUtils.isNotBlank(vCodeTB.getToken()) && !vCodeTB.isExpirado()
					&& StringUtils.isNotBlank(usuario.getCodigoVerificacion())
					&& usuario.getCodigoVerificacion().equalsIgnoreCase(vCodeTB.getToken())) {
				usuarioNuevo = new UsuarioTB();
				usuario.setPassword(Util.encriptar(usuario.getPassword(), usuario.getUsuario()));
				ArchivoTB fotoTb = archivoService.consultarPorId(1l);
				usuario.setFotoTb(fotoTb);

				List<String> listaRoles = new ArrayList<>();
				listaRoles = Util.cargarRolesUsuarioCliente();

				List<RolTB> listaRolesTB = usuarioService.consultarRolesListaCodigosRol(listaRoles);
				usuario.setListaRoles((listaRolesTB != null && !listaRolesTB.isEmpty()) ? null : listaRolesTB);
				usuarioNuevo = usuarioService.crear(usuario);
			} else {
				String mensaje = PropertiesUtil.getProperty("musicroom.msg.validate.errorVCodeRegistrar");
				throw new ModelNotFoundException(mensaje);
			}
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
