package com.proyectos.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.csvreader.CsvReader;
import com.proyectos.enums.ESeveridadMensaje;
import com.proyectos.enums.ETipoDocumento;
import com.proyectos.enums.ETipoUbicacion;
import com.proyectos.enums.ETipoUsuario;
import com.proyectos.model.TerceroTB;
import com.proyectos.model.UbicacionTB;
import com.proyectos.model.UsuarioTB;

public class Util {

	private static final Logger LOG = Logger.getLogger(Util.class.getName());
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

	/**
	 * Permite leer el contenido del archivo guardandolo en una lista de String
	 *
	 * @param file        archivo que se quiere leer extension
	 * @param delimitador tipo de separador del archivo plano, puede ser coma o
	 *                    punto y coma, entre otros.s
	 * @return lista de String con los registros del archivo.
	 */
	public static final List<String> listaCargueArchivos(InputStream file, String delimitador) {
		List<String> resultado = new ArrayList<>();
		CsvReader cvsReader = null;
		try {
			cvsReader = new CsvReader(new java.io.InputStreamReader(file, "ISO-8859-1"), delimitador.charAt(0));
			while (cvsReader.readRecord()) {
				resultado.add(cvsReader.getRawRecord());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				if (cvsReader != null) {
					cvsReader.close();
				}
				if (file != null) {
					file.close();
				}
			} catch (IOException eI) {
				LOG.error(eI.getMessage());
			}
		}
		return resultado;
	}

	/**
	 * Devuelve solo la extension del archivo.
	 *
	 * @param nombreArchivoConExtension recibe el nombre del archivo con la
	 *                                  extension
	 * @return nombre de la extension
	 */
	public static final String extensionArchivo(String nombreArchivoConExtension) {
		String extension = nombreArchivoConExtension.substring(nombreArchivoConExtension.lastIndexOf("."),
				nombreArchivoConExtension.length());
		return extension;
	}

	/**
	 * Metodo que transforma un texto en entidad XML Document
	 *
	 * @param xmlStr es un String que contiene el xml en texto
	 * @return Document entidad XML
	 */
	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Permite validar en contenido de una cadena de caracteres
	 *
	 * @param cadena
	 * @return true si la cadena cumple las validaciones
	 */
	public static boolean vacio(String cadena) {
		return cadena == null || cadena.length() == 0 || cadena.equals("null") || cadena.trim().length() == 0;
	}

	/**
	 * Genera Token En Preasignacion para guardar en el inventario
	 *
	 * @param usuario
	 * @return
	 */
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

	/**
	 * Encripta un texto dada una llave (Para los casos sera el usuario)
	 *
	 * @param texto
	 * @param llave
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String encriptar(String texto, String llave) {

		// llave para encriptar datos
		String secretKey = llave;
		String base64EncryptedString = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
			LOG.error((Supplier<String>) ex);
			return null;
		}
		return base64EncryptedString;
	}

	/**
	 * Devuelve el nombre de un mes
	 *
	 * @param mes empieza desde cero 0 registro de la lista interna es un row
	 * @return nombre mes
	 */
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

	/**
	 * Limpiar los espacios de un texto
	 *
	 * @param text
	 * @return
	 */
	public static String limpiarEspaciosPasarMayuscula(String text) {
		return text.replaceAll("\\s", "").toUpperCase();
	}

	/**
	 * Validar si una estructura de correo es correcto
	 *
	 * @param email
	 * @return
	 */
	public static boolean esCorreoValido(String email) {
		return EMAIL_PATTERN.matcher(email.toLowerCase()).matches();
	}

	/*
	 * Permite convertir un string a una fecha con un formato determinado
	 */
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

