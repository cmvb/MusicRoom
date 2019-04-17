package com.proyectos.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

public class PropertiesUtil {

	private static final Properties PROPERTIE;
	private static final Logger LOG = Logger.getLogger(PropertiesUtil.class.getName());

	static {
		PROPERTIE = new Properties();

		try (InputStream propertiesStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("properties/mensaje.properties")) {
			PROPERTIE.load(propertiesStream);

		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
		}
	}

	public static String getProperty(String key, Object... args) {
		String message = PROPERTIE.getProperty(key);

		if (args != null && args.length > 0) {
			message = String.format(message, args);
		}

		return message;
	}

	public static String getPlantilla(String nombreArchivo) {
		InputStream propertiesStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("plantillas/html5/" + nombreArchivo + ".html");
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(propertiesStream, writer, "UTF-8");
		} catch (IOException ex) {
			LOG.warning(ex.toString());
		}
		String plantilla = writer.toString();

		return plantilla;
	}

}
