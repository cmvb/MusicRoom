package com.proyectos.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.proyectos.enums.ESeveridadMensaje;
import com.proyectos.enums.ETipoDocumento;
import com.proyectos.enums.ETipoUbicacion;
import com.proyectos.enums.ETipoUsuario;
import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;
import com.proyectos.model.SalaTB;
import com.proyectos.model.TerceroTB;
import com.proyectos.model.UbicacionTB;
import com.proyectos.model.UsuarioTB;

public abstract class Util {

	public static final String NOMBRE_LLAVE_TOKEN = "app.bean.sesion";

	private static final String CARACTERES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int TAMANO_TOKEN = 11;
	private static final char[] SIMBOLOS = CARACTERES.toCharArray();
	private static final char[] BUFFER = new char[TAMANO_TOKEN];
	public static final String VACIO = "";
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public static final String RUTA_DIRECTORIO_INFORME_ORACLE = "DIRECTORIO_INFORMES"; // DIRECTORY ORACLE LOCAL
	public static final String REGISTRO_CREADO = "El Registro se creo Satisfactoriamente.";
	public static final String REGISTRO_ACTUALIZADO = "El Registro se Actualizo Satisfactoriamente.";
	public static final String REGISTRO_APROBADO = "El Registro se aprobó Satisfactoriamente.";
	public static final String REGISTRO_RECHAZADO = "El Registro se rechazó Satisfactoriamente.";
	public static final String CAMBIOS_SATISFACTORIOS = "Se Actualizo la informacion Satisfactoriamente.";
	public static final String MISMA_DESCRIPCION = "Ya existe un registro con la misma descripcion.";
	public static final String MISMO_CODIGO = "Ya existe un registro con el mismo codigo.";
	public static final String CARGUE_MASIVO_EXITOSO = "Archivo procesado Correctamente, por favor valide el log";
	public static final String CARGUE_EXITOSO = "Archivo procesado correctamente";
	public static final String TAMANOLISTA = "tamanoLista";
	public static final String ERRORFECHAINICIALMAYOR = "Fecha inicial mayor a la fecha final";
	public static final String ERRORFECHAFINSOBREPASAACTUAL = "Fecha final mayor a la fecha actual";
	public static final String ERRORFECHAINICIOSOBREPASAACTUAL = "Fecha inicial mayor a la fecha actual";
	public static final String REENVIO_TOKEN_EXITOSO = "El token fue reenviado con éxito";
	public static final String ERROR_VALIDACION_RANGO_2_MESES = "Las fechas no pueden superar un rango de 2 meses";
	public static final String CORREO_INVALIDO = "Correo Incorrecto";
	public static final String CORREO_EXISTE = "Correo existente";

	public static final String INFORMACION = "Informacion";
	public static final String ADVERTENCIA = "Advertencia";
	public static final String ERROR = "Error";

	public static final String CAMPO_REQUERIDO = "Campo Requerido";
	public static final String CAMPO_NOREQUERIDO = "Campo no es Requerido";
	public static final String VALOR_INCORRECTO = "Valor Incorrecto";
	public static final String VALOR_VACIO = "Esta Vacio";
	public static final String CODIGO_EXISTENTE = "Código Existente";
	public static final String DESCRIPCION_EXISTENTE = "Descripción Existente";
	public static final String VALOR_EXISTENTE = "Valor Existente";
	public static final String NUMERO_CORTO = "Rango requerido es entre 7 - 15";
	public static final String NUMERO_LARGO = "Rango requerido es entre 7 - 15";
	public static final String DIRECCION_ERRONEA = "Direccion Incorrecta";

	public static final long CANTIDAD_REGISTROS_TABLA_MOSTRAR = 5;
	public static final long CANTIDAD_REGISTROS_TABLA_MOSTRAR_X10 = 50;
	public static final String ESTADO_ACTIVO_TEXTO = "A";
	public static final String ESTADO_INACTIVO_TEXTO = "I";
	public static final String STRINGVACIO = " ";
	public static final String COMODIN = "%";