	/*
	 * Permite convertir un string a una fecha LocalDateTime con un formato
	 * determinado
	 */
	public static LocalDateTime convertirStringToLocalDateTime(String fechaString, String formato) {
		LocalDateTime fecha = null;
		if (fechaString != null && formato != null) {
			DateTimeFormatter simpleFormat = DateTimeFormatter.ofPattern(formato);
			fecha = LocalDateTime.parse(fechaString, simpleFormat);
		}

		return fecha;
	}

	/**
	 * remplaza parametros de la consulta
	 *
	 * @param query
	 * @param params
	 * @return
	 */
	public static String reemplazarParametros(String query, Object... params) {
		for (Object object : params) {
			if (object != null) {
				if (object instanceof Integer[]) {
					StringBuilder fromCharArray = new StringBuilder("");
					for (int j = 0; j < ((Integer[]) object).length; j++) {
						fromCharArray.append(",").append(((Integer[]) object)[j]);
					}
					query = query.replaceFirst("\\?",
							fromCharArray.toString().substring(1, fromCharArray.toString().length()));

				} else if (object instanceof Character[]) {
					StringBuilder fromCharArray = new StringBuilder("");
					for (int j = 0; j < ((Character[]) object).length; j++) {
						fromCharArray.append(",'").append(((Character[]) object)[j]).append("'");
					}
					query = query.replaceFirst("\\?",
							fromCharArray.toString().substring(1, fromCharArray.toString().length()));

				} else if (object instanceof String[]) {
					StringBuilder fromCharArray = new StringBuilder("");
					for (int j = 0; j < ((String[]) object).length; j++) {
						fromCharArray.append(",'").append(((String[]) object)[j]).append("'");
					}
					query = query.replaceFirst("\\?",
							fromCharArray.toString().substring(1, fromCharArray.toString().length()));

				} else if (object instanceof Long[]) {
					StringBuilder fromCharArray = new StringBuilder("");
					for (int j = 0; j < ((Long[]) object).length; j++) {
						fromCharArray.append(",'").append(((Long[]) object)[j]).append("'");
					}
					query = query.replaceFirst("\\?",
							fromCharArray.toString().substring(1, fromCharArray.toString().length()));
				} else if (object instanceof BigDecimal) {
					query = query.replaceFirst("\\?", ((BigDecimal) object).toString());
				} else if (object instanceof Character) {
					query = query.replaceFirst("\\?", ((Character) object).toString());
				} else if (object instanceof String) {
					if (((String) object).contains("--replace")) {
						query = query.replaceFirst("\\--replace", ((String) object).replace("--replace", ""));
					} else {
						query = query.replaceFirst("\\?", "'" + ((String) object) + "'");
					}
				} else if (object instanceof Double) {
					query = query.replaceFirst("\\?", ((Double) object).toString());
				} else if (object instanceof Integer) {
					query = query.replaceFirst("\\?", ((Integer) object).toString());
				} else if (object instanceof Date) {
					query = query.replaceFirst("\\?", "'" + ((Date) object).toString() + "-00.00.00.000'");
				} else if (object instanceof Timestamp) {
					query = query.replaceFirst("\\?", "'" + ((Timestamp) object).toString() + "' ");
				} else if (object instanceof LocalDateTime) {
					query = query.replaceFirst("\\?", "'" + ((LocalDateTime) object).toString() + "' ");
				} else if (object instanceof Long) {
					query = query.replaceFirst("\\?", ((Long) object).toString());
				}
			}
		}

		return query;
	}

	/**
	 * Retorna una fecha formateada para informes en BD
	 *
	 * @param fecha
	 * @return
	 */
	public static String obtenerFechaArmadaParaInforme(LocalDateTime fecha) {

		String dia = fecha.getDayOfMonth() >= 10 ? fecha.getDayOfMonth() + "." : "0" + fecha.getDayOfMonth() + ".";
		String mes = fecha.getMonthValue() >= 10 ? fecha.getMonthValue() + "." : "0" + fecha.getMonthValue() + ".";
		String anio = String.valueOf(fecha.getYear());

		return dia + mes + anio;
	}

