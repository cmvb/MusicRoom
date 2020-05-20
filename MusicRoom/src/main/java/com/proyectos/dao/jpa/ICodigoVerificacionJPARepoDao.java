package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.CodigoVerificacionTB;

@EnableTransactionManagement
public interface ICodigoVerificacionJPARepoDao extends JpaRepository<CodigoVerificacionTB, Integer> {
}
