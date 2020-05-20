package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.UbicacionTB;

@EnableTransactionManagement
public interface IUbicacionJPARepoDao extends JpaRepository<UbicacionTB, Integer> {
}
