package com.proyectos.service.impl;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IArchivoDao;
import com.proyectos.dao.ISalaDao;
import com.proyectos.dao.IUsuarioDao;
import com.proyectos.model.SalaTB;
import com.proyectos.model.UsuarioTB;
import com.proyectos.service.ISalaService;
import com.proyectos.util.ConstantesTablasNombre;
import com.proyectos.util.ConstantesValidaciones;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SalaServiceImpl implements ISalaService {

	@Autowired
	private ISalaDao salaDAO;
	
	@Autowired
	private IArchivoDao archivoDAO;

	@Autowired
	private IUsuarioDao usuarioDAO;

	@Override
	public List<SalaTB> consultarTodos() {
		return salaDAO.consultarTodos();
	}

	@Override
	public List<SalaTB> consultarPorFiltros(SalaTB salaFiltro) {
		return salaDAO.consultarPorFiltros(salaFiltro);
	}

	@Override
	public SalaTB consultarPorId(long idSala) {
		return salaDAO.consultarPorId(idSala);
	}

	@Transactional
	@Override
	public SalaTB crear(SalaTB sala) {
		
		
		sala.setIdSala(salaDAO.obtenerConsecutivo(ConstantesTablasNombre.MRA_SALA_TB));
		return salaDAO.crear(sala);
	}

	@Transactional
	@Override
	public SalaTB modificar(SalaTB sala) {
		return salaDAO.modificar(sala);
	}

	@Transactional
	@Override
	public void eliminar(long idSala) {
		salaDAO.eliminar(idSala);
	}

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