	public static final String PREASIGNACION_EXITOSA = "Se realizó la Pre-Asignación del Inventario Satisfactoriamente.";
	public static final String ASIGNACION_EXITOSA = "Se realizó la Asignación del Inventario Satisfactoriamente.";
	public static final String DEVOLUCION_EXITOSA = "Se realizó la Devolución del Inventario Satisfactoriamente.";
	public static final String GARANTIA_EXITOSA = "Se realizó el envió a Garantía del Inventario Satisfactoriamente.";
	public static final String END_LINE = "\r\n";
	public static final String PARAMETRIZACION_EXISTENTE = "Ya existe una parametrización con la ficha técnica.";
	public static final String PROCESO_SEGUNDO_PLANO = "Se está generando el informe en segundo plano.";
	public static final String FECHAS_MAYORES_A_2_MESES = "El rango entre fechas supera los dos meses";

	public static final String INFORME_MOVIDO_SFTP_SATISFACTORIAMENTE = "El Informe fue creado correctamente en el servidor SFTP.";
	public static final String INFORME_NO_PUDO_SER_MOVIDO_SFTP = "El Informe no pudo ser creado en el servidor SFTP, validar la conexión.";

	public static String generarToken(String usuario) {
		char[] SYM_USUARIO = usuario.toCharArray();
		char[] BUF_USUARIO = new char[TAMANO_TOKEN];
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < BUFFER.length; i++) {
			BUFFER[i] = SIMBOLOS[random.nextInt(SIMBOLOS.length)];
		}
		for (int i = 0; i < BUF_USUARIO.length; i++) {
			BUF_USUARIO[i] = SYM_USUARIO[random.nextInt(SYM_USUARIO.length)];
		}
		String result = new String(BUFFER) + new String(BUF_USUARIO);
		
