package com.proyectos.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyectos.model.ArchivoTB;
import com.proyectos.service.IArchivosService;
import com.proyectos.service.IReportesService;

@RestController
@RequestMapping("/music-room/reporte-archivo")
public class ControladorRestReportesArchivos {

	@Autowired
	IReportesService reporteService;

	@Autowired
	IArchivosService archivoService;

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