	/**
	 * Retorna una fecha formateada para informes en BD. El segundo parametro define
	 * si es una fecha final o inicial
	 *
	 * @param fecha
	 * @param esFechaInicial
	 * @return
	 */
	public static String obtenerFechaArmadaConHorasParaInforme(LocalDateTime fecha, boolean esFechaInicial) {

		String dia = fecha.getDayOfMonth() >= 10 ? fecha.getDayOfMonth() + "." : "0" + fecha.getDayOfMonth() + ".";
		String mes = fecha.getMonthValue() >= 10 ? fecha.getMonthValue() + "." : "0" + fecha.getMonthValue() + ".";
		String anio = String.valueOf(fecha.getYear());

		return dia + mes + anio + (esFechaInicial ? ":00:00:00" : ":23:59:59");
	}

	public static String obtenerFechaArmadaConHorasParaInforme(LocalDate fecha, boolean esFechaInicial) {

		String dia = fecha.getDayOfMonth() >= 10 ? fecha.getDayOfMonth() + "." : "0" + fecha.getDayOfMonth() + ".";
		String mes = fecha.getMonthValue() >= 10 ? fecha.getMonthValue() + "." : "0" + fecha.getMonthValue() + ".";
		String anio = String.valueOf(fecha.getYear());

		return dia + mes + anio + (esFechaInicial ? ":00:00:00" : ":23:59:59");
	}

	/**
	 *
	 * Permite dar formato a fechas que sean tipo LocalDateTime
	 *
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static String formatoFechaLocalDateTime(LocalDateTime dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return dateTime.format(formatter);
	}

	/**
	 *
	 * Permite dar formato a fechas que sean tipo LocalDateTime
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatoFechaLocalDate(LocalDate date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}

	/**
	 *
	 * Permite Retornar la clase o severidad para notificaciones y mensajes
	 *
	 * @param severidad
	 * @return
	 */
	public static String getESeveridadMensaje(ESeveridadMensaje severidad) {
		// Ejemplo de envio de param --> ESeveridadMensaje va =
		// ESeveridadMensaje.DANGER;
		return severidad.getNombre();
	}

	/**
	 *
	 * Permite Retornar El titulo para los mensajes notificaciones de acuerdo a la
	 * clase bootstrap
	 *
	 * @param claseMsj
	 * @return String
	 *
	 */
	public static String getTituloNotificacion(String claseMsj) {
		String titulo = Util.INFORMACION;

		if (claseMsj.equalsIgnoreCase(ESeveridadMensaje.WARNING.getNombre())) {
			titulo = Util.ADVERTENCIA;
		} else if (claseMsj.equalsIgnoreCase(ESeveridadMensaje.DANGER.getNombre())) {
			titulo = Util.ERROR;
		}

		return titulo;
	}

	/**
	 * calcula los dias entre fecha inicial y fecha final
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return
	 */
	public static long getDiasRangoFechas(LocalDateTime fechaInicial, Date fechaFinal) {
		long milisegundos = 24 * 60 * 60 * 1000; // Milisegundos al dia
		long dias = 0;
		dias = (fechaFinal.getTime() - Date.from(fechaInicial.atZone(ZoneId.systemDefault()).toInstant()).getTime())
				/ milisegundos;
		return dias;
	}

	/**
	 *
	 * Obtiene la Lista de Escala Movil
	 *
	 * @return
	 *
	 */
	public static List<Integer> escalaMovil() {
		List<Integer> listaEscala = new ArrayList<>();
		int numero = 0;
		for (int i = 0; i < 99; i++) {
			numero++;
			listaEscala.add(numero);
		}
		return listaEscala;
	}

	/**
	 * valida si la cadena es numero
	 *
	 * @param cadena
	 * @return
	 */
	public static boolean esNumero(String cadena) {
		return cadena.matches("[0-9]*");
	}