		return result.substring(5, 15);
	}

	public static final String extensionArchivo(String nombreArchivoConExtension) {
		String extension = nombreArchivoConExtension.substring(nombreArchivoConExtension.lastIndexOf("."),
				nombreArchivoConExtension.length());
		return extension;
	}

	public static String devolverNombreMes(int mes) {
		if (mes == 0) {
			return "Ene";
		}
		if (mes == 1) {
			return "Feb";
		}
		if (mes == 2) {
			return "Mar";
		}
		if (mes == 3) {
			return "Abr";
		}
		if (mes == 4) {
			return "May";
		}
		if (mes == 5) {
			return "Jun";
		}
		if (mes == 6) {
			return "Jul";
		}
		if (mes == 7) {
			return "Ago";
		}
		if (mes == 8) {
			return "Sep";
		}
		if (mes == 9) {
			return "Oct";
		}
		if (mes == 10) {
			return "Nov";
		}
		if (mes == 11) {
			return "Dic";
		}

		return "";
	}

	public static String armarTablaHtmlAccesorios(List<String> cabeceras, Map<String, Integer> mapaAccesorios) {
		if (cabeceras == null || mapaAccesorios == null) {
			return "";
		}

		String data = "";

		String TR = "<tr>";
		String TR_CLOSE = "</tr>";
		String TH = "<th style=\"background: #dd1820;color: white\">";
		String TH_CLOSE = "</th>";
		String TD = "<td>";
		String TD_CLOSE = "</td>";

		data = data + TR;
		for (String head : cabeceras) {
			data = data + TH;
			data = data + head;
			data = data + TH_CLOSE;
		}
		data = data + TR_CLOSE;

		for (Map.Entry<String, Integer> mapa : mapaAccesorios.entrySet()) {

			data = data + TR;
			String descripcion = mapa.getKey();
			String cantidad = String.valueOf(mapa.getValue());

			data = data + TD;
			data = data + descripcion;
			data = data + TD_CLOSE;

			data = data + TD;
			data = data + cantidad;
			data = data + TD_CLOSE;

			data = data + TR_CLOSE;

		}

		return data;
	}

	public static boolean esCorreoValido(String email) {
		return EMAIL_PATTERN.matcher(email.toLowerCase()).matches();
	}

	public static Date convertirStringToDate(String fechaString, String formato) {
		Date fecha = null;
		if (fechaString != null && formato != null) {
			try {
				SimpleDateFormat simpleFormat = new SimpleDateFormat(formato);
				fecha = simpleFormat.parse(fechaString);
			} catch (ParseException ex) {
				System.out.println(ex.getMessage());
			}
		}

		return fecha;
	}

	public static LocalDateTime convertirStringToLocalDateTime(String fechaString, String formato) {
		LocalDateTime fecha = null;
		if (fechaString != null && formato != null) {
			DateTimeFormatter simpleFormat = DateTimeFormatter.ofPattern(formato);
			fecha = LocalDateTime.parse(fechaString, simpleFormat);
		}

		return fecha;
	}

	public static String formatoFechaLocalDateTime(LocalDateTime dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return dateTime.format(formatter);
	}

	public static String formatoFechaLocalDate(LocalDate date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}

	public static String getTituloNotificacion(String claseMsj) {
		String titulo = Util.INFORMACION;

		if (claseMsj.equalsIgnoreCase(ESeveridadMensaje.WARNING.getNombre())) {
			titulo = Util.ADVERTENCIA;
		} else if (claseMsj.equalsIgnoreCase(ESeveridadMensaje.DANGER.getNombre())) {
			titulo = Util.ERROR;
		}

		return titulo;
	}

	public static long getDiasRangoFechas(LocalDateTime fechaInicial, Date fechaFinal) {
		long milisegundos = 24 * 60 * 60 * 1000; // Milisegundos al dia
		long dias = 0;
		dias = (fechaFinal.getTime() - Date.from(fechaInicial.atZone(ZoneId.systemDefault()).toInstant()).getTime())
				/ milisegundos;
		return dias;
	}

	public static boolean esNumero(String cadena) {
		return cadena.matches("[0-9]*");
	}

	public static String obtenerFicheroString(String rutaArchivo) throws FileNotFoundException, IOException {
		String file = "";

		String cadena;
		FileReader f = new FileReader(rutaArchivo);
		BufferedReader b = new BufferedReader(f);

		while ((cadena = b.readLine()) != null) {
			System.out.println(cadena);
			file = file + cadena;
		}
		b.close();

		return file;
	}

	public static String armarTablaHtmlBodyMsgEmail(List<String> cabeceras, List<ArrayList<String>> datos)
			throws FileNotFoundException, IOException {
		if (cabeceras == null || datos == null) {
			return "";
		}

		if (datos.isEmpty()) {
			return "";
		}

		if (cabeceras.size() != datos.get(0).size()) {
			return "";
		}

		String data = "";

		String TR = "<tr>";
		String TR_CLOSE = "</tr>";
		String TH = "<th style=\"border-style: solid;border-width: 2px 1px 1px 2px;border-color: #dddddd;background: #dd1820;color: white\">";
		String TH_CLOSE = "</th>";
		String TD = "<td style=\"border-style: solid;border-width: 2px 1px 1px 2px;border-color: #dddddd\">";
		String TD_CLOSE = "</td>";

		data = data + TR;
		for (String head : cabeceras) {
			data = data + TH;
			data = data + head;
			data = data + TH_CLOSE;
		}
		data = data + TR_CLOSE;

		for (List<String> row : datos) {
			data = data + TR;
			for (String column : row) {
				data = data + TD;
				data = data + column;
				data = data + TD_CLOSE;
			}
			data = data + TR_CLOSE;
		}

		return data;
	}

	public static boolean validarCaracteres(String cadenaValidar) {
		Pattern mask = Pattern.compile(ConstantesValidaciones.EXPRESION_REGULAR_DE_TEXTO_INGRESADO);
		return mask.matcher(cadenaValidar).matches();
	}

	public static List<String> validaDatos(String tabla, Object objeto) {
		List<String> errores = new ArrayList<>();

		switch (tabla) {
		case ConstantesTablasNombre.MRA_USUARIO_TB:
			errores = validarUsuario((UsuarioTB) objeto);

			break;
		case ConstantesTablasNombre.MRA_UBICACION_TB:
			errores = validarUbicacion((UbicacionTB) objeto);

			break;
		case ConstantesTablasNombre.MRA_TERCERO_TB:
			errores = validarTercero((TerceroTB) objeto);

			break;
		case ConstantesTablasNombre.MRA_SALA_TB:
			errores = validarSala((SalaTB) objeto);

			break;
		case ConstantesTablasNombre.MRA_BANDA_TB:
			errores = validarBanda((BandaTB) objeto);

			break;
		case ConstantesTablasNombre.MRA_INTEGRANTE_TB:
			errores = validarIntegrantes((List<IntegranteTB>) objeto);

			break;
		}

		return errores;

	}

	private static List<String> validarUsuario(UsuarioTB usuario) {
		List<String> errores = new ArrayList<>();
		final String VALOR_INCORRECTO = PropertiesUtil.getProperty("musicroom.msg.validate.valor.incorrecto");

		if (StringUtils.isBlank(usuario.getNombre())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_nombre") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(usuario.getApellido())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_apellido") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(usuario.getNumeroDocumento())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_numero_documento") + VALOR_INCORRECTO);
		}
		if (usuario.getTipoDocumento() == ETipoDocumento.VACIO.ordinal()) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_tipo_documento") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(usuario.getUsuario())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_usuario") + VALOR_INCORRECTO);
		}
		if (usuario.getTipoUsuario() == ETipoUsuario.VACIO.ordinal()) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_tipo_usuario") + VALOR_INCORRECTO);
		}
		if (usuario.getFechaNacimiento() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_fecha_nacimiento") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(usuario.getEmail()) || !esCorreoValido(usuario.getEmail())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_usuario_email") + VALOR_INCORRECTO);
		}

		return errores;
	}

	private static List<String> validarUbicacion(UbicacionTB ubicacion) {
		List<String> errores = new ArrayList<>();
		final String VALOR_INCORRECTO = PropertiesUtil.getProperty("musicroom.msg.validate.valor.incorrecto");

		ETipoUbicacion tipoUbicacionGuardar = ETipoUbicacion.values()[ubicacion.getTipoUbicacion()];

		boolean flagPais = (!StringUtils.isBlank(ubicacion.getCodigoPais()))
				&& (!StringUtils.isBlank(ubicacion.getNombrePais()));
		boolean flagDepartamento = (!StringUtils.isBlank(ubicacion.getCodigoDepartamento()))
				&& (!StringUtils.isBlank(ubicacion.getNombreDepartamento()));
		boolean flagCiudad = (!StringUtils.isBlank(ubicacion.getCodigoCiudad()))
				&& (!StringUtils.isBlank(ubicacion.getNombreCiudad()));

		switch (tipoUbicacionGuardar) {
		case PAIS:
			if (!flagPais) {
				if (StringUtils.isBlank(ubicacion.getCodigoPais())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_codigo") + " "
							+ PropertiesUtil.getProperty("lbl_mtto_ubicacion_pais") + VALOR_INCORRECTO);
				}

				if (StringUtils.isBlank(ubicacion.getNombrePais())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_nombre") + " "
							+ PropertiesUtil.getProperty("lbl_mtto_ubicacion_pais") + VALOR_INCORRECTO);
				}
			}

			break;
		case DEPARTAMENTO:
			if (!flagPais) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_pais_solo") + VALOR_INCORRECTO);
			}

			if (!flagDepartamento) {
				if (StringUtils.isBlank(ubicacion.getCodigoDepartamento())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_codigo") + " "
							+ PropertiesUtil.getProperty("lbl_mtto_ubicacion_departamento") + VALOR_INCORRECTO);
				}

				if (StringUtils.isBlank(ubicacion.getNombreDepartamento())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_nombre") + " "
							+ PropertiesUtil.getProperty("lbl_mtto_ubicacion_departamento") + VALOR_INCORRECTO);
				}
			}

			break;
		case CIUDAD:
			if (!flagPais) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_pais_solo") + VALOR_INCORRECTO);
			}

			if (!flagDepartamento) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_departamento_solo") + VALOR_INCORRECTO);
			}

			if (flagCiudad) {
				if (StringUtils.isBlank(ubicacion.getCodigoCiudad())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_codigo") + " "
							+ PropertiesUtil.getProperty("lbl_mtto_ubicacion_ciudad") + VALOR_INCORRECTO);
				}

				if (StringUtils.isBlank(ubicacion.getNombreCiudad())) {
					errores.add(PropertiesUtil.getProperty("lbl_mtto_ubicacion_nombre") + " "
							+ PropertiesUtil.getProperty("lbl_mtto_ubicacion_ciudad") + VALOR_INCORRECTO);
				}
			}

			break;
		}

		return errores;
	}

	private static List<String> validarTercero(TerceroTB tercero) {
		List<String> errores = new ArrayList<>();
		final String VALOR_INCORRECTO = PropertiesUtil.getProperty("musicroom.msg.validate.valor.incorrecto");

		if (StringUtils.isBlank(tercero.getNit())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_nit") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(tercero.getRazonSocial())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_razon_social") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(tercero.getTelefono1())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_telefono1") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(tercero.getDireccion())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_direccion") + VALOR_INCORRECTO);
		}
		if (tercero.getUbicacionTb() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_tercero_ubicacion") + VALOR_INCORRECTO);
		}

		return errores;
	}

	private static List<String> validarSala(SalaTB sala) {
		List<String> errores = new ArrayList<>();
		final String VALOR_INCORRECTO = PropertiesUtil.getProperty("musicroom.msg.validate.valor.incorrecto");

		if (StringUtils.isBlank(sala.getNombreSala())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_sala_nombre_sala") + VALOR_INCORRECTO);
		}
		if (sala.getTerceroTb() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_sala_tercero") + VALOR_INCORRECTO);
		}
		if (sala.getFotoPrincipalTb() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_sala_foto_principal") + VALOR_INCORRECTO);
		}

		return errores;
	}

	private static List<String> validarBanda(BandaTB banda) {
		List<String> errores = new ArrayList<>();
		final String VALOR_INCORRECTO = PropertiesUtil.getProperty("musicroom.msg.validate.valor.incorrecto");

		if (StringUtils.isBlank(banda.getNombreBanda())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_nombre_banda") + VALOR_INCORRECTO);
		}
		if (StringUtils.isBlank(banda.getGenero())) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_genero") + VALOR_INCORRECTO);
		}
		if (banda.getFotoTb() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_foto") + VALOR_INCORRECTO);
		}
		if (banda.getLogoTb() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_logo") + VALOR_INCORRECTO);
		}
		if (banda.getFechaInicio() == null) {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_fecha_inicio") + VALOR_INCORRECTO);
		} else {
			Date ahora = new Date();
			if (banda.getFechaInicio().after(ahora)) {
				errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_fecha_inicio") + VALOR_INCORRECTO);
			}
		}

		return errores;
	}

	private static List<String> validarIntegrantes(List<IntegranteTB> listaIntegrantes) {
		List<String> errores = new ArrayList<>();
		final String VALOR_INCORRECTO = PropertiesUtil.getProperty("musicroom.msg.validate.valor.incorrecto");
		final String LISTA_VACIA = PropertiesUtil.getProperty("musicroom.msg.validate.integrantes.lista.vacia");
		final String INTEGRANTE_NUM = PropertiesUtil.getProperty("musicroom.msg.validate.integrante.numero");

		if (listaIntegrantes != null && !listaIntegrantes.isEmpty()) {
			int i = 1;
			for (IntegranteTB integrante : listaIntegrantes) {
				String VALIDACIONES = new String("");
				if (StringUtils.isBlank(integrante.getNombreIntegrante())) {
					VALIDACIONES = VALIDACIONES + PropertiesUtil.getProperty("lbl_mtto_integrante_nombre_integrante");
				}
				if (integrante.getInstrumentoAccesorio() <= 0) {
					VALIDACIONES = VALIDACIONES
							+ PropertiesUtil.getProperty("lbl_mtto_integrante_instrumento_accesorio");
				}
				if (integrante.getEdadIntegrante() <= 18) {
					VALIDACIONES = VALIDACIONES + PropertiesUtil.getProperty("lbl_mtto_integrante_edad");
				}

				if (StringUtils.isNotBlank(VALIDACIONES)) {
					errores.add(INTEGRANTE_NUM + " #" + i + " | " + VALIDACIONES + VALOR_INCORRECTO);
				}
				i++;
			}
		} else {
			errores.add(PropertiesUtil.getProperty("lbl_mtto_banda_integrantes") + LISTA_VACIA);
		}

		return errores;
	}

	public static List<String> cargarRolesUsuarioCliente() {
		List<String> listaRoles = new ArrayList<>();

		listaRoles.add(ConstantesRoles.ROL_MENU_SESION);
		listaRoles.add(ConstantesRoles.ROL_MENU_SALAS);
		listaRoles.add(ConstantesRoles.ROL_MENU_INTEGRANTES);
		listaRoles.add(ConstantesRoles.ROL_MENU_PRESTAMOS);
		listaRoles.add(ConstantesRoles.ROL_MENU_ENSAYOS);

		return listaRoles;
	}

	public static List<String> cargarRolesUsuarioEstablecimiento() {
		List<String> listaRoles = new ArrayList<>();

		listaRoles.add(ConstantesRoles.ROL_MENU_SESION);
		listaRoles.add(ConstantesRoles.ROL_MENU_SALAS);
		listaRoles.add(ConstantesRoles.ROL_MENU_INVENTARIO);
		listaRoles.add(ConstantesRoles.ROL_MENU_FACTURACION);

		return listaRoles;
	}

	public static List<String> cargarRolesUsuarioAdministrador() {
		List<String> listaRoles = new ArrayList<>();

		listaRoles.add(ConstantesRoles.ROL_ADMINISTRADOR);

		listaRoles.add(ConstantesRoles.ROL_MENU_PARAMETRIZACION);
		listaRoles.add(ConstantesRoles.ROL_MENU_SESION);
		listaRoles.add(ConstantesRoles.ROL_MENU_USUARIOS);
		listaRoles.add(ConstantesRoles.ROL_MENU_UBICACIONES);
		listaRoles.add(ConstantesRoles.ROL_MENU_TERCEROS);
		listaRoles.add(ConstantesRoles.ROL_MENU_SALAS);
		listaRoles.add(ConstantesRoles.ROL_MENU_INTEGRANTES);
		listaRoles.add(ConstantesRoles.ROL_MENU_INVENTARIO);
		listaRoles.add(ConstantesRoles.ROL_MENU_PRESTAMOS);
		listaRoles.add(ConstantesRoles.ROL_MENU_ENSAYOS);
		listaRoles.add(ConstantesRoles.ROL_MENU_FACTURACION);

		listaRoles.add(ConstantesRoles.ROL_CONSULTA_USUARIOS);
		listaRoles.add(ConstantesRoles.ROL_CONSULTA_ID_USUARIOS);
		listaRoles.add(ConstantesRoles.ROL_CONSULTA_FILTRO_USUARIOS);
		listaRoles.add(ConstantesRoles.ROL_CREACION_USUARIOS);
		listaRoles.add(ConstantesRoles.ROL_ACTUALIZA_USUARIOS);
		listaRoles.add(ConstantesRoles.ROL_ELIMINACION_USUARIOS);

		return listaRoles;

	}

	public static byte[] b64ToBytesArray(String b64) {
		try {
			byte[] name = Base64.getEncoder().encode(b64.getBytes());
			byte[] decodedString = Base64.getDecoder().decode(new String(name).getBytes("UTF-8"));
			return decodedString;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}