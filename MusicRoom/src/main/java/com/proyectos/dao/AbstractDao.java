package com.proyectos.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.proyectos.model.ConsecutivoTB;

public abstract class AbstractDao<T extends Serializable> {

	private Class<T> clazz;

	@PersistenceContext
	EntityManager entityManager;

	public long obtenerConsecutivo(String tabla) {

		// TODO: OPCION 1
		ConsecutivoTB consecutivo = entityManager.find(ConsecutivoTB.class, tabla);
		long idActual = consecutivo.getValor();
		consecutivo.setValor(idActual + 1);
		entityManager.merge(consecutivo);

		return idActual;

		/*
		 * TODO: OPCION 2
		 * 
		 * // PARAMETROS Map<String, Object> pamameters = new HashMap<>();
		 * 
		 * // QUERY StringBuilder JPQL = new
		 * StringBuilder("SELECT c.valor FROM ConsecutivoTB c WHERE c.tabla = :TABLA ");
		 * pamameters.put("TABLA", tabla); // END QUERY
		 * 
		 * TypedQuery<Long> query = entityManager.createQuery(JPQL.toString(),
		 * Long.class); pamameters.forEach((k, v) -> query.setParameter(k, v)); Long
		 * idActual2 = query.getSingleResult();
		 * 
		 * entityManager.merge(entity);
		 * 
		 * return idActual;
		 */
	}

	public final void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T findOne(long id) {
		return entityManager.find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	public void create(T entity) {
		entityManager.persist(entity);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}

	public void deleteById(long entityId) {
		T entity = findOne(entityId);
		entityManager.remove(entity);
	}
}