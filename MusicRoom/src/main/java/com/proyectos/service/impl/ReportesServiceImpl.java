package com.proyectos.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IUsuarioDao;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.IReportesService;
import com.proyectos.util.ConstantesValidaciones;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportesServiceImpl implements IReportesService {

	@Autowired
	private IUsuarioDao usuarioDAO;

	@Override
	public byte[] generarReporteEJM(String nombreArchivoJasper) {
		byte[] data = null;
		List<UsuarioTB> listaUsuarios = usuarioDAO.consultarTodos();
		String urlJasper = ConstantesValidaciones.RUTA_JASPER_REPORTS_USUARIO + nombreArchivoJasper;

		try {
			File file = new ClassPathResource(urlJasper).getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), null,
					new JRBeanCollectionDataSource(listaUsuarios));
			data = JasperExportManager.exportReportToPdf(print);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

}
