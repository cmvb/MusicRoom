package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.SesionTB;

@EnableTransactionManagement
public interface ISesionJPARepoDao extends JpaRepository<SesionTB, Integer> {
}