	/**
	 * Encripta un texto dada una llave (Para los casos sera el usuario)
	 *
	 * @param texto
	 * @param llave
	 * @return
	 */
	public static String Encriptar(String texto, String llave) {

		String secretKey = llave; // llave para encriptar datos
		String base64EncryptedString = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
			LOG.error("Error al Encriptar Texto", ex);
			return null;
		}
		return base64EncryptedString;
	}

	/**
	 * Des-Encripta un texto dada una llave (Para los casos sera el usuario)
	 *
	 * @param textoEncriptado
	 * @param llave
	 * @return
	 * @throws java.lang.Exception
	 *
	 */
	public static String Desencriptar(String textoEncriptado, String llave) throws Exception {

		String secretKey = llave; // llave para encriptar datos
		String base64EncryptedString = "";

		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");

			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText, "UTF-8");

		} catch (Exception ex) {
			LOG.error("Error al Encriptar Texto", ex);
			return null;
		}
		return base64EncryptedString;
	}

	/**
	 * Lee un fichero y lo guarda en un String
	 *
	 * @param rutaArchivo ruta del archivo
	 * @return archivo leido en un string
	 * @throws java.io.FileNotFoundException
	 */
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

	/**
	 * Lee un fichero y lo guarda en un String
	 *
	 * @param texto              ruta del archivo
	 * @param mapaLlaveReemplazo mapa que contiene la llave que se va a reemplzar y
	 *                           el texto por el cual se va a reemplazar
	 * @return archivo leido en un string
	 * @throws java.io.FileNotFoundException
	 */
	public static String reemplazarTexto(String texto, Map<String, String> mapaLlaveReemplazo)
			throws FileNotFoundException, IOException {
		for (String llave : mapaLlaveReemplazo.keySet()) {
			texto = texto.replace(llave, mapaLlaveReemplazo.get(llave));
		}

		return texto;
	}

	/**
	 * Arma una tabla HTML a partir de una lista de Cabeceras y una matriz de datos
	 *
	 * @param cabeceras etiquetas que van en el header de la tabla
	 * @param datos     texto que va en las columnas del cuerpo de la tabla, cada
	 *                  registro de la lista interna es un row
	 * @return tabla html
	 * @throws java.io.FileNotFoundException
	 */
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

	/**
	 * Valida caracteres especiales
	 *
	 * @param cadenaValidar
	 * @return
	 */
	public static boolean validarCaracteres(String cadenaValidar) {
		Pattern mask = Pattern.compile(ConstantesValidaciones.EXPRESION_REGULAR_DE_TEXTO_INGRESADO);
		return mask.matcher(cadenaValidar).matches();
	}

	/**
	 * Valida Direcciones
	 *
	 * @param cadenaValidar
	 * @return
	 */
	public static boolean validarDirecciones(String cadenaValidar) {
		Pattern mask = Pattern.compile(ConstantesValidaciones.EXPRESION_REGULAR_DE_DIRECCIONES);
		return mask.matcher(cadenaValidar).matches();
	}

	/**
	 * Limpiar los espacios de un texto
	 *
	 * @param text
	 * @return
	 */
	public static String limpiarEspacios(String text) {
		return text.replaceAll("\\s", "");
	}

	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}

	/*
	 * Permite validar un rango de fechas en meses
	 */
	public static boolean esValidoRangoFecha(String fechaInicio, String fechaFin, int calendarMeses) {
		boolean result = false;
		try {
			Date dateInicio = convertirStringToDate(fechaInicio, "dd/MM/yyyy");
			Date dateFin = convertirStringToDate(fechaFin, "dd/MM/yyyy");

			if (dateInicio != null && dateFin != null) {
				int dias = (int) ((dateFin.getTime() - dateInicio.getTime()) / 86400000);
				if (dias < 0) {
					dias = dias * (-1);
				}

				int mul = calendarMeses * 30;

				if (dias <= mul) {
					result = true;
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return result;
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
}