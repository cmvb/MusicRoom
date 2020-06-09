package com.proyectos.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyectos.dao.AbstractDao;
import com.proyectos.dao.IUbicacionDao;
import com.proyectos.dao.jpa.IUbicacionJPARepoDao;
import com.proyectos.enums.ETipoUbicacion;
import com.proyectos.model.UbicacionTB;

@Repository
public class UbicacionDaoImpl extends AbstractDao<UbicacionTB> implements IUbicacionDao {

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	@Autowired
	private IUbicacionJPARepoDao ubicacionJPADAO;

	@Override
	public List<UbicacionTB> consultarTodos() {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UbicacionTB t WHERE 1 = 1 ");
		// Q. Order By
		JPQL.append(" ORDER BY t.idUbicacion");
		// END QUERY

		TypedQuery<UbicacionTB> query = em.createQuery(JPQL.toString(), UbicacionTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public List<UbicacionTB> consultarPorTipo(int tipoUbicacion) {
		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UbicacionTB t WHERE 1 = 1 ");
		if (tipoUbicacion == ETipoUbicacion.PAIS.ordinal()) {
			// Q. País
			JPQL.append("AND t.codigoPais IS NOT NULL ");
			JPQL.append("AND t.codigoDepartamento IS NULL ");
			JPQL.append("AND t.codigoCiudad IS NULL ");
		} else if (tipoUbicacion == ETipoUbicacion.DEPARTAMENTO.ordinal()) {
			// Q. Departamento
			JPQL.append("AND t.codigoPais IS NOT NULL ");
			JPQL.append("AND t.codigoDepartamento IS NOT NULL ");
			JPQL.append("AND t.codigoCiudad IS NULL ");
		} else if (tipoUbicacion == ETipoUbicacion.CIUDAD.ordinal()) {
			// Q. Ciudad
			JPQL.append("AND t.codigoPais IS NOT NULL ");
			JPQL.append("AND t.codigoDepartamento IS NOT NULL ");
			JPQL.append("AND t.codigoCiudad IS NOT NULL ");
		}
		// Q. Order By
		JPQL.append(" ORDER BY t.idUbicacion");
		// END QUERY

		TypedQuery<UbicacionTB> query = em.createQuery(JPQL.toString(), UbicacionTB.class);

		return tipoUbicacion > 2 ? new ArrayList<>() : query.getResultList();
	}

	@Override
	public List<UbicacionTB> consultarPorFiltros(UbicacionTB ubicacionFiltro) {
		// PARAMETROS
		Map<String, Object> pamameters = new HashMap<>();

		// QUERY
		StringBuilder JPQL = new StringBuilder("SELECT t FROM UbicacionTB t WHERE 1 = 1 ");
		// Q. País
		if (StringUtils.isNotBlank(ubicacionFiltro.getCodigoPais())) {
			JPQL.append("AND LOWER(t.codigoPais) = LOWER(:PAIS) ");
			pamameters.put("PAIS", ubicacionFiltro.getCodigoPais());
		}
		// Q. Departamento
		if (StringUtils.isNotBlank(ubicacionFiltro.getCodigoDepartamento())) {
			JPQL.append("AND LOWER(t.codigoDepartamento) = LOWER(:DEPARTAMENTO) ");
			pamameters.put("DEPARTAMENTO", ubicacionFiltro.getCodigoDepartamento());
		}
		// Q. Ciudad
		if (StringUtils.isNotBlank(ubicacionFiltro.getCodigoCiudad())) {
			JPQL.append("AND LOWER(t.codigoCiudad) = LOWER(:CIUDAD) ");
			pamameters.put("CIUDAD", ubicacionFiltro.getCodigoCiudad());
		}
		// Q. Tipo Ubicación
		if (ubicacionFiltro.getTipoUbicacion() >= 0) {
			JPQL.append("AND t.tipoUbicacion = :TIPO_UBICACION ");
			pamameters.put("TIPO_UBICACION", ubicacionFiltro.getTipoUbicacion());
		}

		// Q. Order By
		JPQL.append(" ORDER BY t.idUbicacion");
		// END QUERY

		TypedQuery<UbicacionTB> query = em.createQuery(JPQL.toString(), UbicacionTB.class);
		pamameters.forEach((k, v) -> query.setParameter(k, v));

		return query.getResultList();
	}

	@Override
	public UbicacionTB consultarPorId(long idUbicacion) {
		super.setClazz(UbicacionTB.class);
		return super.findOne(idUbicacion);
	}

	@Override
	public UbicacionTB crear(UbicacionTB ubicacion) {
		ubicacion = colocarValoresDefecto(ubicacion);
		super.create(ubicacion);
		return ubicacion;
	}

	@Override
	public UbicacionTB modificar(UbicacionTB ubicacion) {
		ubicacion = colocarValoresDefecto(ubicacion);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		String fechaActualizaStr = sdf.format(ubicacion.getFechaActualiza());
		ETipoUbicacion tipoUbicacionGuardar = ETipoUbicacion.values()[ubicacion.getTipoUbicacion()];

		// Ubicacion Anterior
		UbicacionTB ubicacionOld = this.consultarPorId(ubicacion.getIdUbicacion());

		// Actualizar registros relacionados
		StringBuilder SQL = new StringBuilder("UPDATE musicroom.mra_ubicacion_tb ");
		SQL.append("SET usuario_actualiza = '" + ubicacion.getUsuarioActualiza() + "', ");
		SQL.append("fecha_actualiza = '" + fechaActualizaStr + "', ");

		switch (tipoUbicacionGuardar) {
		case PAIS:
			SQL.append("ubi_codigo_pais = '" + ubicacion.getCodigoPais() + "', ");
			SQL.append("ubi_nombre_pais = '" + ubicacion.getNombrePais() + "' ");

			SQL.append("WHERE LOWER(ubi_codigo_pais) = LOWER('" + ubicacionOld.getCodigoPais() + "') ");
			SQL.append("AND LOWER(ubi_nombre_pais) = LOWER('" + ubicacionOld.getNombrePais() + "') ");

			break;
		case DEPARTAMENTO:
			SQL.append("ubi_codigo_departamento = '" + ubicacion.getCodigoDepartamento() + "', ");
			SQL.append("ubi_nombre_departamento = '" + ubicacion.getNombreDepartamento() + "' ");

			SQL.append("WHERE LOWER(ubi_codigo_departamento) = LOWER('" + ubicacionOld.getCodigoDepartamento() + "') ");
			SQL.append("AND LOWER(ubi_nombre_departamento) = LOWER('" + ubicacionOld.getNombreDepartamento() + "') ");

			break;
		case CIUDAD:
			SQL.append("ubi_codigo_ciudad = '" + ubicacion.getCodigoCiudad() + "', ");
			SQL.append("ubi_nombre_ciudad = '" + ubicacion.getNombreCiudad() + "' ");

			SQL.append("WHERE LOWER(ubi_codigo_ciudad) = LOWER('" + ubicacionOld.getCodigoCiudad() + "') ");
			SQL.append("AND LOWER(ubi_nombre_ciudad) = LOWER('" + ubicacionOld.getNombreCiudad() + "') ");

			break;
		}
		int registrosActualizados = em.createNativeQuery(SQL.toString()).executeUpdate();

		// super.update(ubicacion);
		return ubicacion;
	}

	@Override
	public void eliminar(long idUbicacion) {
		super.setClazz(UbicacionTB.class);
		deleteById(idUbicacion);
	}

	@Override
	public long obtenerConsecutivo(String tabla) {
		return super.obtenerConsecutivo(tabla);
	}

	private UbicacionTB colocarValoresDefecto(UbicacionTB entidad) {
		if (entidad.getFechaCreacion() == null) {
			entidad.setFechaCreacion(new Date());
		}
		entidad.setFechaActualiza(new Date());
		entidad.setUsuarioCreacion("SYSTEM");
		entidad.setUsuarioActualiza("SYSTEM");

		return entidad;
	}

}
