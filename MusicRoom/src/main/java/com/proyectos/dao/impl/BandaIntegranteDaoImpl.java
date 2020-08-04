package com.proyectos.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.IBandaIntegranteDao;
import com.proyectos.dao.jpa.IBandaJPARepoDao;
import com.proyectos.dao.jpa.IIntegranteJPARepoDao;
import com.proyectos.model.BandaIntegranteTB;

@Repository
public class BandaIntegranteDaoImpl extends AbstractDao<BandaIntegranteTB> implements IBandaIntegranteDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IBandaJPARepoDao bandaJPADAO;

	@Autowired
	private IIntegranteJPARepoDao integranteJPADAO;

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private BandaIntegranteTB colocarValoresDefecto(BandaIntegranteTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

	@Override
	public BandaIntegranteTB consultarPorIds(long idBanda, long idIntegrante) {
		return consultarPorIds(idBanda, idIntegrante);
	}

	@Override
	public List<BandaIntegranteTB> consultarPorIdBanda(long idBanda) {
		return consultarPorIdBanda(idBanda);
	}

	@Override
	public List<BandaIntegranteTB> consultarPorIdIntegrante(long idIntegrante) {
		return consultarPorIdIntegrante(idIntegrante);
	}

	@Override
	public BandaIntegranteTB crear(BandaIntegranteTB bandaIntegrante) {
		bandaIntegrante = colocarValoresDefecto(bandaIntegrante);
		super.update(bandaIntegrante);
		return bandaIntegrante;
	}

	@Override
	public void eliminar(long idBanda, long idIntegrante) {
		super.setClazz(BandaIntegranteTB.class);
		BandaIntegranteTB bandaIntegranteEliminar = this.consultarPorIds(idBanda, idIntegrante);
		deleteById(bandaIntegranteEliminar.getIdBandaIntegrante());
	}

	@Override
	public void eliminarIntegrantesPorBanda(long idBanda) {
		super.setClazz(BandaIntegranteTB.class);
		List<BandaIntegranteTB> listaBandasIntegranteEliminar = this.consultarPorIdBanda(idBanda);
		for (BandaIntegranteTB bandaIntegranteEliminar : listaBandasIntegranteEliminar) {
			deleteById(bandaIntegranteEliminar.getIdBandaIntegrante());
		}
	}

}
