package com.proyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectos.model.UsuarioTB;

public interface IUsuarioDao extends JpaRepository<UsuarioTB, Long> {

	// NATIVE SQL
	// @Query(value = "INSERT INTO consulta_examen(id_consulta, id_examen) VALUES
	// (:idConsulta, :idExamen)", nativeQuery = true)
	// Integer registrar(@Param("idConsulta") Integer idConsulta, @Param("idExamen")
	// Integer idExamen);

}
